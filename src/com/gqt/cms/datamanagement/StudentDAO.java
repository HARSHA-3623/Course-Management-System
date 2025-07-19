package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;
import com.gqt.cms.users.Student;
import com.gqt.cms.utils.DialogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {
	private static Connection con = CourseDatabase.getConnection();

	public static void addStudent(Student student) {
		try {
			String query = "INSERT INTO students (sname, email) VALUES (?, ?)";
			PreparedStatement pst = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, student.getName());
			pst.setString(2, student.getEmail());
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				int sid = rs.getInt(1);
				DialogUtil.showMessage("Student registered successfully!\nStudent ID: " + sid);
			} else {
				DialogUtil.showMessage("Student registered but ID not retrieved.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Registration failed. Check DB connection or email duplication.");
		}
	}

	// Get student ID by name
	private static int getStudentId(String name) {
		try {
			String query = "SELECT sid FROM students WHERE sname = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("sid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static int getCourseId(String cname) {
		try {
			String query = "SELECT cid FROM courses WHERE cname = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, cname);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("cid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean enrollStudentById(String studentName, int cid) {
		int sid = getStudentId(studentName);

		if (sid == -1 || cid == -1) {
			DialogUtil.showMessage("Invalid student or course ID.");
			return false;
		}

		try {
			String query = "INSERT INTO enrollments (sid, cid) VALUES (?, ?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, sid);
			pst.setInt(2, cid);
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Enrollment failed in DB.");
		}
		return false;
	}

	public static boolean enrollStudentInCourse(String studentName, String courseName) {
		int sid = getStudentId(studentName);
		int cid = getCourseId(courseName);

		if (sid == -1) {
			DialogUtil.showMessage("Student not found: " + studentName);
			return false;
		}
		if (cid == -1) {
			DialogUtil.showMessage("Course not found: " + courseName);
			return false;
		}

		try {
			String query = "INSERT INTO enrollments (sid, cid) VALUES (?, ?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, sid);
			pst.setInt(2, cid);
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Enrollment failed in DB.");
		}
		return false;
	}

	public static boolean enrollStudentBySid(int sid, int cid) {
		if (sid <= 0 || cid <= 0) {
			DialogUtil.showMessage("Invalid Student ID or Course ID.");
			return false;
		}

		try {
			// Step 1: Insert into enrollments table
			String insertQuery = "INSERT INTO enrollments (sid, cid) VALUES (?, ?)";
			PreparedStatement insertStmt = con.prepareStatement(insertQuery);
			insertStmt.setInt(1, sid);
			insertStmt.setInt(2, cid);
			insertStmt.executeUpdate();

			// Step 2: Update enrolled_course_id in students table
			String updateQuery = "UPDATE students SET enrolled_course_id = ? WHERE sid = ?";
			PreparedStatement updateStmt = con.prepareStatement(updateQuery);
			updateStmt.setInt(1, cid);
			updateStmt.setInt(2, sid);
			updateStmt.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Enrollment failed in DB.");
		}
		return false;
	}

}
