"""Reject missing/old public dependencies before any disposable world is loaded."""
import hashlib
import json
import subprocess
import urllib.request
from run_runtime_checks import FORGE, prepare, write_json

OLD = {
    'base':('base-metals-240967','2323100','f919cef794c73f02758bbd967346dba0b53f6383153c7e29bd79f211cda8c319'),
    'os':('mmd-orespawn-245586','2533153','d964148c20175e16ccefab02ae43a181a50dc08a1d636a77e59cfe8c8ea8d41a')
}

def verify(java,template,output,base,orespawn,modern):
    fixtures=output/'dependency-fixtures';fixtures.mkdir()
    old={}
    for key,(artifact,version,expected) in OLD.items():
        target=fixtures/f'{artifact}-{version}.jar'
        request=urllib.request.Request(f'https://www.cursemaven.com/curse/maven/{artifact}/{version}/{target.name}',headers={'User-Agent':'Mozilla/5.0'})
        with urllib.request.urlopen(request,timeout=45) as response:target.write_bytes(response.read())
        assert hashlib.sha256(target.read_bytes()).hexdigest()==expected,'Public fixture checksum mismatch'
        old[key]=target
    scenarios={
        'missing-base':([orespawn,modern],'basemetals','2.5.1.110021'),
        'old-base':([old['base'],orespawn,modern],'basemetals','2.5.1.110021'),
        'missing-orespawn':([base,modern],'orespawn','4.1.2.110021'),
        'old-orespawn':([base,old['os'],modern],'orespawn','4.1.2.110021')
    }
    result={}
    for name,(jars,modid,version) in scenarios.items():
        target=output/name;prepare(target,template,jars)
        log=target/'rejection-console.log'
        with log.open('wb') as stream:
            subprocess.run([str(java),'-Xmx1G','-jar',FORGE,'nogui'],cwd=target,stdout=stream,stderr=subprocess.STDOUT,timeout=90)
        text=log.read_text(errors='replace')
        assert ('MissingModsException' in text or 'Missing mandatory dependencies' in text) and modid in text and version in text,log
        assert not (target/'world/level.dat').exists(),'Incompatible dependencies reached world loading'
        result[name]={'rejected_before_world':True,'required_mod':modid,'minimum':version}
        print(name,'PASS',flush=True)
    write_json(output/'dependency-requirements.json',result)
