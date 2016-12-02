#!/usr/bin/env bash

this="${BASH_SOURCE:-$0}"
common_bin="$(cd -P -- "$(dirname -- "$this")" && pwd -P)"
script="$(basename -- "$this")"
this="$common_bin/$script"

# convert relative path to absolute path
config_bin="`dirname "$this"`"
script="`basename "$this"`"
config_bin="`cd "$config_bin"; pwd`"
this="$config_bin/$script"

export ATOM_PREFIX="`dirname "$this"`"/..
export ATOM_HOME="${ATOM_PREFIX}"
export ATOM_CONF_DIR="${ATOM_CONF_DIR:-"$ATOM_HOME/conf"}"
