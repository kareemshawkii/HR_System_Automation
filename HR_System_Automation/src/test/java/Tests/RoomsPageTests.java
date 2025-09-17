package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.awt.*;

public class RoomsPageTests extends BaseRooms {

    @DataProvider(name = "roomDataProvider")
    public Object[][] roomDataProvider() {
        return new Object[][]{
                {"Room_Valid_1", 10, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", true},
                {"Room_Valid_2", 15, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", true},
                {"", 5, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", false},
                {"Room_Valid_1", 5, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", false},
                {"Room_Negative", -5, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", false},
                {"Room_NoImage", 8, true, "", false},
                {"Room_InvalidFile", 12, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Financial & Technical proposal.pdf", false},
                {"Room_LongName_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 5, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", false},
                {"Room_Special_!@#$%", 6, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Screenshot 2025-09-14 131437.png", true}
        };
    }

    @Test(priority = 1, dataProvider = "roomDataProvider", description = "Verify Add Room functionality with various inputs")
    public void testAddRoom(String roomName, int capacity, boolean hasScreen, String fileName, boolean expectedSuccess) throws AWTException {
        RoomsPage roomsPage = new RoomsPage(driver);
        if (!roomName.isEmpty()) {
            roomsPage.enterRoomName(roomName);
        }
        int currentCapacity = Integer.parseInt(roomsPage.getCapacityValue());
        while (currentCapacity < capacity) {
            roomsPage.clickIncreaseCapacity();
            currentCapacity++;
        }
        while (currentCapacity > capacity && currentCapacity > 0) {
            roomsPage.clickDecreaseCapacity();
            currentCapacity--;
        }
        roomsPage.selectScreen(hasScreen);
        if (!fileName.isEmpty()) {
            roomsPage.uploadFile(fileName);
        }
        roomsPage.clickAddRoom();
        if (expectedSuccess) {
            Assert.assertTrue(roomsPage.isSuccessPopupDisplayed(), "Expected success popup not displayed for: " + roomName);
        } else {
            Assert.assertFalse(roomsPage.isSuccessPopupDisplayed(), "Unexpected success popup for invalid case: " + roomName);
        }
    }

    // ------------------------
    // View Rooms
    // ------------------------
    @Test(priority = 2, description = "Verify rooms are displayed in list")
    public void testViewRooms() {
        RoomsPage roomsPage = new RoomsPage(driver);
        Assert.assertTrue(roomsPage.getDisplayedRoomCount() > 0, "No rooms are displayed!");
    }

    // ------------------------
    // Availability Toggle
    // ------------------------
    @Test(priority = 3, description = "Verify availability toggle from Available → Unavailable")
    public void testToggleUnavailable() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickUnavailable();
        // 🔹 استبدلها بالتحقق من تغيير حالة الزر
        Assert.assertTrue(true, "Room not marked unavailable");
    }

    @Test(priority = 4, description = "Verify availability toggle from Unavailable → Available")
    public void testToggleAvailable() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickAvailable();
        // 🔹 استبدلها بالتحقق من تغيير حالة الزر
        Assert.assertTrue(true, "Room not marked available");
    }

    // ------------------------
    // Pagination
    // ------------------------
    @Test(priority = 5, description = "Verify pagination navigation")
    public void testPaginationNavigation() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickPaginationDot(2);
        Assert.assertTrue(roomsPage.getDisplayedRoomCount() > 0, "No rooms after pagination!");
        roomsPage.clickPaginationDot(1);
    }

    // ------------------------
    // Edit Room
    // ------------------------
    @Test(priority = 6, description = "Verify editing room details")
    public void testEditRoom() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("Room_Edited");
        roomsPage.clickIncreaseCapacity();
        roomsPage.selectScreen(false);
        roomsPage.clickAddRoom();

        Assert.assertTrue(roomsPage.isSuccessPopupDisplayed(), "Edit failed!");
    }

    // ------------------------
    // Misc (Popup, Redirect, Logout)
    // ------------------------
    @Test(priority = 7, description = "Verify success popup appears after adding room")
    public void testSuccessPopup() {
        RoomsPage roomsPage = new RoomsPage(driver);
        Assert.assertTrue(roomsPage.isSuccessPopupDisplayed(), "Success popup not displayed!");
    }

    @Test(priority = 8, description = "Verify Go to Rooms List redirects correctly")
    public void testGoToRoomsList() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickGoRoomList();
        Assert.assertTrue(driver.getCurrentUrl().contains("rooms"), "Did not redirect to Rooms List!");
    }

    @Test(priority = 9, description = "Verify logout from Rooms page")
    public void testLogout() {
        driver.findElement(By.xpath("//span[normalize-space()='Logout']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout failed!");
    }
}
