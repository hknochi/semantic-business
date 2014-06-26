package com.hknochi.gae.model.rdf;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Text;

@Repository
@Entity
public class PlacesResource implements RDFResource{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2504549786398219201L;

	@Id
	private String id;
	
	private String uri;
	
	private Text rdfContent;
	
	private String locationResourceId; 
	
	public PlacesResource(){
		
	}

	public synchronized final String getId() {
		return id;
	}

	public synchronized final void setId(String id) {
		this.id = id;
	}

	public synchronized final Text getRdfContent() {
		return rdfContent;
	}

	public synchronized final void setRdfContent(Text rdfContent) {
		this.rdfContent = rdfContent;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLocationResourceId() {
		return locationResourceId;
	}

	public void setLocationResourceId(String locationResourceId) {
		this.locationResourceId = locationResourceId;
	}
}
