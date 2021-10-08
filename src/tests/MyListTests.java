package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

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
}
