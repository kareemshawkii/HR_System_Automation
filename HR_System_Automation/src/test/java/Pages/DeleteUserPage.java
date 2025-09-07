package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteUserPage {
    WebDriver driver;

    // Locators
    private final By deleteIcon = By.xpath("(//button[@title='Delete User'])[1]"); // first user
    private final By confirmButton = By.id("confirmDelete");
    private final By cancelButton = By.id("cancelDelete");
    private final By confirmationDialog = By.id("deleteDialog");
    private final By userRow = By.xpath("(//tr[@class='userRow'])[1]");//number of users

    public DeleteUserPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickDeleteIcon() {
        driver.findElement(deleteIcon).click();
    }

    public boolean isConfirmationDialogDisplayed() {
        return driver.findElement(confirmationDialog).isDisplayed();
    }

    public void confirmDeletion() {
        driver.findElement(confirmButton).click();
    }

    public void cancelDeletion() {
        driver.findElement(cancelButton).click();
    }

    public boolean isUserPresent() {
        return driver.findElements(userRow).size() > 0;
    }
}
