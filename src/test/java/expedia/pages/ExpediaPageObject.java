package expedia.pages;

import java.util.Map;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ExpediaPageObject {

  protected WebDriver driver;

  public ExpediaPageObject(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

}
