#!/bin/sh

PROJECT_PATH="$(pwd)"
APK_PATH="$PROJECT_PATH/app/build/outputs/apk/"
DATE="$(date +'%Y-%m-%d')"

echo "$APK_PATH"

TARGET_APK_NAME="$(find $APK_PATH -name '*release.apk')"

RELEASE_APK_FILE="zhihu-$TRAVIS_TAG-$DATE-release.apk"

echo "$TARGET_APK_NAME"
echo "$RELEASE_APK_FILE"

mv "$TARGET_APK_NAME" "./$RELEASE_APK_FILE"
