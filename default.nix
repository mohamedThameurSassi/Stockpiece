{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "stockpiece-dev";

  buildInputs = [
    pkgs.go
    pkgs.nodejs_20
    pkgs.postgresql
  ];

  shellHook = ''
    echo "Available tools:"
    echo " - Go: $(go version)"
    echo " - Node: $(node -v)"
    echo " - npm: $(npm -v)"
    echo " - psql: $(psql --version)"
  '';
}

