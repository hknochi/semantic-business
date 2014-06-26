package com.hknochi.gae.model.location;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Places implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3801210399631797827L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("reference")
	private String reference;

	@JsonProperty("vicinity")
	private String vicinity;
	
	@JsonProperty("formatted_address")
	private String address;
	
	@JsonProperty("rating")
	private Double rating;

	@JsonProperty("geometry")
	private PlaceGeometry geometry;

	@JsonProperty("photos")
	private List<PlacePhoto> photos = Collections.emptyList();

	@JsonProperty("types")
	private List<String> types = Collections.emptyList();

	/**
	 * @return the id
	 */
	public synchronized final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public synchronized final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public synchronized final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public synchronized final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the icon
	 */
	public synchronized final String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public synchronized final void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the reference
	 */
	public synchronized final String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public synchronized final void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the vicinity
	 */
	public synchronized final String getVicinity() {
		return vicinity;
	}

	/**
	 * @param vicinity the vicinity to set
	 */
	public synchronized final void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	/**
	 * @return the rating
	 */
	public synchronized final Double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public synchronized final void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * @return the geometry
	 */
	public synchronized final PlaceGeometry getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public synchronized final void setGeometry(PlaceGeometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * @return the photos
	 */
	public synchronized final List<PlacePhoto> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public synchronized final void setPhotos(List<PlacePhoto> photos) {
		this.photos = photos;
	}

	/**
	 * @return the types
	 */
	public synchronized final List<String> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public synchronized final void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * @return the address
	 */
	public synchronized final String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public synchronized final void setAddress(String address) {
		this.address = address;
	}

}
