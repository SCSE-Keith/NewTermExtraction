#!/bin/bash
# 以阈值为10的数据为基准，得到其他阈值下的正确的错误的和不确定的三个文件
for file in  backup/data/*
do
	while read line
	do
	    grep -w "^"$line"$" backup/term_all.txt > /dev/null
	    if [ $? -eq 0 ]; then
	       echo $line >> ${file%%.txt}"_r.txt"
	    else
	    	grep -w "^"$line"$" backup/finalterm10_w.txt > /dev/null
		    if [ $? -eq 0 ]; then
		        echo $line >> ${file%%.txt}"_w.txt"
		    else
		    	echo $line >> ${file%%.txt}"_o.txt"
		    fi
	    fi
	done < $file
#    # sed -i '1d' $file
#    echo $file
#    for line in $file

#    cat *html >> ../../all_file.html
#    cd ../..
#    # sed -i .bak -e 's/<[^>]*>//g' $file.html
#    # rm *.bak
# #sed -i '1d' $file
# # sed -i .bak -e 1d $file
done