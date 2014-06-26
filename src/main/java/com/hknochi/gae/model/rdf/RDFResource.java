package com.hknochi.gae.model.rdf;

import java.io.Serializable;

import com.google.appengine.api.datastore.Text;

public interface RDFResource extends Serializable{

	String getId();
	void setId(String id);
	
	String getUri();
	void setUri(String uri);
	
	Text getRdfContent();
	void setRdfContent(Text rdfContent);
}
