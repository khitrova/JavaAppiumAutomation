import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "and80");
    capabilities.setCapability("platformVersion", "8.1");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "G:/Dev/JavaAppiumAutomation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

  }


  @After
  public void tearDown() {
    driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    driver.quit();
  }

  @Test
  public void firstTest() {

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );
  }

  @Test
  public void testCancelSearch(){

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );
    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5
    );
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5
    );
  }
  @Test
  public void testCompareArticleTitle(){

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Search Wikipedia input",
            5
    );

    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    String article_title = title_element.getAttribute("text");

    Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title
    );
  }

  @Test
  public void testSearchElementContainsText(){

    assertElementHasText(
            waitForElementPresent(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot find 'Search Wikipedia' input",
                    5
            ),
            "Search Wikipedia",
            "Can't find text 'Search wikipedia'",
            5);

  }

  @Test
  public void testCancelSearchWithResults(){

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    Assert.assertTrue(
            "Not enough elements found",
            checkAmount(
                    By.id("org.wikipedia:id/page_list_item_container"),
                    1,
                    "No search results",
                    5
            )
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/page_list_item_container"),
            "Search results are still present on the page",
            5
    );
  }

  @Test
  public void testSearchResultsContainKeyword() {

    String keyword = "Java";

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            keyword,
            "Cannot find search input",
            5
    );

    checkAmount(
            By.id("org.wikipedia:id/page_list_item_container"),
            0,
            "No results found",
            5
    );

   Assert.assertTrue(
           "Not all elements contains keyword " + keyword,
           checkListElementsContainsString(
            By.id("org.wikipedia:id/page_list_item_container"),
            By.id("org.wikipedia:id/page_list_item_title"),
                   keyword

    ));
  }


  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {

    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by)
    );

  }

  private WebElement waitForElementPresent(By by, String error_message) {

    return waitForElementPresent(by, error_message, 5);

  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }


  private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementAndClick(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  private boolean assertElementHasText(WebElement element, String expected_text, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.attributeContains(
                    element,
                    "text",
                    expected_text)
    );

  }

  private boolean checkAmount(By by, int min_value, String error_message, long timeoutInSeconds) {
    waitForElementPresent(by, error_message, timeoutInSeconds);
    int amount = driver.findElements(by).size();
    return (amount > min_value);
  }

  private boolean checkListElementsContainsString(By by, By inner_by, String keyword){
    List<WebElement> elements = driver.findElements(by);
    int amount = elements.size();
    int allResultsHasMatch = 0;

    for (WebElement searchResult : elements) {
      if(searchResult.findElement(inner_by)
              .getText()
              .contains(keyword)) {
        allResultsHasMatch++;
      }
    }

  return (allResultsHasMatch == amount);
  }

}
