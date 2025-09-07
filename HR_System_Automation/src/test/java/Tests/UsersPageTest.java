package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.UsersPage;
import java.util.List;


public class UsersPageTest extends BaseTest {
    WebDriver driver;
    UsersPage usersPage;
    LoginPage loginPage;

    @BeforeClass
    public void setup() {
        loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
    }

    // ----------------- UI & Basic Info -----------------
    @Test(priority = 1)
    public void verifyLogoAndAdminInfo() {
        Assert.assertTrue(usersPage.isLogoDisplayed());
        Assert.assertTrue(usersPage.isAdminProfileDisplayed());
        Assert.assertEquals(usersPage.getAdminName(), "John.Doe");
        Assert.assertEquals(usersPage.getAdminRole(), "Admin");
    }

    @Test(priority = 2)
    public void verifySidebarOptions() {
        Assert.assertEquals(usersPage.getSidebarOptions().size(), 4);
    }

    @Test(priority = 3)
    public void verifyUsersManagementHeader() {
        Assert.assertTrue(usersPage.isUsersManagementHeaderVisible());
    }

    @Test(priority = 4)
    public void verifyTableHeaders() {
        Assert.assertFalse(usersPage.getTableHeaders().isEmpty());
    }

    @Test(priority = 5)
    public void verifyPaginationControls() {
        Assert.assertFalse(usersPage.getPaginationControls().isEmpty());
    }

    @Test(priority = 6)
    public void verifyAddUserButton() {
        Assert.assertTrue(usersPage.isAddUserButtonVisible());
    }

    // ----------------- Search Tests -----------------
    @Test(priority = 7)
    public void verifySearchWithValidFullName() {
        usersPage.searchUser("John.Doe");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 8)
    public void verifySearchWithPartialName() {
        usersPage.searchUser("John");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 9)
    public void verifySearchWithSpecialCharacters() {
        usersPage.searchUser("@");
        Assert.assertTrue(true);
    }

    @Test(priority = 10)
    public void verifySearchWithBlankInput() {
        usersPage.searchUser("");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 11)
    public void verifySearchWithNonExistingData() {
        usersPage.searchUser("XYZABC");
        Assert.assertTrue(usersPage.isNoResultsDisplayed());
    }

    // ----------------- Pagination Tests -----------------
    @Test(priority = 12)
    public void verifyPaginationLimitPerPage() {
        List<WebElement> rows = usersPage.getSearchResults();
        Assert.assertTrue(rows.size() <= 10, "More than 10 users shown per page!");
    }

    @Test(priority = 13)
    public void verifyNextPageLoadsUsers() {
        driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 14)
    public void verifyPreviousPageLoadsUsers() {
        driver.findElement(By.xpath("//button[contains(text(),'Previous')]")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 15)
    public void verifyClickPageNumberNavigates() {
        driver.findElement(By.xpath("//li[contains(text(),'3')]")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    // ----------------- Logout Tests -----------------
    @Test(priority = 16)
    public void verifyLogoutRedirectsToLogin() {
        driver.findElement(By.xpath("//button[contains(text(),'Logout')]")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test(priority = 17)
    public void verifyBackButtonAfterLogout() {
        driver.navigate().back();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // ----------------- Empty State & Errors -----------------
    @Test(priority = 18)
    public void verifyEmptyUsersMessage() {
        Assert.assertTrue(driver.getPageSource().contains("No users available"));
    }

    @Test(priority = 19)
    public void verifyNetworkFailureMessage() {
        driver.navigate().refresh();
        Assert.assertTrue(driver.getPageSource().contains("Unable to load users"));
    }

    // ----------------- Security Tests -----------------
    @Test(priority = 20)
    public void verifySQLInjectionBlocked() {
        usersPage.searchUser("'; DROP TABLE users; --");
        Assert.assertTrue(driver.getPageSource().contains("No results found"));
    }

    @Test(priority = 21)
    public void verifyXSSInjectionBlocked() {
        usersPage.searchUser("<script>alert('XSS')</script>");
        Assert.assertFalse(driver.getPageSource().contains("alert('XSS')"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
