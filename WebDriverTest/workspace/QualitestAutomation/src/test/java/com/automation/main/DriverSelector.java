package com.automation.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import junitx.util.PropertyManager;
@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class DriverSelector {
	
	// Set Property for ATU Reporter Configuration
		{
			System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

		}
	public  enum BrowserType{
		Firefox("firefox"),IE("ie"),Chrome("chrome"),Safari("safari");
	
	private String val;
	BrowserType (String value)
	{
		this.val=value;
	}
	
	public String getBrowserName(){
	return val;
	}
	
	} 
	
public static WebDriver getDriver(BrowserType type)
{
	WebDriver driver=null;
	DesiredCapabilities capability=null;
	switch(type)
	{
	case Firefox:driver=new FirefoxDriver();
	//capability.setPlatform(Platform.WIN8_1);
	break;
	case Chrome:
		System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
		driver=new ChromeDriver();
		
		//capability=DesiredCapabilities.chrome();
	//capability.setPlatform(Platform.WIN8_1);
	break;
	case IE:
		 System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
		capability=DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		driver=new InternetExplorerDriver(capability);
		
		break;
	default:driver=new FirefoxDriver();
	break;
	}
	return driver;
}
public static String setDriverUniversity(String university) throws IOException
{    String url=null;
	switch(university)
	{
	case "AWSServerAutomation1":
		url="https://awsserverautomation1.tegrity.com";
	
	break;
	case "AWSServerAutomation2":
		url="https://AWSServerAutomation2.tegrity.com";
	break;
	case "AWSServerAutomation3":
		url="https://AWSServerAutomation3.tegrity.com";
	break;
	case "AWSServerAutomation4":
		url="https://AWSServerAutomation4.tegrity.com";
	break;
	case "AWSServerAutomation5":
		url="https://AWSServerAutomation5.tegrity.com";
	break;
	case "AWSServerAutomation6":
		url="https://AWSServerAutomation6.tegrity.com";
	break;
	case "AWSServerAutomation7":
		url="https://AWSServerAutomation7.tegrity.com";
	break;
	case "AWSServerAutomation8":
		url="https://AWSServerAutomation8.tegrity.com";
	break;
	case "AWSServerAutomation9":
		url="https://AWSServerAutomation9.tegrity.com";
	break;
	case "AWSServerAutomation10":
		url="https://AWSServerAutomation10.tegrity.com";
	break;
	case "AWSServerAutomation11":
		url="https://AWSServerAutomation11.tegrity.com";
	break;
	case "AWSServerAutomation12":
		url="https://AWSServerAutomation12.tegrity.com";
	break;
	case "AWSServerAutomation13":
		url="https://AWSServerAutomation13.tegrity.com";
	break;
	case "AWSServerAutomation14":
		url="https://AWSServerAutomation14.tegrity.com";
	break;
	case "AWSServerAutomation15":
		url="https://AWSServerAutomation15.tegrity.com";
	break;
	case "AWSServerAutomation16":
		url="https://AWSServerAutomation16.tegrity.com";
	break;
	case "AWSServerAutomation17":
		url="https://AWSServerAutomation17.tegrity.com";
	break;
	case "AWSServerAutomation18":
		url="https://AWSServerAutomation18.tegrity.com";	
	break;
	case "AWSServerAutomation19":
		url="https://AWSServerAutomation19.tegrity.com";
	break;
	case "https://AWSServerAutomation20":
		url="AWSServerAutomation20.tegrity.com";
	break;
	case "awsserverautomation-qa-1":
		url="https://awsserverautomation-qa-1.tegrity.com";
		break;
	default:


	break;
	}
	return url;
}
public static BrowserType getBrowserTypeByProperty()
{	BrowserType	type=null;
	
    String browser=System.getProperty("Browser");
    if(browser==null)
	browser=PropertyManager.getProperty("Browser");
    	System.out.println(browser);
	for(BrowserType btype:BrowserType.values())
	{if(btype.getBrowserName().equalsIgnoreCase(browser))
	{
		type=btype;
	}
		
	}
  
	if(type!=null)
	{
		ATUReports.add("browser was found", LogAs.PASSED,
				null);	
	}
		else
		{
			ATUReports.add("browser was not found", LogAs.FAILED,
					null);	
		}
	return type;
}

}
