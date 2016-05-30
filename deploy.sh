#!/bin/sh

PROJECT_PATH="$(pwd)"
APK_PATH="$PROJECT_PATH/app/build/outputs/apk/"
DATE="$(date +'%Y-%m-%d')"

echo "$APK_PATH"

RELEASE_APK_NAME="$(find $APK_PATH -name '*release.apk')"

echo "$RELEASE_APK_NAME"

mv "$RELEASE_APK_NAME" "$APK_PATH/zhihu.apk"

