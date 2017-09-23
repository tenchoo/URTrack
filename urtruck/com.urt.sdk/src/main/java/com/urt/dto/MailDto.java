package com.urt.dto;

public class MailDto {
	public static final String ENCODEING = "UTF-8";  
	  
    private String host; // 服务器地址  
  
    private String sender; // 发件人的邮箱  
  
    private String receiver; // 收件人的邮箱  
  
    private String name; // 发件人昵称  
  
    private String username; // 账号  
  
    private String password; // 密码  
  
    private String subject; // 主题  
  
    private String message; // 信息(支持HTML) 

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getEncodeing() {
		return ENCODEING;
	}
	public MailDto(String receiver,String subject,String message){
		this.setHost("smtp.163.com");
		this.setSender("18519575569@163.com");
		this.setName("LenovoConnect");
		this.setUsername("18519575569@163.com");
		this.setPassword("chenjj7");
		this.setMessage(message);
		this.setReceiver(receiver);
		this.setSubject(subject);
	}

	public MailDto() {
		// TODO Auto-generated constructor stub
	}
    
}
