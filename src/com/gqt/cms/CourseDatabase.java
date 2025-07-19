package com.gqt.cms;

import java.sql.Connection;
import java.sql.DriverManager;

public class CourseDatabase {
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/cms_db";
		String userName = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
