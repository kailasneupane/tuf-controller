package np.com.kailasneupane.app;

import np.com.kailasneupane.utils.TufUtils;
import np.com.kailasneupane.watcher.FileWatcher;

/**
 * @author : kailasneupane@gmail.com
 * @created : 2020-11-05
 **/
public class Main {

    public static void main(String[] args) {
        //TufUtils.lockApp("/tmp", ".tufJLock");
        GUI gui = GUI.getInstance();
        startWatcher(gui);
    }

    private static void startWatcher(GUI gui) {
        FileWatcher fileWatcher = new FileWatcher() {
            @Override
            public void onFanModified() {
                gui.readCurrentFanMode();
            }

            @Override
            public void onBrightnessModified() {
                gui.colorIntensitySlider.setValue(TufUtils.readLEDIntensity());
            }
        };
        fileWatcher.watchFile();
    }

}