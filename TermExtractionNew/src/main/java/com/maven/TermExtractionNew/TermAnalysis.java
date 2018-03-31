package com.maven.TermExtractionNew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.domain.Nature;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;


public class TermAnalysis {

    private static HashSet<String> terms;
    //	private static HashMap<String, Integer> tf;
    private static HashSet<String> borderWords;
    public static String allText;
    public static String lowcandidate;
    public static String highcandidate;

    public static void start() {
        try {
            terms = new HashSet<String>();
            allText = "";
            lowcandidate = "";
            highcandidate = "";
            BufferedReader br = new BufferedReader(new FileReader("data/process/textclean"));
            String line = "";
            while ((line = br.readLine()) != null) {
                allText += line + "\n";
            }
            br.close();
            br = new BufferedReader(new FileReader("data/process/finalterm"));
            while ((line = br.readLine()) != null) {
                terms.add(line);
            }
            br.close();
            br = new BufferedReader(new FileReader("data/process/lowcandidate"));
            while ((line = br.readLine()) != null) {
                lowcandidate += line + "\n";
            }
            br.close();
            br = new BufferedReader(new FileReader("data/process/highcandidate"));
            line = null;
            while ((line = br.readLine()) != null) {
                highcandidate += line + "\n";
            }
            br.close();

//			tf = new HashMap<String, Integer>();
            borderWords = new HashSet<String>();
            borderWords.add("为");
            borderWords.add("由");
            borderWords.add("呢");
            borderWords.add("是");
            borderWords.add("时");
            borderWords.add("即");
            borderWords.add("求");
            borderWords.add("与");
            borderWords.add("各");
            borderWords.add("第");
            borderWords.add("于");
            borderWords.add("上");
            borderWords.add("两");
            borderWords.add("四");
            borderWords.add("五");
            borderWords.add("用");
            borderWords.add("其");
            borderWords.add("吗");
            borderWords.add("以");
            borderWords.add("多少");
            borderWords.add("存在");
            borderWords.add("叫做");
            borderWords.add("着");
            borderWords.add("怎样");
            borderWords.add("怎么");
            borderWords.add("哪个");
            borderWords.add("例如");
            borderWords.add("什么");
            borderWords.add("该");
            borderWords.add("这个");
            borderWords.add("这种");
            borderWords.add("做");
            borderWords.add("这时");
            borderWords.add("那个");
            borderWords.add("把");
            borderWords.add("了");
            borderWords.add("的");
            borderWords.add("一个");
            borderWords.add("一个个");
            borderWords.add("四个");
            borderWords.add("一次");
            borderWords.add("一种");
            borderWords.add("一");
            borderWords.add("一组");
            borderWords.add("同一个");
            borderWords.add("任意");
            borderWords.add("同一");
            borderWords.add("几个");
            borderWords.add("两个");
            borderWords.add("一条");
            borderWords.add("一对");
            borderWords.add("两条");

            //初始词频计算
//			calTf();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}