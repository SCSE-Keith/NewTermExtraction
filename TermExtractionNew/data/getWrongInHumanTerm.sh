#!/bin/bash
# 查看错误的有没有在人工术语里有的，如果有则表明挑错了
while read line
do
   grep $line backup/term_human.txt > /dev/null
   if [ ! $? -eq 0 ]; then
      echo $line
  else
      echo $line > nothing
   fi
done < backup/finalterm10_w.txt
