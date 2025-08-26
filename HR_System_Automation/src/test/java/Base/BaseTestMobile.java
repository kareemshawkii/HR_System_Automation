package Base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTestMobile {
    protected AndroidDriver driver;
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Medium_Phone_API_36.0");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability("appPackage", "com.example.app.package"); // <-- REPLACE
        caps.setCapability("appActivity", ".MainActivity"); // <-- REPLACE

        // The URL of the running Appium server
        URL appiumServerUrl = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(appiumServerUrl, caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            if (!driver.getConnection().isWiFiEnabled()) {
                setNetworkOnline();
            }
            driver.quit();
        }
    }


    protected void setNetworkConnection(boolean offline) {
        if (offline) {
            System.out.println("Setting network to OFFLINE");
            driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().withDataDisabled().build());
        } else {
            System.out.println("Setting network to ONLINE");
            driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
        }

        ConnectionState state = driver.getConnection();
        System.out.println("Is WiFi enabled: " + state.isWiFiEnabled());
        System.out.println("Is Data enabled: " + state.isDataEnabled());
    }

    protected void setNetworkOnline() {
        setNetworkConnection(false);
    }

    protected void setNetworkOffline() {
        setNetworkConnection(true);
    }
}
