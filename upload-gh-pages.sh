#!/usr/bin/env bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  echo -e "Starting to update gh-pages\n"

  mkdir $HOME/android/

  cp -R app/build/outputs/apk/app-debug.apk $HOME/android/

  # go to home and setup git
  cd $HOME
  git config --global user.email    "fuck.soc@protonmail.com"
  git config --global user.name     "PusherWoodstock"


  # clone gh-pages branch
  git clone --quiet --branch=master https://github.com/k4ppaj/DroidInfo.git  master > /dev/null

  # go into directory and copy data we're interested
  cd master
  cp -Rf $HOME/android/* docs/Apk

  # add, commit and push files
  git add -f .
  git remote rm origin
  git remote add origin https://PusherWoodstock:$GH_TOKEN@github.com/WoodstockInc/woodstockinc.github.io.git
  git add -f .
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER pushed to gh-pages!"
  git push -fq origin master > /dev/null

  echo -e "All Apk's are up to date!\n"
fi