package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;


public class SearchTests extends CoreTestCase {

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
  public void testAmountOfNotEmptySearch(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchLine = "Linkin Park discography";
    searchPageObject.typeSearchLine(searchLine);
    int amountOfSearchElements =searchPageObject.getAmountOfFoundArticles();
    assertTrue(
            "Found too few results",
            amountOfSearchElements>0
    );
  }

  @Test
  public void testAmountOfEmptySearch(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchLine = "dfghdfghdfh";
    searchPageObject.typeSearchLine(searchLine);
    searchPageObject.waitForEmptyResultsLabel();
    searchPageObject.assertThereIsNoResultOfSearch();
  }

  @Test
  public void testSearchElementContainsText(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.assertSearchElementHasText("Search Wikipedia");

  }

  @Test
  public void testCancelSearchWithResults(){

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    int amountOfSearchElements =searchPageObject.getAmountOfFoundArticles();
    assertTrue(
            "Found too few results",
            amountOfSearchElements>0
    );
    searchPageObject.clickCancelSearch();
    searchPageObject.assertSearchResultsAbsent();
  }

  @Test
  public void testSearchResultsContainKeyword() {

    String keyword = "Java";
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine(keyword);
    int amountOfSearchElements =searchPageObject.getAmountOfFoundArticles();
    assertTrue(
            "Found too few results",
            amountOfSearchElements>0
    );
    assertTrue(
            "Not all elements contains keyword " + keyword,
            searchPageObject.assertResultsContainKeyword(keyword));
  }

  @Test
  public void testresultsTitleAndDescription(){
    String keyword = "String",
            firstTitle = "String",
            firstDescription = "Wikimedia disambiguation page",
            secondTitle = "String theory",
            secondDescription = "A fundamental theory of physics",
            thirdTitle = "String instrument",
            thirdDescription ="Musical instrument that generates tones by one or more strings stretched between two points";

    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine(keyword);
    int amountOfSearchElements =searchPageObject.getAmountOfFoundArticles();
    assertTrue(
            "Found too few results",
            amountOfSearchElements>=3
    );
    searchPageObject.waitForElementByTitleAndDescription(firstTitle,firstDescription);
    searchPageObject.waitForElementByTitleAndDescription(secondTitle,secondDescription);
    searchPageObject.waitForElementByTitleAndDescription(thirdTitle,thirdDescription);

  }
}
