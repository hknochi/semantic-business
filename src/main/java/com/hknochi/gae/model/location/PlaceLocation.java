package com.hknochi.gae.model.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceLocation implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2961445779289992758L;

	@JsonProperty("lat")
	private String lat;

	@JsonProperty("lng")
	private String lng;

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
