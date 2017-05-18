package com.automation.main.utilities;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Listeners;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class DriverSelector {

    private static String rootPath = "c:/selenium-drivers";

    // Set Property for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

    }

    public enum BrowserType {
        Firefox("firefox"), IE("ie"), Chrome("chrome"), Safari("safari");

        private String val;

        BrowserType(String value) {
            this.val = value;
        }

        public String getBrowserName() {
            return val;
        }

    }

    @SuppressWarnings({"deprecation", "null"})
    public static WebDriver getDriver(BrowserType type) {
        WebDriver driver = null;
        DesiredCapabilities capability = null;
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        switch (type) {
            case Firefox:
                System.setProperty("webdriver.gecko.driver", rootPath + "/geckodriver.exe");
                capability = DesiredCapabilities.firefox();
                capability.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                capability.setCapability("marionette", true);
                driver = new FirefoxDriver(capability);
                //driver = new MarionetteDriver();
                break;
            case Chrome:
                System.setProperty("webdriver.chrome.driver", rootPath + "/chromedriver.exe");
                capability = DesiredCapabilities.chrome();
                capability.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                driver = new ChromeDriver(capability);
                driver.manage().window().maximize();
                // capability=DesiredCapabilities.chrome();
                // capability.setPlatform(Platform.WIN8_1);
                break;
            case IE:
                System.setProperty("webdriver.ie.driver", rootPath + "/IEDriverServer.exe");
                capability = DesiredCapabilities.internetExplorer();
                capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                capability.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                //capability.setCapability("nativeEvents",false);
                capability.setCapability("ignoreZoomSetting", true);
                //capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                driver = new InternetExplorerDriver(capability);
                driver.manage().window().maximize();

                break;
            default:
                System.setProperty("webdriver.gecko.driver", rootPath + "geckodriver.exe");
                // MarionetteDriverManager.getInstance().setup();
                // driver = new MarionetteDriver();
                // System.setProperty("webdriver.gecko.driver",
                // "src/test/resources");
                driver = new MarionetteDriver();
                //driver = new FirefoxDriver();
                //String marionetteDriverLocation = currentDir + "/tools/marionette/wires.exe";
                //System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
                //driver = new MarionetteDriver();
                // DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                // capabilities.setCapability("marionette", true);
                // driver = new FirefoxDriver(capabilities);
                break;
        }
        return driver;
    }

    public static String setDriverUniversity(String university) throws IOException {

        return "https://" + university + ".tegrity.com";
    }

    public static BrowserType getBrowserTypeByProperty() {
        BrowserType type = null;

        String browser = System.getProperty("Browser");
        if (browser == null)
            browser = PropertyManager.getProperty("Browser");
        System.out.println(browser);
        for (BrowserType btype : BrowserType.values()) {
            if (btype.getBrowserName().equalsIgnoreCase(browser)) {
                type = btype;
                break;
            }

        }

        if (type != null) {
            ATUReports.add("browser was found", LogAs.PASSED, null);
        } else {
            ATUReports.add("browser was not found", LogAs.FAILED, null);
        }
        return type;
    }


    public static WebDriver getDriverForDownloadFile(String pathToDownloadFolder) {
        System.setProperty("webdriver.chrome.driver", rootPath + "/chromedriver.exe");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", pathToDownloadFolder);
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--test-type");
        options.addArguments("--disable-extensions"); //to disable browser extension popup

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(cap);
    }





    }
