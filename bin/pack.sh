#!/bin/bash

rm -Rf papp
rm -Rf mapp
rm -Rf capp

#papp
mkdir papp
cp papp.jar papp/
cp inters.jar papp/
cp matrix.jar papp/
cp queue.jar papp/
cp server.policy papp/
cp papp.sh papp/

#mapp
mkdir mapp
cp mapp.jar mapp/
cp inters.jar mapp/
cp matrix.jar mapp/
cp server.policy mapp/
cp mapp.sh mapp/

#capp
mkdir capp
cp capp.jar capp/
cp inters.jar capp/
cp matrix.jar capp/
cp server.policy capp/
cp capp.sh capp/

