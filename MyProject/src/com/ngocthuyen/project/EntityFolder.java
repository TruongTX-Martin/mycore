package com.ngocthuyen.project;

import java.io.Serializable;

public class EntityFolder implements Serializable {

	private String name;

	public EntityFolder() {
	}

	public EntityFolder(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
