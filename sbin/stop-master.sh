#!/usr/bin/env bash

sbin=`dirname "$0"`
sbin=`cd "$sbin";pwd`
. "$sbin/atom-config.sh"

"$sbin"/atom-daemon.sh stop com.ctrip.atom.deploy.master.Master 1
