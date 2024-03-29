package RestBooker.Utilities;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int getRandomNumber() {
        /* return a random number from 1 to 10001 */
        return ThreadLocalRandom.current().nextInt(1, 1001);
    }
}
