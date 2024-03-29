package RestBooker.Utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class DataUtils {
    private static final String NEW_BOOKING_DATA_PATH="src/test/java/Resources/newBookindData.json";
    private static final String ENVIRONMENT_PATH = "src/test/java/Resources/environment.properties";
    private static final String UPDATE_BODY_DATA_PATH = "src/test/java/Resources/updateBodyData.json";
    private static final String AUTH_BODY_DATA_PATH = "src/test/java/Resources/authBodyData.json";
    public static String getEnvironmentPropertyValue(String key) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(ENVIRONMENT_PATH));
            return properties.getProperty(key);
        } catch (Exception e) {
            System.out.println("error in file");
            return "";
        }
    }
    public static void setEnvironmentPropertyValue(String key, String newValue) {
        try {
            Properties properties = new Properties();
            properties.put(key,newValue);
            FileOutputStream file = new FileOutputStream(ENVIRONMENT_PATH);
            properties.store(file,"comment");

        } catch (Exception e) {
            System.out.println("error in file");
        }
    }

    public static File getNewBookingDataFile(){
        return new File(NEW_BOOKING_DATA_PATH);
    }
    public static File getUpdateBodyDataFile(){
        return new File(UPDATE_BODY_DATA_PATH);
    }
    public static File getAuthBodyDataFile(){
        return new File(AUTH_BODY_DATA_PATH);
    }
}
