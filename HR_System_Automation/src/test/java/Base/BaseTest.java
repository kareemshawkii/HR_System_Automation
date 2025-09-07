package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import java.time.Duration;



public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\kimok\\OneDrive\\Documents\\GitHub\\HR_System_Automation\\HR_System_Automation\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver.get("https://tvadmin.taqavolt.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            Process process = Runtime.getRuntime().exec("netsh interface show interface name=\"WiFi\"");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream())
            );
            String line;
            boolean isOffline = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Disabled")) {
                    isOffline = true;
                    break;
                }
            }

            if (isOffline) {
                setNetworkOffline(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    protected void setNetworkOffline(boolean offline) {
        String adapterName = "WiFi";
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

