package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

  private static final String
          TITLE = "id:org.wikipedia:id/view_page_title_text",
          FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
          OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
          OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
          ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
          MY_LIST_NAME_INPUT ="id:org.wikipedia:id/text_input",
          MY_LIST_OK_BUTTON = "id:android:id/button1",
          MY_LIST_NAME_TPL = "xpath://android.widget.TextView[@text='{LIST_NAME}']",
          CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";

  private static String getListXpathByName(String nameOfFolder){
    return MY_LIST_NAME_TPL.replace("{LIST_NAME}",nameOfFolder);
  }
  public ArticlePageObject(AppiumDriver driver){
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(TITLE,
            "Cannot find article title",
            15);
  }

  public void instantTitleCheck() {
    this.assertElementPresent(
            "org.wikipedia:id/view_page_title_text",
            "Element not found"
    );
  }

  public String getArticleTitle(){
    WebElement titleElement = waitForTitleElement();
    return titleElement.getAttribute("text");
  }

  public void swipeToFooter(){
    this.swipeUpToFindElement(
            FOOTER_ELEMENT,
            "Cannot find the end of the article",
            20
    );
  }

  public void addArticleToMyList(String nameOfFolder){
    this.waitForElementAndClick(
            OPTIONS_BUTTON,
            "Cannot find button to open article options ",
            5
    );

    this.waitForElementPresent(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            10
    );

    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            5
    );

    this.waitForElementAndClick(
            ADD_TO_MY_LIST_OVERLAY,
            "Cannot find 'got it' tip overlay",
            5
    );

    this.waitForElementAndClear(
            MY_LIST_NAME_INPUT,
            "Cannot find input to set name of articles folder",
            5
    );

    this.waitForElementAndSendKeys(
            MY_LIST_NAME_INPUT,
            nameOfFolder,
            "Cannot find text into articles folder input",
            5
    );

    this.waitForElementAndClick(
           MY_LIST_OK_BUTTON,
            "Cannot press OK",
            5
    );
  }

  public void closeArticle(){

    this.waitForElementAndClick(
            CLOSE_ARTICLE_BUTTON,
            "Cannot close article, no X ",
            5
    );
  }

  public void addArticleToExistingList(String nameOfFolder){
    this.waitForElementAndClick(
            OPTIONS_BUTTON,
            "Cannot find button to open article options ",
            5
    );

    this.waitForElementPresent(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            10
    );

    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            5
    );
    String folderNameXpath = getListXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            folderNameXpath,
            "Cannot find created folder",
            5
    );
  }
}
