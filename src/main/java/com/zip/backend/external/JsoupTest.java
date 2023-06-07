package com.zip.backend.external;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// 청약 또는 부동산 관련 추가 정보 Jsoup 크롤링
public class JsoupTest {

  public static void main(String[] args) throws IOException {
    String query = "청약";
    String url = String.format("https://www.youtube.com/results?search_query=%s", query);

    // Jsoup 을 사용해서 검색 결과 페이지를 파싱
    Document doc = Jsoup.connect("https://realestate.daum.net/news").get();

//    System.out.println(doc);
    // 검색 결과에서 동영상 정보를 추출
    Elements elements = doc.select(".ico_news");

    for (Element element : elements) {
      System.out.println("https:" + element.attr("href"));
    }


  }
}
