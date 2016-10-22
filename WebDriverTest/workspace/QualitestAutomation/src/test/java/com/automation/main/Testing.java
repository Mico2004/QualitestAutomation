package com.automation.main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.jboss.netty.handler.codec.http.websocket.DefaultWebSocketFrame;

public class Testing {

	public static void main(String[] args) throws IOException, InterruptedException {

		

		
		
//		
//		String active_xml = "<ArrayOfCourseViewModel xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.datacontract.org/2004/07/Tegrity.WebAPI.Models\"><CourseViewModel><CourseCaption>Abawsserverautomation101032016110122_Name</CourseCaption><CurrentUserRole>Student</CurrentUserRole><CurrentUserState>Active</CurrentUserState><HasAdditionalContent>true</HasAdditionalContent><Id>2f4722e7-9334-4847-bb43-ca6b4d0a9bb7</Id><IsPrivate>false</IsPrivate><dateModified>2016-03-28T09:18:31Z</dateModified></CourseViewModel><CourseViewModel><CourseCaption>abcawsserverautomation101032016110122_Name</CourseCaption><CurrentUserRole>Instructor</CurrentUserRole><CurrentUserState>Active</CurrentUserState><HasAdditionalContent>false</HasAdditionalContent><Id>59c22158-a406-4bcc-86fd-90f221d7e9d8</Id><IsPrivate>false</IsPrivate><dateModified>2016-03-28T09:18:31Z</dateModified></CourseViewModel><CourseViewModel><CourseCaption>BankInvalidRecordingawsserverautomation1</CourseCaption><CurrentUserRole>Student</CurrentUserRole><CurrentUserState>Active</CurrentUserState><HasAdditionalContent>false</HasAdditionalContent><Id>5da08169-9bd8-428c-9480-09ab616a4a65</Id><IsPrivate>false</IsPrivate><dateModified>2016-03-28T09:18:31Z</dateModified></CourseViewModel><CourseViewModel><CourseCaption>User301032016110122 sandbox course</CourseCaption><CurrentUserRole>Instructor</CurrentUserRole><CurrentUserState>Active</CurrentUserState><HasAdditionalContent>false</HasAdditionalContent><Id>8189fcaf-7342-49bf-ae04-05fed5d3823d</Id><IsPrivate>true</IsPrivate><dateModified>2016-03-28T09:24:32Z</dateModified></CourseViewModel></ArrayOfCourseViewModel>";
//			
//		System.out.println(active_xml);
//		
//		String course_name = "Abawsserverautomation101032016110122_Name</CourseCaption";
//		
//		System.out.println(course_name.split("<")[0]);
		
//		String user = "UserTemp29032016023835";
//		String course_name = "UserTemp29032016023835 sandbox course";
//		System.out.println(course_name.substring(0, user.length()));
//		System.out.println(course_name.substring(user.length()+1, course_name.length()));
		
		// ‘X recording(s) – Y new | last updated: mm/dd/yyyy’
//		String s1 = "10 recordings - 10 new";
//		String s2 = "| last updated: 4/3/2016";
//		System.out.println(Integer.parseInt(s1.split(" ")[0]));
//		System.out.println(s1.split(" ")[3]);
//
//		System.out.println(s2.split(" ")[3].split("/")[1]);
//		System.out.println("aBB".compareTo("A"));
//		System.out.println("Aa".compareTo("aA"));
//		System.out.println("a".compareTo("B"));
//		System.out.println("A".compareTo("B"));
		
//		List<String> courses_list = new ArrayList<>();
//		courses_list.add("A");
//		courses_list.add("a");
//		courses_list.add("b");
//		courses_list.add("B");
//		
//		String previous_course_name = courses_list.get(0);
//		
//		boolean sorting_not_correct = false;
//		for(int i=1; i<courses_list.size(); i++) {
//			String current_course_name = courses_list.get(i);
//			if(previous_course_name.compareTo(current_course_name.toLowerCase()) == 0) {
//				if(previous_course_name.compareTo(current_course_name) <= 0) {
//					previous_course_name = current_course_name;
//					continue;
//				} else {
//					sorting_not_correct = true;
//					break;
//				}
//			} else if (previous_course_name.compareTo(current_course_name.toLowerCase()) < 0) {
//				previous_course_name = current_course_name;
//				continue;
//			} else {
//				sorting_not_correct = true;
//				break;
//			}
//		}
//		
//		System.out.println(sorting_not_correct);
//		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
		
		String a1 = "NewKeyword" + sdf.format(date);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		
		String a2 = "NewKeyword" + sdf.format(date);
		
		System.out.println(a1);
		System.out.println(a2);
	}

}
