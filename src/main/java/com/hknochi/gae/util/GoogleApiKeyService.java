package com.hknochi.gae.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.hknochi.gae.util.GoogleKeyCatalogue.GoogleKey;

@Service
public class GoogleApiKeyService {

	private static GoogleKeyCatalogue keyCatalogue;
	
	
	@PostConstruct
	private void init(){
		keyCatalogue= new GoogleKeyCatalogue();
		keyCatalogue.addGoogleKey(new GoogleKey("AIzaSyCggwWTWB5mgsNFkbV6QzGN85tQmjH5l00"));
		keyCatalogue.addGoogleKey(new GoogleKey("AIzaSyAroDX5LquZzLetW96TIF8olVa_aGH56Pw"));
	}
	
	public String getGoogleApiKey(){
		return keyCatalogue.anyGoogleKey();
	}
	
}
