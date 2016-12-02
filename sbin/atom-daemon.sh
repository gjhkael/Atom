#!/usr/bin/env bash

usage="Usage: spark-daemon.sh [--config <conf-dir>] (start|stop|status) <atom-command> <args...>"

if [ $# -le 1 ]; then
    echo $usage
    exit 1
fi

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

. "$sbin/atom-config.sh"

if [ "$1" == "--config" ]; then
    shift
    conf_dir="$1"
    if [ ! -d $conf_dir ]; then
        echo "Error: $conf_dir is not a directory"
    else
        export ATOM_CONF_DIR="$conf_dir"
    fi
    shift
fi

option=$1
shift
command=$1
shift

atom_rotate_log ()
{
    log=$1;
    num=5;
    if [ -n "$2" ]; then
        num=$2
    fi
    if [ -f "$log" ]; then # rotate logs
        while [ $num -gt 1 ]; do
            prev=`expr $num - 1`
            [ -f "$log.$prev" ] && mv "$log.$prev" "$log.$num"
            num=$prev
        done
        mv "$log" "$log.$num";
    fi
}

. "$ATOM_PREFIX/bin/load-atom-env.sh"

if [ "$ATOM_IDENT_STRING" = "" ]; then
    export ATOM_IDENT_STRING="$USER"
fi

export ATOM_PRING_LAUNCH_COMMAND="1"

#get log directory
if [ "$ATOM_LOG_DIR" = "" ]; then
    export ATOM_LOG_DIR="$ATOM_HOME/logs"
fi

mkdir -p "$ATOM_LOG_DIR"
touch "$ATOM_LOG_DIR"/.atom_test > /dev/null 2>&1
TEST_LOG_DIR=$?
if [ $TEST_LOG_DIR -eq 0 ]; then
    rm -f "$ATOM_LOG_DIR"/.atom_test
else
    echo "privilege error! $ATOM_LOG_DIR"
fi

if [ "$ATOM_PID_DIR" = "" ]; then
    ATOM_PID_DIR=/tmp
fi

log="$ATOM_LOG_DIR/atom-$ATOM_IDENT_STRING-$HOSTNAME.out"
pid="$ATOM_PID_DIR/atom-$ATOM_IDENT_STRING-$command.pid"

#Set default scheduling priority
if [ "$ATOM_NICENESS"="" ]; then
    export ATOM_NICENESS=0
fi

run_command() {
  mode="$1"
  shift

  mkdir -p "$ATOM_PID_DIR"

  if [ -f "$pid" ]; then
    TARGET_ID="$(cat "$pid")"
    if [[ $(ps -p "$TARGET_ID" -o comm=) =~ "java" ]]; then
      echo "$command running as process $TARGET_ID.  Stop it first."
      exit 1
    fi
  fi

  if [ "$ATOM_MASTER" != "" ]; then
    echo rsync from "$SPARK_MASTER"
    rsync -a -e ssh --delete --exclude=.svn --exclude='logs/*' --exclude='contrib/hod/logs/*' "$ATOM_MASTER/" "$ATOM_HOME"
  fi

  atom_rotate_log "$log"
  echo "starting $command, logging to $log"

  #nice -n :set the priority of the process,default is zero
  case "$mode" in
    (class)
      nohup nice -n "$ATOM_NICENESS" "$ATOM_PREFIX"/bin/atom-class $command "$@" >> "$log" 2>&1 < /dev/null &
      newpid="$!"
      ;;

    (submit)
      nohup nice -n "$ATOM_NICENESS" "$ATOM_PREFIX"/bin/atom-submit --class $command "$@" >> "$log" 2>&1 < /dev/null &
      newpid="$!"
      ;;

    (*)
      echo "unknown mode: $mode"
      exit 1
      ;;
  esac

  echo "$newpid" > "$pid"
   sleep 2
  # Check if the process has died; in that case we'll tail the log so the user can see
  if [[ ! $(ps -p "$newpid" -o comm=) =~ "java" ]]; then
    echo "failed to launch $command:"
    tail -2 "$log" | sed 's/^/  /'
    echo "full log in $log"
  fi
}


case $option in

  (submit)
    run_command submit "$@"
    ;;

  (start)
    run_command class "$@"
    ;;

  (stop)

    if [ -f $pid ]; then
      TARGET_ID="$(cat "$pid")"
      if [[ $(ps -p "$TARGET_ID" -o comm=) =~ "java" ]]; then
        echo "stopping $command"
        kill "$TARGET_ID" && rm -f "$pid"
      else
        echo "no $command to stop"
      fi
    else
      echo "no $command to stop"
    fi
    ;;

  (status)

    if [ -f $pid ]; then
      TARGET_ID="$(cat "$pid")"
      if [[ $(ps -p "$TARGET_ID" -o comm=) =~ "java" ]]; then
        echo $command is running.
        exit 0
      else
        echo $pid file is present but $command not running
        exit 1
      fi
    else
      echo $command not running.
      exit 2
    fi

    ;;

  (*)
    echo $usage
    exit 1
    ;;

esac

