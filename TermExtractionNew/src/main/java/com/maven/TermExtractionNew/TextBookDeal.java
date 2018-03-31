package com.maven.TermExtractionNew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextBookDeal {

    private String chapterName;//章的名字

    private String sectionName;//节的名字

    private String subsectionName;//小节的名字

    private int page;//记录第几页
    int st1 = 0;
    int st2 = 0;
    int st3 = 0;

    private BufferedWriter definitionWriter;


    private boolean start;

    private String tmpInfo;

    private BufferedWriter fbw;


    public TextBookDeal() {
//		loadTerm();
        try {
            fbw = new BufferedWriter(new FileWriter("data/process/textclean"));
            definitionWriter = new BufferedWriter(new FileWriter("data/process/definition"));
            CandidateDeal.init();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		nlp = new NLPAnalysis();
    }

//讀取data/textbook裡的文件夾
    public void  dealTextBook() {
        File rootPath = new File("data/textbook/"); //"data/textbook/"
        File fs[] = rootPath.listFiles();
        for (int i = 0; i < fs.length; i ++) {
            if(fs[i].isDirectory()) {
                page = 1;
                st1 = 0;
                File pages[] = fs[i].listFiles();
                for (int j = 0; j < pages.length; j ++)
                    if(!pages[j].getName().equals(".DS_Store")) //将章节中非xxx的文件进行处理
                        dealChapter(pages[j]);
            }
        }
        clean();
        CandidateDeal.write();
    }


    private void clean() {
        try {
            definitionWriter.close();
            fbw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    private void cleanComment(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String content = "";
            String line = "";
            while((line = br.readLine()) != null) {
                content += line + "\n";
            }
            br.close();
            content = content.replaceAll("<!--", "<div style='display:none'>");
            content = content.replaceAll("-->", "</div>");
            content = content.replaceAll("([^>])<sup>", "$1\\^（");
            content = content.replaceAll("([^>])</sup>([^<])", "$1\\）$2");
            content = content.replaceAll("<sup>", "");
            content = content.replaceAll("</sup>", "");
            content = content.replaceAll("[“”●]", "");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //	private void dealText(File file) {
//		System.out.println("开始处理："+file.getName());
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String line = "";
//			while((line = br.readLine()) != null) {
//				if(line.length() > 0) {
//					write(line);
//					dealLines(line);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

    private void dealChapter(File file) {
        System.out.println("开始处理："+file.getName());
        start = false;
        tmpInfo = "";
        cleanComment(file);
        try {
            //Jsoup是一個抽取html文件的方法，將文本分詞
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements ets = doc.getAllElements();//獲取所有的元素（一個標籤算是一個元素
            //以html標籤結構順序遍歷
            for(Element e : ets) {
                //若元素是以page開頭
                if(e.text().startsWith("page")) {
                    page ++;
//					System.out.println(page);
                }

                //若標籤含有h1 文本中含有空格 則將文本按照字符分割 取第二個字符記為章節名
                if(e.tagName().equals("h1")) {
                    if(e.text().contains("　"))
                        chapterName = e.text().split("　")[1];
                    start = false;
                    st1 ++;
                    st2 = 0;
//					System.out.println(chapterName);
                }

                if(e.tagName().equals("h2")) {
                    if(e.text().contains("　")) {
                        sectionName = e.text().split("　")[1];
                        start = true;
                        st2 ++;
                        st3 = 0;
                    }

//					System.out.println(sectionName);
                }


                if(e.tagName().equals("h3")) {
                    if(e.text().contains("　")) {
                        subsectionName = e.text().split("　")[1];
                    }
                }
                if(e.tagName().equals("h4")) {
                    start = true;
                    st3 ++;
                    System.out.println(st1+"."+st2+"."+st3 + " " + page);
                }
//				if(e.tagName().equals("h5")) {
////					start = true;
//				}
                //當標題為h2||h4時 start為真
                if(start) {
                    write(e.text());
                    dealElement(e);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void write(String info) {
        try {

            //刪除文本中的各種符號
            info = info.replaceAll("[△≥⊙oxyz≈stρpqP∞ 〉〈 QθlrRφβα×/÷·a-d+\\*/＋-－≠＝=^（）()∈>\\dA-D0-9≤<∈\\{}\\[\\]→∶\\|“”′℃±'…π%β°]{3,}", "");
            info = info.replaceAll("[（(].*?[）)]", "");
            info = info.replaceAll("[\\r\\n△≥①②“”″′℃±'…π%≈βραφθ∞°⊙〉〈 ]", "");
            info = info.replaceAll("^图\\d+\\.?", "");
            if(info.length() < 3)
                return;
            //按照下面幾個符號分割文本
            String infos[] = info.split("[，。\\.;：？（）； 　]");
            for(String st : infos) {
                if(st.length() < 3)
                    continue;
                //List<Term> terms = ToAnalysis.parse(st);
                Result terms = ToAnalysis.parse(st);
                for(int i = 0; i < terms.size();i ++) {
                    fbw.write(terms.get(i).getName() + " ");
                }
                fbw.write("\n");
                fbw.flush();
            }

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


    }

    private void dealElement(Element e) {

        if(e.tagName().equals("p")) {
            dealLines(e.text());
//			tmpInfo += e.text();
//			if(tmpInfo.endsWith(".")) {
//				dealLines(tmpInfo);
//				tmpInfo = "";
//
//			}

        }
    }

    //將文本按以下字符分割 檢查是否有被錯分割的小數 如果有則合併
    private void dealLines(String lines) {
        String st[] = lines.split("[。.；？]");
        int i = 1;
        if(st.length == 0)
            return;
        String tpInfo = st[0];
        while(i < st.length) {
            if((tpInfo.matches(".*?[0-9]$") && st[i].matches("^[0-9].*?")) || (tpInfo.matches(".*<[^>]+$"))) {//检查是否是小数点
                tpInfo += "." + st[i];
            } else {
                calculateTerm(tpInfo + ".");
                tpInfo = st[i];

            }
            i ++;
        }
        calculateTerm(tpInfo + ".");

    }



    private void calculateTerm(String info) {
        if(matchDefinition(info)) {
            dealDefCandidate(info);
        }

    }


    //匹配模板 進行定義抽取
    private boolean matchDefinition(String line) {

        return searchTerm(line).size() != 0;
    }

    private HashSet<String> searchTerm(String line) {
        line = line.replaceAll("<img class.*>", "");

        Pattern pattern;
        Matcher matcher;
        HashSet<String> terms =  new HashSet<String>();
        String patterns[] = {//".*?<span.*?bold.*?>"+"([^，<>（）()：；？。\\.考探问\\d试]+).*?</span>?",
                ".*?[为做是在]([^为做是在\\.，（(]+)[（(][A-Za-z ]{5,}[)）]",
                ".*?称为([^\\.，：（\\(）；……精]{1,50})",
                ".*?简称([^\\.，：（\\(）；……精]{1,50})",
                ".*?称做([^\\.，：（\\(）；……精]{1,50})",
                ".*?叫做([^\\.，：（\\(）；……精]{1,50})",
                ".*?也叫([^\\.，：（\\(）；……精]{1,50})"};
//				"([^0-9、（）]*)?指.*+"};

        //去除公式后寻找
//		line = line.replaceAll("[abcdxy+＋-－*/^\\d（）]+", "");
        //寻找定义语句候选
        for(String pt : patterns) {
            pattern = Pattern.compile(pt);
            matcher = pattern.matcher(line);
            while(matcher.find()) {
                String term = cleanString(matcher.group(1));

                if(!term.equals("归纳") && !term.equals("分析")) {

                    terms.add(term);
                }

            }
        }
        return terms;
    }

    //处理判断为定义的句子
    private void dealDefCandidate(String line) {
        String part[] = line.split("，|；");//将句子切分为小块
        boolean containDef[] = new boolean[part.length];
        String prefix = "";
        for(int i = 0; i < part.length; i++) {
            containDef[i] = matchDefinition(part[i]);
            if(!containDef[i]) {
                prefix += cleanString(part[i]) + "，";
            } else {
                try {
                    String terms[] = searchTerm(part[i]).toArray(new String[1]);
                    for (String term : terms) {
                        //List<Term> tms = ToAnalysis.parse(term);
                        Result tms = ToAnalysis.parse(term);
                        term = tms.get(0).getName();
                        for(int j = 1; j < tms.size();j ++) {
                            term += " " + tms.get(j).getName();
                        }
                        CandidateDeal.addCandidate(term, true);
                        definitionWriter.write(term + " ");
                    }
                    definitionWriter.write("\n"+prefix+cleanString(part[i])+"\n");
                    definitionWriter.flush();
                } catch (Exception e) {

                }

            }
        }


    }

    private String cleanString(String line) {
        Document doc = Jsoup.parseBodyFragment(line);
        return doc.text();
    }


}
