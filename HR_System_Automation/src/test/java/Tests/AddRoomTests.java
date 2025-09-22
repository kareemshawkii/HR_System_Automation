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
                {"Room_Valid_1", 10, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", true},
                {"Room_Valid_2", 15, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", true},
                {"", 5, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", false},
                {"Room_Valid_1", 5, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", false},
                {"Room_Min", 1, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", false},
                {"Room_Max", 21, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", false},
                {"Room_NoImage", 8, true, "", true},
                {"Room_InvalidFile", 12, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\Financial & Technical proposal.pdf", false},
                {"Room_LongName_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 5, true, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", false},
                {"Room_Special_!@#$%", 6, false, "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\src\\test\\java\\resources\\test.jpg", true}
        };
    }

    @Test(priority = 1, dataProvider = "roomDataProvider", description = "Verify Add Room functionality with various inputs")
    public void testAddRoom(String roomName, int capacity, boolean hasScreen, String fileName, boolean expectedSuccess) throws AWTException, InterruptedException {
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
        Thread.sleep(700);
        if (!roomName.equals("Room_Min") &&!roomName.equals("Room_Max")) {
                if (!fileName.isEmpty()) {
                    roomsPage.uploadFile(fileName);
                    Thread.sleep(3000);
                }
                roomsPage.clickAddRoom();
        }
        if (expectedSuccess) {
            roomsPage.clickGoRoomList();
            Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
            Assert.assertTrue(roomsPage.isRoomInCardsContainer(roomName));
        } else {
            if (roomName.isEmpty()) {
                Assert.assertEquals(roomsPage.getErrorMessage(),"Missing Information\n" +
                        "Please enter a room name");
            }
            else if (capacity < 2) {
                Assert.assertEquals(roomsPage.getErrorMessage(),"Minimum Capacity\n" +
                        "Room capacity cannot be less than 2 people");
            }
            else if (capacity > 20) {
                Assert.assertEquals(roomsPage.getErrorMessage(),"Maximum Capacity\n" +
                        "Room capacity cannot exceed 20 people");
            }
            else if (roomName.length() > 50) {
                Assert.assertEquals(roomsPage.getErrorMessage(),"Unable to Process Room Request\n" +
                        "Room name must be between 2 and 50 characters");
            }
            else if (fileName.endsWith(".pdf")){
                Assert.assertEquals(roomsPage.getErrorMessage(),"Invalid File Type\n" +
                        "Only JPG and PNG images are allowed.");
            }
            else {
                Assert.assertEquals(roomsPage.getErrorMessage(),"Unable to Process Room Request\n" +
                        "Room name already exists.");
            }
        }
    }
}
