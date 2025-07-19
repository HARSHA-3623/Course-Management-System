package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;
import com.gqt.cms.utils.DialogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {
	private static Connection con = CourseDatabase.getConnection();

	public static void addCourse(String cname, String duration, float fees) {
		try {
			String query = "INSERT INTO courses (cname, duration, fees) VALUES (?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, cname);
			pst.setString(2, duration);
			pst.setFloat(3, fees);
			pst.executeUpdate();
			DialogUtil.showMessage("Course added successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Failed to add course. Try again.");
		}
	}
	

	public static boolean deleteCourse(String courseName) {
		try {
			// Get course ID first
			String getCidQuery = "SELECT cid FROM courses WHERE cname = ?";
			PreparedStatement getCidStmt = con.prepareStatement(getCidQuery);
			getCidStmt.setString(1, courseName);
			ResultSet rs = getCidStmt.executeQuery();

			if (rs.next()) {
				int cid = rs.getInt("cid");

				// First delete enrollments
				String delEnrollments = "DELETE FROM enrollments WHERE cid = ?";
				PreparedStatement delEnrollStmt = con.prepareStatement(delEnrollments);
				delEnrollStmt.setInt(1, cid);
				delEnrollStmt.executeUpdate();

				// Then delete course
				String delCourse = "DELETE FROM courses WHERE cid = ?";
				PreparedStatement delCourseStmt = con.prepareStatement(delCourse);
				delCourseStmt.setInt(1, cid);
				int rows = delCourseStmt.executeUpdate();

				return rows > 0;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
