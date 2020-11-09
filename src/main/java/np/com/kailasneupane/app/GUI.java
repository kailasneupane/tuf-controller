package np.com.kailasneupane.app;

import np.com.kailasneupane.utils.ColorChooserPanel;
import np.com.kailasneupane.utils.TufUtils;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author : kailasneupane@gmail.com
 * @created : 2020-11-05
 **/
public class GUI {

    JFrame frame;
    JButton colorSet;
    JButton fanButton1;
    JButton fanButton2;
    JButton fanButton0;
    JLabel textOutput;
    JLabel textOutput2;
    JLabel textOutput3;
    JLabel textOutput4;
    JLabel textOutput5;
    JLabel textOutput6;
    JLabel colorView;
    JTextField colorCode;
    JSlider colorIntensitySlider;
    JComboBox<String> lightTypeComboBox;
    JComboBox<String> speedBox;
    JToggleButton toggleButton;

    String lightEffects[] = {"static", "breathing", "color cycle", "strobe"};
    String speed[] = {"slow", "normal", "fast"};

    Color bluish = new Color(59, 89, 182);
    Font f = new Font("serif", Font.BOLD, 14);
    Font fTitle = new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 18);
    ImageIcon iconImg = new ImageIcon(GUI.class.getResource("/images/tuf_logo.png"));

    JColorChooser jColorChooser;
    boolean notificationToggle = true;

    private static GUI gui = new GUI();

    private GUI() {

        TufUtils.oneTimeInit();

        Color initialColor = Color.decode(TufUtils.readLEDColor());

        //frame
        frame = new JFrame("TUF Controller");
        //frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Output text
        textOutput = new JLabel("Keyboard LED Controller");
        textOutput.setFont(fTitle);
        textOutput.setSize(10, 10);
        textOutput.setBounds(10, 20, 230, 15);
        textOutput.setOpaque(true);
        frame.add(textOutput);


        jColorChooser = new ColorChooserPanel().chooser;
        jColorChooser.setColor(initialColor);
        jColorChooser.setBounds(10, 40, 400, 180);
        //////////////////
        AbstractColorChooserPanel[] panels = jColorChooser.getChooserPanels();

        for (AbstractColorChooserPanel panel : panels) {
            if ("HSL".equals(panel.getDisplayName())) {
                jColorChooser.add(panel);

//                List<JSlider> sliders = np.com.kailasneupane.utils.SwingUtils.getDescendantsOfType(JSlider.class, panel, true);
//                List<JRadioButton> radioButtons = np.com.kailasneupane.utils.SwingUtils.getDescendantsOfType(JRadioButton.class, panel, true);
//                List<JSpinner> spinners = np.com.kailasneupane.utils.SwingUtils.getDescendantsOfType(JSpinner.class, panel, true);
//                for (JSlider slider : sliders) {
//                    slider.setVisible(false);
//                }
//
//                for (JRadioButton rb : radioButtons) {
//                    rb.setVisible(false);
//                }
//
//                for (JSpinner js : spinners) {
//                    js.setVisible(false);
//                }

            }
        }
        jColorChooser.setPreviewPanel(new JPanel());
        ////////////
        //System.out.println(jColorChooser);
        frame.add(jColorChooser);

        textOutput2 = new JLabel("Color");
        textOutput2.setSize(10, 10);
        textOutput2.setBounds(40, 225, 65, 25);
        textOutput2.setFont(f);
        frame.add(textOutput2);

        colorView = new JLabel("");
        colorView.setOpaque(true);
        colorView.setBounds(120, 225, 80, 30);
        colorView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorView.setBackground(initialColor);
        frame.add(colorView);


        colorCode = new JTextField("#" + getColorCode(initialColor));
        colorCode.setBounds(210, 230, 70, 20);
        frame.add(colorCode);

        //button
        colorSet = new JButton("set");
        colorSet.setBounds(290, 230, 57, 20);
        colorSet.setFocusPainted(false);
        frame.add(colorSet);

        textOutput3 = new JLabel("Mode");
        textOutput3.setSize(10, 10);
        textOutput3.setBounds(40, 270, 65, 25);
        textOutput3.setFont(f);
        frame.add(textOutput3);

        lightTypeComboBox = new JComboBox(lightEffects);
        lightTypeComboBox.setBounds(120, 270, 100, 20);
        int ledMode = TufUtils.readLEDMode();
        lightTypeComboBox.setSelectedIndex(ledMode);
        if (ledMode == 2) {
            colorSet.setEnabled(false);
        }
        frame.add(lightTypeComboBox);

        textOutput4 = new JLabel("Speed");
        textOutput4.setSize(10, 10);
        textOutput4.setBounds(240, 270, 65, 25);
        textOutput4.setFont(f);
        frame.add(textOutput4);

        speedBox = new JComboBox(speed);
        speedBox.setBounds(310, 270, 89, 20);
        if (ledMode == 3 || ledMode == 0) {
            speedBox.setEnabled(false);
        } else {
            speedBox.setEnabled(true);
        }
        speedBox.setSelectedIndex(TufUtils.readLEDSpeed());
        frame.add(speedBox);

        textOutput5 = new JLabel("Intensity");
        textOutput5.setSize(10, 10);
        textOutput5.setBounds(30, 320, 80, 25);
        textOutput5.setFont(f);
        frame.add(textOutput5);

        colorIntensitySlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
        colorIntensitySlider.setBounds(114, 310, 290, 60);
        colorIntensitySlider.setMinorTickSpacing(0);
        colorIntensitySlider.setMajorTickSpacing(1);
        colorIntensitySlider.setPaintTicks(true);
        colorIntensitySlider.setPaintLabels(true);
        colorIntensitySlider.setMaximum(3);
        colorIntensitySlider.setMinimum(0);
        colorIntensitySlider.setValue(TufUtils.readLEDIntensity());
        frame.add(colorIntensitySlider);


        textOutput6 = new JLabel("FAN Controller");
        textOutput6.setSize(10, 10);
        textOutput6.setBounds(12, 395, 140, 15);
        textOutput6.setFont(fTitle);
        frame.add(textOutput6);

        toggleButton = new JToggleButton("Notification ON");
        toggleButton.setBounds(250, 390, 150, 20);
        //frame.add(toggleButton);

        Font fanFont = new Font("Tahoma", Font.BOLD, 15);

        fanButton2 = new JButton("Silent");
        fanButton2.setBackground(Color.WHITE);
        fanButton2.setFont(fanFont);
        fanButton2.setFocusPainted(false);
        fanButton2.setOpaque(true);
        fanButton2.setBounds(20, 430, 100, 40);
        frame.add(fanButton2);

        fanButton0 = new JButton("Normal");
        fanButton0.setOpaque(true);
        fanButton0.setFont(fanFont);
        fanButton0.setBackground(new Color(59, 89, 182));
        fanButton0.setForeground(Color.WHITE);
        fanButton0.setFocusPainted(false);
        fanButton0.setBounds(160, 430, 100, 40);
        frame.add(fanButton0);

        fanButton1 = new JButton("Boost");
        fanButton1.setFont(fanFont);
        fanButton1.setOpaque(true);
        fanButton1.setBackground(Color.WHITE);
        fanButton1.setFocusPainted(false);
        fanButton1.setBounds(300, 430, 100, 40);
        frame.add(fanButton1);


        frame.setLayout(null);
        frame.setIconImage(iconImg.getImage());
        frame.setSize(420, 520);
        frame.setResizable(false);
        frame.setLocation(1400, 170);
        frame.setAlwaysOnTop(false);
        frame.setVisible(true);

        readCurrentFanMode();
        setListeners();
    }

    public static GUI getInstance() {
        return gui;
    }


    public void toggleVisibility() {
        frame.setVisible(!frame.isVisible());
    }

    public boolean getFanNotification() {
        return notificationToggle;
    }

    public String getColorCode(Color color) {
        String colorInput = Integer.toHexString(color.getRGB()).substring(2);
        return colorInput;
    }

    protected void setListeners() {

        jColorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                String colorInput = getColorCode(jColorChooser.getColor());
                colorView.setBackground(jColorChooser.getColor());
                colorCode.setText("#" + colorInput);
                if (lightTypeComboBox.getSelectedIndex() != 2) {
                    TufUtils.updateKeyboard(colorInput);
                } else {
                    // System.out.println("mode is set to color cycle. Color change will not be visible.");
                }
            }
        });

        colorSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String color = colorCode.getText();
                jColorChooser.setColor(Color.decode(color));
            }
        });

        colorIntensitySlider.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                TufUtils.updateIntensity(colorIntensitySlider.getValue());
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        lightTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = lightTypeComboBox.getSelectedIndex();
                TufUtils.updateLightMode(index);
                if (index == 3 || index == 0) {
                    speedBox.setEnabled(false);
                } else {
                    speedBox.setEnabled(true);
                }
                if (index == 2) {
                    colorSet.setEnabled(false);
                } else {
                    colorSet.setEnabled(true);
                }
            }
        });

        speedBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TufUtils.updateLightSpeed(speedBox.getSelectedIndex());
            }
        });

        fanButton0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TufUtils.updateFanMode(0);
            }
        });

        fanButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TufUtils.updateFanMode(1);
            }
        });

        fanButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TufUtils.updateFanMode(2);
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                notificationToggle = !notificationToggle;
                if(notificationToggle){
                    toggleButton.setText("Notification ON");
                } else {
                    toggleButton.setText("Notification OFF");
                }
            }
        });
    }

    public String readCurrentFanMode() {
        /*
0 - normal
1 - overboost
2 - silent
         */
        int mode = TufUtils.readFanMode();
        switch (mode) {
            case 0:
                updateFanButton(fanButton0, fanButton1, fanButton2);
                return "Normal";
            case 1:
                updateFanButton(fanButton1, fanButton0, fanButton2);
                return "Boost";
            case 2:
                updateFanButton(fanButton2, fanButton1, fanButton0);
                return "Silent";
        }
        return "Unknown";
    }

    private void updateFanButton(JButton button, JButton... buttons) {
        button.setForeground(Color.WHITE);
        button.setBackground(bluish);
        for (JButton b : buttons) {
            if (button != null && b != null && b != button) {
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
            }
        }
    }
}
