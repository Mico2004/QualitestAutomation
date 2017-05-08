package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lenovo on 08/05/2017.
 */
    public class CopyResourcesFromNetwork {

    String pathToResources = "\\\\teg-fs1\\QA\\Test Library\\Qualitest Automation\\Test-resources\\";


    public void copyFileByName(String fileName){
        fileName="addFewFilesFile.txt";
        try {
            File fromfile = new File(pathToResources+fileName);
            File tofile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\resouces-to-upload\\"+fileName);
            tofile.createNewFile();
            FileInputStream from = new FileInputStream(fromfile);
            FileOutputStream to = new FileOutputStream(tofile);
            byte [] buffer = new byte[4096];
            int bytesread;
            while ((bytesread = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesread);
            }
        } catch (IOException e) {
            System.out.println("Failed to copy files from network");
            e.printStackTrace();
        }
    }
}
