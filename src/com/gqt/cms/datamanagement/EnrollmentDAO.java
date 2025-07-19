package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
	private static Connection con = CourseDatabase.getConnection();

	public static List<String> getAllEnrollments() {
		List<String> enrollments = new ArrayList<>();

		try {
			String query = "SELECT s.sname, c.cname FROM enrollments e " + "JOIN students s ON e.sid = s.sid "
					+ "JOIN courses c ON e.cid = c.cid";

			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String studentName = rs.getString("sname");
				String courseName = rs.getString("cname");
				enrollments.add(studentName + " → " + courseName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return enrollments;
	}

}
