import re
import subprocess

HEAP_DUMP_PATH = 'logs'
GC_LOG_PATH = 'logs'
JAR_FILE = 'build/libs/hw04l08-GC.jar'
MAIN_CLASS = 'CalcDemo'

gcs = {
    'serial': {'config': '-XX:+UseSerialGC'},
    'parallel': {'config': '-XX:+UseParallelGC'},
    'cms': {'config': '-XX:+UseConcMarkSweepGC'},
    'g1': {'config': '-XX:+UseG1GC'},
    'zgc': {'config': '-XX:+UnlockExperimentalVMOptions -XX:+UseZGC'},
}
currentXms = 256
MAX_XMS = 2048
currentXmx = 256
MAX_XMX = 2048


# output = subprocess.run(runCommand.split(' '))
pattern = re.compile(".*spend msec:(?P<msec>\d{1,})", re.MULTILINE|re.S)
with open('logs/statLog.csv', 'w') as f:
    header = 'heap value'
    for key in gcs:
        header += f',{key}'
    f.write(f'{header}\n')
    
    while currentXms <= MAX_XMS:
        results = dict()
        for name, props in gcs.items():
            runCommand = (f'java -Xms{currentXms}m -Xmx{currentXmx}m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath={HEAP_DUMP_PATH}/heapdump.hprof {props["config"]} '
                          f'-Xlog:gc=debug:file={GC_LOG_PATH}/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m -jar {JAR_FILE} {MAIN_CLASS}')

            output = subprocess.run(runCommand.split(), capture_output=True, text=True)
            if output.stderr == '':
                matchedString = pattern.match(output.stdout)
                resultBuffer = matchedString.group('msec')
            else:
                resultBuffer = 0

            results[name] = resultBuffer

        currentXmsResult = f'{currentXms}'
        for key in results:
            currentXmsResult += f',{results[key]}'

        f.write(f'{currentXmsResult}\n')
        print(f'{currentXmsResult}')
        currentXms += 16
        currentXmx += 16
    f.write('\n')




     
