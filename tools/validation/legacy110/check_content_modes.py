"""Check Modern Metals-owned fantasy configuration using installed release jars."""
import argparse,json,shutil
from pathlib import Path
from run_runtime_checks import ROOT,dependency,prepare,run

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    for name in ('java','template','output'):parser.add_argument('--'+name,type=Path,required=True)
    parser.add_argument('--scenario',choices=['modern-low'])
    args=parser.parse_args()
    args.output.mkdir(parents=True,exist_ok=False)
    specs=json.loads((ROOT/'gradle/dependencies.json').read_text())
    jars=[dependency(s) for s in specs if s.get('runtime')]
    jars += [ROOT/'build/libs/ModernMetals-2.5.1.110021.jar',ROOT/'build/test-mods/modernmetals-probe-packaged.jar']
    cases=[('default-with-base-low',None,'low_fantasy',False),
           ('modern-low','low_fantasy','high_fantasy',True),
           ('modern-high-with-base-low','high_fantasy','low_fantasy',False),
           ('invalid-mode','invalid_value','low_fantasy',False)]
    completed=[]
    for name,modern,base,low in cases:
        if args.scenario and name!=args.scenario:continue
        target=args.output/name
        prepare(target,args.template,jars)
        (target/'config').mkdir()
        (target/'config/BaseMetals.cfg').write_text('general {\n S:contentMode='+base+'\n}\n')
        if modern is not None:
            (target/'config/ModernMetals.cfg').write_text('general {\n S:contentMode='+modern+'\n}\n')
        for phase in ('first','reload'):
            run(args.java,target,phase,{'modern.lowFantasy':str(low).lower(),'modern.saved':'true'})
        text=(target/'config/ModernMetals.cfg').read_text()
        assert 'S:contentMode='+('low_fantasy' if low else 'high_fantasy') in text
        completed.append(name)
    if not args.scenario:
        source=args.output/'default-with-base-low'/'world'
        # Copy only the disposable fixture; never run against its source directory.
        from run_packaged_suite import fingerprint
        before=fingerprint(source)
        switched=args.output/'high-to-low-existing-world'
        prepare(switched,args.template,jars,source)
        (switched/'config').mkdir()
        (switched/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n')
        run(args.java,switched,'reload',{'modern.lowFantasy':'true','modern.saved':'true'})
        assert fingerprint(source)==before
        completed.append(switched.name)
        optional=args.output/'modern-low-tinkers'
        prepare(optional,args.template,jars)
        (optional/'config').mkdir()
        (optional/'config/ModernMetals.cfg').write_text('general {\n S:contentMode=low_fantasy\n}\n')
        for spec in specs:
            if spec['id'] in ('tconstruct','mantle'):
                jar=dependency(spec);shutil.copy2(jar,optional/'mods'/jar.name)
        for phase in ('first','reload'):
            run(args.java,optional,phase,{'modern.lowFantasy':'true','modern.saved':'true'})
        completed.append(optional.name)
    (args.output/'qualification.json').write_text(json.dumps({'passed':True,'scenarios':completed},indent=2)+'\n')
    print('MODERN_METALS_OWNED_CONTENT_MODE_PASS',len(completed),flush=True)

if __name__=='__main__':main()
