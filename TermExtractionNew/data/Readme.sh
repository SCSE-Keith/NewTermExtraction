##ccks文章中数据处理的相关说明
all_file.html
	textbook目录下的所有教材的合并
getFrequency.sh
	得到article_data/elements文件夹下的一、二、三、四、五元词在textall中出现的总频次
getRWO.sh
	得到article_data/data文件夹内的术语文件中存在于article_data/term_all.txt中的术语——>_r.txt 是正确的术语
		article_data/data文件夹内的术语文件中存在于article_data/finalterm10_w.txt中的术语——>_w.txt 是错误的术语
		article_data/data文件夹内的术语文件中以上两个文件都没有的术语——>_o.txt 是不确定正确与否的术语
getTermNotInText.sh
	得到人工抽取得到的术语中在课本中能找到的术语——>termInTextbook
	得到人工抽取得到的术语中在课本中找不到的术语——>termNotInTextbook
getText.sh
	得到all_file.html
getWrongInHumanTerm.sh
	检查挑出来的错误是否有在人工挑选出来的术语中存在的，如果有，则证明是挑错了，则需要修改挑出来的错误的术语
article_data/term_all
	之前人工术语和书本中挑出来的术语的并集，作为总术语，用来计算召回率
article_data/term_human
	之前人工挑出来的术语