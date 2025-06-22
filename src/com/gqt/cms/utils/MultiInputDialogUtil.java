// File: com/gqt/cms/utils/MultiInputDialogUtil.java
package com.gqt.cms.utils;

import javax.swing.*;
import java.awt.*;

public class MultiInputDialogUtil {

    public static String[] showInputDialog(Component parent, int count, String title, String labelPrefix) {
        JPanel panel = new JPanel(new GridLayout(count, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 248, 255));

        JTextField[] fields = new JTextField[count];

        for (int i = 0; i < count; i++) {
            JLabel label = new JLabel(labelPrefix + " " + (i + 1) + ": ");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            fields[i] = new JTextField(20);
            panel.add(label);
            panel.add(fields[i]);
        }

        UIManager.put("OptionPane.background", panel.getBackground());
        UIManager.put("Panel.background", panel.getBackground());
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String[] inputs = new String[count];
            for (int i = 0; i < count; i++) {
                inputs[i] = fields[i].getText().trim();
            }
            return inputs;
        }

        return null; // Cancelled
    }
}
