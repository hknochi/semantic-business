package com.hknochi.gae.model.rdf;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Text;

@Repository
@Entity
public class AddressResource implements RDFResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6470357606991962151L;

	@Id
	private String id;

	private String uri;

	private Text rdfContent;

	public AddressResource() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Text getRdfContent() {
		return rdfContent;
	}

	public void setRdfContent(Text rdfContent) {
		this.rdfContent = rdfContent;
	}
}
