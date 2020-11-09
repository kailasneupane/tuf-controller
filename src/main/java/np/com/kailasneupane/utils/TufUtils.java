package np.com.kailasneupane.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

/**
 * @author : kailasneupane@gmail.com
 * @created : 2020-11-05
 **/
public class TufUtils {

    public static String KBBL_FLAGS = "/sys/devices/platform/faustus/kbbl/kbbl_flags";
    public static String KBBL_SET = "/sys/devices/platform/faustus/kbbl/kbbl_set";
    public static String KBBL_RED = "/sys/devices/platform/faustus/kbbl/kbbl_red";
    public static String KBBL_GREEN = "/sys/devices/platform/faustus/kbbl/kbbl_green";
    public static String KBBL_BLUE = "/sys/devices/platform/faustus/kbbl/kbbl_blue";
    public static String KBBL_MODE = "/sys/devices/platform/faustus/kbbl/kbbl_mode";
    public static String KBBL_SPEED = "/sys/devices/platform/faustus/kbbl/kbbl_speed";
    public static String BRIGHTNESS = "/sys/class/leds/asus::kbd_backlight/brightness";
    public static String THROTTLE_THERMAL_POLICY = "/sys/devices/platform/faustus/throttle_thermal_policy";
    public static String BRIGHTNESS_HW_CHANGED = "/sys/class/leds/asus::kbd_backlight/brightness_hw_changed";


    public static void writeToFile(String fileName, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(value);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFirstLineOfFile(String path) {
        BufferedReader brTest = null;
        String data = null;
        try {
            brTest = new BufferedReader(new FileReader(path));
            data = brTest.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                brTest.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public static void updateKeyboard(String colorInput) {
        //  long in = System.currentTimeMillis();
        String r = colorInput.substring(0, 2);
        String g = colorInput.substring(2, 4);
        String b = colorInput.substring(4, 6);

        writeToFile(KBBL_RED, r);
        writeToFile(KBBL_GREEN, g);
        writeToFile(KBBL_BLUE, b);
        writeToFile(KBBL_SET, "2");
        // System.out.println("time taken: " + (System.currentTimeMillis() - in));
    }

    public static void updateLightMode(int mode) {
        if (mode < 0) {
            mode = 0;
        } else if (mode > 3) {
            mode = 3;
        }
        writeToFile(KBBL_MODE, String.valueOf(mode));
        writeToFile(KBBL_SET, "2");
    }

    public static void updateLightSpeed(int mode) {
        if (mode < 0) {
            mode = 0;
        } else if (mode > 2) {
            mode = 2;
        }
        writeToFile(KBBL_SPEED, String.valueOf(mode));
        writeToFile(KBBL_SET, "2");
    }

    public static void updateIntensity(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > 3) {
            value = 3;
        }
        writeToFile(BRIGHTNESS, String.valueOf(value));
    }


    public static void updateFanMode(int mode) {
        if (mode < 0) {
            mode = 0;
        } else if (mode > 2) {
            mode = 2;
        }
        writeToFile(THROTTLE_THERMAL_POLICY, String.valueOf(mode));
    }

    public static int readLEDMode() {
        return Integer.parseInt(readFirstLineOfFile(KBBL_MODE));
    }

    public static int readLEDSpeed() {
        String content = TufUtils.readFirstLineOfFile(KBBL_SPEED);
        int speedIndex = Integer.parseInt(content);
        return speedIndex;
    }

    public static String readLEDColor() {
        String cr = TufUtils.readFirstLineOfFile(KBBL_RED).substring(0, 2);
        String cg = TufUtils.readFirstLineOfFile(KBBL_GREEN).substring(0, 2);
        String cb = TufUtils.readFirstLineOfFile(KBBL_BLUE).substring(0, 2);
        return "#" + cr + cg + cb;
    }

    public static void oneTimeInit() {
        writeToFile(KBBL_FLAGS, "2a");
    }

    public static int readLEDIntensity() {
        return Integer.parseInt(readFirstLineOfFile(BRIGHTNESS));
    }

    public static int readFanMode() {
        return Integer.parseInt(readFirstLineOfFile(THROTTLE_THERMAL_POLICY));
    }

    public static void lockApp(String parentDir, String lockFileName) {
        //String userHome = System.getProperty("user.home");
        File file = new File(parentDir, lockFileName);
        try {
            FileChannel fc = FileChannel.open(file.toPath(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            FileLock lock = fc.tryLock();
            if (lock == null) {
                System.out.println("Another instance is already running. If it was force killed then delete file " + file.getAbsolutePath() + " and re-run the application.");
                System.exit(1);
            }
        } catch (IOException e) {
            throw new Error(e);
        }
    }

//    public static void runCommand(String command) {
//        String[] cmdline = {"sh", "-c", command};
//        //System.out.println("$ " + command);
//        Process process;
//        try {
//            process = Runtime.getRuntime().exec(cmdline);
//            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            lineReader.lines().forEach(System.out::println);
//
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            errorReader.lines().forEach(System.out::println);
//
//            process.waitFor();
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
//        }
//    }
}
