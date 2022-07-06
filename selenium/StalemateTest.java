// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class StalemateTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void stalemate() {
    driver.get("https://xadruff.herokuapp.com/");
    driver.findElement(By.id("button")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(2) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(2) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(2) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-options:nth-child(3) > .submenu-option:nth-child(1) .menu-piece")).click();
    driver.findElement(By.cssSelector("#c5 > .w")).click();
    driver.findElement(By.cssSelector("#c6 > .b")).click();
    driver.findElement(By.cssSelector(".board-button")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(1) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(2) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-option:nth-child(2) .menu-piece")).click();
    driver.findElement(By.cssSelector(".submenu-options:nth-child(3) > .submenu-option:nth-child(1) .menu-piece")).click();
    driver.close();
  }
}
