package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UsersPage {
    WebDriver driver;

    // Locators
    private final By logo = By.cssSelector("img[src*='logo']");
    private final By adminProfilePic = By.cssSelector("img[src*='profile']");
    private final By adminName = By.xpath("//div[contains(text(),'John.Doe')]");
    private final By adminRole = By.xpath("//div[contains(text(),'Admin')]");
    private final By sidebarMenu = By.cssSelector("aside ul li");
    private final By usersManagementHeader = By.xpath("//h2[contains(text(),'Users Management')]");
    private final By tableHeaders = By.cssSelector("table thead th");
    private final By paginationControls = By.cssSelector(".pagination button, .pagination li");
    private final By addUserButton = By.xpath("//button[contains(text(),'Add User')]");
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By usersTableRows = By.cssSelector("table tbody tr");
    private final By noResultsMessage = By.xpath("//*[contains(text(),'No results found')]");
    private final By editIcon = By.cssSelector(".edit-icon");
    private final By deleteIcon = By.cssSelector(".edit-icon");

    public UsersPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
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

    public List<WebElement> getSidebarOptions() {
        return driver.findElements(sidebarMenu);
    }

    public List<WebElement> getTableHeaders() {
        return driver.findElements(tableHeaders);
    }

    public List<WebElement> getPaginationControls() {
        return driver.findElements(paginationControls);
    }

    public boolean isAddUserButtonVisible() {
        return driver.findElement(addUserButton).isDisplayed();
    }

    public void clickAddUserButton() {
        driver.findElement(addUserButton).click();
    }

    public void searchUser(String keyword) {
        WebElement search = driver.findElement(searchBar);
        search.clear();
        search.sendKeys(keyword);
        search.submit();
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
}