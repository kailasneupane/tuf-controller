package np.com.kailasneupane.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public abstract class FileWatcher {

    public void watchFile() {
        // We obtain the file system of the Path
        FileSystem fileSystem = Paths.get(System.getProperty("user.home")).getFileSystem();

        // We create the new WatchService using the try-with-resources block
        try (WatchService service = fileSystem.newWatchService()) {
            // We watch for modification events
            Path folderPath1 = Paths.get("/sys/devices/platform/faustus/");
            Path folderPath2 = Paths.get("/sys/class/leds/asus::kbd_backlight/");
            folderPath1.register(service, ENTRY_MODIFY);
            folderPath2.register(service, ENTRY_MODIFY);

            // Start the infinite polling loop
            while (true) {
                // Wait for the next event
               // Thread.sleep(100);
                WatchKey watchKey = service.take();

                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    // Get the type of the event
                    Kind<?> kind = watchEvent.kind();

                    if (kind == ENTRY_MODIFY) {
                        Path watchEventPath = (Path) watchEvent.context();

                        // Call this if the right file is involved
                        if (watchEventPath.toString().equals("throttle_thermal_policy")) {
                            onFanModified();
                        } else if (watchEventPath.toString().equals("brightness") || watchEventPath.toString().equals("brightness_hw_changed")) {
                            onBrightnessModified();
                        }
                    }
                }

                if (!watchKey.reset()) {
                    // Exit if no longer valid
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void onFanModified();

    public abstract void onBrightnessModified();
}