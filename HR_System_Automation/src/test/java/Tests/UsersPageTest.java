package Tests;

import Base.BaseTestUsers;
import Pages.LoginPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.UsersPage;
import java.time.Duration;
import java.util.List;

public class UsersPageTest extends BaseTestUsers {

    @Test(priority = 1)
    public void verifyLogoAndAdminInfo() {
        UsersPage usersPage = new UsersPage(driver);
        Assert.assertTrue(usersPage.isLogoDisplayed());
        Assert.assertTrue(usersPage.isAdminProfileDisplayed());
        Assert.assertEquals(usersPage.getAdminName(), "adminIN");
        Assert.assertEquals(usersPage.getAdminRole(), "Admin");
    }

    @Test(priority = 2)
    public void verifySideBar(){
        UsersPage usersPage = new UsersPage(driver);
        Assert.assertTrue(usersPage.sideBar());
    }

    @Test(priority = 3)
    public void verifyUsersManagementHeader() {
        UsersPage usersPage = new UsersPage(driver);
        Assert.assertTrue(usersPage.isUsersManagementHeaderVisible());
        Assert.assertEquals(usersPage.ManagementHeaderText(), "Users Management");
    }

    @Test(priority = 4)
    public void verifyAddUserButton() {
        UsersPage usersPage = new UsersPage(driver);
        Assert.assertTrue(usersPage.isAddUserButtonVisible());
    }

    @Test(priority = 5)
    public void verifySearchWithValidFullName() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("kareem shawki");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 6)
    public void verifySearchWithPartialName() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("kareem");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 7)
    public void verifySearchWithSpecialCharacters() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("@");
        Assert.assertTrue(usersPage.isNoResultsDisplayed());
    }

    @Test(priority = 8)
    public void verifySearchWithBlankInput() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("");
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 9)
    public void verifySearchWithNonExistingData() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("XYZABC");
        Assert.assertTrue(usersPage.isNoResultsDisplayed());
    }

    @Test(priority = 10)
    public void verifyPaginationLimitPerPage() {
        UsersPage usersPage = new UsersPage(driver);
        List<WebElement> rows = usersPage.getSearchResults();
        Assert.assertTrue(rows.size() <= 6, "More than 6 users shown per page!");
    }

    @Test(priority = 11)
    public void verifyNextPageLoadsUsers() {
        UsersPage usersPage = new UsersPage(driver);
        driver.findElement(By.xpath("//button[@title='Next Page']")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 12)
    public void verifyPreviousPageLoadsUsers() {
        UsersPage usersPage = new UsersPage(driver);
        driver.findElement(By.xpath("//button[@title='Previous Page']")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 13)
    public void verifyClickPageNumberNavigates() {
        UsersPage usersPage = new UsersPage(driver);
        driver.findElement(By.xpath("//a[normalize-space()='3']")).click();
        Assert.assertFalse(usersPage.getSearchResults().isEmpty());
    }

    @Test(priority = 14)
    public void verifyLogoutRedirectsToLogin() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.clickLogoutButton();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isInLoginPage());
    }

    @Test(priority = 16)
    public void verifyNetworkFailureMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Logo']")));
            setNetworkOffline(true);
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            Assert.assertNotNull(alert, "❌ Expected alert not shown after shutting down internet.");
            alert.accept();
        } finally {
            setNetworkOffline(false);
        }
    }

    @Test(priority = 16)
    public void verifySQLInjectionBlocked() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("'; DROP TABLE users; --");
        Assert.assertTrue(usersPage.isNoResultsDisplayed());
    }

    @Test(priority = 17)
    public void verifyXSSInjectionBlocked() {
        UsersPage usersPage = new UsersPage(driver);
        usersPage.searchUser("<script>alert('XSS')</script>");
        Assert.assertTrue(usersPage.isNoResultsDisplayed());
    }
}
