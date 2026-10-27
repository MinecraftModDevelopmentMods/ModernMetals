"""Qualify native generation, migrations, switches and saved worlds in disposable servers."""
import argparse
import copy
import json
from pathlib import Path
from run_runtime_checks import ROOT, dependency, digest, prepare, run, write_json

RULE='modernmetals:legacy/'
SELECTOR='orespawn:all_except_nether_end'
FIELDS=['Aluminum','AluminumBrass','Beryllium','Boron','Cadmium','Chromium','GalvanizedSteel',
        'Iridium','Magnesium','Manganese','Nichrome','Osmium','Plutonium','Rutile','StainlessSteel',
        'Tantalum','Thorium','Titanium','Tungsten','Uranium','Zirconium']

def fingerprint(directory):
    return {str(p.relative_to(directory)):digest(p) for p in directory.rglob('*') if p.is_file()}

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    for name in ('java','template','output'):parser.add_argument('--'+name,type=Path,required=True)
    parser.add_argument('--mirror',type=Path)
    args=parser.parse_args()
    args.output.mkdir(parents=True,exist_ok=False)
    specs=json.loads((ROOT/'gradle/dependencies.json').read_text())
    jars=[dependency(s,args.mirror) for s in specs if s.get('runtime')]
    jars += [ROOT/'build/libs/ModernMetals-2.5.1.110021.jar',ROOT/'build/test-mods/modernmetals-probe-packaged.jar']
    native=json.loads((ROOT/'build/reports/native-provider.json').read_text())
    stock=json.loads((ROOT/'tools/validation/legacy110/fixtures/orespawn3-modernmetals.json').read_text())

    def setup(name,world=None):
        target=args.output/name
        prepare(target,args.template,jars,world)
        return target
    def check(target,expected=None,properties=None,sample=False,saved=False):
        properties=dict(properties or {})
        if expected:
            write_json(target/'expected.json',expected)
            properties['modern.expected']=str((target/'expected.json').resolve())
        if saved:properties['modern.saved']='true'
        first=run(args.java,target,'first',{**properties,**({'modern.sample':'true'} if sample else {})})
        run(args.java,target,'reload',properties)
        assert not first['profile'].get('terrain_dimensions'), 'Ore-only mods enabled terrain replacement'
        return first

    fresh=setup('native-fresh')
    (fresh/'config').mkdir(exist_ok=True)
    (fresh/'config/BaseMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n',encoding='utf-8')
    report=check(fresh,{'ores':native['ores']},properties={'modern.lowFantasy':'false'},sample=True,saved=True)
    base_profile=report['profile']
    assert len([key for key in base_profile['ores'] if key.startswith('modernmetals:')])==17
    before=fingerprint(fresh/'world')

    disabled=setup('materials-disabled')
    config=disabled/'config/ModernMetals.cfg';config.parent.mkdir(parents=True,exist_ok=True)
    config.write_text('metals {\n'+''.join(f' B:Enable{name}=false\n' for name in ('Boron','Beryllium','Thorium','Titanium'))+'}\n',encoding='utf-8')
    expected={k:v for k,v in native['ores'].items() if not any(k.endswith('/'+x+'_ore') for x in ('boron','beryllium','thorium','titanium'))}
    result=check(disabled,{'ores':expected},{'modern.disabled':'boron,beryllium,thorium,titanium'})
    assert len([k for k in result['profile']['ores'] if k.startswith('modernmetals:')])==13

    all_disabled=setup('all-materials-disabled')
    config=all_disabled/'config/ModernMetals.cfg';config.parent.mkdir(parents=True,exist_ok=True)
    config.write_text('metals {\n'+''.join(f' B:Enable{name}=false\n' for name in FIELDS)+'}\n',encoding='utf-8')
    check(all_disabled,properties={'modern.disabled':','.join(n.lower() for n in FIELDS),'modern.allDisabled':'true'})

    titanium=setup('titanium-enabled')
    override=copy.deepcopy(native);override['ores'][RULE+'titanium_ore']['enabled']=True
    write_json(titanium/'config/modernmetals-orespawn.json',override)
    check(titanium,{'ores':override['ores']},{'modern.titanium':'true'},sample=True,saved=True)

    no_ores=setup('ores-disabled')
    override=copy.deepcopy(native)
    for ore in override['ores'].values():ore['enabled']=False
    write_json(no_ores/'config/modernmetals-orespawn.json',override)
    result=check(no_ores,{'ores':override['ores']},{'modern.noOres':'true'},sample=True)
    assert all(not counts for counts in result['oreSamples'].values())

    custom=copy.deepcopy(stock)
    custom['spawns']['aluminum_ore']['parameters'].update(size=5,variation=0,frequency=2.75,minHeight=10,maxHeight=50)
    custom['spawns']['thorium_ore']['enabled']=False
    custom['spawns']['boron_ore']['dimensions']=[0]
    target=setup('os3-import',fresh/'world')
    # An existing OS4 profile is authoritative; remove only this disposable copy's snapshot
    # to exercise an old world which has not yet been imported by OreSpawn 4.
    snapshot=target/'world/serverconfig/orespawn-worldgen.json'
    assert snapshot.resolve().is_relative_to(args.output.resolve())
    snapshot.unlink()
    legacy=target/'config/orespawn3/modernmetals.json';write_json(legacy,custom)
    legacy_hash=digest(legacy)
    expected={'ores':{RULE+'aluminum_ore':{'dimension_selectors':{SELECTOR:{'frequency':2.75,'min_y':10,'max_y':49,'quantity':5}}},
                      RULE+'thorium_ore':{'enabled':False},RULE+'boron_ore':{'dimensions':{'minecraft:overworld':{'enabled':True}}}}}
    check(target,expected,saved=True)
    assert digest(legacy)==legacy_hash
    override_path=target/'config/modernmetals-orespawn.json'
    assert override_path.is_file()
    stable={p:digest(p) for p in (legacy,override_path,target/'world/serverconfig/orespawn-worldgen.json')}
    run(args.java,target,'later',{'modern.expected':str((target/'expected.json').resolve()),'modern.saved':'true'})
    assert all(digest(p)==value for p,value in stable.items())

    os1=setup('os1-import')
    legacy=os1/'config/orespawn/modernmetals.json'
    write_json(legacy,{'dimensions':[{'dimension':'0','ores':[{'blockID':'modernmetals:aluminum_ore','size':4,'variation':0,'frequency':1.5,'minHeight':8,'maxHeight':40}]}]})
    legacy_hash=digest(legacy)
    result=check(os1)
    imported=[v for v in result['profile']['ores'].values() if v.get('block')=='modernmetals:aluminum_ore']
    assert len(imported)==1 and '1.5' in json.dumps(imported)
    assert digest(legacy)==legacy_hash

    existing=setup('existing-os4',fresh/'world')
    profile_path=existing/'world/serverconfig/orespawn-worldgen.json'
    profile=json.loads(profile_path.read_text())
    profile['ores'][RULE+'aluminum_ore']['dimension_selectors'][SELECTOR]['frequency']=6.25
    write_json(profile_path,profile)
    override=copy.deepcopy(native)
    override['ores'][RULE+'aluminum_ore']['dimension_selectors'][SELECTOR]['frequency']=9.0
    write_json(existing/'config/modernmetals-orespawn.json',override)
    write_json(existing/'config/orespawn3/modernmetals.json',custom)
    hashes={p:digest(p) for p in (profile_path,existing/'config/modernmetals-orespawn.json')}
    check(existing,{'ores':{RULE+'aluminum_ore':{'dimension_selectors':{SELECTOR:{'frequency':6.25}}}}},saved=True)
    assert all(digest(p)==value for p,value in hashes.items())
    future=setup('existing-override-new-world')
    write_json(future/'config/modernmetals-orespawn.json',override)
    write_json(future/'config/orespawn3/modernmetals.json',custom)
    check(future,{'ores':override['ores']})
    assert fingerprint(fresh/'world')==before,'A source fixture world changed'

    optional=setup('optional-integrations')
    for spec in specs:
        if spec['id'] in ('tconstruct','mantle','mekanism','mcmultipart','ic2'):
            import shutil
            jar=dependency(spec,args.mirror);shutil.copy2(jar,optional/'mods'/jar.name)
    check(optional,saved=True)
    low=setup('low-fantasy')
    (low/'config').mkdir(exist_ok=True)
    (low/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n',encoding='utf-8')
    (low/'config/BaseMetals.cfg').write_text('general {\n S:contentMode=high_fantasy\n}\n',encoding='utf-8')
    check(low,properties={'modern.lowFantasy':'true'},saved=True)
    switched=setup('high-to-low-existing-world',fresh/'world')
    (switched/'config').mkdir(exist_ok=True)
    (switched/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n',encoding='utf-8')
    run(args.java,switched,'reload',{'modern.lowFantasy':'true','modern.saved':'true'})
    custom_dimension=setup('ordinary-custom-dimension')
    check(custom_dimension,properties={'modern.customDimension':'true'},sample=True)
    low_optional=setup('low-fantasy-tinkers')
    (low_optional/'config').mkdir(exist_ok=True)
    (low_optional/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n',encoding='utf-8')
    for spec in specs:
        if spec['id'] in ('tconstruct','mantle'):
            jar=dependency(spec,args.mirror);shutil.copy2(jar,low_optional/'mods'/jar.name)
    check(low_optional,properties={'modern.lowFantasy':'true'},saved=True)
    disabled_integration=setup('tinkers-disabled')
    (disabled_integration/'config').mkdir(exist_ok=True)
    (disabled_integration/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n',encoding='utf-8')
    (disabled_integration/'config/BaseMetals.cfg').write_text('"mod integration" {\n B:tinkers_construct_integration=false\n}\n',encoding='utf-8')
    for spec in specs:
        if spec['id'] in ('tconstruct','mantle'):
            jar=dependency(spec,args.mirror);shutil.copy2(jar,disabled_integration/'mods'/jar.name)
    check(disabled_integration,properties={'modern.lowFantasy':'true','modern.disabledIntegrationTest':'true'},saved=True)
    disabled_materials_optional=setup('materials-disabled-with-tinkers')
    (disabled_materials_optional/'config').mkdir(exist_ok=True)
    (disabled_materials_optional/'config/ModernMetals.cfg').write_text('metals {\n'+''.join(f' B:Enable{name}=false\n' for name in ('Boron','Beryllium','Thorium','Titanium'))+'}\n',encoding='utf-8')
    for spec in specs:
        if spec['id'] in ('tconstruct','mantle'):
            jar=dependency(spec,args.mirror);shutil.copy2(jar,disabled_materials_optional/'mods'/jar.name)
    check(disabled_materials_optional,properties={'modern.disabled':'boron,beryllium,thorium,titanium'})
    from dependency_requirements import verify
    verify(args.java,args.template,args.output,dependency(next(s for s in specs if s['id']=='basemetals'),args.mirror),
           dependency(next(s for s in specs if s['id']=='orespawn'),args.mirror),ROOT/'build/libs/ModernMetals-2.5.1.110021.jar')
    assert fingerprint(fresh/'world')==before,'A source fixture world changed'
    write_json(args.output/'qualification.json',{'passed':True,'scenarios':16,'source_world_unchanged':True})
    print('MODERN_METALS_PACKAGED_SUITE_PASS',flush=True)

if __name__=='__main__':main()
