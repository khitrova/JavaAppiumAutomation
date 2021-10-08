package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListTests extends CoreTestCase {

  @Test
  public void testSaveFirstArticleToMyList() {

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
  public void testSavingTwoArticles(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    String firstArticleTitle = articlePageObject.getArticleTitle();
    String nameOfFolder = "Learning programming";
    articlePageObject.addArticleToMyList(nameOfFolder);
    articlePageObject.closeArticle();
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Island of Indonesia");
    String secondArticleName = articlePageObject.getArticleTitle();
    articlePageObject.addArticleToExistingList(nameOfFolder);
    articlePageObject.closeArticle();
    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();
    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
    myListsPageObject.openFolderByName(nameOfFolder);
    myListsPageObject.swipeByArticleToDelete(firstArticleTitle);
    myListsPageObject.openSavedArticle("island of Indonesia");
    String savedArticleName = articlePageObject.getArticleTitle();

    Assert.assertEquals(
            "Article name " +savedArticleName+" is not equal to expected "+secondArticleName,
            savedArticleName,
            secondArticleName
    );

  }
}
