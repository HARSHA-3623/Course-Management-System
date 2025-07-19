package com.gqt.cms.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

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

        int result = JOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int value = Integer.parseInt(countField.getText().trim());
                return (value > 0 && value <= 100) ? value : null;
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    

    public static void showAvailableCoursesDialog() {
        String[] columnNames = {"Course ID", "Course Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms_db", "root", "root");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT cid, cname FROM courses")) {

            while (rs.next()) {
                int cid = rs.getInt("cid");
                String cname = rs.getString("cname");
                model.addRow(new Object[]{cid, cname});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load courses: " + e.getMessage());
            return;
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setEnabled(false); // make table non-editable

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Available Courses", JOptionPane.PLAIN_MESSAGE);
    }
    
}
