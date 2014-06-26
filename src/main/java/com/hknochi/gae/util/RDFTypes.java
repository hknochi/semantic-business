package com.hknochi.gae.util;

public enum RDFTypes {

	TURTLE("Turtle"), N3("N3");
	
	private String type;
	
	RDFTypes(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type; 
	}
	
}
