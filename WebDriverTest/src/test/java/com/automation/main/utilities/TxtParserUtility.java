package com.automation.main.utilities;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TxtParserUtility {

	public TxtParserUtility() {
		// TODO Auto-generated constructor stub
	}




	public String getJsonValueOutOfTXT(String filePath, String key,int offset){

		try{

			String text=pathToText(filePath);

			int firstIndex=text.indexOf(key,offset);

			int SecondIndex=text.indexOf("\"",firstIndex + key.length()+1);

			return text.substring(firstIndex+key.length()+1,SecondIndex);

		}catch(Exception e){

			System.out.println(e.getMessage());

			return e.getMessage();

		}
	}




	public String pathToText(String path){

		int x=0;

		byte[] encoded1={};

		do {

			try{

				encoded1 = Files.readAllBytes(Paths.get(path));				

				Thread.sleep(1000);

				System.out.println("loop");

			}catch(Exception e){
				try{
					System.out.println("loop ex");
					Thread.sleep(1000);
					encoded1 = Files.readAllBytes(Paths.get(path));	

				}catch(Exception ex){
					System.out.println("loop ex2");
					System.out.println(ex.getMessage());

				}
			}

			x++;

		}while( x<10 && encoded1.length==0);

		if(encoded1.length==0)
			return "";
		else{				
			return new String(encoded1);
		}

	}
}
