package com.hknochi.gae.model.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5863398466533092951L;
	@JsonProperty("result")
	private PlaceDetails result;

	public PlaceDetails getResult() {
		return result;
	}

	public void setResult(PlaceDetails result) {
		this.result = result;
	}
}
