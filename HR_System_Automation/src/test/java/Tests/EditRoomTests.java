package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditRoomTests extends BaseRooms {

    @Test
    public void testEditRoomName() throws InterruptedException {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.editRoomByName("Room_Special");
        roomsPage.enterRoomName("Edited Room");
        roomsPage.clickSaveChanges();
        roomsPage.clickGoRoomList();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
    }

    @Test
    public void testEditRoomCapacity() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.editRoomByName("Edited Room");
        roomsPage.clickIncreaseCapacity();
        roomsPage.clickSaveChanges();
        roomsPage.clickGoRoomList();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
    }

    @Test
    public void testEditRoomScreenFlag() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.editRoomByName("Edited Room");
        roomsPage.selectScreen(true);
        roomsPage.clickSaveChanges();
        roomsPage.clickGoRoomList();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
    }

    @Test
    public void testCancelEdit() throws InterruptedException {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("Temp Name");
        roomsPage.clickCancelEditRoom();
        Assert.assertEquals(driver.getCurrentUrl(), "https://innoviticshr-web.azurewebsites.net/home/rooms");
    }

    @Test
    public void testEditWithInvalidValues() throws InterruptedException {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("");
        roomsPage.clickSaveChanges();
        Assert.assertEquals(roomsPage.getErrorMessage(), "Missing Information\n" +
                "Please enter a room name");
    }
}

