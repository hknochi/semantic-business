package com.hknochi.gae.model.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceGeometry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8602009672793082088L;
	
	@JsonProperty("location")
	private PlaceLocation location;

	public PlaceLocation getLocation() {
		return location;
	}

	public void setLocation(PlaceLocation location) {
		this.location = location;
	}
}
