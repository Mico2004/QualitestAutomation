package com.automation.main.utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
//import io.github.bonigarcia.wdm.MarionetteDriverManager;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class DriverSelector {

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

	public static WebDriver getDriver(BrowserType type) {
		WebDriver driver = null;
		DesiredCapabilities capability = null;
		switch (type) {
		case Firefox:
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");	
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
//			FirefoxProfile prfl = new FirefoxProfile();
//		    prfl.setPreference("browser.startup.homepage", "about:blank");
//		    prfl.setPreference("browser.startup.homepage_override.mstone", "ignore");
//		    prfl.setPreference("startup.homepage_welcome_url", "about:blank");
//		    prfl.setPreference("startup.homepage_welcome_url.additional", "about:blank");
//		    capabilities.setCapability(FirefoxDriver.PROFILE, prfl);
			driver = new FirefoxDriver(capabilities);	
			//driver = new FirefoxDriver();
			break;
		case Chrome:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			// capability=DesiredCapabilities.chrome();
			// capability.setPlatform(Platform.WIN8_1);
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);			
			//capability.setCapability("nativeEvents",false);			
			capability.setCapability("ignoreZoomSetting", true);
			//capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			driver = new InternetExplorerDriver(capability);
			driver.manage().window().maximize();
			
			break;
		default:
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
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
			
		return "https://"+university+".tegrity.com";
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

}
