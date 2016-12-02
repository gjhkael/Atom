#!/usr/bin/env bash
FWDIR="$(cd "`dirname "$0"`"/..; pwd)"

if [ -z "$ATOM_ENV_LOADED" ]; then
  export ATOM_ENV_LOADED=1

  # Returns the parent of the directory this script lives in.
  parent_dir="$(cd "`dirname "$0"`"/..; pwd)"

  user_conf_dir="${ATOM_CONF_DIR:-"$parent_dir"/conf}"

  if [ -f "${user_conf_dir}/atom-env.sh" ]; then
    # Promote all variable declarations to environment (exported) variables
    set -a
    . "${user_conf_dir}/atom-env.sh"
    set +a
  fi
fi

# Setting ATOM_SCALA_VERSION if not already set.

if [ -z "$ATOM_SCALA_VERSION" ]; then

    ASSEMBLY_DIR2="$FWDIR/assembly/target/scala-2.11"
    ASSEMBLY_DIR1="$FWDIR/assembly/target/scala-2.10"

    if [[ -d "$ASSEMBLY_DIR2" && -d "$ASSEMBLY_DIR1" ]]; then
        echo -e "Presence of build for both scala versions(SCALA 2.10 and SCALA 2.11) detected." 1>&2
        echo -e 'Either clean one of them or, export ATOM_SCALA_VERSION=2.11 in atom-env.sh.' 1>&2
        exit 1
    fi

    if [ -d "$ASSEMBLY_DIR2" ]; then
        export ATOM_SCALA_VERSION="2.11"
    else
        export ATOM_SCALA_VERSION="2.10"
    fi
fi
