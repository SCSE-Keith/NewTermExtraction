#!/bin/bash
# 得到一二三四五元词的总词频
for file in  elements/*
do
	let count=0
	while read line
	do
		num=`grep  "^"$line"$" textall | wc -l | awk '{print $1}'`
		# echo $num
	    let count=${count}+${num}
	done < $file
	echo $file
	echo $count
done