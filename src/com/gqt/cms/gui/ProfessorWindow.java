package com.gqt.cms.gui;

import com.gqt.cms.datamanagement.CourseDAO;
import com.gqt.cms.datamanagement.ProfessorDAO;
import com.gqt.cms.users.Professor;
import com.gqt.cms.utils.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProfessorWindow extends JFrame {

	public ProfessorWindow() {
		UIManager.put("OptionPane.background", new Color(255, 248, 240));
		UIManager.put("Panel.background", new Color(255, 248, 240));
		UIManager.put("Button.background", new Color(210, 105, 30));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 13));

		setTitle("👨‍🏫 Professor Panel - GQT CMS");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel background = new JPanel(new BorderLayout());
		background.setBackground(new Color(255, 248, 240));

		JLabel heading = new JLabel("Professor Dashboard", JLabel.CENTER);
		heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
		heading.setForeground(new Color(139, 69, 19));
		heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		background.add(heading, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new GridLayout(4, 1, 15, 15));
		btnPanel.setBackground(background.getBackground());
		btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 30, 100));

		JButton registerBtn = createStyledButton("Register as Professor");
		JButton viewCoursesBtn = createStyledButton("View Available Courses");
		JButton assignBtn = createStyledButton("Assign Yourself to a Course");
		JButton backBtn = createStyledButton("Back to Main Menu");

		btnPanel.add(registerBtn);
		btnPanel.add(viewCoursesBtn);
		btnPanel.add(assignBtn);
		btnPanel.add(backBtn);

		background.add(btnPanel, BorderLayout.CENTER);
		add(background);

		registerBtn.addActionListener(e -> showProfessorRegistrationDialog());
		viewCoursesBtn.addActionListener(e -> showCourseList());
		assignBtn.addActionListener(e -> showAssignmentDialog());
		backBtn.addActionListener(e -> {
			dispose();
			new MainWindow();
		});

		setVisible(true);
	}

	private void showProfessorRegistrationDialog() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBackground(new Color(255, 248, 240));

		JTextField nameField = new JTextField();
		JTextField emailField = new JTextField();

		panel.add(new JLabel("Professor Name:"));
		panel.add(nameField);
		panel.add(new JLabel("Email:"));
		panel.add(emailField);

		int result = JOptionPane.showConfirmDialog(this, panel, "Professor Registration",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String name = nameField.getText().trim();
			String email = emailField.getText().trim();

			if (!name.isEmpty() && !email.isEmpty()) {
				Professor professor = new Professor(name, email);
				ProfessorDAO.addProfessor(professor);
			} else {
				DialogUtil.showMessage("All fields are required.");
			}
		}
	}

	private void showCourseList() {
		List<String[]> courseData = CourseDAO.getCourseIdAndNames();  // Each item: [cid, cname]

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
		table.getTableHeader().setBackground(new Color(210, 180, 140));
		table.setBackground(new Color(255, 248, 240));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		scrollPane.getViewport().setBackground(new Color(255, 248, 240));

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(255, 248, 240));
		panel.add(scrollPane, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(this, panel, "📘 Available Courses", JOptionPane.PLAIN_MESSAGE);
	}

	private void showAssignmentDialog() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBackground(new Color(255, 248, 240));

		JTextField pidField = new JTextField();
		JTextField cidField = new JTextField();

		panel.add(new JLabel("Professor ID:"));
		panel.add(pidField);
		panel.add(new JLabel("Course ID:"));
		panel.add(cidField);

		int result = JOptionPane.showConfirmDialog(this, panel, "Assign Yourself to Course",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				int pid = Integer.parseInt(pidField.getText().trim());
				int cid = Integer.parseInt(cidField.getText().trim());

				if (ProfessorDAO.assignCourseToProfessor(pid, cid)) {
					DialogUtil.showMessage("Course assigned successfully!");
				} else {
					DialogUtil.showMessage("Assignment failed. Check IDs.");
				}
			} catch (NumberFormatException ex) {
				DialogUtil.showMessage("Please enter valid numeric IDs.");
			}
		}
	}

	private JButton createStyledButton(String text) {
		JButton btn = new JButton(text);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(210, 105, 30));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		Color defaultColor = btn.getBackground();
		Color hoverColor = new Color(160, 82, 45);

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
