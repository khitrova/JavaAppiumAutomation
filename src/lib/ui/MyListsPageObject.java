package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject{

  private static final String
  MENU_SORT_ELEMENTS = "id:org.wikipedia:id/menu_sort_options",
  FOLDER_BY_NAME_TPL = "xpath://lib.ui.android.widget.TextView[@text='{FOLDER_NAME}']",
  ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

  public MyListsPageObject(AppiumDriver driver) {
    super(driver);
  }

  private static String getFolderXpathByName(String nameOfFolder){
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
  }

  private static String getSavedArticleXpathByTitle(String articleTitle){
    return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",articleTitle);
  }

  public void openFolderByName(String nameOfFolder){
    //check to avoid mis click
    this.waitForElementPresent(
            MENU_SORT_ELEMENTS,
            "Cannot not find sort menu element",
            10
    );

    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    this.waitForElementAndClick(
             folderNameXpath,
            "Cannot find folder by name "+nameOfFolder,
            5
    );
  }

  public void swipeByArticleToDelete(String articleTitle){
    this.waitForArticleToAppearByTitle(articleTitle);
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.swipeElementToLeft(
            articleXpath,
            "Cannot find saved article"
    );
    this.waitForArticleToDisappearByTitle(articleTitle);
  }

  public void waitForArticleToAppearByTitle(String articleTitle){

    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(
            articleXpath,
            "Cannot find saved article "+ articleTitle,
            15
    );
  }

  public void waitForArticleToDisappearByTitle(String articleTitle){

    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementNotPresent(
            articleXpath,
            "Saved article still present with title "+ articleTitle,
            15
    );
  }

  public void openSavedArticle(String articleTitle){
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementAndClick(
            articleXpath,
            "Cannot find saved article",
            5
    );
  }
}
