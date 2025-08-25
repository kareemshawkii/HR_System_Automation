package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.devtools.v126.network.Network;
import java.time.Duration;
import java.util.Optional;
import org.openqa.selenium.devtools.v126.network.model.ConnectionType;


public class BaseTest {
    protected WebDriver driver;
    protected DevTools devTools;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\kimok\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver.get("https://tvadmin.taqavolt.com");
    }

    @AfterMethod
    public void tearDown() {
        if (devTools != null) {
            // Disable the network domain and close the DevTools session
            devTools.send(Network.disable());
            devTools.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    protected void setNetworkOffline(boolean offline) {
        String adapterName = "WiFi"; // الاسم الصحيح من netsh interface show interface
        String command = offline
                ? "netsh interface set interface \"" + adapterName + "\" admin=disable"
                : "netsh interface set interface \"" + adapterName + "\" admin=enable";
        try {
            Process process = new ProcessBuilder("cmd.exe", "/c", command)
                    .redirectErrorStream(true)
                    .start();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ Network interface \"" + adapterName + "\" set to " + (offline ? "OFFLINE" : "ONLINE"));
            } else {
                System.out.println("❌ Failed to change network state. Exit code: " + exitCode);
            }
            Thread.sleep(3000); // wait for effect
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

