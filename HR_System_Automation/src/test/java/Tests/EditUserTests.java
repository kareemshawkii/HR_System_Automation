package Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.UsersPage;
import Pages.EditUserPage;
import Pages.LoginPage;
import Base.BaseTest;

public class EditUserTests extends BaseTest {
    LoginPage loginPage;
    EditUserPage editUserPage;

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage(driver);
        UsersPage usersPage = new UsersPage(driver);
        editUserPage = new EditUserPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        usersPage.clickEditUser();
    }

    @Test
    public void verifyEditUserScreenPrefilled() {
        Assert.assertEquals(editUserPage.getFullName(),"admin");
        Assert.assertEquals(editUserPage.getEmail(),"admin@innovitics.com");
    }

    @Test
    public void verifyChangeNameAndEmailValid() {
        editUserPage.setFullName("Kareem Shawki");
        editUserPage.setEmail("kareem.shawki@innovitics.com");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getSuccessMessage(), "Changes Saved Successfully");
    }

    @Test
    public void verifyChangeNameAndEmailInvalid() {
        editUserPage.setFullName("Kareem Shawk1");
        editUserPage.setEmail("kareem.shawk1@innovitics.com");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Invalid username & Email Format");
    }

    @Test
    public void verifyChangePasswordValid() {
        editUserPage.setPassword("Strong#123");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getSuccessMessage(), "Changes Saved Successfully");
    }

    @Test
    public void verifyChangePasswordWeak() {
        editUserPage.setPassword("12345678");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Password too weak");
    }

    @Test
    public void verifyChangeDepartment() {
        editUserPage.selectDepartment("Finance");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getSuccessMessage(), "Changes Saved Successfully");
    }

    @Test
    public void verifyChangeLevel() {
        editUserPage.selectLevel("Senior");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getSuccessMessage(), "Changes Saved Successfully");
    }

    @Test
    public void verifyUploadValidPhoto() {
        editUserPage.uploadPhoto("src/test/resources/profile.jpg");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getSuccessMessage(), "Changes Saved Successfully");
    }

    @Test
    public void verifyUploadInvalidPhoto() {
        editUserPage.uploadPhoto("src/test/resources/resume.pdf");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Invalid file format");
    }

    @Test
    public void verifyCancelButton() {
        editUserPage.setFullName("Test User");
        editUserPage.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    // Unauthorized role
    @Test
    public void verifyUnauthorizedRole() {
        driver.get("https://appurl.com/editUser?id=1");//edit page url
        Assert.assertTrue(driver.getPageSource().contains("Access denied")
                || driver.getCurrentUrl().contains("dashboard"));
    }

    // Non-existing user
    @Test
    public void verifyNonExistingUser() {
        driver.get("https://appurl.com/editUser?id=9999");
        Assert.assertTrue(driver.getPageSource().contains("User not found"));
    }

    // Duplicate email
    @Test
    public void verifyDuplicateEmail() {
        editUserPage.setEmail("kareem.shawki@example.com");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Email already exists");
    }
}
