package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.awt.*;

public class AddRoomTests extends BaseRooms {

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
            roomsPage.clickGoRoomList();
            Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
            Assert.assertTrue(roomsPage.isRoomInCardsContainer(roomName));
        } else {
            Assert.assertEquals(roomsPage.getErrorMessage(),"");
        }
    }
}
