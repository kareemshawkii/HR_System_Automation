package Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.UsersPage;
import Pages.DeleteUserPage;
import Pages.LoginPage;
import Base.BaseTest;

public class DeleteUserTests extends BaseTest {
    LoginPage loginPage;
    UsersPage usersPage;
    DeleteUserPage deleteUserPage;
    String deletedEmail = "testuser@example.com";
    String deletedPassword = "Password123";

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage(driver);
        usersPage = new UsersPage(driver);
        deleteUserPage = new DeleteUserPage(driver);
        loginPage.enterEmail("admin@innovitics.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();
        usersPage.clickDeleteUser();
    }

    @Test
    public void verifyDeleteIconPromptsConfirmation() {
        deleteUserPage.clickDeleteIcon();
        Assert.assertTrue(deleteUserPage.isConfirmationDialogDisplayed(),
                "Confirmation dialog should appear");
    }

    @Test
    public void verifyConfirmingDeletionRemovesUser() {
        deleteUserPage.clickDeleteIcon();
        deleteUserPage.confirmDeletion();
        Assert.assertFalse(deleteUserPage.isUserPresent(),
                "User should be removed from the table");
    }

    @Test
    public void verifyCancelingDeletionKeepsUser() {
        deleteUserPage.clickDeleteIcon();
        deleteUserPage.cancelDeletion();
        Assert.assertTrue(deleteUserPage.isUserPresent(),
                "User should remain in the table after cancel");
    }

    @Test
    public void verifyDeletedUserCannotLogin() {
        // Step 1: Delete user
        deleteUserPage.clickDeleteIcon();
        deleteUserPage.confirmDeletion();

        // Step 2: Logout and try to log in with deleted user
        loginPage.enterEmail(deletedEmail);
        loginPage.enterPassword(deletedPassword);
        loginPage.clickLogin();

        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials",
                "Deleted user should not be able to login");
    }
}
