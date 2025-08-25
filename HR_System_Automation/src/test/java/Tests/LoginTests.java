package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {

    @Test
    public void SCRUM_1_verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains(""), "Login failed!");
    }

    @Test
    public void SCRUM_2_verifyInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("adminn");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains(""), "Invalid password test failed!");
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
    public void SCRUM_14_testSessionEndAfterSecondLogin() {
        // Step 1: Login successfully with account in Device 1 (first WebDriver instance)
        WebDriver driver1 = new EdgeDriver();
        driver1.manage().window().maximize();
        driver1.get("");

        LoginPage loginPage1 = new LoginPage(driver1);
        loginPage1.enterEmail("admin@innovitics.com");
        loginPage1.enterPassword("admin");
        loginPage1.clickLogin();

        Assert.assertTrue(driver1.getCurrentUrl().contains(""),
                "Login failed on Device 1.");

        // Step 2: Login with the same account in Device 2 (new WebDriver instance)
        WebDriver driver2 = new EdgeDriver();
        driver2.manage().window().maximize();
        driver2.get("");

        LoginPage loginPage2 = new LoginPage(driver2);
        loginPage2.enterEmail("admin@innovitics.com");
        loginPage2.enterPassword("admin");
        loginPage2.clickLogin();

        Assert.assertTrue(driver2.getCurrentUrl().contains(""),
                "Login failed on Device 2.");

        // Step 3: Verify that Device 1 session ended automatically
        driver1.navigate().refresh();
        Assert.assertTrue(
                driver1.getCurrentUrl().contains("login") ||
                        driver1.getPageSource().toLowerCase().contains("session ended"),
                "First session did not end after second login."
        );

        // Cleanup
        driver1.quit();
        driver2.quit();
    }

    @Test
    public void SCRUM_15_verifyEmailValidations() {
        LoginPage loginPage = new LoginPage(driver);

        // 1- email without .com
        loginPage.enterEmail("admin@innovitics");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Login accepted invalid email without .com!");

        // 2- email without dot
        loginPage.enterEmail("admin@innoviticscom");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Login accepted invalid email without dot!");

        // 3- email with number between .com
        loginPage.enterEmail("admin@innovitics.co1m");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Login accepted invalid email with number inside .com!");

        // 4- email without @
        loginPage.enterEmail("admininnovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Login accepted invalid email without @!");

        // 5- valid email to confirm login works
        loginPage.enterEmail("admin@gmail.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Valid email login failed!");
    }

    @Test
    public void SCRUM_16_verifyEmailCaseInsensitive() {
        LoginPage loginPage = new LoginPage(driver);

        // Lowercase email
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed with lowercase email!");

        // Logout before next attempt (if needed)
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        // Mixed-case email
        loginPage.enterEmail("Admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed with mixed-case email!");
    }

    @Test
    public void SCRUM_50_testLoginWithoutInternet() {
        LoginPage loginPage = new LoginPage(driver);

        //step0
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-field")));

        // 1. Shut the network down
        setNetworkOffline(true);

        // 2. Enter email
        loginPage.enterEmail("admin@innovitics.com");

        // 3. Enter password
        loginPage.enterPassword("admin");

        // 4. Click login
        loginPage.clickLogin();

        // 5. Verify error message
        String error = loginPage.getErrorMessage();
        Assert.assertEquals(error, "close\n" +
                "notifications\n" +
                "Cannot reach to the central server");
    }

    @Test
    public void SCRUM_54_testLoginAndThenShutDownInternet() {
        LoginPage loginPage = new LoginPage(driver);

        //step0
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-field")));

        // 1. Enter email
        loginPage.enterEmail("admin@innovitics.com");

        // 2. Enter password
        loginPage.enterPassword("admin");

        // 3. Click login
        loginPage.clickLogin();

        //4.shut down the internet
        setNetworkOffline(true);

        // 5. Verify error message
        Assert.assertTrue(
                driver.getCurrentUrl().contains("login") ||
                        driver.getPageSource().contains("session ended") ||
                        driver.getPageSource().toLowerCase().contains("no connection"),
                "Session did not end after shutting down internet."
        );
    }
}
