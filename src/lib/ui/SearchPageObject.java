package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

  private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";


  public SearchPageObject(AppiumDriver driver) {

    super(driver);
  }
//Templates methods
  private static String getResultSearchElement(String substring){
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
  }
  //Templates methods
  public void initSearchInput(){
    this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input");
    this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
  }

  public void typeSearchLine(String searchLine){
    this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),searchLine,"Cannot find and type into search input",5);
  }

  public void waitForSearchResult(String substring){
    String searchResultXpath = getResultSearchElement(substring);
    this.waitForElementPresent(By.xpath(searchResultXpath),"Cannot find search result with substring" + substring);
  }

  public void waitForCancelButtonToAppear(){
    this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button",5);
  }

  public void waitForCancelButtonToDisappear(){
    this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present",5);
  }

  public void clickCancelSearch(){
    this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button",5);
  }

  public void clickByArticleWithSubstring(String substring){
    String searchResultXpath = getResultSearchElement(substring);
    this.waitForElementAndClick(By.xpath(searchResultXpath),"Cannot find and click search result with substring" + substring,10);
  }
}
