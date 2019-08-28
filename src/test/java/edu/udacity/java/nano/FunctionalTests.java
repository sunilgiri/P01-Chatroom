package edu.udacity.java.nano;

import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class FunctionalTests extends TestCase {

    private static FirefoxOptions options;
    private static ChromeDriverService service;
    private static WebDriver driver;

    private static Logger logger = LoggerFactory.getLogger(FunctionalTests.class);

  @BeforeClass
    public static void createAndStartService() {

      if(System.getProperty("webdriver.chrome.driver")!=null) {

                   service = new ChromeDriverService.Builder()
                  .usingDriverExecutable(new File(System.getProperty("webdriver.chrome.driver")))
                  .usingAnyFreePort()
                  .build();
          try {
              service.start();
          } catch (IOException e) {
              e.printStackTrace();
          }
      } else {
          logger.error(" Path for the Chrome driver not set: Please set System.setProperty(\"webdriver.chrome.driver\"");
      }

  }

    @Before
    public void createDriver() {

             driver = new RemoteWebDriver(service.getUrl(),
                    DesiredCapabilities.chrome());
   }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }

   @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testAppRunning() {

      String baseUrl = "http://localhost:8080/";
      String expectedTitle = "Chat Room Login";
      String actualTitle = "";

      // launch Browser and direct it to the Base URL
      driver.get(baseUrl);

      // get the actual value of the title
      actualTitle = driver.getTitle();
      assertEquals(expectedTitle,actualTitle);
    }

    @Test
    public void testLogin() {

        String baseUrl = "http://localhost:8080/";
        // launch Browser and direct it to the Base URL
        driver.get(baseUrl);
        // Wait For Page To Load
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement inputUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.name("submitlink"));
        inputUsername.sendKeys("Ram");
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Chat Room", driver.getTitle());
        WebElement welcomeUserName =driver.findElement(By.xpath("//*[@id=\"username\"]"));
        assertEquals("Ram",welcomeUserName.getText());
    }

    @Test
    public void testChat() {

        String baseUrl = "http://localhost:8080/";
        // launch Browser and direct it to the Base URL
        driver.get(baseUrl);
        // Wait For Page To Load
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement inputUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.name("submitlink"));
        inputUsername.sendKeys("Ram");
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Chat Room", driver.getTitle());
        WebElement chatMessage =driver.findElement(By.cssSelector("#msg"));
        chatMessage.sendKeys("Hello world");

        WebElement submitMessageButton =driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div[2]/div[2]/button[1]"));
        submitMessageButton.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement sentMessage = driver.findElement(By.cssSelector("body > div:nth-child(2) > div > div > div.mdui-col-xs-6.mdui-col-sm-5 > div.message-container > div:nth-child(2) > div > div"));

        assertEquals("Ram：Hello world",sentMessage.getText());

    }

    @Test
    public void testJoin() {

        String baseUrl = "http://localhost:8080/";
        // launch Browser and direct it to the Base URL
        driver.get(baseUrl);
        // Wait For Page To Load
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement inputUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.name("submitlink"));
        inputUsername.sendKeys("Ram");
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Chat Room", driver.getTitle());

        WebElement sentMessage = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[3]/div/div/div"));
        assertEquals("Ram：Joined",sentMessage.getText());

    }

    @Test
    public void testLeave() {

        String baseUrl = "http://localhost:8080/";
        // launch Browser and direct it to the Base URL
        driver.get(baseUrl);
        // Wait For Page To Load
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement inputUsername = driver.findElement(By.name("username"));
        WebElement submitButton = driver.findElement(By.name("submitlink"));
        inputUsername.sendKeys("Ram");
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Chat Room", driver.getTitle());

        WebElement sentMessage = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[3]/div/div/div"));
        assertEquals("Ram：Joined",sentMessage.getText());

       WebDriver driver2= new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        driver2.get(baseUrl);
        // Wait For Page To Load
        driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement inputUsername2 = driver2.findElement(By.name("username"));
        WebElement submitButton2 = driver2.findElement(By.name("submitlink"));
        inputUsername2.sendKeys("Laxman");
        submitButton2.click();
        driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertEquals("Chat Room", driver2.getTitle());

        WebElement sentMessage2 = driver2.findElement(By.cssSelector("body > div:nth-child(2) > div > div > div.mdui-col-xs-6.mdui-col-sm-5 > div.message-container > div > div > div"));
        assertEquals("Laxman：Joined",sentMessage2.getText());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver2.navigate().back();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        sentMessage = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[3]/div[3]/div/div"));
        assertEquals("Laxman：Left",sentMessage.getText());

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement onlineCount = driver.findElement(By.cssSelector(" body > div:nth-child(2) > div > div > div.mdui-col-xs-6.mdui-col-sm-5 > div:nth-child(2) > span.mdui-chip-title.chat-num"));
        assertEquals("1",onlineCount.getText());

        driver2.quit();


    }

}
