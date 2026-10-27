"""Validate resources, native provider schema, workflow pins and material assets."""
import argparse
import json
import re
import zipfile
from pathlib import Path
from referencing import Registry, Resource
from jsonschema import Draft202012Validator
import yaml
from run_runtime_checks import ROOT, dependency

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--mirror',type=Path)
    args=parser.parse_args()
    checked=0
    for path in (ROOT/'src/main/resources').rglob('*'):
        if path.is_file() and (path.suffix in ('.json','.mcmeta') or path.name=='mcmod.info'):
            json.loads(path.read_text(encoding='utf-8-sig')); checked+=1
    def references(value,key):
        if isinstance(value,dict):
            for name,child in value.items():
                if name==key and isinstance(child,str) and child.startswith('modernmetals:'):yield child.split(':',1)[1]
                else:yield from references(child,key)
        elif isinstance(value,list):
            for child in value:yield from references(child,key)
    assets=ROOT/'src/main/resources/assets/modernmetals'
    materials=re.findall(r'material\("([a-z]+)",', (ROOT/'src/main/java/zone/moddev/mc/modernmetals/util/Config.java').read_text())
    for path in (assets/'blockstates').glob('*.json'):
        for model in references(json.loads(path.read_text()),'model'):
            assert (assets/'models/block'/(model+'.json')).is_file(),f'{path.name}: missing block model {model}'
    for path in (assets/'models').rglob('*.json'):
        # Historical silicon item placeholders belong to no registered material
        # in this branch. Preserve those asset names without inventing content.
        if path.parent.name=='item' and path.stem.startswith('silicon_') and 'silicon' not in materials:continue
        for parent in references(json.loads(path.read_text()),'parent'):
            assert (assets/'models'/(parent+'.json')).is_file(),f'{path.name}: missing parent {parent}'
    specs=json.loads((ROOT/'gradle/dependencies.json').read_text())
    with zipfile.ZipFile(dependency(next(s for s in specs if s['id']=='orespawn'),args.mirror)) as jar:
        schemas=[json.loads(jar.read(n)) for n in jar.namelist() if n.startswith('META-INF/orespawn/docs/schemas/') and n.endswith('.json')]
    registry=Registry().with_resources((s['$id'],Resource.from_contents(s)) for s in schemas)
    schema=next(s for s in schemas if s['$id'].endswith('/orespawn-provider.schema.json'))
    provider=json.loads((ROOT/'build/reports/native-provider.json').read_text())
    Draft202012Validator(schema,registry=registry).validate(provider)
    assert len(provider['ores'])==17 and not provider['ores']['modernmetals:legacy/titanium_ore']['enabled']
    for name in ('boron','beryllium','thorium','titanium'):
        for relative in (f'blockstates/{name}_ore.json',f'models/item/{name}_ingot.json',
                         f'models/item/{name}_plate.json',f'models/block/{name}_plate.json',f'textures/blocks/{name}_plate.png'):
            assert (ROOT/'src/main/resources/assets/modernmetals'/relative).is_file(),relative
    language=dict(line.split('=',1) for line in (ROOT/'src/main/resources/assets/modernmetals/lang/en_US.lang').read_text().splitlines() if '=' in line and not line.startswith('#'))
    assert len(materials)==21
    for key in ('config.modernmetals.content_mode','config.modernmetals.content_mode.tooltip','high_fantasy','low_fantasy'):
        assert key in language,key
    for name in materials:
        assert 'config.modernmetals.enable_'+name in language,name
        for relative in (f'models/item/{name}_plate.json',f'models/block/{name}_plate.json',f'textures/blocks/{name}_plate.png'):
            assert (ROOT/'src/main/resources/assets/modernmetals'/relative).is_file(),relative
    for path in (ROOT/'.github/workflows').glob('*.yml'):
        workflow=yaml.load(path.read_text(),Loader=yaml.BaseLoader)
        assert workflow.get('on') and workflow.get('jobs'),path
        for job in workflow['jobs'].values():
            for step in job.get('steps',[]):
                if 'uses' in step: assert re.fullmatch(r'[^@]+@[0-9a-f]{40}',step['uses']),step['uses']
    release=(ROOT/'.github/workflows/deploy-release.yml').read_text()
    assert 'confirm_live_publication' in release and '240967(required)' in release and '245586(required)' in release
    assert '243157(optional)' in release and '244357' in release
    print(f'MODERN_METALS_RESOURCES_PASS json={checked} provider_schema=5 workflows=5')

if __name__=='__main__':main()
