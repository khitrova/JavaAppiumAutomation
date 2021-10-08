package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

  private static final String
          TITLE = "org.wikipedia:id/view_page_title_text",
          FOOTER_ELEMENT = "//*[@text='View page in browser']",
          OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
          OPTIONS_ADD_TO_MY_LIST_BUTTON = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
          ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
          MY_LIST_NAME_INPUT ="org.wikipedia:id/text_input",
          MY_LIST_OK_BUTTON = "android:id/button1",
          MY_LIST_NAME_TPL = "//android.widget.TextView[@text='{LIST_NAME}']",
          CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

  private static String getListXpathByName(String nameOfFolder){
    return MY_LIST_NAME_TPL.replace("{LIST_NAME}",nameOfFolder);
  }
  public ArticlePageObject(AppiumDriver driver){
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(By.id(TITLE),
            "Cannot find article title",
            15);
  }

  public void instantTitleCheck() {
    this.assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Element not found"
    );
  }

  public String getArticleTitle(){
    WebElement titleElement = waitForTitleElement();
    return titleElement.getAttribute("text");
  }

  public void swipeToFooter(){
    this.swipeUpToFindElement(
            By.xpath(FOOTER_ELEMENT),
            "Cannot find the end of the article",
            20
    );
  }

  public void addArticleToMyList(String nameOfFolder){
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options ",
            5
    );

    this.waitForElementPresent(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            10
    );

    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            5
    );

    this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY),
            "Cannot find 'got it' tip overlay",
            5
    );

    this.waitForElementAndClear(
            By.id(MY_LIST_NAME_INPUT),
            "Cannot find input to set name of articles folder",
            5
    );

    this.waitForElementAndSendKeys(
            By.id(MY_LIST_NAME_INPUT),
            nameOfFolder,
            "Cannot find text into articles folder input",
            5
    );

    this.waitForElementAndClick(
            By.id(MY_LIST_OK_BUTTON),
            "Cannot press OK",
            5
    );
  }

  public void closeArticle(){

    this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON),
            "Cannot close article, no X ",
            5
    );
  }

  public void addArticleToExistingList(String nameOfFolder){
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options ",
            5
    );

    this.waitForElementPresent(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            10
    );

    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            5
    );
    String folderNameXpath = getListXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            By.xpath(folderNameXpath),
            "Cannot find created folder",
            5
    );
  }
}
