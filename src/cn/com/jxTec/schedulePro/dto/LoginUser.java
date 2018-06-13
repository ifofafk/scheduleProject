package cn.com.jxTec.schedulePro.dto;

import java.io.Serializable;
/**
 * 
 * 2017年10月20日
 */
public class LoginUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	


}
