#!/bin/bash

set -e
set -o pipefail

FILE=algoliasearch/src/androidTest/java/com/algolia/search/saas/Helpers.java

if ! [[ $TRAVIS_JOB_NUMBER && ${TRAVIS_JOB_NUMBER-_} ]]; then
    echo "/!\ TRAVIS_JOB_NUMBER is not set."
    TRAVIS_JOB_NUMBER=$RANDOM.$RANDOM
fi

echo "Run Android test..."
cp $FILE $FILE.bak

echo "Replace env variable..."
sed -i.tmp "s/APP_ID_REPLACE_ME/${ALGOLIA_APPLICATION_ID}/g" $FILE
sed -i.tmp "s/API_KEY_REPLACE_ME/${ALGOLIA_API_KEY}/g" $FILE
sed -i.tmp "s/JOB_NUMBER_REPLACE_ME/${TRAVIS_JOB_NUMBER}/g" $FILE

./gradlew connectedAndroidTest

mv $FILE.bak $FILE
rm $FILE.tmp
