package com.hknochi.gae.model.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacePhoto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4038717097923681834L;
	
	@JsonProperty("photo_reference")
	private String reference;
	
	@JsonProperty("height")
	private int height;
	
	@JsonProperty("width")
	private int width;
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
