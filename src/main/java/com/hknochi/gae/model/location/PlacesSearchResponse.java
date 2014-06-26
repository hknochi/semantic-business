package com.hknochi.gae.model.location;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "next_page_token", "results" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesSearchResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1960820064742508313L;

	@JsonProperty("results")
	private List<Places> results = Collections.emptyList();

	@JsonProperty("next_page_token")
	private String nextPageToken;

	@JsonProperty("html_attributions")
	private List<String> htmlAttributions;
	
	
	public List<Places> getResults() {
		return results;
	}

	public void setResults(List<Places> results) {
		this.results = results;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public List<String> getHtmlAttributions() {
		return htmlAttributions;
	}

	public void setHtmlAttributions(List<String> htmlAttributions) {
		this.htmlAttributions = htmlAttributions;
	}
}
