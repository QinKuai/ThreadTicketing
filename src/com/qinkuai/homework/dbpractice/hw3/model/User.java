package com.qinkuai.homework.dbpractice.hw3.model;

public class User {
	//用户名
	private String username;
	//密码
	private String passwd;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public User() {
	}
	
	public User(String username, String passwd) {
		this.username = username;;
		this.passwd = passwd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (passwd == null) {
			if (other.passwd != null) {
				return false;
			}
		} else if (!passwd.equals(other.passwd)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
}
