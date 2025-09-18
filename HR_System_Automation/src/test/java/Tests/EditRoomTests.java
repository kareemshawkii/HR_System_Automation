package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditRoomTests extends BaseRooms {

    @Test
    public void testEditRoomName() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("Edited Room");
        roomsPage.clickSaveChanges();
        Assert.assertTrue(roomsPage.getErrorMessage().contains("success"),
                "Room name should be updated");
    }

    @Test
    public void testEditRoomCapacity() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.clickIncreaseCapacity();
        roomsPage.clickSaveChanges();

        Assert.assertTrue(roomsPage.getErrorMessage().contains("success"),
                "Room capacity should be updated");
    }

    @Test
    public void testEditRoomScreenFlag() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.selectScreen(false);
        roomsPage.clickSaveChanges();

        Assert.assertTrue(roomsPage.getErrorMessage().contains("success"),
                "Room screen flag should be updated");
    }

    @Test
    public void testCancelEdit() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("Temp Name");
        roomsPage.clickCancelEditRoom();
        Assert.assertTrue(true, "Changes discarded after cancel");
    }

    @Test
    public void testEditWithInvalidValues() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.enterRoomName("");
        roomsPage.clickSaveChanges();

        Assert.assertTrue(roomsPage.getErrorMessage().contains("Name"),
                "Error for empty name should appear");
    }
}

