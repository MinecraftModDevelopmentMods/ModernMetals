"""Reject missing/old public dependencies before any disposable world is loaded."""
import hashlib
import json
import subprocess
import urllib.request
from run_runtime_checks import FORGE, prepare, write_json

OLD = {
    'base':('base-metals-240967','2323100','f919cef794c73f02758bbd967346dba0b53f6383153c7e29bd79f211cda8c319'),
    'previous_base':('base-metals-240967','9280029','8bb1823592dcd558d17c4848ae917266fe7197b754ca628297df837cfb3d20a3'),
    'os':('mmd-orespawn-245586','2533153','d964148c20175e16ccefab02ae43a181a50dc08a1d636a77e59cfe8c8ea8d41a'),
    'previous_os':('mmd-orespawn-245586','9276430','4d4975421feebf17505056306d06b0344d7a4553b6275885d7c5c5065b0348a6')
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
    modern_owner = 'modernmetals (Modern Metals)'
    base_owner = 'basemetals (Base Metals)'
    # Forge checks Base Metals first. Both mods require the same OreSpawn minimum.
    scenarios = {
        'missing-base': ([orespawn, modern], modern_owner, 'basemetals', '2.6.0.110021'),
        'old-base': ([old['base'], orespawn, modern], modern_owner, 'basemetals', '2.6.0.110021'),
        'previous-base': ([old['previous_base'], orespawn, modern], modern_owner, 'basemetals', '2.6.0.110021'),
        'missing-orespawn': ([base, modern], base_owner, 'orespawn', '4.1.6.110021'),
        'old-orespawn': ([base, old['os'], modern], base_owner, 'orespawn', '4.1.6.110021'),
        'previous-orespawn': ([base, old['previous_os'], modern], base_owner, 'orespawn', '4.1.6.110021'),
    }
    result={}
    for name, (jars, owner, modid, version) in scenarios.items():
        target=output/name;prepare(target,template,jars)
        log=target/'rejection-console.log'
        with log.open('wb') as stream:
            subprocess.run([str(java),'-Xmx1G','-jar',FORGE,'nogui'],cwd=target,stdout=stream,stderr=subprocess.STDOUT,timeout=90)
        text=log.read_text(errors='replace')
        assert f'MissingModsException: Mod {owner} requires [{modid}@[{version},' in text, log
        assert not (target/'world/level.dat').exists(),'Incompatible dependencies reached world loading'
        result[name]={'rejected_before_world':True,'requirement_owner':owner,'required_mod':modid,'minimum':version}
        print(name,'PASS',flush=True)
    write_json(output/'dependency-requirements.json',result)
