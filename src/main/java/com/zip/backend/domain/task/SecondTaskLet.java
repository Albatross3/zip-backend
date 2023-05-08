package com.zip.backend.domain.task;

import com.zip.backend.domain.housing.Housing;
import com.zip.backend.domain.housing.HousingDetailRepository;
import com.zip.backend.domain.housing.HousingRepository;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

// task : 데이터를 바탕으로 Image 크롤링하기
@Component
public class SecondTaskLet implements Tasklet {

  private final HousingRepository housingRepository;
  private final HousingDetailRepository housingDetailRepository;

  public SecondTaskLet(HousingRepository housingRepository,
      HousingDetailRepository housingDetailRepository) {
    this.housingRepository = housingRepository;
    this.housingDetailRepository = housingDetailRepository;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    // WebDriver 객체 생성
    System.setProperty("webdriver.chrome.driver", "/Users/albatross/Desktop/development/zip_backend/chromedriver");

    // Chrome 연결 안되는 문제 해결
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    WebDriver driver = new ChromeDriver(options);

    // google 홈페이지 연결
    driver.get("https://www.google.com/");

    // WebElement 찾기
    WebElement searchBox = driver.findElement(By.name("q"));
    searchBox.sendKeys("houseName");

    // 검색 실행
    searchBox.submit();

    // 이미지 탭
    WebElement imageButton = driver.findElement(By.xpath("/html/body/div[7]/div/div[4]/div/div[1]/div/div[1]/div/div[2]/a"));
    imageButton.click();

    Thread.sleep(1000);

    List<Housing> all = housingRepository.findAll();
    for (Housing h : all) {
      String houseName = h.getHOUSE_NM();

      WebElement searchInput = driver.findElement(By.name("q"));
      searchInput.clear();
      searchInput.sendKeys(houseName);

      // 검색 실행
      searchInput.submit();

      // 이미지 동적 크롤링 후 Housing 객체에 저장
      WebElement firstImageElement = driver.findElement(By.xpath(
          "/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div/div[1]/div[1]/span/div[1]/div[1]/div[1]/a[1]/div[1]/img"));

      String imageSrc = firstImageElement.getAttribute("src");
      h.setImage(imageSrc);
      System.out.println(imageSrc);
      housingRepository.save(h);
      Thread.sleep(1000);

    }
    // WebDriver 종료
    driver.quit();


    return RepeatStatus.FINISHED;
  }
}
