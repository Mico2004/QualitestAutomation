package com.automation.main.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOUtility {

	File varTmpDir;

	public IOUtility() {

	}

	public boolean waitForDirectoryToExist(String filePath){

		try{

			varTmpDir= new File(filePath);

			for(int i=0; i<60 && !varTmpDir.exists(); i++){

				Thread.sleep(100);

				System.out.println("Wait For it");
			}		
			if(!varTmpDir.exists())
				return false;
			else
				return true;

		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}

	}

	public void deleteFile(String filePath){
		try{

			Path path2 = Paths.get(filePath);

			Files.delete(path2);

		}catch(Exception e){

			System.out.println(e.getMessage());

		}

	}

	public void writeTextToPath(String txt, String path){

		FileWriter fw = null;

		BufferedWriter	bw = null;

		try{

			fw = new FileWriter(path);

			bw = new BufferedWriter(fw);

			bw.write(txt);

			bw.flush();

			bw.close();

			fw.close();

		}catch(Exception e){

			System.out.println("catch:"+e.getMessage());

		}
		finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

}
