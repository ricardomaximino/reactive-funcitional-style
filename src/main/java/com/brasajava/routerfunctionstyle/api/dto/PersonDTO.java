package com.brasajava.routerfunctionstyle.api.dto;

public class PersonDTO {
	
  private String id;
  private String name;
  private String lastname;
  private String username;
  private String password;
  
  
public PersonDTO() {}


public PersonDTO(String id, String name, String lastname, String username, String password) {
	super();
	this.id = id;
	this.name = name;
	this.lastname = lastname;
	this.username = username;
	this.password = password;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
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


@Override
public String toString() {
	return "LeadDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username + ", password="
			+ password + "]";
}
  
  
}
