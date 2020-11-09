package np.com.kailasneupane.utils;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.util.List;

public class ColorChooserPanel extends JPanel {
    public JColorChooser chooser;
    public ColorChooserPanel() {
        chooser = new JColorChooser();
        AbstractColorChooserPanel[] panels = chooser.getChooserPanels();

        for (AbstractColorChooserPanel panel : panels) {
            if ("HSL".equals(panel.getDisplayName())) {
                add(panel);
                List<JSlider> sliders = SwingUtils.getDescendantsOfType(JSlider.class, panel, true);
//                List<JRadioButton> radioButtons = SwingUtils.getDescendantsOfType(JRadioButton.class, panel, true);
//                List<JSpinner> spinners = SwingUtils.getDescendantsOfType(JSpinner.class, panel, true);
                for (JSlider slider : sliders) {
                    slider.setVisible(false);
                }

//                for (JRadioButton rb : radioButtons) {
//                    rb.setVisible(false);
//                }
//
//                for (JSpinner js : spinners) {
//                    js.setVisible(false);
//                }

            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("np.com.kailasneupane.utils.ColorChooserPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ColorChooserPanel colorChooserPanel = new ColorChooserPanel();
        frame.add(colorChooserPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // EventQueue.invokeLater(() -> createAndShowGUI());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
}