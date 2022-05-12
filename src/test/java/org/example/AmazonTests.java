package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AmazonTests {

    private String baseUrl = null;
    private List<String> searchTerms = null;

    @BeforeTest
    public void beforeTest() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("MainConfig.properties"));
        baseUrl = prop.getProperty("baseUrl");
        searchTerms = Arrays.asList(prop.getProperty("searchTerms").split(","));
    }

    @Test
    public void amazonSearchTest() {
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        for (String searchTerm : searchTerms) {
            driver.get(baseUrl);
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchTerm);
            driver.findElement(By.id("nav-search-submit-button")).click();
            String firstResultTitle = driver.findElements(By.cssSelector("[data-component-type='s-search-result']:not(.AdHolder) h2")).get(0).getText();
            System.out.println("First result for search: \"" + searchTerm + "\" --> \"" + firstResultTitle + "\"");
        }

        driver.quit();
    }
}
