package com.brasajava.routerfunctionstyle.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import com.brasajava.routerfunctionstyle.message.model.Event;

@Document
public class Person extends AbstractAggregateRoot<Person>{

	@Id
	private String id;
	private String name;
	private String lastname;
	private String username;
	private String password;

public Person() {}

public Person(String id, String name, String lastname, String username, String password) {
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
		return "LeadDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + "]";
	}
	
	public Person create(Event event) {
		registerEvent(event);
		return this;
	}
	
	public Person update(Event event) {
		registerEvent(event);
		return this;
	}

}
