package com.hknochi.gae.service;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.code.geocoder.model.LatLng;
import com.hknochi.gae.model.location.PlaceDetails;
import com.hknochi.gae.model.location.PlaceDetailsResponse;
import com.hknochi.gae.model.location.PlacesException;
import com.hknochi.gae.model.location.PlacesSearchResponse;
import com.hknochi.gae.util.GoogleApiKeyService;

@Service
public class GooglePlacesService {

	private static final Logger logger = LoggerFactory
			.getLogger(GooglePlacesService.class);

	private static final String PLACE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?photoreference"
			+ "={photoreference}&maxwidth={maxwidth}&maxheight={maxheight}&sensor={sensor}&key={key}";

	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?reference"
			+ "={searchId}&sensor={sensor}&key={key}";

	private static final String PLACE_NEARBY_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location"
			+ "={latLng}&radius={radius}&types={types}&name={name}&sensor={sensor}&key={key}";

	private static final String PLACE_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query"
			+ "={query}&location={latLng}&radius={radius}&types={types}&sensor={sensor}&key={key}";

	private static final String PLACE_TEXT_NEXT_PAGE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?"
			+ "pagetoken={token}&sensor=false&key={key}";
	
	private static final String PLACE_NEARBY_NEXT_PAGE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "pagetoken={token}&sensor=false&key={key}";

	private RestTemplate restTemplate;

	@Autowired
	private MemcacheService memcacheService;

	@Autowired
	private GoogleApiKeyService googleApiKeyService;

	private static final int DEFAULT_EXPIRATION_TIME = 86400;

	private static final int PRECISION = 3;

	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();
	}

	/**
	 * Fetches the details of a place identified by a reference (searchId)
	 * 
	 * @param searchId
	 *            the place reference identifier
	 * @return
	 * @return the details of a place identified by a reference (searchId)
	 */
	public PlaceDetails getPlaceDetails(String searchId, String sensor) {
		try {
			return details(searchId, sensor);
		} catch (PlacesException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param location
	 * @param radius
	 * @return a list of places within the radius around the given location
	 */
	public PlacesSearchResponse getPlacesNearby(LatLng location, Long radius,
			String types, String name, String sensor) {
		return getPlacesNearby(location.toUrlValue(), radius, types, name,
				sensor);
	}

	/**
	 * 
	 * @param location
	 * @param radius
	 * @return a list of places within the radius around the given location
	 */
	public PlacesSearchResponse getPlacesNearby(String location, Long radius,
			String types, String name, String sensor) {
		try {
			return nearBySearch(location, radius, types, name, sensor);
		} catch (PlacesException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PlacesSearchResponse getNextPlacesNearby(String token) {
		try {
			return nearbySearch(token);
		} catch (PlacesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param query
	 * @param location
	 * @param radius
	 * @param types
	 * @param sensor
	 * @return
	 */
	public PlacesSearchResponse getPlacesText(String query, String location,
			Long radius, String types, String sensor) {
		try {
			return textSearch(query, location, radius, types, sensor);
		} catch (PlacesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public PlacesSearchResponse getNextPlaces(String token) {
		try {
			return textSearch(token);
		} catch (PlacesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<byte[]> getPhoto(String photoreference,
			String maxwidth, String maxheight, String sensor) {
		try {
			return photo(photoreference, maxwidth, maxheight, sensor);
		} catch (PlacesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private PlaceDetails details(String searchId, String sensor)
			throws PlacesException {
		PlaceDetailsResponse response = (PlaceDetailsResponse) memcacheService
				.get(searchId);
		if (response != null && response.getResult() != null) {
			System.out.println("local source");
			return response.getResult();
		}

		response = restTemplate.getForObject(PLACE_DETAILS_URL,
				PlaceDetailsResponse.class, searchId, sensor,
				googleApiKeyService.getGoogleApiKey());

		logger.debug("external source with apikey:"
				+ googleApiKeyService.getGoogleApiKey());

		if (response.getResult() != null) {
			memcacheService.put(searchId, response,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
			return response.getResult();
		} else {
			throw new PlacesException("Unable to find details for reference: "
					+ searchId);
		}
	}

	private PlacesSearchResponse nearBySearch(String location, Long radius,
			String types, String name, String sensor) throws PlacesException {
		PlacesSearchResponse response = (PlacesSearchResponse) memcacheService
				.get(getKey(name + location + types));
		if (response != null) {
			System.out.println("local source");
			return response;
		}

		response = restTemplate.getForObject(PLACE_NEARBY_SEARCH_URL,
				PlacesSearchResponse.class, location, radius, types, name,
				sensor, googleApiKeyService.getGoogleApiKey());
		logger.debug("external source with apikey:"
				+ googleApiKeyService.getGoogleApiKey());

		if (response.getResults() != null) {
			memcacheService.put(getKey(name + location + types), response,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
			return response;
		} else {
			throw new PlacesException("Unable to find places for location: "
					+ location.toString() + " and radius: " + radius);
		}
	}

	private PlacesSearchResponse textSearch(String query, String location,
			Long radius, String types, String sensor) throws PlacesException {
		PlacesSearchResponse response = (PlacesSearchResponse) memcacheService
				.get(getKey(query + location + types));
		if (response != null) {
			System.out.println("local source");
			return response;
		}

		response = restTemplate.getForObject(PLACE_TEXT_SEARCH_URL,
				PlacesSearchResponse.class, query, location, radius, types,
				sensor, googleApiKeyService.getGoogleApiKey());
		logger.debug("external source with apikey:"
				+ googleApiKeyService.getGoogleApiKey());

		if (response.getResults() != null) {
			memcacheService.put(getKey(query + location + types), response,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
			return response;
		} else {
			throw new PlacesException("Unable to find places for location: "
					+ location.toString() + " and radius: " + radius);
		}
	}

	private PlacesSearchResponse textSearch(String token)
			throws PlacesException {
		PlacesSearchResponse response = (PlacesSearchResponse) memcacheService
				.get(getKey(token));
		if (response != null) {
			System.out.println("local source");
			return response;
		}

		response = restTemplate.getForObject(PLACE_TEXT_NEXT_PAGE_URL,
				PlacesSearchResponse.class, token,
				googleApiKeyService.getGoogleApiKey());
		logger.debug("external source with apikey:"
				+ googleApiKeyService.getGoogleApiKey());

		if (response.getResults() != null) {
			memcacheService.put(getKey(token), response,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
			return response;
		} else {
			throw new PlacesException("Unable to find next-page for token: "
					+ token);
		}
	}
	
	private PlacesSearchResponse nearbySearch(String token)
			throws PlacesException {
		PlacesSearchResponse response = (PlacesSearchResponse) memcacheService
				.get(getKey(token));
		if (response != null) {
			System.out.println("local source");
			return response;
		}

		response = restTemplate.getForObject(PLACE_NEARBY_NEXT_PAGE_URL,
				PlacesSearchResponse.class, token,
				googleApiKeyService.getGoogleApiKey());
		logger.debug("external source with apikey:"
				+ googleApiKeyService.getGoogleApiKey());

		if (response.getResults() != null) {
			memcacheService.put(getKey(token), response,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
			return response;
		} else {
			throw new PlacesException("Unable to find next-page for token: "
					+ token);
		}
	}

	private ResponseEntity<byte[]> photo(String photoreference,
			String maxwidth, String maxheight, String sensor)
			throws PlacesException {
		URI uri = (URI) memcacheService.get(photoreference);

		if (uri == null) {
			System.out.println("local source");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			HttpEntity requestEntity = new HttpEntity(headers);
			uri = restTemplate.postForLocation(PLACE_PHOTO_URL, requestEntity,
					photoreference, maxwidth, maxheight, sensor,
					googleApiKeyService.getGoogleApiKey());
			memcacheService.put(photoreference, uri,
					Expiration.byDeltaSeconds(DEFAULT_EXPIRATION_TIME));
		}

		/*
		 * ResponseEntity<byte[]> response =
		 * restTemplate.exchange(PLACE_PHOTO_URL, HttpMethod.GET, requestEntity,
		 * byte[].class, photoreference, maxwidth, maxheight, sensor,
		 * googleApiKeyService.getGoogleApiKey());
		 */

		return restTemplate.exchange(uri, HttpMethod.GET, null, byte[].class);
	}

	/**
	 * generate key for memCacheServive
	 * 
	 * @param location
	 * @param radius
	 * @return
	 */
	private Key getKey(LatLng location, Long radius, String types, String name) {
		/* evaluate if toUrlValue with precision is also okay ?!? */
		return KeyFactory.createKey(location.toString() + types + name, radius);
	}

	/**
	 * generate key for memCacheServive
	 * 
	 * @param location
	 * @param radius
	 * @return
	 */
	private Key getKey(String keyString) {
		/* evaluate if toUrlValue with precision is also okay ?!? */
		return KeyFactory.createKey(LatLng.class.getName(), keyString);
	}
}
