package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SampleTests {

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
    public void seleniumAmazonSearchTest() {
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for (String searchTerm : searchTerms) {
            report("Navigate to: " + baseUrl);
            driver.get(baseUrl);
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchTerm);
            driver.findElement(By.id("nav-search-submit-button")).click();
            String firstResultTitle = driver.findElements(By.cssSelector("[data-component-type='s-search-result']:not(.AdHolder) h2")).get(0).getText();
            report("Search: \"" + searchTerm + "\" --> First result: \"" + firstResultTitle + "\"");
        }

        driver.quit();
    }

    @Test
    public void flakyTest() {
        report("Generating a random int in range: 0-100");
        report("Test will fail if the int is odd");
        int random = randomInt(0, 100);
        Assert.assertEquals(random % 2, 0, "Random int is: " + random + " - that's an odd number :(");
        report("Random int is: " + random + " - that's an even number - Great!");
    }

    @Test
    public void passTest() {
        report("Hello world! Everything's OK :)");
    }

    @Test
    public void failTest() {
        Assert.fail("This test is doomed to always fail :( ");
    }

    private int randomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void report(String message) {
        Reporter.log(new SimpleDateFormat("[HH:mm:ss]: ").format(new Date()) + message, true);
    }
}
