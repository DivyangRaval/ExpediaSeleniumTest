package expedia.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import io.github.bonigarcia.wdm.WebDriverManager;


public class ExpediaSearchPage extends ExpediaPageObject {

  public ExpediaSearchPage(WebDriver driver) {
    super(driver);
  }
  // locators

  @FindBy(xpath = "//*[@data-stid='location-field-destination-menu-trigger']")
  WebElement destinationTrigger;

  @FindBy(xpath = "//*[@id='location-field-destination']")
  WebElement destinationInput;

  @FindBy(xpath = "//*[@data-stid='location-field-destination-results']//button")
  WebElement destinationResult;

  @FindBy(
      xpath = "//*[@data-stid='input-date'][@placeholder='Check-in']/following::button[contains(@aria-label,'Check-in')]")
  WebElement checkinBox;

  @FindBy(xpath = "//*[@data-stid='date-picker-month'][1]")
  WebElement checkinDatepicker;

  @FindBy(xpath = "//*[@data-stid='apply-date-picker']")
  WebElement datepickerDoneButton;

  @FindBy(xpath = "//*[@data-testid='travelers-field-trigger']")
  WebElement travellersBox;

  @FindBy(xpath = "//*[@id='adult-input-0']//following-sibling::button")
  WebElement adultsIncrease;

  @FindBy(xpath = "//*[@id='adult-input-0']")
  WebElement adultsCount;

  @FindBy(xpath = "//*[@id='child-input-0']")
  WebElement childCount;

  @FindBy(xpath = "//*[@id='child-input-0']//following-sibling::button")
  WebElement childIncrease;


  @FindBy(xpath = "//*[@data-testid='guests-done-button']")
  WebElement travellersDoneButton;

  @FindBy(xpath = "//*[@data-testid='submit-button'][contains(text(),'Search')]")
  WebElement searchButton;


  @FindBy(xpath = "//*[@class='results']")
  WebElement searchResults;


  @FindBy(id = "mealPlan-0-FREE_BREAKFAST")
  WebElement mealBF;


  @FindBy(id = "mealPlan-1-HALF_BOARD")
  WebElement mealLN;


  @FindBy(id = "mealPlan-2-FULL_BOARD")
  WebElement mealDN;


  @FindBy(id = "mealPlan-3-ALL_INCLUSIVE")
  WebElement mealAll;

  @FindBy(id = "sort")
  WebElement sortBy;

  @FindBy(xpath = "//*[@data-stid='sticky-button']")
  WebElement reserveButtonNav;

  @FindBy(
      xpath = "//button[@type='button']/span[@aria-hidden='true'][contains(text(),'Reserve')][1]")
  WebElement reserveButtonRoom;

  @FindBy(xpath = "//*[@type='submit'][contains(text(),'Pay now')]")
  WebElement payNowButton;

  @FindBy(xpath = "//h1[@class='section-header-main  ']")
  public WebElement RnBheader;


  @FindBy(xpath = "//*[@class='results']//ol[@class='results-list no-bullet']/li//a")
  WebElement cheapestHotel;



  public void waitFor(WebElement locator) {

    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.elementToBeClickable(locator));

  }


  public void addTravellers(String a, String c, String age) {
    Select childAge;

    if (adultsCount.getAttribute("value").equals(a))
      System.out.println("adults travellers already at default value");
    else {
      for (int i = 0; i < Integer.valueOf(a); i++)
        adultsIncrease.click();
    }

    if (childCount.getAttribute("value").equals(c))
      System.out.println("childeren travellers already at default value");
    else {
      for (int i = 0; i < Integer.valueOf(c); i++) {
        childIncrease.click();
        childAge = new Select(driver.findElement(By.id("child-age-input-0-" + i + "")));
        childAge.selectByValue(age);

      }
    }
    travellersDoneButton.click();
  }

  public void fillData(Map<String, String> data) {
    destinationTrigger.click();
    destinationInput.sendKeys(data.get("city"));
    destinationInput.sendKeys(Keys.ENTER);
    checkinBox.click();
    checkinDatepicker.findElement(By.xpath("//button[@data-day='"+ data.get("date") + "']")).click();
    datepickerDoneButton.click();
    travellersBox.click();
    addTravellers(data.get("adults"), data.get("child"), data.get("childAge"));
    searchButton.click();
    waitFor(searchResults);

  }

  public void applyFilter(Map<String, String> data) {
    /*
     * Price Filter [Disabled as of now as couldn't find valid list of input which can show results within given price range]
     * --Feel free to enable with valid input 
     * driver.findElement(By.xpath("//*[contains(@id,'price')]//following::span[contains(text(),'to Rs"+data.get("budget")+"')]")).click(); 
     * waitFor(searchResults);
     */

    // Ratings Filter
    driver.findElement(By.xpath(
        "//*[contains(@id,'guestRating')]//following::label[contains(@for,'guestRating')]/span[contains(text(),'"
            + data.get("rating") + "')]"))
        .click();
    
    waitFor(searchResults);
    

    for (int i = 1; i <= Integer.valueOf(data.get("meals")); i++) {
      switch (data.get("meal" + i + "").toLowerCase()) {
        case "breakfast":
                    mealBF.click();
                    waitFor(searchResults);
                    break;
        case "lunch":
                    mealLN.click();
                    waitFor(searchResults);
                    break;
        case "dinner":
                    mealDN.click();
                    waitFor(searchResults);
                    break;
        case "all":
                    mealAll.click();
                    waitFor(searchResults);
                    break;
        default:
                System.out.println("No Meal preference Provided");
                break;
      }
    }

  }

  public void parseResults() {
    Select sort = new Select(sortBy);
    sort.selectByValue("PRICE_LOW_TO_HIGH");
    
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    List<WebElement> hotelList = driver
        .findElements(By.xpath("//*[@class='results']//ol[@class='results-list no-bullet']/li/h3"));
    waitFor(searchResults);


    if (hotelList.size() == 0)
      System.out.println("No Results Found");
    else {
      System.out.println("List of Hotels are: ");
      for (WebElement hotel : hotelList)
        System.out.println(hotel.getText());

      System.out.println("*****************************************");
      System.out.println("Most affordable Hotel is:" + hotelList.get(0).getText());
      System.out.println("*****************************************");
    }

  }

  public void reserve() {

    cheapestHotel.click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    // switch to new tab
    ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
    driver.switchTo().window(newTab.get(1));
    System.out.println(driver.getTitle());
    reserveButtonNav.click();
    waitFor(reserveButtonRoom);
    reserveButtonRoom.click();

    if (payNowButton.isDisplayed())
      payNowButton.click();

    waitFor(RnBheader);

  }

}
