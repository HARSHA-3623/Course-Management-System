package com.gqt.cms.datamanagement;

import com.gqt.cms.CourseDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
	private static Connection con = CourseDatabase.getConnection();

	public static List<String[]> getCourseIdAndNames() {
		List<String[]> courseList = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement("SELECT cid, cname FROM courses");
				ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				String[] row = { String.valueOf(rs.getInt("cid")), rs.getString("cname") };
				courseList.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}
	

	public static int getCourseIdByName(String courseName) {
		try (PreparedStatement pstmt = con.prepareStatement("SELECT cid FROM courses WHERE cname = ?")) {
			pstmt.setString(1, courseName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
