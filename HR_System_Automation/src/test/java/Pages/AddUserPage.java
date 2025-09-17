package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class AddUserPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.id("fullName");
    private final By passwordInput = By.id("password");
    private final By levelDropdown = By.id("level");
    private final By emailInput = By.id("email");
    private final By photoUploadInput = By.xpath("//img[@alt='Edit']");
    private final By addButton = By.xpath("//button[.//span[normalize-space()='Add']]");
    private final By disabledAddButton = By.xpath("//button[@disabled and .//span[normalize-space()='Add']]");
    private final By cancelButton = By.xpath("//button[.//span[normalize-space()='cancel']]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'ant-notification-notice')]");
    private final By goToUserPageButton = By.cssSelector("button.goToUsers");

    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void selectDepartment(String department) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("title")));
        titleInput.click();
        By dropdownLocator = By.xpath("//ul[contains(@class,'autocomplete-dropdown')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        By optionLocator = By.xpath("//ul[contains(@class,'autocomplete-dropdown')]//li[normalize-space()='" + department + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void selectLevel(String level)  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement levelInput = wait.until(ExpectedConditions.elementToBeClickable(levelDropdown));
        levelInput.click();
        By levelOptionLocator = By.xpath("//ul[contains(@class,'autocomplete-dropdown')]//li[normalize-space()='" + level + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(levelOptionLocator));
        option.click();
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void uploadPhoto(String filePath) throws AWTException {
        driver.findElement(photoUploadInput).click();
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void clickAdd() {
        driver.findElement(addButton).click();
    }

    public boolean disabledAddButton() {
        return driver.findElement(disabledAddButton).isDisplayed();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public void clickGoToUserPageButton() {
        driver.findElement(goToUserPageButton).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
