package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

  private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "org.wikipedia:id/page_list_item_container",
            SEARCH_RESULT_LIST_ELEMENT = "org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "//android.widget.TextView[@text='{DESCRIPTION}']/../android.widget.TextView[@text='{TITLE}']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";


  public SearchPageObject(AppiumDriver driver) {

    super(driver);
  }
//Templates methods
  private static String getResultSearchElement(String substring){
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
  }

  private static String getResultSearchElement(String title, String description){
    String editedTitle = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION.replace("{TITLE}",title);
    return editedTitle.replace("{DESCRIPTION}",description);
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

  public int getAmountOfFoundArticles(){

    this.waitForElementPresent(
            By.id(SEARCH_RESULT_ELEMENT),
            "Cannot find anything by the request ",
            15
    );

    return this.getAmountOfElements( By.id(SEARCH_RESULT_ELEMENT));
  }

  public void waitForEmptyResultsLabel(){
    this.waitForElementPresent(
            By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
            "Cannot find empty result label",
            15
    );
  }

  public void assertThereIsNoResultOfSearch(){
    this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"We suppose not to find any results");
  }

  public void assertSearchElementHasText(String expected){
    this.assertElementHasText(
            this.waitForElementPresent(
                    By.xpath(SEARCH_INIT_ELEMENT),
                    "Cannot find 'Search Wikipedia' input",
                    5
            ),
            expected,
            "Can't find text "+ expected,
            5
    );
  }

  public void assertSearchResultsAbsent(){
    this.waitForElementNotPresent(
            By.id(SEARCH_RESULT_ELEMENT),
            "Search results are still present on the page",
            5
    );
  }

  public boolean assertResultsContainKeyword(String keyword) {
    return this.checkListElementsContainsString(
                    By.id(SEARCH_RESULT_ELEMENT),
                    By.id(SEARCH_RESULT_LIST_ELEMENT),
                    keyword
    );
  }
  public void waitForElementByTitleAndDescription(String title, String description){

    String searchResultXpath = getResultSearchElement(title,description);
    this.waitForElementPresent(
            By.xpath(searchResultXpath),
            "Result has wrong title or description compared to "+title+" and "+description,
            5);
  }

}
