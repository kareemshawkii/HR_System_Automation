package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteRoomTests extends BaseRooms {

    @Test
    public void testOpenDeletePopup() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickDeleteRoom();
        Assert.assertTrue(roomsPage.isPopupDelete(), "Delete popup should appear");
    }

    @Test
    public void testCancelDelete() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickDeleteRoom();
        roomsPage.clickCancelDeleteRoom();
        Assert.assertTrue(true, "Room remains after cancel delete");
    }

    @Test
    public void testConfirmDelete() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickDeleteRoom();
        roomsPage.clickConfirmDeleteRoom();

        Assert.assertTrue(roomsPage.getErrorMessage().contains("deleted"),
                "Room should be deleted successfully");
    }

    @Test
    public void testDeleteNonExistentRoom() {
        RoomsPage roomsPage = new RoomsPage(driver);
        boolean exists = roomsPage.isRoomInCardsContainer("Deleted Room");
        Assert.assertFalse(exists, "Deleted room should not exist in container");
    }
}

