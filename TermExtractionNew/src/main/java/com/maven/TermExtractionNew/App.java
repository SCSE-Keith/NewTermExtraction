package com.maven.TermExtractionNew;

/**
 *抽取定義
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //第一步进行，产生定义中出现的术语，运行一次后可以不用再运行
    	TextBookDeal td = new TextBookDeal();
    	td.dealTextBook();
    }
}
