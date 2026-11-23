"""Read-only comparison of named Modern Metals states and stacks across 1.13/1.14."""
import argparse,json,sys
from pathlib import Path
sys.path.insert(0,str(Path(__file__).resolve().parents[1]/'port113'))
import verify_legacy_world as legacy

def named_states(level,unused):
    for section in level.get('Sections',[]):
        palette=section.get('Palette',[])
        if not any(str(p['Name']).startswith(legacy.PREFIX) for p in palette):continue
        locations=[(int(section['Y']),i) for i in range(4096)]
        for location,state in zip(locations,legacy.actual_states(level,locations)):
            if state[0].startswith(legacy.PREFIX):yield location,state

if __name__=='__main__':
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('source',type=Path);parser.add_argument('upgraded',type=Path)
    args=parser.parse_args();legacy.legacy_states=named_states
    result=legacy.compare(args.source,args.upgraded,{'blocks':{},'items':{}})
    print(json.dumps(result,indent=2))
    if result['errors']:sys.exit(1)
