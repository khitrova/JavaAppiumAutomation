import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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

    driver.rotate(ScreenOrientation.PORTRAIT);

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

  @Test
  public void testSwipeArticle(){

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Appium",
            "Cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium' in search",
            5
    );

   waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

   swipeUpToFindElement(
           By.xpath("//*[@text='View page in browser']"),
           "Cannot find the end of the article",
           20
   );
  }

  @Test
  public void saveFirstArticleToMyList(){

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

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options ",
            5
    );

    waitForElementAndClick(
            By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
            "Cannot find option to add article to reading list",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'got it' tip overlay",
            5
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );

    String nameOfFolder = "Learning programming";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot find text into articles folder input",
            5
    );

    waitForElementAndClick(
            By.id("android:id/button1"),
            "Cannot press OK",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, no X ",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists' button ",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/menu_sort_options"),
            "error menu",
            10
    );

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'"+nameOfFolder+"')]"),
            "Cannot find created folder",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );

  }

  @Test
  public void testAmountOfNotEmptySearch(){
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "Linkin Park discography";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    String searchResultLocator = "org.wikipedia:id/page_list_item_container";
    waitForElementPresent(
            By.id(searchResultLocator),
                    "Cannot find anything by the request "+ searchLine,
                    15
            );

    int amountOfSearchElements = getAmountOfElements(
            By.id(searchResultLocator)
    );

    Assert.assertTrue(
            "Found too few results",
            amountOfSearchElements>0
    );
  }

  @Test
  public void testAmountOfEmptySearch(){
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "dfghdfghdfh";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    String searchResultLocator = "org.wikipedia:id/page_list_item_container";
    String emptyResultsLabel ="//*[@text='No results found']";

    waitForElementPresent(
            By.xpath(emptyResultsLabel),
            "Cannot find empty result label by "+ searchLine,
            15
    );

    assertElementNotPresent(
            By.xpath(searchResultLocator),
            "Found some results by request "+ searchLine
    );
  }

  @Test
  public void testChangeScreenRotationOnSearchResults(){
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "Java";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language topic by searching by "+searchLine,
            15
    );

    String titleBeforeRotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String titleAfterRotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            titleBeforeRotation,
            titleAfterRotation
    );

    driver.rotate(ScreenOrientation.PORTRAIT);

    String titleAfterSecondRotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            titleBeforeRotation,
            titleAfterSecondRotation
    );
  }

  @Test
  public void testCheckSearchArticleInBackground(){
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
            "Cannot find Object-oriented programming language topic",
            5
    );

    driver.runAppInBackground(2);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from background",
            5
    );
  }

  @Test
  public void testSavingTwoArticles(){

    addArticleToReadingList(
            "Java",
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']")

    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'got it' tip overlay",
            5
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );

    String nameOfFolder = "Learning programming";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot find text into articles folder input",
            5
    );

    waitForElementAndClick(
            By.id("android:id/button1"), //опять же, не срабатывает поиск по @text
            "Cannot press OK",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, no X ",
            5
    );

    addArticleToReadingList(
            "Java",
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Island of Indonesia']")

    );

    waitForElementAndClick(
            By.xpath("//android.widget.TextView[@text='"+nameOfFolder+"']"),
            "Cannot find created folder",
            5
    );

    String secondArticleName = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );


    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, no X ",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists' button ",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/menu_sort_options"),
            "Menu element not present",
            10
    );

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'"+nameOfFolder+"')]"),
            "Cannot find created folder",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    waitForElementAndClick(
            By.xpath("//*[@text='island of Indonesia']"),
            "Cannot find second article",
            5
    );

    String savedArticleName = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    Assert.assertEquals(
            "Article name " +savedArticleName+" is not equal to expected "+secondArticleName,
            savedArticleName,
            secondArticleName
    );

  }

  @Test
  public void testAssertTitle(){
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
            "Cannot find article",
            5
    );

    assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Element not found"
    );

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

  protected void swipeUp(int timeOfSwipe){
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width/2;
    int start_y = (int)(size.height * 0.8);
    int end_y = (int)(size.height * 0.2);
    action
            .press(x,start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x,end_y)
            .release()
            .perform();

  }

  protected void swipeUpQuick(){
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String error_message, int max_swipes){

    int already_swiped= 0;
    while (driver.findElements(by).size() ==0) {

      if (already_swiped > max_swipes) {
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message,0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }

  }

  protected void swipeElementToLeft(By by, String errorMessage){
    WebElement element = waitForElementPresent(
            by,
            errorMessage,
            10);
    int leftX = element.getLocation().getX();
    int rightX = leftX +element.getSize().getWidth();
    int upperY = element.getLocation().getY();
    int lowerY = upperY + element.getSize().getHeight();
    int middleY = (upperY + lowerY)/2;
    TouchAction action = new TouchAction(driver);
    action
            .press(rightX,middleY)
            .waitAction(300)
            .moveTo(leftX,middleY)
            .release()
            .perform();

  }

  private int getAmountOfElements(By by){
    List elements = driver.findElements(by);
    return elements.size();

  }

  private void assertElementNotPresent(By by, String errorMessage){

    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements>0){
      String defaultMessage = "An element "+by.toString()+" supposed not to be present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  private String waitForElementAndGetAttribute(By by,String attribute, String errorMessage, long timeoutInSeconds){
    WebElement element= waitForElementPresent(by,errorMessage,timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  private void addArticleToReadingList(String searchLine,By by){
    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    waitForElementAndClick(
            by,
            "Cannot find results for "+searchLine,
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options ",
            5
    );

    waitForElementAndClick(
            By.xpath("/hierarchy/android.widget.FrameLayout/" +
                    "android.widget.FrameLayout/" +
                    "android.widget.ListView/" +
                    "android.widget.LinearLayout[3]/" +
                    "android.widget.RelativeLayout/" +
                    "android.widget.TextView"),
            //приходится использовать такой неоптимальный xpath из-за непонятных проблем с поиском элемента по @text
            "Cannot find option to add article to reading list",
            5
    );
  }

  private void assertElementPresent(By by, String errorMessage){

    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements==0){
      String defaultMessage = "An element "+by.toString()+" supposed to be present";
      throw new AssertionError(defaultMessage +" "+errorMessage);
    }
  }

}
