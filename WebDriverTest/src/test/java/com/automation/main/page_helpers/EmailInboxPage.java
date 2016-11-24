package com.automation.main.page_helpers;

import java.util.List;

import javax.xml.xpath.XPath;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

public class EmailInboxPage extends Page{
	public EmailInboxPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//*[@id=\"email-list\"]/li/a") List<WebElement> email_list;
	@FindBy(xpath= "//*[@id=\"email-list\"]//div[2]/p") List<WebElement> subject_list;	
	@FindBy(xpath= "//*[@id=\":3t\"]") WebElement comment;
	WebElement contentFrame;
	public final static String EMAIL_SENDING_USER="rndsupport@tegrity.com";
	//*[@id="email-list"]/li[3]/div/dl/dd[2]
	//*[@id="email-list"]/li[1]/div/dl/dd[2]
	

//click message by subject name and give an index
 public int clickMessageBySubject(String subj)
 { 
	 try{
	 int i=1;
	 for(WebElement e:subject_list) {
	   if(e.getText().equals(subj)) {
			e.findElement(By.xpath("../../..")).click();
			System.out.println("click succeded");
			ATUReports.add("Subject Verification and click", "Exp: "+subj , "Act: "+e.getText(), LogAs.PASSED, null);	      
	       return 1;
		}
		  i++;
		}
	 System.out.println("click not succeded");
	 ATUReports.add("click message","name", "click succeded", "click not succeded", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 Assert.assertTrue(false);
	 }catch(Exception e){
		 ATUReports.add("Subject verification", "Subject not found",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		 
		 
		 return -1;
	 }
 return -1;
 }
 
 ///verify comment and from:
	public void verifyEmailMessage(WebDriver driver,String subject,String sender,String comment_message_need_to_be, 
String userID, String userNAME, String customerNAME, String pageURL) throws InterruptedException
 {
	
	int i=clickMessageBySubject(subject);
	Thread.sleep(2000);
	//String from= driver.findElement(By.xpath("//*[@id=\"email-list\"]/li["+String.valueOf(i)+"]/div/dl/dd[2]")).getText();
	switchToContentFrame();
	//WebElement comment= driver.findElement(By.xpath("/html/body"));
	String contentText=driver.findElement(By.xpath("/html/body")).getText();                    
	System.out.println("contentText: "+	contentText);
	String[] contentArray=contentText.split("\n");
	String[] fromAndComments=contentArray[0].split(": ");
	String[] userId=contentArray[1].split(":");	
	String[] userName=contentArray[2].split(": ");
	String[] customerName=contentArray[3].split(": ");
	String[] pageUrl=contentArray[4].split(": ");
	String[] userAgent=contentArray[5].split(": ");
	

	// fromAndComments verification
	if(!fromAndComments[0].equals("From") || !fromAndComments[1].equals(comment_message_need_to_be)  ){
		ATUReports.add("From And Comments field verification", "Exp: From: "+comment_message_need_to_be, "Act: "+fromAndComments[0]+": "+fromAndComments[1], LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else
		ATUReports.add("From And Comments  verification", "Exp: From: "+comment_message_need_to_be, "Act: "+fromAndComments[0]+": "+fromAndComments[1], LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
	// UserID verification
	if(!userId[0].equals("User ID") &&  userNAME.equals("Guest") && userId.length<2){
		ATUReports.add("User id field verification", "Exp: User ID: ", "Act: "+userId[0], LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else // *************** need to add logic in case user is not a guest ****************
		ATUReports.add("User id field verification", "Exp: User ID: ", "Act: "+userId[0], LogAs.PASSED, null);
	//user name verification	
	if(!userName[0].equals("User Name") || !userName[1].equals(userNAME)  ){
		ATUReports.add("User Name field verification", "Exp: User Name: "+userNAME, "Act: "+userName[0]+": "+userName[1], LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else
		ATUReports.add("User Name field verification", "Exp: User Name: "+userNAME, "Act: User Name: "+userName[1], LogAs.PASSED, null);
	
	//Customer name verification	
	if(!customerName[0].equals("Customer Name") || !customerName[1].toLowerCase().equals(customerNAME.toLowerCase())  ){
		ATUReports.add("Customer name field verification", "Exp: Customer name: "+customerNAME, "Act: "+customerName[0]+": "+customerName[1], LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else
		ATUReports.add("Customer name field verification", "Exp: Customer name: "+customerNAME, "Act: Customer name: "+customerName[1], LogAs.PASSED, null);
	
	//Page URL verification	
	if(!pageUrl[0].equals("Page URL")  || !pageUrl[1].equals(pageURL)  ){
		ATUReports.add("Page URL field verification", "Exp: Page URL: "+pageURL, "Act: "+pageUrl[0]+pageUrl[1], LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else
		ATUReports.add("Page URL field verification", "Exp: Page URL: "+pageURL, "Act: Page URL: "+pageUrl[1], LogAs.PASSED, null);
	
	//User agent verification	
	if(!userAgent[0].equals("Page Agent") && userAgent[1].equals("") ){
		ATUReports.add("user Agent field verification", "Exp: User Agent: ", "Act: "+userAgent[0]+": Not empty", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	 }
	else
		ATUReports.add("user Agent field verification", "Exp: User Agent: <Anything>", "Act: User Agent: <Anything>", LogAs.PASSED, null);
		
 }
	
	public WebElement getContentFrame(){				
		return contentFrame=driver.findElement(By.id("emailFrame"));
	}
	
	public void switchToContentFrame(){		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("emailFrame"));		
	}

}
