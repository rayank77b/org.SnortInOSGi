#!/bin/sh

DIR=$1


T="file:../plugins/"

# create files for backend
echo "copy needed jars"
# copy all needed jars
cp *.jar ${DIR}/plugins

cd ${DIR}/plugins

rm -rf ${DIR}/config
mkdir ${DIR}/config

CONFIG=${DIR}"/config/config.ini"

echo "eclipse.ignoreApp=true"> $CONFIG
echo "osgi.noShutdown=true">> $CONFIG
echo "file.encoding=UTF-8">> $CONFIG
echo "org.SnortInOSGi.backend.remotePort=9279" >> $CONFIG
echo "org.SnortInOSGi.backend.remoteIP=134.108.2.52" >> $CONFIG
echo "org.SnortInOSGi.dbanalyzer.remotePort=9279" >> $CONFIG
echo "org.SnortInOSGi.dbanalyzer.remoteIP=134.108.2.52" >> $CONFIG
echo "org.SnortInOSGi.bundle.dbconnector.configFile=/home/ray/daten/dissertation/Config.xml" >> $CONFIG

echo "osgi.bundles=\\" >> $CONFIG
echo $T"org.eclipse.osgi.services_3.2.0.v20090520-1800.jar@5:start,\\">> $CONFIG
echo $T"org.objectweb.asm_3.1.0.v200803061910.jar@5:start,\\">> $CONFIG
echo $T"javax.servlet_2.5.0.v200806031605.jar@5:start,\\">> $CONFIG
echo $T"ch.ethz.iks.r_osgi.remote_1.0.0.RC4_v20091012-1618.jar@5:start,\\">> $CONFIG

echo  $T$(ls org.SnortInOSGi.openAPI.snortschema_1.0.0*.jar)"@5:start,\\">> $CONFIG
echo  $T$(ls org.SnortInOSGi.openAPI.interfaces_1.0.0*.jar)"@5:start,\\">> $CONFIG
echo  $T$(ls org.SnortInOSGi.zzzRemoteTests_1.0.0*.jar)"@6:start">> $CONFIG


 
#arguments starting:
echo "for starting, enter the directory and start: "
echo "java -classpath ./plugins/org.eclipse.equinox.launcher_1.0.201.R35x_v20090715.jar  org.eclipse.equinox.launcher.Main -configuration ./config -clean -console"

