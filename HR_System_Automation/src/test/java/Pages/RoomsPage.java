package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

public class RoomsPage {
    protected WebDriver driver;

    private final By roomNameInput = By.xpath("//input[@formcontrolname='name']");
    private final By capacityInput = By.xpath("//input[@formcontrolname='capacity']");
    private final By capacityIncreaseButton = By.xpath("//img[@alt='increment']");
    private final By capacityDecreaseButton = By.xpath("//button[.//img[@alt='decrement']]");
    private final By screenButton = By.xpath("//button[normalize-space() = 'Screen']");
    private final By noScreenButton = By.xpath("//button[.//img[@alt='No Screen']]");
    private final By fileInput = By.xpath("//button[@class='uploadBtn']");
    private final By addRoomButton = By.xpath("//button[.//span[normalize-space()='Add Room']]");
    private final By roomCards = By.xpath("//div[@class='roomCard']");
    private final By paginationDots = By.xpath("//div[@part='pagination' and contains(@class, 'swiper-pagination')]");
    private final By successPopup = By.xpath("//img[@src='../../assets/imgs/successAddUser.svg']");
    private final By goRoomList = By.xpath("//button[@class='goToUsers' and normalize-space()='Go to Rooms List']");
    private final By nameError = By.id("room-name-error"); // Replace with actual error element ID
    private final By availableButton = By.xpath("//button[normalize-space()='Available']");
    private final By unavailableButton = By.xpath("//button[normalize-space()='Unavailable']");
    private final By editRoomButton = By.xpath("//img[contains(@src, 'Vector (11).svg')]");
    private final By roomName = By.xpath("//h3[@class='roomLabel' and normalize-space()='test1']");
    private final By roomCapacity = By.xpath("//p[@class='roomCapacity']");
    private final By screenFlag = By.xpath("//p[@class='screenAvailability' and text()='Screen']\n");
    private final By noScreenFlag = By.xpath("//p[@class='screenAvailability' and text()='No screen']\n");


    public RoomsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterRoomName(String name) {
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

    public int getDisplayedRoomCount() {
        return driver.findElements(roomCards).size();
    }

    public WebElement getRoomCardByName(String name) {
        String roomCardXPath = String.format("//div[contains(@class, 'room-card-class') and .//h3[text()='%s']]", name);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(roomCardXPath)));
    }

    public String getRoomCapacityFromCard(WebElement roomCard) {
        return roomCard.findElement(By.xpath(".//span[contains(text(), 'people')]")).getText();
    }

    public boolean doesRoomHaveScreenIcon(WebElement roomCard) {
        return !roomCard.findElements(By.xpath(".//span[text()='Screen']")).isEmpty();
    }

    public void clickPaginationDot(int index) {
        List<WebElement> dots = driver.findElements(paginationDots);
        if (index > 0 && index <= dots.size()) {
            dots.get(index - 1).click();
        }
    }

    public void clickUnavailable() {
        driver.findElement(unavailableButton).click();
    }

    public void clickAvailable() {
        driver.findElement(availableButton).click();
    }

    public void clickGoRoomList() {
        driver.findElement(goRoomList).click();
    }

    public void clickEditRoom() {
        driver.findElement(editRoomButton).click();
    }

    public String getRoomName() {
        return driver.findElement(roomName).getAttribute("value");
    }

    public String getRoomCapacity() {
        return driver.findElement(roomCapacity).getAttribute("value");
    }

    public boolean getScreenFlag() {
        return driver.findElement(screenFlag).isDisplayed();
    }

    public boolean getNoScreenFlag() {
        return driver.findElement(noScreenFlag).isDisplayed();
    }

    // --- Methods for Validation ---
    public boolean isSuccessPopupDisplayed() {
        return driver.findElement(successPopup).isDisplayed();
    }

    public String getNameErrorMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).getText();
    }
}
