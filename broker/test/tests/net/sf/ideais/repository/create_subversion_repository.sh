#!/bin/sh

REPOSITORY_BASEDIR="/tmp"
REPOSITORY_NAME="test"
REPOSITORY_PROTOCOL="file:/"
REPOSITORY_URL=$REPOSITORY_PROTOCOL/$REPOSITORY_BASEDIR/$REPOSITORY_NAME

CHANGELOG_MESSAGE="Galileo Galilei"

CHECKOUT_DIR="/tmp/checkout"

README_FILENAME="README"

README_V1="If a moving particle, carried uniformly at a constant speed,\ntransverses two distances the time-intervals required are to\neach other in the ratio of these distances."

README_V2="If a moving particle, carried uniformly at a constant speed,\ntransverses two distances, the time-intervals required are to\neach other in the ratio of these distances."

TESTS_FILENAME="Tests"

TESTS_V1="Given an inclined plane and a limited vertical line, it is required\nto find a distance on the inclined plane which a body starting from\nrest, will transverse in the same time as that needed to transverse\nboth the vertical and the inclined plane."

rm -rf $CHECKOUT_DIR
rm -rf $REPOSITORY_BASEDIR/$REPOSITORY_NAME

# Create repository
svnadmin create $REPOSITORY_BASEDIR/$REPOSITORY_NAME

# Populate v1
svn checkout -q $REPOSITORY_URL $CHECKOUT_DIR
mkdir $CHECKOUT_DIR/About
echo -e "$README_V1" > $CHECKOUT_DIR/$README_FILENAME
echo -e "$TESTS_V1" > $CHECKOUT_DIR/About/$TESTS_FILENAME
svn add -q $CHECKOUT_DIR/*
svn commit -q -m "$CHANGELOG_MESSAGE" $CHECKOUT_DIR

# Populate v2
echo -e "$README_V2" > $CHECKOUT_DIR/$README_FILENAME
svn commit -q -m "$CHANGELOG_MESSAGE" $CHECKOUT_DIR

# Update the repository to the latest revision
svn update -q $CHECKOUT_DIR