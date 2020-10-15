package com.kh.second.test.model.vo;

public class CryptoUser implements java.io.Serializable {
	private static final long serialVersionUID = 5555L;
	
	private String userid;
	private String userpwd;
	private String username;
	
	public CryptoUser() {}
	
	
	public CryptoUser(String userid, String userpwd, String username) {
		super();
		this.userid = userid;
		this.userpwd = userpwd;
		this.username = username;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "CryptoUser [userid=" + userid + ", userpwd=" + userpwd + ", username=" + username + "]";
	}
	
	
}
