package Tests;

import Base.BaseTest;
import Pages.*;
import org.openqa.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class LoginTests extends BaseTest {

    @Test
    public void SCRUM_1_verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Successful']]")));
        Assert.assertTrue(loginPage.isLoginSuccessful());
    }

    @Test
    public void SCRUM_2_verifyInvalidPassword() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("adminn");
        loginPage.clickLogin();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."), "Invalid password test failed!");
    }

    @Test
    public void SCRUM_3_verifyInvalidEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("adminn@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."));
    }

    @Test
    public void SCRUM_4_verifyInvalidEmailAndPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("adminn@innovitics.com");
        loginPage.enterPassword("adminn");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."));
    }

    @Test
    public void SCRUM_5_verifyEmptyEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(" ");
        loginPage.enterPassword("admin");
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
    }

    @Test
    public void SCRUM_6_verifyEmptyPassword() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword(" ");
        loginPage.clearPassword();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed());
    }

    @Test
    public void SCRUM_7_verifyEmptyPasswordAndEmail() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(" ");
        loginPage.enterPassword(" ");
        loginPage.clearPassword();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed());
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg[data-icon=\"eye-invisible\"]")));
        Assert.assertTrue(loginPage.isPasswordVisible(), "Password is not visible after clicking show button!");
    }

    @Test
    public void SCRUM_10_verifyPasswordCaseSensitive() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("Admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."));
    }

    @Test
    public void SCRUM_11_verifySpacesBeforeEmail() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(" admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
    }

    @Test
    public void SCRUM_11_verifySpacesAfterEmail() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com ");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
    }

    @Test
    public void SCRUM_12_verifySpacesBeforePassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword(" admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."));
    }

    @Test
    public void SCRUM_12_verifySpacesAfterPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin ");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login Failed\n" +
                "Invalid email or password."));
    }

    @Test
    public void SCRUM_13_verifySQLInjectionProtection() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("' OR '1'='1");
        loginPage.enterPassword("' OR '1'='1");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
    }

    @Test
    public void SCRUM_14_testSessionEndAfterSecondLogin() {
        WebDriver driver2;
        System.setProperty("webdriver.edge.driver", "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\edgedriver_win64\\msedgedriver.exe");
        driver2 = new EdgeDriver();
        driver2.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver2.get("https://innoviticshr-web.azurewebsites.net");
            try {
                LoginPage loginPage1 = new LoginPage(driver);
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='email']")));
                loginPage1.enterEmail("admin@innovitics.com");
                loginPage1.enterPassword("admin");
                loginPage1.clickLogin();
                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Successful']]")));
                Assert.assertTrue(loginPage1.isLoginSuccessful());
                LoginPage loginPage2 = new LoginPage(driver2);
                WebDriverWait wait2 = new WebDriverWait(driver2, Duration.ofSeconds(20));
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='email']")));
                loginPage2.enterEmail("admin@innovitics.com");
                loginPage2.enterPassword("admin");
                loginPage2.clickLogin();
                wait2.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Successful']]")
                ));
                Assert.assertTrue(loginPage2.isLoginSuccessful());
                driver.navigate().refresh();
                WebDriverWait waitAfterRefresh = new WebDriverWait(driver, Duration.ofSeconds(15));
                try {
                    waitAfterRefresh.until(ExpectedConditions.urlContains("/login"));
                } catch (TimeoutException e) {
                    waitAfterRefresh.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@formcontrolname='email']")
                    ));
                }
                Assert.assertTrue( loginPage1.isInLoginPage());
            } finally {
                if (driver != null) driver.quit();
                if (driver2 != null) driver2.quit();
            }
        }

    @Test
    public void SCRUM_15_verifyEmailValidations() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
        loginPage.enterEmail("admin@innoviticscom");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
        loginPage.enterEmail("admin@innovitics.co1m");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.getErrorMessage(),"Login Failed\n" + "Email must be a valid @innovitics.com address.");
        loginPage.enterEmail("admininnovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isEmailErrorDisplayed());
        loginPage.enterEmail("admin@gmail.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.getErrorMessage(), "Login Failed\n" + "Email must be a valid @innovitics.com address.");
    }

    @Test
    public void SCRUM_16_verifyEmailCaseInsensitive() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("Admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ant-notification-notice') and .//div[text()='Login Successful']]")));
        Assert.assertTrue(loginPage.isLoginSuccessful());
    }

    @Test(priority = 50)
    public void SCRUM_50_testLoginWithoutInternet() {
        LoginPage loginPage = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='email']")));
        try {
            setNetworkOffline(true);
            loginPage.enterEmail("admin@innovitics.com");
            loginPage.enterPassword("admin");
            loginPage.clickLogin();
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            alertWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("⚠️ Alert Message: " + alertText);
            Assert.assertTrue(alertText.toLowerCase().contains("internet"), "❌ Expected alert about internet, but got: " + alertText);
            alert.accept();
        } catch (TimeoutException e) {
            Assert.fail("❌ Expected an alert for no internet, but none appeared.");
        } finally {
            setNetworkOffline(false);
        }
    }
}


