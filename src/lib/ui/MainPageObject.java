package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver){
    this.driver = driver;
  }


  public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {

    By by = this.getLocatorByString(locator);
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by)
    );

  }

  public WebElement waitForElementPresent(String locator, String error_message) {

    return waitForElementPresent(locator, error_message, 5);

  }

  public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }


  public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
    By by = this.getLocatorByString(locator);
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {

    WebElement element = waitForElementAndClick(locator, error_message, timeoutInSeconds);
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

  public boolean checkAmount(String locator, int min_value, String error_message, long timeoutInSeconds) {
    waitForElementPresent(locator, error_message, timeoutInSeconds);
    By by = this.getLocatorByString(locator);
    int amount = driver.findElements(by).size();
    return (amount > min_value);
  }

  public boolean checkListElementsContainsString(String locator, By inner_by, String keyword){
    By by = this.getLocatorByString(locator);
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

  public void swipeUpToFindElement(String locator, String error_message, int max_swipes){
    By by = this.getLocatorByString(locator);
    int already_swiped= 0;
    while (driver.findElements(by).size() ==0) {

      if (already_swiped > max_swipes) {
        waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message,0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }

  }

  public void swipeElementToLeft(String locator, String errorMessage){
    WebElement element = waitForElementPresent(
            locator,
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

  public int getAmountOfElements(String locator){
    By by = this.getLocatorByString(locator);
    List elements = driver.findElements(by);
    return elements.size();

  }

  public void assertElementNotPresent(String locator, String errorMessage){

    int amountOfElements = getAmountOfElements(locator);
    if (amountOfElements>0){
      String defaultMessage = "An element "+locator+" supposed not to be present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  public String waitForElementAndGetAttribute(String locator,String attribute, String errorMessage, long timeoutInSeconds){

    WebElement element= waitForElementPresent(locator,errorMessage,timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  public void assertElementPresent(String locator, String errorMessage){

    int amountOfElements = getAmountOfElements(locator);
    if (amountOfElements==0){
      String defaultMessage = "An element "+locator+" supposed to be present";
      throw new AssertionError(defaultMessage +" "+errorMessage);
    }
  }

  private By getLocatorByString(String locatorWithType){
    String[] explodedLocator = locatorWithType.split(Pattern.quote(":"),2);
    String byType = explodedLocator[0];
    String locator = explodedLocator[1];

    if (byType.equals("xpath")){
      return By.xpath(locator);
    } else if (byType.equals("id")){
      return By.id(locator);
    } else {
      throw new IllegalArgumentException("Cannot fet type of locator. Locator: " + locatorWithType);
    }
  }

}
