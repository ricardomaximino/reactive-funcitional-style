package com.brasajava.routerfunctionstyle.api.dto;

public class PersonIDDTO {
	private String id;

	public PersonIDDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonIDDTO(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LeadIDDTO [id=" + id + "]";
	}

}
