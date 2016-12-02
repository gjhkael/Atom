#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

ORIGINAL_ARGS="$@"

. "$sbin/atom-config.sh"

. "$ATOM_PREFIX/bin/load-atom-env.sh"

if [ "$ATOM_MASTER_PORT" = "" ]; then
  ATOM_MASTER_PORT=7077
fi

if [ "$ATOM_MASTER_IP" = "" ]; then
  ATOM_MASTER_IP=`hostname`
fi

"$sbin"/atom-daemon.sh start com.ctrip.atom.deploy.master.Master 1 --ip $ATOM_MASTER_IP --port
$SPARK_MASTER_PORT $ORIGINAL_ARGS
