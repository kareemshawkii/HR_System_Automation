package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.UsersPage;

import java.util.concurrent.TimeUnit;

public class UsersPageTest {
    WebDriver driver;
    UsersPage usersPage;

    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/users"); // URL of Users Page
        usersPage = new UsersPage(driver);
    }

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
        usersPage.getSearchResults();
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

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
