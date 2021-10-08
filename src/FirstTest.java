import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class FirstTest extends CoreTestCase {


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

    assertTrue(
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

  assertTrue(
           "Not all elements contains keyword " + keyword,
           mainPageObject.checkListElementsContainsString(
            By.id("org.wikipedia:id/page_list_item_container"),
            By.id("org.wikipedia:id/page_list_item_title"),
                   keyword

    ));
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
