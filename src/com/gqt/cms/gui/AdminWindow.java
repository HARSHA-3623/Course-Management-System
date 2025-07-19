package com.gqt.cms.gui;

import com.gqt.cms.datamanagement.AdminDAO;
import com.gqt.cms.datamanagement.CourseDAO;
import com.gqt.cms.datamanagement.EnrollmentDAO;
import com.gqt.cms.utils.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AdminWindow extends JFrame {

	public AdminWindow() {
		UIManager.put("OptionPane.background", new Color(240, 248, 255));
		UIManager.put("Panel.background", new Color(240, 248, 255));
		UIManager.put("Button.background", new Color(70, 130, 180));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

		setTitle("Admin Panel - GQT Course Management System");
		setSize(500, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel background = new JPanel(new BorderLayout());
		background.setBackground(new Color(240, 248, 255));

		JLabel heading = new JLabel("Admin Dashboard", JLabel.CENTER);
		heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
		heading.setForeground(new Color(30, 60, 120));
		heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		background.add(heading, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(new Color(240, 248, 255));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

		JButton addCourseBtn = createStyledButton("Add New Course");
		JButton viewCoursesBtn = createStyledButton("View All Courses");
		JButton viewEnrollmentsBtn = createStyledButton("View Enrollments");
		JButton deleteCourseBtn = createStyledButton("Delete Course");
		JButton backBtn = createStyledButton("Back to Main Menu");

		buttonPanel.add(addSpacing());
		buttonPanel.add(addCourseBtn);
		buttonPanel.add(addSpacing());
		buttonPanel.add(viewCoursesBtn);
		buttonPanel.add(addSpacing());
		buttonPanel.add(viewEnrollmentsBtn);
		buttonPanel.add(addSpacing());
		buttonPanel.add(deleteCourseBtn);
		buttonPanel.add(addSpacing());
		buttonPanel.add(backBtn);

		background.add(buttonPanel, BorderLayout.CENTER);
		add(background);

		addCourseBtn.addActionListener(e -> showAddCourseDialog());
		viewCoursesBtn.addActionListener(e -> showAllCoursesDialog());
		viewEnrollmentsBtn.addActionListener(e -> showAllEnrollmentsDialog());
		deleteCourseBtn.addActionListener(e -> showDeleteCourseDialog());
		backBtn.addActionListener(e -> {
			dispose();
			new MainWindow();
		});

		setVisible(true);
	}

	private void showAddCourseDialog() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(240, 248, 255));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel cnameLabel = new JLabel("Course Name:");
		JTextField cnameField = new JTextField(15);
		JLabel durationLabel = new JLabel("Duration:");
		JTextField durationField = new JTextField(15);
		JLabel feesLabel = new JLabel("Fees:");
		JTextField feesField = new JTextField(15);

		cnameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		durationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		feesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		cnameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		durationField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		feesField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		cnameField.setBackground(new Color(245, 250, 255));
		durationField.setBackground(new Color(245, 250, 255));
		feesField.setBackground(new Color(245, 250, 255));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(cnameLabel, gbc);
		gbc.gridx = 1;
		panel.add(cnameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(durationLabel, gbc);
		gbc.gridx = 1;
		panel.add(durationField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(feesLabel, gbc);
		gbc.gridx = 1;
		panel.add(feesField, gbc);

		int result = JOptionPane.showConfirmDialog(this, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String cname = cnameField.getText().trim();
			String duration = durationField.getText().trim();
			String feeText = feesField.getText().trim();

			if (!cname.isEmpty() && !duration.isEmpty() && !feeText.isEmpty()) {
				try {
					float fees = Float.parseFloat(feeText);
					AdminDAO.addCourse(cname, duration, fees);
				} catch (NumberFormatException e) {
					DialogUtil.showMessage("Fees must be a valid number.");
				}
			} else {
				DialogUtil.showMessage("Please fill all fields!");
			}
		}
	}

	private void showDeleteCourseDialog() {
	    List<String[]> courseList = CourseDAO.getCourseIdAndNames();
	    if (courseList.isEmpty()) {
	        DialogUtil.showMessage("No courses to delete.");
	        return;
	    }

	    // Convert courseList to a list of course names (or "cid - cname" for better clarity)
	    List<String> courseNames = new ArrayList<>();
	    for (String[] course : courseList) {
	        courseNames.add(course[0] + " - " + course[1]);  // Format: "101 - Java Basics"
	    }

	    String selectedCourse = (String) JOptionPane.showInputDialog(
	            this,
	            "Select course to delete:",
	            "Delete Course",
	            JOptionPane.PLAIN_MESSAGE,
	            null,
	            courseNames.toArray(),
	            courseNames.get(0)
	    );

	    if (selectedCourse != null && !selectedCourse.trim().isEmpty()) {
	        // Extract course name (assuming format: "101 - Java Basics")
	        String courseName = selectedCourse.split(" - ", 2)[1];

	        int confirm = JOptionPane.showConfirmDialog(
	                this,
	                "Are you sure you want to delete the course \"" + courseName + "\"?",
	                "Confirm Delete",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (confirm == JOptionPane.YES_OPTION) {
	            boolean success = AdminDAO.deleteCourse(courseName);
	            DialogUtil.showMessage(success ? "Course deleted successfully." : "Failed to delete course.");
	        }
	    }
	}


	private void showAllCoursesDialog() {
	    List<String[]> courseList = CourseDAO.getCourseIdAndNames();

	    if (courseList.isEmpty()) {
	        DialogUtil.showMessage("No courses available.");
	        return;
	    }

	    // Table headers
	    String[] columnNames = {"Course ID", "Course Name"};

	    // Table data
	    Object[][] data = new Object[courseList.size()][2];
	    for (int i = 0; i < courseList.size(); i++) {
	        data[i][0] = courseList.get(i)[0];
	        data[i][1] = courseList.get(i)[1];
	    }

	    JTable table = new JTable(data, columnNames);
	    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    table.setRowHeight(24);
	    table.setEnabled(false);
	    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
	    table.getTableHeader().setBackground(new Color(100, 149, 237));
	    table.getTableHeader().setForeground(Color.WHITE);

	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setPreferredSize(new Dimension(380, 220));
	    scrollPane.getViewport().setBackground(new Color(240, 248, 255));

	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(new Color(240, 248, 255));
	    panel.add(scrollPane, BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(this, panel, "Available Courses", JOptionPane.PLAIN_MESSAGE);
	}


	private void showAllEnrollmentsDialog() {
		List<String> enrollments = EnrollmentDAO.getAllEnrollments();

		if (enrollments.isEmpty()) {
			DialogUtil.showMessage("No enrollments found.");
			return;
		}

		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String entry : enrollments) {
			sb.append(i++).append(". ").append(entry).append("\n");
		}

		JTextArea area = new JTextArea(sb.toString());
		area.setEditable(false);
		area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		area.setBackground(new Color(245, 250, 255));
		area.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 240)));

		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(380, 220));

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(240, 248, 255));
		panel.add(scroll);

		JOptionPane.showMessageDialog(this, panel, "Enrollments", JOptionPane.PLAIN_MESSAGE);
	}

	private JButton createStyledButton(String text) {
		JButton btn = new JButton(text);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(70, 130, 180));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(220, 40));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Color defaultColor = btn.getBackground();
		Color hoverColor = new Color(60, 110, 160);

		btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(hoverColor);
			}

			public void mouseExited(MouseEvent e) {
				btn.setBackground(defaultColor);
			}
		});

		return btn;
	}

	private Component addSpacing() {
		return Box.createVerticalStrut(10);
	}
}
