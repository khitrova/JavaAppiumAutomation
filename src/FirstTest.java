import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class FirstTest extends CoreTestCase {

  private MainPageObject mainPageObject;

  protected void setUp() throws Exception{
    super.setUp();

    mainPageObject = new MainPageObject(driver);
  }


  @Test
  public void testSearch() {

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResult("Object-oriented programming language");
  }

  @Test
  public void testCancelSearch(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    searchPageObject.initSearchInput();
    searchPageObject.waitForCancelButtonToAppear();
    searchPageObject.clickCancelSearch();
    searchPageObject.waitForCancelButtonToDisappear();

  }
  @Test
  public void testCompareArticleTitle(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);

    String articleTitle = articlePageObject.getArticleTitle();

    Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            articleTitle
    );
  }

  @Test
  public void testSearchElementContainsText(){

    mainPageObject.assertElementHasText(
            mainPageObject.waitForElementPresent(
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

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    Assert.assertTrue(
            "Not enough elements found",
            mainPageObject.checkAmount(
                    By.id("org.wikipedia:id/page_list_item_container"),
                    1,
                    "No search results",
                    5
            )
    );

    mainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );

    mainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/page_list_item_container"),
            "Search results are still present on the page",
            5
    );
  }

  @Test
  public void testSearchResultsContainKeyword() {

    String keyword = "Java";

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            keyword,
            "Cannot find search input",
            5
    );

    mainPageObject.checkAmount(
            By.id("org.wikipedia:id/page_list_item_container"),
            0,
            "No results found",
            5
    );

   Assert.assertTrue(
           "Not all elements contains keyword " + keyword,
           mainPageObject.checkListElementsContainsString(
            By.id("org.wikipedia:id/page_list_item_container"),
            By.id("org.wikipedia:id/page_list_item_title"),
                   keyword

    ));
  }

  @Test
  public void testSwipeArticle(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Appium");
    searchPageObject.clickByArticleWithSubstring("Appium");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);

    articlePageObject.waitForTitleElement();
    articlePageObject.swipeToFooter();

  }

  @Test
  public void testSaveFirstArticleToMyList(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);

    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);

    articlePageObject.waitForTitleElement();
    String articleTitle = articlePageObject.getArticleTitle();
    String nameOfFolder = "Learning programming";
    articlePageObject.addArticleToMyList(nameOfFolder);
    articlePageObject.closeArticle();

    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
    myListsPageObject.openFolderByName(nameOfFolder);
    myListsPageObject.swipeByArticleToDelete(articleTitle);

  }

  @Test
  public void testAmountOfNotEmptySearch(){
    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "Linkin Park discography";
    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    String searchResultLocator = "org.wikipedia:id/page_list_item_container";
    mainPageObject.waitForElementPresent(
            By.id(searchResultLocator),
                    "Cannot find anything by the request "+ searchLine,
                    15
            );

    int amountOfSearchElements = mainPageObject.getAmountOfElements(
            By.id(searchResultLocator)
    );

    Assert.assertTrue(
            "Found too few results",
            amountOfSearchElements>0
    );
  }

  @Test
  public void testAmountOfEmptySearch(){
    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "dfghdfghdfh";
    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    String searchResultLocator = "org.wikipedia:id/page_list_item_container";
    String emptyResultsLabel ="//*[@text='No results found']";

    mainPageObject.waitForElementPresent(
            By.xpath(emptyResultsLabel),
            "Cannot find empty result label by "+ searchLine,
            15
    );

    mainPageObject.assertElementNotPresent(
            By.xpath(searchResultLocator),
            "Found some results by request "+ searchLine
    );
  }

  @Test
  public void testChangeScreenRotationOnSearchResults(){
    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    String searchLine = "Java";
    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language topic by searching by "+searchLine,
            15
    );

    String titleBeforeRotation = mainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String titleAfterRotation = mainPageObject.waitForElementAndGetAttribute(
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

    String titleAfterSecondRotation = mainPageObject.waitForElementAndGetAttribute(
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
    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    mainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language topic",
            5
    );

    driver.runAppInBackground(2);

    mainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from background",
            5
    );
  }

  @Test
  public void testSavingTwoArticles(){

    mainPageObject.addArticleToReadingList(
            "Java",
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']")

    );

    mainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'got it' tip overlay",
            5
    );

    mainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );

    String nameOfFolder = "Learning programming";
    mainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot find text into articles folder input",
            5
    );

    mainPageObject.waitForElementAndClick(
            By.id("android:id/button1"), //опять же, не срабатывает поиск по @text
            "Cannot press OK",
            5
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, no X ",
            5
    );

    mainPageObject.addArticleToReadingList(
            "Java",
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Island of Indonesia']")

    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.TextView[@text='"+nameOfFolder+"']"),
            "Cannot find created folder",
            5
    );

    String secondArticleName = mainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );


    mainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, no X ",
            5
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists' button ",
            5
    );

    mainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/menu_sort_options"),
            "Menu element not present",
            10
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'"+nameOfFolder+"')]"),
            "Cannot find created folder",
            5
    );

    mainPageObject.swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='island of Indonesia']"),
            "Cannot find second article",
            5
    );

    String savedArticleName = mainPageObject.waitForElementAndGetAttribute(
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
    mainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    mainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    mainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article",
            5
    );

    mainPageObject.assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Element not found"
    );

  }


}
