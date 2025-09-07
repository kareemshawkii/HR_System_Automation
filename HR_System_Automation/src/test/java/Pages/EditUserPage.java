package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditUserPage {
    WebDriver driver;

    // Locators
    private final By fullName = By.id("fullName");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By department = By.id("department");
    private final By level = By.id("level");
    private final By photoUpload = By.id("photoUpload");
    private final By saveBtn = By.id("saveChanges");
    private final By cancelBtn = By.id("cancelBtn");
    private final By successPopup = By.id("successPopup");
    private final By errorPopup = By.id("errorPopup");

    public EditUserPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void setFullName(String name) {
        driver.findElement(fullName).clear();
        driver.findElement(fullName).sendKeys(name);
    }

    public String getFullName(){
        return driver.findElement(fullName).getAttribute("value");
    }

    public void setEmail(String mail) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(mail);
    }

    public String  getEmail() {
      return driver.findElement(email).getAttribute("value");
    }

    public void setPassword(String pwd) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pwd);
    }

    public void selectDepartment(String dept) {
        driver.findElement(department).sendKeys(dept);
    }

    public void selectLevel(String lvl) {
        driver.findElement(level).sendKeys(lvl);
    }

    public void uploadPhoto(String filePath) {
        driver.findElement(photoUpload).sendKeys(filePath);
    }

    public void clickSave() {
        driver.findElement(saveBtn).click();
    }

    public void clickCancel() {
        driver.findElement(cancelBtn).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successPopup).getText();
    }

    public String getErrorMessage() {
        return driver.findElement(errorPopup).getText();
    }
}