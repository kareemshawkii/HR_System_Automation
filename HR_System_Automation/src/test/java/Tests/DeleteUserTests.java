package Tests;

import Base.BaseTestUsers;
import Pages.AddUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.*;

public class DeleteUserTests extends BaseTestUsers {

    @Test
    public void verifyDeleteIconPromptsConfirmation() {
        UsersPage usersPage=new UsersPage(driver);
        DeleteUserPage deleteUserPage =new DeleteUserPage(driver);
        deleteUserPage.clickNextPage();
        usersPage.clickDeleteUser();
        Assert.assertTrue(deleteUserPage.isConfirmationDialogDisplayed(),
                "Confirmation dialog should appear");
    }

    @Test
    public void verifyCancelingDeletionKeepsUser() {
        UsersPage usersPage=new UsersPage(driver);
        DeleteUserPage deleteUserPage =new DeleteUserPage(driver);
        deleteUserPage.clickNextPage();
        usersPage.clickDeleteUser();
        deleteUserPage.cancelDeletion();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
    }

    @Test
    public void verifyConfirmingDeletionRemovesUser() {
        UsersPage usersPage=new UsersPage(driver);
        DeleteUserPage deleteUserPage =new DeleteUserPage(driver);
        deleteUserPage.clickNextPage();
        usersPage.clickDeleteUser();
        deleteUserPage.confirmDeletion();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/users");
        AddUserPage addUserPage = new AddUserPage(driver);
        Assert.assertEquals(addUserPage.getErrorMessage(), "User Deleted\n" +
                "user has been removed.");
    }
}


