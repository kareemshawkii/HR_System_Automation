package Tests;

import Base.BaseTestEditUsers;
import Pages.AddUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.EditUserPage;
import java.awt.*;

public class EditUserTests extends BaseTestEditUsers {

    @Test
    public void verifyEditUserScreenPrefilled() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        Assert.assertEquals(editUserPage.getFullName(),"kareem shawki");
        Assert.assertEquals(editUserPage.getEmail(),"kareem.shawki@innovitics.com");
    }

    @Test
    public void verifyChangeNameAndEmailValid() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.setFullName("kareem mohamed");
        editUserPage.setEmail("kareem.mohamed@innovitics.com");
        editUserPage.clickSave();
        editUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test
    public void verifyChangeNameAndEmailInvalid() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.setFullName("Kareem Shawk1");
        editUserPage.setEmail("kareem.shawk1@innovitics.com");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Error\n" +
                "Email username can only contain letters and dots (e.g., john.doe).");
    }

    @Test
    public void verifyChangePasswordValid() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.setPassword("User@123");
        editUserPage.clickSave();
        editUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test
    public void verifyChangePasswordWeak() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        editUserPage.setPassword("12345678");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Error\n" +
                "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character [!@#$%^&*]. Minimum length is 8 characters.");
    }

    @Test
    public void verifyChangeDepartment() {
        AddUserPage addUserPage = new AddUserPage(driver);
        EditUserPage editUserPage = new EditUserPage(driver);
        addUserPage.selectDepartment("Mobile");
        editUserPage.clickSave();
        editUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test
    public void verifyChangeLevel() {
        AddUserPage addUserPage = new AddUserPage(driver);
        EditUserPage editUserPage = new EditUserPage(driver);
        addUserPage.selectLevel("Senior");
        editUserPage.clickSave();
        editUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test
    public void verifyUploadValidPhoto() throws AWTException, InterruptedException {
        AddUserPage addUserPage = new AddUserPage(driver);
        EditUserPage editUserPage = new EditUserPage(driver);
        addUserPage.uploadPhoto("C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\my logo w.png");
        Thread.sleep(5000);
        editUserPage.clickSave();
        editUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");    }

    @Test
    public void verifyUploadInvalidPhoto() throws AWTException, InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.uploadPhoto("C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Financial & Technical proposal.pdf");
        Thread.sleep(5000);
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "Error\n" +
                "Only JPG, JPEG, PNG files are allowed.");
    }

    @Test
    public void verifyCancelButton() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.setFullName("Test User");
        editUserPage.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("home"));
    }

    @Test
    public void verifyDuplicateEmail() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.setEmail("kareem.mohamed@innovitics.com");
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "No changes detected\n" +
                "You didn’t update anything.");
    }

    @Test
    public void verifyNoChange() throws InterruptedException {
        EditUserPage editUserPage = new EditUserPage(driver);
        Thread.sleep(3000);
        editUserPage.clickSave();
        Assert.assertEquals(editUserPage.getErrorMessage(), "No changes detected\n" +
                "You didn’t update anything.");
    }
}
