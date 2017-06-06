package utils;

/**
 * Created by Lenovo on 06/06/2017.
 */
public class UniversityConfigure {

    private static String universityName = "awsserverautomation-qa-1";
    private static String urlTouniversity = "https://"+universityName+".tegrity.com";

    public static String getURlUniversityName(){
        String universityFromSystemProp = System.getProperty("University");
        if (universityFromSystemProp!=null){
            urlTouniversity = "https://"+universityFromSystemProp+".tegrity.com";
        }
        return urlTouniversity;
    }

    public static String getUniversityName() {
        return universityName;
    }
}
