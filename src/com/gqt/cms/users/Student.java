package com.gqt.cms.users;

public class Student {
	private int id;
	private String name;
	private String email;
	private int enrolledCourseId;

	public Student(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Student(int id, String name, String email, int enrolledCourseId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.enrolledCourseId = enrolledCourseId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getEnrolledCourseId() {
		return enrolledCourseId;
	}

	public void setEnrolledCourseId(int enrolledCourseId) {
		this.enrolledCourseId = enrolledCourseId;
	}
}
