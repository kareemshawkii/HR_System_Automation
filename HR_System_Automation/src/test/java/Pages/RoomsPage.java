package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class RoomsPage {
    protected WebDriver driver;
    //View
    private final By roomCardsContainer = By.cssSelector(".roomsContainer");
    private final By availableButton = By.xpath("//button[normalize-space()='Available']");
    private final By unavailableButton = By.xpath("//button[normalize-space()='Unavailable']");
    //Add
    private final By roomNameInput = By.xpath("//input[@formcontrolname='name']");
    private final By capacityInput = By.xpath("//input[@formcontrolname='capacity']");
    private final By capacityIncreaseButton = By.xpath("//img[@alt='increment']");
    private final By capacityDecreaseButton = By.xpath("//button[.//img[@alt='decrement']]");
    private final By screenButton = By.xpath("//button[normalize-space() = 'Screen']");
    private final By noScreenButton = By.xpath("//button[.//img[@alt='No Screen']]");
    private final By fileInput = By.xpath("//button[@class='uploadBtn']");
    private final By addRoomButton = By.xpath("//button[@type='submit' and @class='ant-btn actionButton ant-btn-primary' and .//span[text()=' Add Room ']]");
    private final By goRoomList = By.xpath("//button[@class='goToUsers ng-star-inserted' and text()='Go to Rooms List']");
    private final By errorMessage = By.xpath("//div[\ncontains(@class, 'ant-notification-notice')]");
   //Edit
    private final By editRoomButton = By.xpath("//img[contains(@src, 'Vector (11).svg')]");
    private final By cancelEdit = By.xpath("//button[@class='backButton ng-star-inserted' and .//img[@alt='Back']]");
    private final By saveChanges = By.xpath("//span[normalize-space()='Save Changes']");
    //Delete
    private final By deleteRoomButton = By.xpath("//span[normalize-space()='Delete']");
    private final By popupDelete = By.cssSelector(".ant-modal-content");
    private final By cancelDelete = By.xpath("//button[@class='cancelDelete' and normalize-space()='Cancel']");
    private final By confirmDelete = By.xpath("//button[@class='confirmDeleteUser' and .//span[normalize-space()='Delete']]");

    public RoomsPage(WebDriver driver) {
        this.driver = driver;
    }

    //View
    public boolean isRoomInCardsContainer(String name) {
        try {
            WebElement container = driver.findElement(roomCardsContainer);
            WebElement room = container.findElement(By.xpath(".//*[contains(text(),'" + name + "')]"));
            return room.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void validateRoomDetails(String roomName, String expectedCapacity, String expectedScreenStatus) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String roomCardXPath = String.format("//h3[contains(@class, 'roomLabel') and normalize-space()='%s']/ancestor::div[contains(@class, 'roomCard')]", roomName);
        WebElement roomCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(roomCardXPath)));
        WebElement capacityElement = roomCard.findElement(By.cssSelector("p.roomCapacity"));
        WebElement screenElement = roomCard.findElement(By.cssSelector("p.screenAvailability"));
        String actualCapacity = capacityElement.getText().trim();
        String actualScreenStatus = screenElement.getText().trim();
        Assert.assertEquals(actualCapacity, expectedCapacity, "The capacity for '" + roomName + "' is incorrect.");
        Assert.assertEquals(actualScreenStatus, expectedScreenStatus, "The screen status for '" + roomName + "' is incorrect.");
    }

    public void clickUnavailable() {
        driver.findElement(unavailableButton).click();
    }

    public void clickAvailable() {
        driver.findElement(availableButton).click();
    }

    //Add
    public void enterRoomName(String name) throws InterruptedException {
        driver.findElement(roomNameInput).clear();
        Thread.sleep(1000);
        driver.findElement(roomNameInput).sendKeys(name);
    }

    public void clickIncreaseCapacity() {
        driver.findElement(capacityIncreaseButton).click();
    }

    public void clickDecreaseCapacity() {
        driver.findElement(capacityDecreaseButton).click();
    }

    public String getCapacityValue() {return driver.findElement(capacityInput).getAttribute("value");}

    public void selectScreen(boolean hasScreen) {
        if (hasScreen) {
            driver.findElement(screenButton).click();
        } else {
            driver.findElement(noScreenButton).click();
        }
    }

    public void uploadFile(String filePath) throws AWTException {
        driver.findElement(fileInput).click();
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

    public void clickAddRoom() {
        driver.findElement(addRoomButton).click();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public void clickGoRoomList() {
        driver.findElement(goRoomList).click();
    }

    //Edit
    public void clickEditRoom() {
        driver.findElement(editRoomButton).click();
    }

    public void clickCancelEditRoom() {
        driver.findElement(cancelEdit).click();
    }

    public void clickSaveChanges() {
        driver.findElement(saveChanges).click();
    }

    //Delete
    public void clickDeleteRoom() {
        driver.findElement(deleteRoomButton).click();
    }

    public void clickCancelDeleteRoom() {
        driver.findElement(cancelDelete).click();
    }

    public void clickConfirmDeleteRoom() {
        driver.findElement(confirmDelete).click();
    }

    public boolean isPopupDelete() {
        return driver.findElement(popupDelete).isDisplayed();
    }

    public void deleteRoomByName(String roomName) {
        try {
            String roomCardXpath = String.format("//div[contains(@class, 'roomCard') and .//h3[normalize-space()='%s']]", roomName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement roomCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(roomCardXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", roomCard);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", roomCard);
            String editButtonXpath = String.format("//div[contains(@class, 'roomCard') and .//h3[normalize-space()='%s']]//img[contains(@src, 'Vector (11).svg')]", roomName);
            By editButtonLocator = By.xpath(editButtonXpath);
            driver.findElement(editButtonLocator).click();
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteRoomButton));
            deleteButton.click();
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmDelete));
            confirmButton.click();
            Assert.assertEquals(driver.findElement(errorMessage).getText(), "Room Deleted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to delete room '" + roomName + "'. Error: " + e.getMessage());
        }
    }

    public void editRoomByName(String roomName) {
        try {
            String roomCardXpath = String.format("//div[contains(@class, 'roomCard') and .//h3[normalize-space()='%s']]", roomName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement roomCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(roomCardXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", roomCard);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", roomCard);
            String editButtonXpath = String.format("//div[contains(@class, 'roomCard') and .//h3[normalize-space()='%s']]//img[contains(@src, 'Vector (11).svg')]", roomName);
            By editButtonLocator = By.xpath(editButtonXpath);
            driver.findElement(editButtonLocator).click();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to delete room '" + roomName + "'. Error: " + e.getMessage());
        }
    }
}
