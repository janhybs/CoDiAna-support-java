#!/bin/bash


function usage () {
	echo "Usage: $0 -c <path> -s <path> -d <path> [ <options> ]" 1>&2;
	echo "  -c <path>           classpath, where solution structure begins, source files ale located" 1>&2;
	echo "  -s <path>           startpath, main file path (start file with static main method, e.g. java file)" 1>&2;
	echo "  -d <path>           destination, where to place generated class files" 1>&2;

	echo "  -t <int>            maxtime in s" 1>&2;
	echo "  -m <int>            maxmemory in MB" 1>&2;
	echo "  -o <path>           output file" 1>&2;
	echo "  -e <path>           error file" 1>&2;
	echo "  -v                  verbose" 1>&2;
	echo "  -h                  help" 1>&2;
	exit 1;
}

function getMemory () {
	Info=$(pmap $PID -x 2> /dev/null | tail -n 1)
	if [ -n "$Info" ]; then
		InfoArray=($Info)
		echo ${InfoArray[3]}
	else
		echo -1
	fi
}

function measureMemory () {
	CURRENTMEMORY=1
	TOTALMEMORY=0
	TOTALTICKS=0
	while [ "$CURRENTMEMORY" -ge "0" ]
	do
		# get memory in bytes 
		CURRENTMEMORY=$(getMemory $PID)

		if [ "$CURRENTMEMORY" -ne "0" ]; then
			# increment total ticks
			TOTALTICKS=$(($TOTALTICKS+1))

			# add current memory value
			TOTALMEMORY=`echo "scale=9;($TOTALMEMORY+$CURRENTMEMORY/1024)" | bc`
		fi
#		echo $CURRENTMEMORY
		sleep 0.05
	done

	TOTALMEMORY=`echo "scale=0;($TOTALMEMORY*1024)/$TOTALTICKS" | bc`
	echo $TOTALMEMORY > $1
}

function limitTime () {
	(sleep $MAXTIME && kill $PID) 2>/dev/null & WATCHER=$!
	if wait $PID 2>/dev/null; then
		EXITVALUE=$?
	else
		EXITVALUE=124
	fi
	kill $WATCHER 2>/dev/null
	sleep 0.5
}

function getDuration () {
	DURATION=`echo "scale=0;((${ENDTIME}-${STARTTIME})*1000)/1" | bc`
}


#--------------------------------------------------------------------

# defaults
MAXTIME=60
MAXMEORY=100
VERBOSE=0
VERBOSEARG=''
EXITVALUE=-1
OUTPUT=/dev/null
ERROR=/dev/null


while getopts ":c:s:d:t:m:o:e:hv" o; do
    case "${o}" in
        c)
			# classpath
            CLASSPATH=${OPTARG}
            ;;
        s)
			# startpath
            STARTPATH=${OPTARG}
            ;;
        d)
			# destination
            DESTINATION=${OPTARG}
            ;;
        t)
			# maximum timelimitTime
            MAXTIME=${OPTARG}
			;;
        m)
			# maximum time
            MAXMEORY=${OPTARG}
            ;;
        o)
			# output file
            OUTPUT=${OPTARG}
            ;;
        e)
			# error file
            ERROR=${OPTARG}
			;;
        v)
			# error file
            VERBOSE=1
			VERBOSEARG=-verbose
			;;
        h)
			# help
            usage
            ;;
        *)
			# invalid option
            usage
            ;;
        :)
			# missing argument
            usage
            ;;
    esac
done
shift $((OPTIND-1))

if [[ $VERBOSE -eq 1 ]]; then	
	echo "CLASSPATH   = ${CLASSPATH}"
	echo "STARTPATH   = ${STARTPATH}"
	echo "DESTINATION = ${DESTINATION}"
	echo "MAXTIME     = ${MAXTIME}"
	echo "MAXMEORY    = ${MAXMEORY}"
	echo "OUTPUT      = ${OUTPUT}"
	echo "ERROR       = ${ERROR}"
fi

if [ -z "${CLASSPATH}" ] || [ -z "${STARTPATH}" ] || [ -z "${DESTINATION}" ]; then
	echo "missing required argument" 1>&2;
	usage
fi


#------------------------------------------------------------------


EXITVALUE=-1
MEMORYFILE=$(mktemp)
chmod 777 $MEMORYFILE
STARTTIME=$(date +%s.%N)

	# start javac and get PID
	javac ${VERBOSEARG} -sourcepath ${CLASSPATH} -d ${DESTINATION} ${STARTPATH} > ${OUTPUT} 2> ${ERROR} & PID=$!

	# attach async measurements
	( measureMemory $MEMORYFILE ) & MP=$!

	# wait to finish
		(sleep $MAXTIME && kill $PID) 2>/dev/null & WATCHER=$!
		if wait $PID 2>/dev/null; then
			EXITVALUE=$?
		else
			EXITVALUE=124
		fi
		kill $WATCHER 2>/dev/null

	# retrieve results from files
	wait $MP
	TOTALMEMORY=$(cat $MEMORYFILE)

	#wait PP
	#CPU=$(cat $MEMORYFILE)

ENDTIME=$(date +%s.%N)

# get run time
getDuration


echo "run-time=${DURATION}"
echo "memory-peak=${TOTALMEMORY}"
echo "exit-value=${EXITVALUE}";
exit ${EXITVALUE};


