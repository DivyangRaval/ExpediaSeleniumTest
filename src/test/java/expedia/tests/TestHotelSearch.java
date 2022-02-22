package expedia.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import expedia.pages.ExpediaPageObject;
import expedia.pages.ExpediaSearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import testdata.CustomDataProvider;

public class TestHotelSearch {

  WebDriver driver;
  ExpediaSearchPage searchPageObject;
  

  @BeforeMethod
  public void intialization() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-blink-features");
    options.addArguments("--disable-blink-features=AutomationControlled");
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();

  }

  @Test(dataProvider = "search-data", dataProviderClass = CustomDataProvider.class)
  public void verifySearchResults(Map<String, String> data) {
    driver.get("https://www.expedia.co.in");
    searchPageObject = new ExpediaSearchPage(driver);
    searchPageObject.fillData(data);
    searchPageObject.applyFilter(data);
    searchPageObject.parseResults();
    
    if (data.get("reserve").equals("true")) {
      searchPageObject.reserve();
      assertEquals(searchPageObject.RnBheader.getText(), "Review and book");
      System.out.println("Page Title is: " + driver.getTitle());
    }

  }

  @AfterMethod
  public void tearDown() {
    
    driver.quit();
  }

}
