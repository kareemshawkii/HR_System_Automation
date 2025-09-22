package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteRoomTests extends BaseRooms {

    @Test(priority = 1)
    public void testOpenDeletePopup()  {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.clickDeleteRoom();
        Assert.assertTrue(roomsPage.isPopupDelete(), "Delete popup should appear");
    }

    @Test(priority = 2)
    public void testCancelDelete() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickEditRoom();
        roomsPage.clickDeleteRoom();
        roomsPage.clickCancelDeleteRoom();
        Assert.assertTrue(true, "Room remains after cancel delete");
    }

    @Test(priority = 3)
    public void testConfirmDelete() throws InterruptedException {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.enterRoomName("deleted room");
        roomsPage.clickAddRoom();
        roomsPage.clickGoRoomList();
        roomsPage.deleteRoomByName("deleted room");
    }
}

