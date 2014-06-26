package com.hknochi.gae.model.location;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;

import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

@Repository
@Entity
public class GeoLocation implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -8915494075090059209L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double lat;
	private Double lng;

	private String formatedAdress;
	private List<GeocoderResult> results;

	public GeoLocation(){
		
	}
	
	public GeoLocation(final Double lat, final Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	/**
	 * @return the lat
	 */
	public synchronized final Double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public synchronized final void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public synchronized final Double getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public synchronized final void setLng(Double lng) {
		this.lng = lng;
	}

	/**
	 * @return the formatedAdress
	 */
	public synchronized final String getFormatedAdress() {
		return formatedAdress;
	}

	/**
	 * @param formatedAdress
	 *            the formatedAdress to set
	 */
	public synchronized final void setFormatedAdress(String formatedAdress) {
		this.formatedAdress = formatedAdress;
	}

	public synchronized final LatLng getLatLng() {
		return new LatLng(lat.toString(), lng.toString());
	}

	public List<GeocoderResult> getResults() {
		return results;
	}

	public void setResults(List<GeocoderResult> results) {
		this.formatedAdress=results.get(0).getFormattedAddress();
		this.results = results;
	}

}
