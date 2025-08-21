package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.devtools.v129.network.Network;
import java.time.Duration;
import java.util.Optional;
import org.openqa.selenium.devtools.v129.network.model.ConnectionType;


public class BaseTest {
    protected WebDriver driver;
    protected DevTools devTools;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\kimok\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void setNetworkOffline(boolean offline) {
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));

        devTools.send(Network.emulateNetworkConditions(
                offline,                         // offline or not
                100,                             // latency in ms
                0,                               // download throughput
                0,                               // upload throughput
                Optional.of(ConnectionType.CELLULAR3G), // connection type
                Optional.empty(),                 // maxTotalBufferSize
                Optional.empty(),                 // maxResourceBufferSize
                Optional.empty()                  // throughputKbps
        ));
    }
}
