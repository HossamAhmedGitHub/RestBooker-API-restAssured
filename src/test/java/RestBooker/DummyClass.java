package RestBooker;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;

public class DummyClass {
    @Test
    public void testone(){
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].toString())
                .error("yes");
    }
}
