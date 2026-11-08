"""Exercise the fantasy mode handshake over a real loopback Forge connection."""
import argparse
import json
import socket
import subprocess
import sys
import time
from pathlib import Path

from run_runtime_checks import FORGE, ROOT, dependency, digest, prepare, write_json


def check(args, server_mode, client_mode):
    output = args.output / f"{server_mode}-{client_mode}"
    server = output / "server"
    specs = json.loads((ROOT / "gradle/dependencies.json").read_text())
    jars = [dependency(spec, args.mirror) for spec in specs if spec.get("runtime")]
    prepare(server, args.template, jars + [args.server_jar])
    with socket.socket() as reservation:
        reservation.bind(("127.0.0.1", 0))
        port = reservation.getsockname()[1]
    properties = server / "server.properties"
    properties.write_text(properties.read_text().replace("server-port=0", f"server-port={port}"))
    config = server / "config/ModernMetals.cfg"
    config.parent.mkdir()
    config.write_text(f"general {{\n    S:contentMode={server_mode}_fantasy\n}}\n")
    console = server / "console.log"
    mismatch = server_mode != client_mode
    rejection = args.incompatible_build or mismatch
    with console.open("wb") as log, subprocess.Popen(
        [str(args.java), "-Xmx2G", "-jar", FORGE, "nogui"], cwd=server,
        stdin=subprocess.PIPE, stdout=log, stderr=subprocess.STDOUT
    ) as process:
        try:
            deadline = time.monotonic() + 90
            while 'For help, type "help"' not in console.read_text(errors="replace"):
                if process.poll() is not None or time.monotonic() >= deadline:
                    raise AssertionError(f"Dedicated server did not start: {console}")
                time.sleep(0.25)
            command = [sys.executable, str(Path(__file__).with_name("run_packaged_client.py")),
                       "--java", str(args.java), "--cache", str(args.cache), "--installer", str(args.installer),
                       "--template", str(args.template), "--output", str(output / "client"),
                       "--mod-jar", str(args.client_jar), "--remote-port", str(port)]
            if args.mirror:
                command += ["--mirror", str(args.mirror)]
            if client_mode == "low":
                command.append("--low-fantasy")
            if args.incompatible_build:
                command += ["--expected-disconnect", "Use the same Modern Metals build"]
            elif mismatch:
                command += ["--expected-disconnect", f"The server uses {server_mode.title()} Fantasy, "
                            f"but your game uses {client_mode.title()} Fantasy"]
            subprocess.run(command, check=True, timeout=360)
        finally:
            if process.poll() is None:
                try:
                    process.communicate(b"stop\n", timeout=45)
                except subprocess.TimeoutExpired:
                    process.kill()
                    process.wait()
                    raise
    assert process.returncode == 0, console
    assert ("ModernCheck joined the game" in console.read_text(errors="replace")) == (not rejection), console
    write_json(output / "receipt.json", {
        "passed": True, "server_mode": server_mode, "client_mode": client_mode,
        "server_sha256": digest(args.server_jar), "client_sha256": digest(args.client_jar),
        "joined": not rejection,
    })


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ("java", "cache", "installer", "template", "output", "server-jar", "client-jar"):
        parser.add_argument("--" + name, required=True, type=lambda path: Path(path).resolve())
    parser.add_argument("--mirror", type=lambda path: Path(path).resolve())
    parser.add_argument("--scenario", action="append", choices=["high-high", "high-low", "low-high", "low-low"])
    parser.add_argument("--incompatible-build", action="store_true", help="Expect a missing mode advertisement.")
    args = parser.parse_args()
    for scenario in args.scenario or ["high-low", "low-high", "high-high", "low-low"]:
        check(args, *scenario.split("-"))


if __name__ == "__main__":
    main()
