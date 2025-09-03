package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    // Locators
    private final By usernameField = By.id("email-field");
    private final By passwordField = By.id("password-field");
    private final By loginButton = By.id("sign-in-button");
    private final By errorMessage = By.xpath("//div[@data-notify='container' and @data-notify-position='top-right']");
    private final By showPasswordButton = By.id("show-password-btn");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterEmail(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isPasswordMasked() {
        String type = driver.findElement(passwordField).getAttribute("type");
        return type.equals("password");
    }

    public void clickShowPassword() {
        driver.findElement(showPasswordButton).click();
    }

    public boolean isPasswordVisible() {
        return driver.findElement(passwordField).getAttribute("type").equals("text");
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("");
    }
}
