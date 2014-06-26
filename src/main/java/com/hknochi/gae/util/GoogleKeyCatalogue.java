package com.hknochi.gae.util;

import java.util.ArrayList;
import java.util.Random;

public class GoogleKeyCatalogue {

	private Random randomGenerator;
	private ArrayList<GoogleKey> catalogue;

	public GoogleKeyCatalogue() {
		catalogue = new ArrayList<GoogleKey>();
		randomGenerator = new Random();
	}
	
	public boolean addGoogleKey(GoogleKey googleKey){
		return catalogue.add(googleKey);
	}

	public String anyGoogleKey() {
		int index = randomGenerator.nextInt(catalogue.size());
		return catalogue.get(index).getApiKey();
	}

	public static class GoogleKey {

		private String apiKey;
		private int selectionCount=0;
		
		public GoogleKey(String apiKey) {
			this.apiKey=apiKey;
		}

		private String getApiKey(){
			this.selectionCount++;
			return this.apiKey;
		}
	}
}
