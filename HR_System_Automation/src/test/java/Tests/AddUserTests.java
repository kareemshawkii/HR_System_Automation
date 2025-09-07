package Tests;

import Base.BaseTest;
import Pages.AddUserPage;
import Pages.UsersPage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;
import java.nio.file.Paths;

public class AddUserTests extends BaseTest {

    private final AddUserPage addUserPage = new AddUserPage(driver);

    @BeforeMethod
    public void navigateToAddUserPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        UsersPage usersPage = new UsersPage(driver);
        usersPage.clickAddUserButton();
    }

    @DataProvider(name = "validUserDataProvider")
    public Object[][] getValidUserData() {
        return new Object[][] {
                {"John Doe", "ValidPass123!", "john.doe@company.com", "Engineering", "Senior"},
                {"Alice Williams", "AnotherSecure_456", "alice.williams@company.com", "HR", "Junior"},
                {"Sam Brown", "P@ssword789", "sam.brown@company.com", "Marketing", "Mid-level"}
        };
    }

    @DataProvider(name = "invalidNameDataProvider")
    public Object[][] getInvalidNameData() {
        return new Object[][] {
                {"SingleName", "Name must be two words"},
                {"123 Numeric", "Name must contain letters"},
                {"Special Chars!", "Name must contain letters"}
        };
    }

    @DataProvider(name = "invalidEmailDataProvider")
    public Object[][] getInvalidEmailData() {
        return new Object[][] {
                {"test@gmail.com", "Email must use the company's domain"},
                {"invalid-email-format", "Invalid email format."},
                {"user@.com", "Invalid email format."}
        };
    }

    @DataProvider(name = "invalidPasswordDataProvider")
    public Object[][] getInvalidPasswordData() {
        return new Object[][] {
                {"nouppercase1!", "Password must have an uppercase letter..."},
                {"NOLOWERCASE1!", "Password must have an uppercase letter..."},
                {"NoNumber!", "Password must have an uppercase letter..."},
                {"NoSpecial1", "Password must have an uppercase letter..."}
        };
    }


    // --- Test Cases ---

    @Test(priority = 1, description = "Verify adding new users successfully without a photo.", dataProvider = "validUserDataProvider")
    public void verifyAddUserSuccessfullyWithoutPhoto(String name, String password, String email, String department, String level) {
        addUserPage.enterName(name);
        addUserPage.enterPassword(password);
        addUserPage.enterEmail(email);
        addUserPage.selectDepartment(department);
        addUserPage.selectLevel(level);
        addUserPage.clickAdd();
        Assert.assertTrue(addUserPage.isSuccessPopupDisplayed(), "Success popup for user '" + name + "' did not appear.");
    }

    @Test(priority = 2, description = "Verify adding a new user successfully with a photo.")
    public void verifyAddUserSuccessfullyWithPhoto() {
        addUserPage.enterName("Jane Smith");
        addUserPage.enterPassword("ValidPass456!");
        addUserPage.enterEmail("jane.smith@company.com");
        addUserPage.selectDepartment("Design");
        addUserPage.selectLevel("Mid-level");
        String photoPath = Paths.get("src/test/resources/profile.png").toAbsolutePath().toString();
        addUserPage.uploadPhoto(photoPath);
        addUserPage.clickAdd();
        Assert.assertTrue(addUserPage.isSuccessPopupDisplayed(), "'User Added Successfully' popup did not appear.");
    }

    @Test(priority = 3, description = "Verify that clicking cancel redirects to the dashboard.")
    public void verifyCancelButtonRedirects() {
        addUserPage.clickCancel();
        // TODO: Replace with the actual dashboard URL
        Assert.assertEquals(driver.getCurrentUrl(), "https://your-app-url.com/dashboard/users");
    }

    @Test(priority = 4, description = "Verify validation for all empty required fields.")
    public void verifyEmptyFieldsValidation() {
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getNameErrorMessage(), "This field is required.", "Name field empty validation failed.");
        Assert.assertEquals(addUserPage.getEmailErrorMessage(), "This field is required.", "Email field empty validation failed.");
        Assert.assertEquals(addUserPage.getPasswordErrorMessage(), "This field is required.", "Password field empty validation failed.");
    }

    @Test(priority = 5, description = "Verify validation for invalid name formats.", dataProvider = "invalidNameDataProvider")
    public void verifyInvalidNameFormats(String invalidName, String expectedError) {
        addUserPage.enterName(invalidName);
        addUserPage.enterPassword("ValidPass123!");
        addUserPage.enterEmail("valid.name@company.com");
        addUserPage.selectDepartment("HR");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getNameErrorMessage(), expectedError);
    }

    @Test(priority = 6, description = "Verify validation for invalid email formats and domains.", dataProvider = "invalidEmailDataProvider")
    public void verifyInvalidEmailFormats(String invalidEmail, String expectedError) {
        addUserPage.enterName("External User");
        addUserPage.enterPassword("ValidPass123!");
        addUserPage.enterEmail(invalidEmail);
        addUserPage.selectDepartment("Sales");
        addUserPage.selectLevel("Senior");
        addUserPage.clickAdd();
        Assert.assertEquals(addUserPage.getEmailErrorMessage(), expectedError);
    }

    @Test(priority = 7, description = "Verify validation for passwords missing required criteria.", dataProvider = "invalidPasswordDataProvider")
    public void verifyInvalidPasswordFormats(String invalidPassword, String expectedError) {
        addUserPage.enterName("Weak Password");
        addUserPage.enterPassword(invalidPassword);
        addUserPage.enterEmail("weak.pass@company.com");
        addUserPage.selectDepartment("Marketing");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();
        Assert.assertTrue(addUserPage.getPasswordErrorMessage().contains("Password must have an uppercase letter, lowercase letter, a special character, and a number."),
                "Password validation message for password '" + invalidPassword + "' is incorrect.");
    }

    @Test(priority = 8, description = "Verify validation for a duplicate email address.")
    public void verifyAddUserWithDuplicateEmail() {
        // Step 1: Create the initial user.
        addUserPage.enterName("Duplicate Test");
        addUserPage.enterPassword("ValidPass999!");
        addUserPage.enterEmail("duplicate.user@company.com");
        addUserPage.selectDepartment("Finance");
        addUserPage.selectLevel("Senior");
        addUserPage.clickAdd();
        Assert.assertTrue(addUserPage.isSuccessPopupDisplayed(), "Initial user creation failed.");

        // Step 2: Navigate back to the Add User page to try again.
        // This assumes the app stays on the dashboard after success.
        driver.get("https://your-app-url.com/dashboard/users/add"); // Navigate directly

        // Step 3: Attempt to create the user again with the same email.
        addUserPage.enterName("Another Name");
        addUserPage.enterPassword("ValidPass000!");
        addUserPage.enterEmail("duplicate.user@company.com");
        addUserPage.selectDepartment("Finance");
        addUserPage.selectLevel("Junior");
        addUserPage.clickAdd();

        Assert.assertEquals(addUserPage.getEmailErrorMessage(), "Email already exists.", "Duplicate email validation failed.");
    }

    @Test(priority = 9, description = "Verify validation for uploading an invalid file type as a photo.")
    public void verifyInvalidPhotoUpload() {
        addUserPage.enterName("Invalid File");
        addUserPage.enterPassword("ValidPassFile1!");
        addUserPage.enterEmail("invalid.file@company.com");
        addUserPage.selectDepartment("Operations");
        addUserPage.selectLevel("Junior");
        String invalidPhotoPath = Paths.get("src/test/resources/document.txt").toAbsolutePath().toString();
        addUserPage.uploadPhoto(invalidPhotoPath);
        addUserPage.clickAdd();
        // Assert.assertEquals(addUserPage.getPhotoErrorMessage(), "Invalid file type. Please use JPG, PNG.");
    }
}

