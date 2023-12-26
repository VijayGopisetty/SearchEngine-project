package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH=2;
    Crawler(){
        urlSet=new HashSet<>();
    }
    public void getPageTextsAndLinks(String url,int depth) {
        if(urlSet.contains(url))
            return;
        if(depth>=MAX_DEPTH)
            return;
        if(urlSet.add(url)){
            System.out.println(url);
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            //Indexer work starts here
            Indexer indexer=new Indexer(document,url);

            System.out.println(document.title());
            Elements availableLinksOnPage = document.select("a[href]");
            for (Element currLink : availableLinksOnPage) {
                getPageTextsAndLinks(currLink.attr("abs:href"), depth);
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String[] args) {
            Crawler crawler=new Crawler();
            crawler.getPageTextsAndLinks("https://www.javatpoint.com",0);
        }
    }