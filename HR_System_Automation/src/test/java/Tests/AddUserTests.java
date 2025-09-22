package Tests;

import Base.BaseTestAddUsers;
import Pages.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.awt.*;

public class AddUserTests extends BaseTestAddUsers {

    @DataProvider(name = "validUserDataProvider")
    public Object[][] getValidUserData() {
        return new Object[][] {
                {"John Doe", "ValidPass123!", "john.doe@innovitics.com", "Android Developer", "Senior"},
                {"Alice Williams", "AnotherSecure@456", "alice.williams@innovitics.com", "Human Resources", "Junior"},
                {"Sam Brown", "P@ssword789", "sam.brown@innovitics.com", "DevOps Engineer", "MidLevel"}
        };
    }

    @DataProvider(name = "invalidNameDataProvider")
    public Object[][] getInvalidNameData() {
        return new Object[][] {
                {"SingleName", "Error\n" +
                        "Username must be in format 'FirstName SecondName'."},
                {"123 Numeric", "Error\n" +
                        "Username can only have chars."},
                {"Special Chars!", "Error\n" +
                        "Username can only have chars."}
        };
    }

    @DataProvider(name = "invalidEmailDataProvider")
    public Object[][] getInvalidEmailData() {
        return new Object[][] {
                {"test@gmail.com", "Error\n" +
                        "Email must be a valid @innovitics.com address."},
                {"invalid-email-format", "Error\n" +
                        "Email must be a valid @innovitics.com address."},
                {"user@.com", "Error\n" +
                        "Email must be a valid @innovitics.com address."}
        };
    }

    @DataProvider(name = "invalidPasswordDataProvider")
    public Object[][] getInvalidPasswordData() {
        return new Object[][] {
                {"nouppercase1!", "Error\n" +
                        "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character [!@#$%^&*]. Minimum length is 8 characters."},
                {"NOLOWERCASE1!", "Error\n" +
                        "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character [!@#$%^&*]. Minimum length is 8 characters."},
                {"NoNumber!", "Error\n" +
                        "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character [!@#$%^&*]. Minimum length is 8 characters."},
                {"NoSpecial1", "Error\n" +
                        "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character [!@#$%^&*]. Minimum length is 8 characters."}
        };
    }

    @Test(priority = 1, description = "Verify adding new users successfully without a photo.", dataProvider = "validUserDataProvider")
    public void verifyAddUserSuccessfullyWithoutPhoto(String name, String password, String email, String department, String level) throws InterruptedException {
        AddUserPage addUserPage = new AddUserPage(driver);
        Thread.sleep(2000);
        addUserPage.selectDepartment(department);
        addUserPage.selectLevel(level);
        addUserPage.enterName(name);
        addUserPage.enterEmail(email);
        addUserPage.enterPassword(password);
        addUserPage.clickAdd();
        addUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test(priority = 2, description = "Verify adding a new user successfully with a photo.")
    public void verifyAddUserSuccessfullyWithPhoto() throws AWTException, InterruptedException {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("Jane Smith");
        addUserPage.enterPassword("ValidPass456!");
        addUserPage.enterEmail("jane.smith@innovitics.com");
        addUserPage.selectDepartment("iOS Developer");
        addUserPage.selectLevel("MidLevel");
        addUserPage.uploadPhoto("C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\my logo w.png");
        Thread.sleep(5000);
        addUserPage.clickAdd();
        addUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test(priority = 3, description = "Verify that clicking cancel redirects to the dashboard.")
    public void verifyCancelButtonRedirects() {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.clickCancel();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test(priority = 4, description = "Verify validation for all empty required fields.")
    public void verifyEmptyFieldsValidation() {
        AddUserPage addUserPage = new AddUserPage(driver);
        Assert.assertTrue(addUserPage.disabledAddButton());
    }

    @Test(priority = 5, description = "Verify validation for invalid name formats.", dataProvider = "invalidNameDataProvider")
    public void verifyInvalidNameFormats(String invalidName, String expectedError)  {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName(invalidName);
        addUserPage.enterPassword("ValidPass123!");
        addUserPage.enterEmail("valid.name@innovitics.com");
        addUserPage.selectDepartment("UI/UX Designer");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), expectedError);
    }

    @Test(priority = 6, description = "Verify validation for invalid email formats and domains.", dataProvider = "invalidEmailDataProvider")
    public void verifyInvalidEmailFormats(String invalidEmail, String expectedError)  {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("External User");
        addUserPage.enterPassword("ValidPass123!");
        addUserPage.enterEmail(invalidEmail);
        addUserPage.selectDepartment("Sales Manager");
        addUserPage.selectLevel("Senior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), expectedError);
    }

    @Test(priority = 7, description = "Verify validation for passwords missing required criteria.", dataProvider = "invalidPasswordDataProvider")
    public void verifyInvalidPasswordFormats(String invalidPassword, String expectedError)  {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("Weak Password");
        addUserPage.enterPassword(invalidPassword);
        addUserPage.enterEmail("weak.pass@innovitics.com");
        addUserPage.selectDepartment("Mobile");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), expectedError);
    }

    @Test(priority = 8, description = "Verify validation for a duplicate email address.")
    public void verifyAddUserWithDuplicateEmail()  {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("Duplicate user");
        addUserPage.enterPassword("ValidPass999!");
        addUserPage.enterEmail("duplicate.user@innovitics.com");
        addUserPage.selectDepartment("Product Owner");
        addUserPage.selectLevel("Senior");
        addUserPage.clickAdd();
        addUserPage.clickGoToUserPageButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://innoviticshr-web.azurewebsites.net/home/users");
        driver.get("https://innoviticshr-web.azurewebsites.net/home/user-form?mode=add");
        addUserPage.enterName("duplicate user");
        addUserPage.enterPassword("ValidPass000!");
        addUserPage.enterEmail("duplicate.user@innovitics.com");
        addUserPage.selectDepartment("Product Owner");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), "Error\n" +
                "Username already exists.", "Duplicate email validation failed.");
    }

    @Test(priority = 9, description = "Verify validation for uploading an invalid file type as a photo.")
    public void verifyInvalidPhotoUpload() throws AWTException {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("Invalid File");
        addUserPage.enterPassword("ValidPassFile1!");
        addUserPage.enterEmail("invalid.file@innovitics.com");
        addUserPage.selectDepartment("Backend Developer");
        addUserPage.selectLevel("Junior");
        addUserPage.uploadPhoto("C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Financial & Technical proposal.pdf");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), "Error\n" +
                "Only PNG and JPG images are allowed");
    }

    @Test(priority = 9, description = "Verify validation for uploading an invalid file type as a photo.")
    public void verifyInvalidPhotoRatioUpload() throws AWTException, InterruptedException {
        AddUserPage addUserPage = new AddUserPage(driver);
        addUserPage.enterName("Invalid Filee");
        addUserPage.enterPassword("ValidPassFile1!");
        addUserPage.enterEmail("invalid.filee@innovitics.com");
        addUserPage.selectDepartment("Backend Developer");
        addUserPage.selectLevel("Junior");
        addUserPage.uploadPhoto("C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png");
        Thread.sleep(3000);
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getErrorMessage(), "Error\n" +
                "User photo must be 1:1 ratio.");
    }
}

