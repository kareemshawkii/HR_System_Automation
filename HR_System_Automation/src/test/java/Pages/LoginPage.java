package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import java.util.List;

public class LoginPage {
    private final WebDriver driver;

    private final By usernameField = By.xpath("//input[@formcontrolname='email']");
    private final By passwordField = By.xpath("//input[@formcontrolname='password']");
    private final By loginButton = By.xpath("//button[.//span[text()=' Login ']]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Failed']]");
    private final By successMessage = By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Successful']]");
    private final By showPasswordButton = By.cssSelector("svg[data-icon=\"eye\"]");
    private final By emailError = By.xpath("//div[text()='Please input a valid email!']");
    private final By passwordError = By.xpath("//div[text()='Please input your Password!']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public boolean isInLoginPage() {
        return driver.findElement(usernameField).isDisplayed() && driver.findElement(passwordField).isDisplayed() && driver.findElement(loginButton).isDisplayed();
    }

    public void clearPassword() {
        WebElement passwordInput = driver.findElement(passwordField);
        String currentValue = passwordInput.getAttribute("value");
        for (int i = 0; i < currentValue.length(); i++) {
            passwordInput.sendKeys(Keys.BACK_SPACE);
        }
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

    public boolean isLoginSuccessful() {
        List<WebElement> elements = driver.findElements(successMessage);
        return !elements.isEmpty() && elements.getFirst().isDisplayed();
    }

    public boolean isEmailErrorDisplayed() {
        List<WebElement> elements = driver.findElements(emailError);
        return !elements.isEmpty() && elements.getFirst().isDisplayed();
    }

    public boolean isPasswordErrorDisplayed() {
        List<WebElement> elements = driver.findElements(passwordError);
        return !elements.isEmpty() && elements.getFirst().isDisplayed();
    }
}
