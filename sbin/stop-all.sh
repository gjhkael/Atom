#!/usr/bin/env bash
sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"
pids=`ls $sbin/../pids/`
for i in $pids
  do
   pidfile=$sbin/../pids/$i
   cat $pidfile | xargs kill -9
   rm -rf $pidfile
  done
rm -rf sbin/../worker/*
