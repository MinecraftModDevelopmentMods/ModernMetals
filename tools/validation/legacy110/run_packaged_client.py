"""Run installed release jars on the obfuscated Forge client, in a disposable game directory."""
import argparse,hashlib,json,os,shutil,subprocess,time,zipfile
from concurrent.futures import ThreadPoolExecutor
from pathlib import Path
from urllib.request import urlopen
from run_runtime_checks import ROOT,dependency,FORGE

def prepare_assets(output,cache,metadata):
    """Use this version's verified index without overwriting a shared Gradle index."""
    assets=output/'assets'
    index=assets/'indexes'/(metadata['id']+'.json')
    index.parent.mkdir(parents=True,exist_ok=True)
    if not index.is_file():
        with urlopen(metadata['url'],timeout=60) as response:index.write_bytes(response.read())
    assert hashlib.sha1(index.read_bytes()).hexdigest()==metadata['sha1'],'Minecraft asset index checksum mismatch'
    objects=json.loads(index.read_text())['objects']
    def stage(spec):
        sha=spec['hash'];relative=Path('objects')/sha[:2]/sha
        target=assets/relative
        if not target.is_file():
            target.parent.mkdir(parents=True,exist_ok=True)
            cached=cache/'caches/minecraft/assets'/relative
            if cached.is_file() and hashlib.sha1(cached.read_bytes()).hexdigest()==sha:
                shutil.copy2(cached,target)
            else:
                with urlopen(f'https://resources.download.minecraft.net/{sha[:2]}/{sha}',timeout=60) as response:
                    target.write_bytes(response.read())
        assert target.stat().st_size==spec['size'] and hashlib.sha1(target.read_bytes()).hexdigest()==sha,sha
    # Multiple names may share one content-addressed object.
    unique={spec['hash']:spec for spec in objects.values()}
    with ThreadPoolExecutor(max_workers=8) as pool:list(pool.map(stage,unique.values()))
    print('MODERN_METALS_CLIENT_ASSETS_VERIFIED',metadata['id'],len(unique),flush=True)
    return assets

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    for name in ('java','cache','installer','template','output'):parser.add_argument('--'+name,type=Path,required=True)
    parser.add_argument('--phase',choices=['first','reload'],default='first')
    args=parser.parse_args()
    if args.phase=='first':args.output.mkdir(parents=True,exist_ok=False)
    assert hashlib.sha256(args.installer.read_bytes()).hexdigest().upper()=='664393C5A924B8F06CFB953903ABCF9FE5E99ECBE6AF8C7EA217339C766E519C'
    with zipfile.ZipFile(args.installer) as jar:forge=json.loads(jar.read('install_profile.json'))['versionInfo']
    vanilla_root=args.cache/'caches/minecraftforge/forgegradle/mavenizer/caches/minecraft_tasks/1.10.2'
    vanilla=json.loads((vanilla_root/'version.json').read_text())
    assert hashlib.sha1((vanilla_root/'client.jar').read_bytes()).hexdigest()==vanilla['downloads']['client']['sha1']
    libraries={lib['name']:lib for lib in vanilla['libraries']}
    libraries.update({lib['name']:lib for lib in forge['libraries']})
    classpath=[]
    for coordinate,lib in libraries.items():
        if lib.get('clientreq') is False:continue
        if 'rules' in lib:
            allowed=False
            for rule in lib['rules']:
                if not rule.get('os') or rule['os'].get('name')=='windows':allowed=rule['action']=='allow'
            if not allowed:continue
        group,artifact,version,*classifier=coordinate.split(':')
        if group=='net.minecraftforge' and artifact=='forge':
            # The server distribution's manifest names its neighboring server jar.
            # Isolate the unchanged universal jar so it cannot shadow client classes.
            engine=args.output/'engine';engine.mkdir(exist_ok=True)
            shutil.copy2(args.template/FORGE,engine/FORGE)
            classpath.append(engine/FORGE);continue
        if 'natives' in lib and not lib.get('downloads',{}).get('artifact'):continue
        filename=artifact+'-'+version+('-'+classifier[0] if classifier else '')+'.jar'
        paths=list((args.cache/'caches/modules-2/files-2.1'/group/artifact/version).glob('*/'+filename))
        if not paths:
            path=args.template/'libraries'/group.replace('.','/')/artifact/version/filename
            if path.is_file():paths=[path]
        if not paths:raise FileNotFoundError(coordinate)
        classpath.append(paths[0])
    classpath.append(vanilla_root/'client.jar')
    if args.phase=='first':
        (args.output/'mods').mkdir()
        specs=json.loads((ROOT/'gradle/dependencies.json').read_text())
        jars=[dependency(s) for s in specs if s.get('runtime')]
        jars+=[ROOT/'build/libs/ModernMetals-2.5.1.110021.jar',ROOT/'build/test-mods/modernmetals-probe-packaged.jar']
        for jar in jars:shutil.copy2(jar,args.output/'mods'/jar.name)
    natives=args.cache/'caches/minecraftforge/forgegradle/slime-launcher/cache/net/minecraftforge/forge/1.10.2-12.18.3.2511/natives'
    assets=prepare_assets(args.output,args.cache,vanilla['assetIndex'])
    command=[str(args.java),'-Xmx3G',f'-Djava.library.path={natives}',f'-Dmodern.phase={args.phase}',
             '-Dmodern.saved=true','-Dmodern.client=true','-Dmodern.lowFantasy='+str(args.phase=='reload').lower(),
             '-cp',os.pathsep.join(map(str,classpath)),forge['mainClass'],
             '--username','ModernCheck','--uuid','00000000000000000000000000000001','--accessToken','0',
             '--version',forge['id'],'--gameDir',str(args.output),'--assetsDir',str(assets),'--assetIndex',vanilla['assetIndex']['id'],
             '--userType','legacy','--tweakClass','net.minecraftforge.fml.common.launcher.FMLTweaker','--versionType','Forge']
    started=time.time_ns()
    with (args.output/(args.phase+'-console.log')).open('wb') as log:
        result=subprocess.run(command,cwd=args.output,stdout=log,stderr=subprocess.STDOUT,timeout=300)
    for suffix in ('client','modern'):
        report=args.output/(args.phase+'-'+suffix+'.json')
        assert report.is_file() and report.stat().st_mtime_ns>started,report
        assert json.loads(report.read_text())['passed'],report
    assert result.returncode==0
    console=(args.output/(args.phase+'-console.log')).read_text(errors='replace')
    assert "Can't find the resource index file" not in console,'Minecraft assets were not loaded'
    print('MODERN_METALS_PACKAGED_CLIENT_PASS',args.phase,flush=True)

if __name__=='__main__':main()
