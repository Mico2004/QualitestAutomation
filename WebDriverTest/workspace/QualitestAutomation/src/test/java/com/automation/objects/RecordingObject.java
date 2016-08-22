package com.automation.objects;

import org.openqa.selenium.WebElement;

public class RecordingObject {
 private String date;
private String name;
 private String duration;
 private String recordman;
private WebElement checkbox ;
	
	
 public RecordingObject(String dat,String nam,String dur,String recname) {
	setDate(dat);
	setName(nam);
    setDuration(dur);
	setRecordman(recname);	
	//setCheckbox(check);	
		// TODO Auto-generated constructor stub
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRecordman() {
		return recordman;
	}
	public void setRecordman(String recordman) {
		this.recordman = recordman;
	}
	public WebElement getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(WebElement checkbox) {
		this.checkbox = checkbox;
	}

}
