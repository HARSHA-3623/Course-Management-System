package com.gqt.cms.gui;

import com.gqt.cms.datamanagement.StudentDAO;
import com.gqt.cms.users.Student;
import com.gqt.cms.datamanagement.CourseDAO;
import com.gqt.cms.utils.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StudentWindow extends JFrame {

	public StudentWindow() {
		UIManager.put("OptionPane.background", new Color(240, 248, 255));
		UIManager.put("Panel.background", new Color(240, 248, 255));
		UIManager.put("Button.background", new Color(70, 130, 180));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

		setTitle("🎓 Student Panel - GQT CMS");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel background = new JPanel(new BorderLayout());
		background.setBackground(new Color(240, 248, 255));

		JLabel heading = new JLabel("Student Dashboard", JLabel.CENTER);
		heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
		heading.setForeground(new Color(25, 50, 130));
		heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		background.add(heading, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new GridLayout(4, 1, 15, 15));
		btnPanel.setBackground(background.getBackground());
		btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 30, 100));

		JButton registerBtn = createStyledButton("Register as Student");
		JButton viewCoursesBtn = createStyledButton("View Available Courses");
		JButton enrollBtn = createStyledButton("Enroll in a Course");
		JButton backBtn = createStyledButton("Back to Main Menu");

		btnPanel.add(registerBtn);
		btnPanel.add(viewCoursesBtn);
		btnPanel.add(enrollBtn);
		btnPanel.add(backBtn);

		background.add(btnPanel, BorderLayout.CENTER);
		add(background);

		registerBtn.addActionListener(e -> showStudentRegistrationDialog());
		viewCoursesBtn.addActionListener(e -> showStyledCourseList());
		enrollBtn.addActionListener(e -> showStyledEnrollmentDialog());
		backBtn.addActionListener(e -> {
			dispose();
			new MainWindow();
		});

		setVisible(true);
	}

	private void showStudentRegistrationDialog() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(240, 248, 255));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel nameLabel = new JLabel("Student Name:");
		JTextField nameField = new JTextField(15);
		JLabel emailLabel = new JLabel("Email:");
		JTextField emailField = new JTextField(15);

		nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		nameField.setBackground(new Color(245, 250, 255));
		emailField.setBackground(new Color(245, 250, 255));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(nameLabel, gbc);
		gbc.gridx = 1;
		panel.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(emailLabel, gbc);
		gbc.gridx = 1;
		panel.add(emailField, gbc);

		int result = JOptionPane.showConfirmDialog(this, panel, "Student Registration", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String name = nameField.getText().trim();
			String email = emailField.getText().trim();
			if (!name.isEmpty() && !email.isEmpty()) {
				Student student = new Student(name, email);
				StudentDAO.addStudent(student);
			} else {
				DialogUtil.showMessage("Please enter all fields!");
			}
		}
	}

	private void showStyledCourseList() {
		List<String[]> courseData = CourseDAO.getCourseIdAndNames(); // Each item: [cid, cname]

		if (courseData.isEmpty()) {
			DialogUtil.showMessage("No courses available.");
			return;
		}

		String[] columnNames = { "Course ID", "Course Name" };
		String[][] tableData = new String[courseData.size()][2];

		for (int i = 0; i < courseData.size(); i++) {
			tableData[i][0] = courseData.get(i)[0]; // CID
			tableData[i][1] = courseData.get(i)[1]; // CNAME
		}

		JTable table = new JTable(tableData, columnNames);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
		table.getTableHeader().setBackground(new Color(180, 200, 240));
		table.setBackground(new Color(245, 250, 255));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		scrollPane.getViewport().setBackground(new Color(245, 250, 255));

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(240, 248, 255));
		panel.add(scrollPane, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(this, panel, "📘 Available Courses", JOptionPane.PLAIN_MESSAGE);
	}

	private void showStyledEnrollmentDialog() {
		List<String[]> courseList = CourseDAO.getCourseIdAndNames();
		if (courseList.isEmpty()) {
			DialogUtil.showMessage("No courses available for enrollment.");
			return;
		}

		// Build course name -> ID map
		java.util.Map<String, Integer> courseMap = new java.util.HashMap<>();
		for (String[] course : courseList) {
			int cid = Integer.parseInt(course[0]);
			String cname = course[1];
			courseMap.put(cname, cid);
		}

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(240, 248, 255));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel sidLabel = new JLabel("Student ID:");
		JTextField sidField = new JTextField(10);
		JLabel courseLabel = new JLabel("Select Course:");
		JComboBox<String> courseBox = new JComboBox<>(courseMap.keySet().toArray(new String[0]));

		sidLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		sidField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		courseBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(sidLabel, gbc);
		gbc.gridx = 1;
		panel.add(sidField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(courseLabel, gbc);
		gbc.gridx = 1;
		panel.add(courseBox, gbc);

		int result = JOptionPane.showConfirmDialog(this, panel, "Enroll in Course", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String sidText = sidField.getText().trim();
			String selectedCourse = (String) courseBox.getSelectedItem();

			if (!sidText.isEmpty() && selectedCourse != null) {
				try {
					int sid = Integer.parseInt(sidText);
					int cid = courseMap.getOrDefault(selectedCourse, -1);

					if (sid > 0 && cid > 0) {
						boolean enrolled = StudentDAO.enrollStudentBySid(sid, cid);
						if (enrolled) {
							DialogUtil.showMessage("Enrolled in course successfully!");
						} else {
							DialogUtil.showMessage("Enrollment failed. Check Student ID or course.");
						}
					} else {
						DialogUtil.showMessage("Invalid Student ID or Course.");
					}
				} catch (NumberFormatException ex) {
					DialogUtil.showMessage("Please enter a valid numeric Student ID.");
				}
			} else {
				DialogUtil.showMessage("Please fill all fields.");
			}
		}
	}

	private JButton createStyledButton(String text) {
		JButton btn = new JButton(text);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(70, 130, 180));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
}
