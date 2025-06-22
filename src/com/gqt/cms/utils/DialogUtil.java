// File: com/gqt/cms/utils/DialogUtil.java
package com.gqt.cms.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {

    public static Integer askCountInput(Component parent, String title, String labelText) {
        JTextField countField = new JTextField(10);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 248, 255));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        countField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(label, BorderLayout.NORTH);
        panel.add(countField, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

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
            try {
                int value = Integer.parseInt(countField.getText().trim());
                return (value > 0 && value <= 100) ? value : null;
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }
    
    
}
