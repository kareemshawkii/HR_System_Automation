package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(priority = 1, description = "Verify login with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // Verify navigation to inventory page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "User should be navigated to inventory page after successful login");
    }

    @Test(priority = 2, description = "Verify login with invalid password")
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message should appear for invalid login");
    }

    @Test(priority = 3, description = "Verify login with blank fields")
    public void testBlankFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error message should appear when username is blank");
    }
}
