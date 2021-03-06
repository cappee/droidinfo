#!/usr/bin/env bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  echo -e "Starting to update gh-pages\n"
 
  mkdir $HOME/android/

  cp -R app/build/outputs/apk/debug/app-debug.apk $HOME/android/

  # go to home and setup git
  cd $HOME
  git config --global user.email    "fuck.soc@protonmail.com"
  git config --global user.name     "PusherWoodstock"

  # clone gh-pages branch
  git clone --quiet --branch=master https://PusherWoodstock:$GH_TOKEN@github.com/gabrielecappellaro/DroidInfo.git  master > /dev/null

  # go into directory and copy data we're interested
  cd master
  rm -rf docs/apk
  mkdir docs/apk
  cp -Rf $HOME/android/* docs/apk/app-debug.apk

  # add, commit and push files

  # Check if latest commit was done by PusherWoodstock
  string=$(git log -1)
    
  if [[ $string == *"PusherWoodstock"* ]]; then
    echo -e "APK File is updated!\n"
    exit 0
  fi
  
  git add -f .
  git remote rm origin
  git remote add origin https://PusherWoodstock:$GH_TOKEN@github.com/gabrielecappellaro/DroidInfo.git
  git add -f .
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER pushed to gh-pages!"
  git push -fq origin master > /dev/null

  echo -e "Finished .sh script.\n"
fi
