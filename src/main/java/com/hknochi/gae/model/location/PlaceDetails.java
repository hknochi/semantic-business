package com.hknochi.gae.model.location;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3800473262633886786L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("url")
	private String url;

	@JsonProperty("formatted_address")
	private String address;

	@JsonProperty("geometry")
	private PlaceGeometry geometry;

	@JsonProperty("photos")
	private List<PlacePhoto> photos = Collections.emptyList();

	public String getAddress() {
		return address;
	}

	public PlaceGeometry getGeometry() {
		return geometry;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public List<PlacePhoto> getPhotos() {
		return photos;
	}

	public String getUrl() {
		return url;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setGeometry(PlaceGeometry geometry) {
		this.geometry = geometry;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotos(List<PlacePhoto> photos) {
		this.photos = photos;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
