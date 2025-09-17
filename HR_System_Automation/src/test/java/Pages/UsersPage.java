package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import java.util.List;

public class UsersPage {
    protected WebDriver driver;

    private final By logo = By.xpath("//img[@alt='Logo']");
    private final By adminProfilePic = By.cssSelector("img[src*=\"innovitics-HR/user.jpg\"]");
    private final By adminName = By.xpath("//h1[@class='name']");
    private final By users = By.xpath("//a[text()=' Users ']");
    private final By meetings = By.xpath("//a[normalize-space()='Bookings']");
    private final By rooms = By.xpath("//a[normalize-space()='Rooms']");
    private final By adminRole = By.xpath("//p[@class='role']");
    private final By logoutButton = By.xpath("//span[@class='ng-star-inserted' and text()='Logout']");
    private final By usersTableRows = By.cssSelector("tr.ant-table-row.ng-star-inserted");
    private final By usersManagementHeader = By.xpath("//p[@class='userManagement']");
    private final By addUserButton = By.xpath("//button[@class='ant-btn ant-btn-primary' and .//span[normalize-space()='Add User']]");
    private final By searchBar = By.cssSelector("input[placeholder=\"Search users...\"]");
    private final By noResultsMessage = By.xpath("//nz-empty//p[normalize-space()='No Data']");
    private final By editIcon = By.xpath("//tr[\n.//td[normalize-space()='kareem mohamed'] \n]//img[@alt='Edit']/ancestor::button");
    private final By deleteIcon = By.xpath("//tr[\n.//td[normalize-space()='invalid.file@innovitics.com']\n]//img[@alt='delete']/ancestor::button");

    public UsersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }

    public boolean isAdminProfileDisplayed() {
        return driver.findElement(adminProfilePic).isDisplayed();
    }

    public String getAdminName() {
        return driver.findElement(adminName).getText();
    }

    public String getAdminRole() {
        return driver.findElement(adminRole).getText();
    }

    public boolean isUsersManagementHeaderVisible() {
        return driver.findElement(usersManagementHeader).isDisplayed();
    }

    public String ManagementHeaderText() {
        return driver.findElement(usersManagementHeader).getText();
    }

    public boolean isAddUserButtonVisible() {
        return driver.findElement(addUserButton).isDisplayed();
    }

    public void clickAddUserButton() {
        driver.findElement(addUserButton).click();
    }

    public void searchUser(String keyword) {
        driver.findElement(searchBar).clear();
        driver.findElement(searchBar).sendKeys(keyword);
    }

    public List<WebElement> getSearchResults() {
        return driver.findElements(usersTableRows);
    }

    public boolean isNoResultsDisplayed() {
        return driver.findElement(noResultsMessage).isDisplayed();
    }

    public void clickEditUser() {
        driver.findElement(editIcon).click();
    }

    public void clickDeleteUser() {
        driver.findElement(deleteIcon).click();
    }

    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    public void clickMeetings() {
        driver.findElement(meetings).click();
    }

    public void clickRooms() {
        driver.findElement(rooms).click();
    }

    public boolean sideBar(){
        return driver.findElement(users).isDisplayed() && driver.findElement(meetings).isDisplayed() && driver.findElement(rooms).isDisplayed() && driver.findElement(logoutButton).isDisplayed();
    }
}