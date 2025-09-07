package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddUserPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By nameInput = By.id("full-name");
    private final By passwordInput = By.id("password");
    private final By departmentDropdown = By.id("department");
    private final By levelDropdown = By.id("level");
    private final By emailInput = By.id("email");
    private final By photoUploadInput = By.id("photo-upload");
    private final By addButton = By.xpath("//button[text()='Add']");
    private final By cancelButton = By.xpath("//button[text()='Cancel']");
    private final By successPopup = By.xpath("//div[contains(text(), 'User Added Successfully')]");
    private final By nameError = By.id("name-error");
    private final By emailError = By.id("email-error");
    private final By passwordError = By.id("password-error");

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
        Select deptSelect = new Select(driver.findElement(departmentDropdown));
        deptSelect.selectByVisibleText(department);
    }

    public void selectLevel(String level) {
        Select levelSelect = new Select(driver.findElement(levelDropdown));
        levelSelect.selectByVisibleText(level);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void uploadPhoto(String filePath) {
        driver.findElement(photoUploadInput).sendKeys(filePath);
    }

    public void clickAdd() {
        driver.findElement(addButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public boolean isSuccessPopupDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }

    public String getNameErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).getText();
    }

    public String getEmailErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailError)).getText();
    }

    public String getPasswordErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }
}
