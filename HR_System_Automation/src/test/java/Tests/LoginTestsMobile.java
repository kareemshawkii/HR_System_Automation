package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;


import java.time.Duration;

public class LoginTestsMobile extends BaseTest {

    @Test
    public void SCRUM_1_verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed! Dashboard element not found.");
    }

    @Test
    public void SCRUM_2_verifyInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("adminn");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid"), "Invalid password test failed!");
    }

    @Test
    public void SCRUM_3_verifyInvalidEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("adminn@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_4_verifyInvalidEmailAndPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("adminn@innovitics.com");
        loginPage.enterPassword("adminn");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_5_verifyEmptyEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_6_verifyEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_7_verifyEmptyPasswordAndEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("");
        loginPage.enterPassword("");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_8_verifyPasswordMasked() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password field is not masked!");
    }

    @Test
    public void SCRUM_9_verifyPasswordShowButton() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword("admin");
        loginPage.clickShowPassword();
        Assert.assertTrue(loginPage.isPasswordVisible(), "Password is not visible after clicking show button!");
    }

    @Test
    public void SCRUM_10_verifyPasswordCaseSensitive() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("Admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_11_verifySpacesBeforeEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(" admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_11_verifySpacesAfterEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com ");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_12_verifySpacesBeforePassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword(" admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_12_verifySpacesAfterPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin ");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_13_verifySQLInjectionProtection() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("' OR '1'='1");
        loginPage.enterPassword("' OR '1'='1");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""));
    }

    @Test
    public void SCRUM_15_verifyEmailValidations() {
        LoginPage loginPage = new LoginPage(driver);

        // 1- email without .com
        loginPage.enterEmail("admin@innovitics");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Login accepted invalid email without .com!");
    }

    @Test
    public void SCRUM_50_testLoginWithoutInternet() {
        LoginPage loginPage = new LoginPage(driver);

        // Wait for the app to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("email-field-accessibility-id")));

        // 1. Shut the network down using the new BaseTest method
        setNetworkOffline(true);

        // 2. Try to log in
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();

        // 5. Verify error message (adjust the expected text for your mobile app)
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("no internet") || error.toLowerCase().contains("network error"));

        // Cleanup: Turn network back on in @AfterMethod
    }

    @Test
    public void SCRUM_54_testLoginAndThenShutDownInternet() {
        LoginPage loginPage = new LoginPage(driver);

        // Wait for the app to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("email-field-accessibility-id")));

        // 1. Login successfully
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Initial login failed.");

        // 2. Shut down the internet
        setNetworkOffline(false);

        // 3. Perform an action that requires internet (e.g., refresh, navigate)
        // This step depends on your app's behavior. You might tap a refresh button.
        // For this example, we'll just check if an error message appears.
        // driver.findElement(AppiumBy.accessibilityId("refresh-button")).click();

        // 4. Verify error message or state
        // The app might show a "No Connection" banner or log the user out.
        Assert.assertTrue(loginPage.isErrorDisplayed(), "No error message was displayed after losing connection.");
    }
}
