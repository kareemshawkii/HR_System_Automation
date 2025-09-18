package Tests;

import Base.BaseRooms;
import Pages.RoomsPage;
import org.testng.Assert;
import org.testng.annotations.*;

public class ViewRoomsTests extends BaseRooms {

    @Test(priority = 1)
    public void testRoomDisplayed() {
        RoomsPage roomsPage = new RoomsPage(driver);
        Assert.assertTrue(roomsPage.isRoomInCardsContainer("Room 1"),
                "Room should be displayed in container");
    }

    @Test(priority = 2)
    public void testIsRoomInContainer() {
        String roomName = "Room 1";
        RoomsPage roomsPage = new RoomsPage(driver);
            Assert.assertTrue(roomsPage.isRoomInCardsContainer(roomName));
    }

    @Test(priority = 3)
    public void testUnavailableFilter() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickUnavailable();
        Assert.assertEquals(roomsPage.getErrorMessage(), "Status Updated\n" +
                "Room \"Room 1 \" status changed successfully.");
    }

    @Test(priority = 4)
    public void testAvailableFilter() {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.clickAvailable();
        Assert.assertEquals(roomsPage.getErrorMessage(), "Status Updated\n" +
                "Room \"Room 1 \" status changed successfully.");
    }

    @Test(priority = 5)
    public void testRoomDetails()  {
        RoomsPage roomsPage = new RoomsPage(driver);
        roomsPage.validateRoomDetails("Room 1", "3 people", "Screen");
        roomsPage.validateRoomDetails("8888888888", "11 people", "No screen");
    }

}

