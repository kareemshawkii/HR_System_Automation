package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteUserPage {
    WebDriver driver;

    private final By confirmButton = By.xpath("//button[@class='confirmDeleteUser']//span[normalize-space()='Delete']");
    private final By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private final By confirmationDialog = By.xpath("//p[contains(@class, 'deleteConfirmation') and normalize-space()='Are you sure you want to delete this user ?']");
    private final By nextPage = By.xpath("//a[normalize-space()='2']");

    public DeleteUserPage(WebDriver driver) {
        this.driver = driver;
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

    public void clickNextPage() {
        driver.findElement(nextPage).click();
    }
}
