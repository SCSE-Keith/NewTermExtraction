#!/bin/bash
# 将最终得到的人工术语分为两部分，一部分是在书中有的，一部分是在书中没有的
rm termInTextbook
rm termNotInTextbook
while read line
do
   grep $line all_file.html > /dev/null
   if [ ! $? -eq 0 ]; then
      echo $line >> termNotInTextbook
  else
      echo $line >> termInTextbook
   fi
done < backup/iterm_human_textbook.txt
