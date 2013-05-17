#!/bin/bash 
SRC=$1

if [ -z $SRC ]; then
	echo "Usage: $0 <libgdx-path>"
	exit 1
fi

cp ${SRC}/gdx.jar Desktop/libs/
cp ${SRC}/sources/gdx-sources.jar Desktop/libs/

cp ${SRC}/gdx-natives.jar Desktop/libs/
cp ${SRC}/gdx-backend-lwjgl.jar Desktop/libs/
cp ${SRC}/gdx-backend-lwjgl-natives.jar Desktop/libs/

cp ${SRC}/gdx-backend-android.jar Android/libs/
cp -r ${SRC}/armeabi Android/libs/
cp -r ${SRC}/armeabi-v7a Android/libs/