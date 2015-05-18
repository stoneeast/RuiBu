#! /bin/bash

# run in data/input/ dir
for i in $(find . -name *txt); do
  echo $i
  cat $i|perl -pe "$_~s/\|\|'\|'\|\|/|/g" > tmp
  mv tmp $i
done
