#!/usr/bin/env bash
usage="Usage:start-slave.sh <di-master-url> where <di-master-url> is like atom://localhost:7080"
if [ $# -lt 1 ]; then
    echo $usage
    echo Called as start-slave.sh $*
    exit 1
fi

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

. "$sbin/atom-config.sh"

MASTER=$1
shift

if [ "$ATOM_WORKER_PORT" = "" ]; then
    PORT_FLAG=
    PORT_NUM=
else
    PORT_FLAG="--port"
    PORT_NUM=$ATOM_WORK_PORT
fi

"$sbin"/atom-daemon.sh start com.ctrip.atom.deploy.worker.Worker $PORT_FLAG $PORT_NUM $MASTER "$@"
