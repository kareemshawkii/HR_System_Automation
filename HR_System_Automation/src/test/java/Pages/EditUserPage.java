package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditUserPage {
    WebDriver driver;

    private final By fullName = By.id("fullName");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By department = By.id("department");
    private final By level = By.id("level");
    private final By photoUpload = By.xpath("//label[@class='edit-icon' and .//img[@alt='Edit']]");
    private final By saveBtn = By.xpath("//button[@type='submit' and .//span[normalize-space()='Save Changes']]");
    private final By cancelBtn = By.xpath("//button[@class='ant-btn cancelButton' and .//span[normalize-space()='cancel']]");
    private final By errorPopup = By.xpath("//div[contains(@class, 'ant-notification-notice')]");
    private final By goToUserPageButton = By.cssSelector("button.goToUsers");

    public EditUserPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFullName(String name) throws InterruptedException {
        driver.findElement(fullName).clear();
        Thread.sleep(1000);
        driver.findElement(fullName).sendKeys(name);
    }

    public String getFullName(){
        return driver.findElement(fullName).getAttribute("value");
    }

    public void setEmail(String mail) throws InterruptedException {
        driver.findElement(email).clear();
        Thread.sleep(1000);
        driver.findElement(email).sendKeys(mail);
    }

    public String  getEmail() {
      return driver.findElement(email).getAttribute("value");
    }

    public void setPassword(String pwd) throws InterruptedException {
        driver.findElement(password).clear();
        Thread.sleep(1000);
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

    public String getErrorMessage() {
        return driver.findElement(errorPopup).getText();
    }

    public void clickGoToUserPageButton() {
        driver.findElement(goToUserPageButton).click();
    }
}