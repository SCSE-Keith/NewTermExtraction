#!/bin/bash
# 将所有的html合并
for file in  textbook/*
do
   # sed -i '1d' $file
   echo $file
   cd $file
   cat *html >> ../../all_file.html
   cd ../..
   # sed -i .bak -e 's/<[^>]*>//g' $file.html
   # rm *.bak
#sed -i '1d' $file
# sed -i .bak -e 1d $file
done