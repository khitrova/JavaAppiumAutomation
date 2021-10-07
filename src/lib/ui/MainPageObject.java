package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver){
    this.driver = driver;
  }


  public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {

    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by)
    );

  }

  public WebElement waitForElementPresent(By by, String error_message) {

    return waitForElementPresent(by, error_message, 5);

  }

  public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }


  public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementAndClick(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  public boolean assertElementHasText(WebElement element, String expected_text, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.attributeContains(
                    element,
                    "text",
                    expected_text)
    );

  }

  public boolean checkAmount(By by, int min_value, String error_message, long timeoutInSeconds) {
    waitForElementPresent(by, error_message, timeoutInSeconds);
    int amount = driver.findElements(by).size();
    return (amount > min_value);
  }

  public boolean checkListElementsContainsString(By by, By inner_by, String keyword){
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

  public void swipeUp(int timeOfSwipe){
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

  public void swipeUpQuick(){
    swipeUp(200);
  }

  public void swipeUpToFindElement(By by, String error_message, int max_swipes){

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

  public void swipeElementToLeft(By by, String errorMessage){
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

  public int getAmountOfElements(By by){
    List elements = driver.findElements(by);
    return elements.size();

  }

  public void assertElementNotPresent(By by, String errorMessage){

    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements>0){
      String defaultMessage = "An element "+by.toString()+" supposed not to be present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  public String waitForElementAndGetAttribute(By by,String attribute, String errorMessage, long timeoutInSeconds){
    WebElement element= waitForElementPresent(by,errorMessage,timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  public void addArticleToReadingList(String searchLine,By by){
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

  public void assertElementPresent(By by, String errorMessage){

    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements==0){
      String defaultMessage = "An element "+by.toString()+" supposed to be present";
      throw new AssertionError(defaultMessage +" "+errorMessage);
    }
  }

}
