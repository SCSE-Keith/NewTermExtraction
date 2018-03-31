package com.maven.TermExtractionNew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class CandidateDeal {

    private static HashSet<String> lowCandidates;
    private static HashSet<String> highCandidates;


    public static void init() {
        lowCandidates = new HashSet<String>();
        highCandidates = new HashSet<String>();
        try {
            TermAnalysis.start();

            BufferedReader br = new BufferedReader(new FileReader("data/process/lowcandidate"));
            String line = null;
            while((line = br.readLine()) != null) {
                lowCandidates.add(line);
            }
            br.close();
            br = new BufferedReader(new FileReader("data/process/highcandidate"));
            line = null;
            while((line = br.readLine()) != null) {
                highCandidates.add(line);
            }
            br.close();



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public static void addCandidate(String term, boolean isHigh) {
        if(isHigh && !highCandidates.contains(term))
            highCandidates.add(term);
        if(!isHigh && !lowCandidates.contains(term))
            lowCandidates.add(term);
    }

    public static void write() {
        try {
            if(lowCandidates.size() != 0) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("data/process/lowcandidate"));
                String tms[] = lowCandidates.toArray(new String[1]);
                for(String tm : tms) {
                    bw.write(tm + "\n");
                }
                bw.close();
            }
            if(highCandidates.size() != 0) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("data/process/highcandidate"));
                String tms[] = highCandidates.toArray(new String[1]);
                for(String tm : tms) {
                    bw.write(tm + "\n");
                }
                bw.close();
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
