package com.example.springsecurity.security;

public enum ApplicationUserPermission {
	
	STUDENT_READ("student_read"),
	STUDENT_WRITE("student_write"),
	COURSE_READ("course_read"),
	COURSE_WRITE("course_write");
	
	private final String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
}
