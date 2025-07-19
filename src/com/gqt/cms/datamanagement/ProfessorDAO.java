package com.gqt.cms.datamanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gqt.cms.CourseDatabase;
import com.gqt.cms.users.Professor;
import com.gqt.cms.utils.DialogUtil;

public class ProfessorDAO {
	private static Connection con = CourseDatabase.getConnection();

	public static void addProfessor(Professor professor) {
		try {
			String query = "INSERT INTO professors (pname, email) VALUES (?, ?)";
			PreparedStatement pst = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, professor.getName());
			pst.setString(2, professor.getEmail());
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				int pid = rs.getInt(1);
				DialogUtil.showMessage("Professor registered successfully!\nProfessor ID: " + pid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showMessage("Registration failed. Try again.");
		}
	}

	public static boolean assignCourseToProfessor(int pid, int cid) {
		try {
			String query = "INSERT INTO professor_course (pid, cid) VALUES (?, ?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, pid);
			pst.setInt(2, cid);
			int updated = pst.executeUpdate();
			return updated > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
