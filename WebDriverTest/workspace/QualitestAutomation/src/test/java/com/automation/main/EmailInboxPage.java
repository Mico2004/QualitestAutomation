package com.automation.main;

import java.util.List;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.FindBy;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junit.framework.Assert;

public class EmailInboxPage extends Page{
	public EmailInboxPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//*[@id=\"email-list\"]/li/a") List<WebElement> email_list;
	@FindBy(xpath= "//*[@id=\"email-list\"]/li/a/div/div[2]") List<WebElement> subject_list;
	@FindBy(xpath= "//*[@id=\":3t\"]") WebElement comment;
	//*[@id="email-list"]/li[3]/div/dl/dd[2]
	//*[@id="email-list"]/li[1]/div/dl/dd[2]
	

//click message by subject name and give an index
 public int clickMessageBySubject(String subj)
 { int i=1;
	for(WebElement e:subject_list) {
	   if(e.getText().equals(subj)) {
			e.click();
			System.out.println("click succeded");
			ATUReports.add("click message","name", "click succeded", "click succeded", LogAs.PASSED, null);
	       Assert.assertTrue(true);
	       return i;
		}
		  i++;
		}
	System.out.println("click not succeded");
	ATUReports.add("click message","name", "click succeded", "click not succeded", LogAs.FAILED, null);
   Assert.assertTrue(false);
 return -1;
 }
 
 ///verify comment and from:
	public void verifyEmailMessage(WebDriver driver,String subject,String sender,String comment_message,String comment_message_need_to_be) throws InterruptedException
 {
	 int i=clickMessageBySubject(subject);
	 Thread.sleep(Page.TIMEOUT_TINY);

	 String from= driver.findElement(By.xpath("//*[@id=\"email-list\"]/li["+String.valueOf(i)+"]/div/dl/dd[2]")).getText();
    driver.switchTo().frame(1);
	WebElement comment= driver.findElement(By.xpath("/html/body"));
  String c=driver.findElement(By.xpath("/html/body")).getText();                    ///"From: Guest (rndsupport@tegrity.com)"
	if((from.equals(sender))&&(comment.getText().contains(comment_message))&&(comment.getText().contains("From: "+comment_message_need_to_be)))
   {
	   System.out.println("email from and comment verified");
		ATUReports.add("email from and comment verified","comment+from", "verified", "verified", LogAs.PASSED, null);
		Assert.assertTrue(true);
   }
   else
   {
		System.out.println("email from and comment not verified");
		ATUReports.add("email from and comment not verified","comment+from", "verified", " not verified", LogAs.FAILED, null);
	   Assert.assertTrue(false);

   }
 }

}
