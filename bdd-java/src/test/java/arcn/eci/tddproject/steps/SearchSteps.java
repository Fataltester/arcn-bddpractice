package arcn.eci.tddproject.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class SearchSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am on the Google search page")
    public void i_am_on_the_google_search_page() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void i_search_for(String query) {

        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );

        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("I should see {string} in the results")
    public void i_should_see_in_the_results(String text) {

        wait.until(ExpectedConditions.titleContains(text));

        assertTrue(driver.getTitle().contains(text));

        driver.quit();
    }
}