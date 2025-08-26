package Pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPageMobile {
    private AndroidDriver driver;

    private By usernameField = AppiumBy.accessibilityId("email-field-accessibility-id");
    private By passwordField = AppiumBy.accessibilityId("password-field-accessibility-id");
    private By loginButton = AppiumBy.accessibilityId("sign-in-button-accessibility-id");
    private By showPasswordButton = AppiumBy.accessibilityId("show-password-btn-accessibility-id");
    // XPath can be slow and brittle, use it as a last resort.
    private By errorMessage = AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Error')]");
    // A hypothetical element on the screen after a successful login.
    private By dashboardHeader = AppiumBy.accessibilityId("dashboard-header");


    // Constructor
    public LoginPageMobile(AndroidDriver driver) {
        this.driver = driver;
    }

    // --- Actions ---

    public void enterEmail(String username) {
        WebElement emailElement = driver.findElement(usernameField);
        emailElement.clear();
        emailElement.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        // It's good practice to wait for the element to be visible
        return driver.findElement(errorMessage).getText();
    }

    public void clickShowPassword() {
        driver.findElement(showPasswordButton).click();
    }


    public boolean isPasswordMasked() {
        return driver.findElement(passwordField).getAttribute("password").equals("true");
    }

    public boolean isPasswordVisible() {
        return driver.findElement(passwordField).getAttribute("password").equals("false");
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public boolean isLoginSuccessful() {
        return !driver.findElements(dashboardHeader).isEmpty();
    }
}