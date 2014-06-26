package com.hknochi.gae;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hknochi.gae.model.location.PlaceDetails;
import com.hknochi.gae.model.location.PlacesSearchResponse;
import com.hknochi.gae.service.GooglePlacesService;
import com.hknochi.gae.service.RDFService;
import com.hknochi.gae.util.RDFTypes;

/**
 * GooglePlacesServiceWrapper with placesService
 * 
 * possibility to hide googleapikey (maybe keyswitch)
 * 
 * @returns json
 * 
 *          get list of supported types here
 *          https://developers.google.com/places
 *          /documentation/supported_types?hl=de
 * @author HKnochi
 * 
 */
@Controller
@RequestMapping(value = "/geolocation/places")
@SessionAttributes
public class GooglePlacesController {

	@Autowired
	private GooglePlacesService placesService;
	
	@Autowired
	private RDFService rdfService;

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public @ResponseBody
	PlaceDetails details(
			@RequestParam(value = "searchId") String searchId,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor) {
		return placesService.getPlaceDetails(searchId, sensor);
	}

	@RequestMapping(value = "/nearbysearch", method = RequestMethod.GET)
	public @ResponseBody
	PlacesSearchResponse nearbysearch(
			@RequestParam(value = "location") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor) {
		return placesService.getPlacesNearby(location, radius, types, name,
				sensor);
	}

	@RequestMapping(value = "/nearbysearchLatLng", method = RequestMethod.GET)
	public @ResponseBody
	PlacesSearchResponse nearbysearch(
			@RequestParam(value = "lat") BigDecimal lat,
			@RequestParam(value = "lng") BigDecimal lng,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor) {
		return placesService.getPlacesNearby(lat + "," + lng, radius, types,
				name, sensor);
	}

	@RequestMapping(value = "/textsearch", method = RequestMethod.GET)
	public @ResponseBody
	PlacesSearchResponse textsearch(
			@RequestParam(value = "query") String query,
			@RequestParam(value = "location", required = false, defaultValue = "") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor) {
		return placesService.getPlacesText(query, location, radius, types,
				sensor);
	}
	
	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public @ResponseBody
	PlacesSearchResponse textsearch(
			@RequestParam(value = "token") String token) {
		return placesService.getNextPlaces(token);
	}
	
	@RequestMapping(value = "/textsearch", method = RequestMethod.GET, produces={"text/turtle"})
	public @ResponseBody
	String textsearchAsRDF(
			@RequestParam(value = "query") String query,
			@RequestParam(value = "location", required = false, defaultValue = "") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor) throws IOException {
		PlacesSearchResponse resp = placesService.getPlacesText(query, location, radius, types,
				sensor);
		return rdfService.convert(resp, RDFTypes.TURTLE);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/photo", method = RequestMethod.GET)
	public ResponseEntity<byte[]> photo(
			@RequestParam(value = "photoreference") String photoreference,
			@RequestParam(value = "maxwidth", required = false, defaultValue = "640") String maxwidth,
			@RequestParam(value = "maxheight", required = false, defaultValue = "480") String maxheight,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor,
			@RequestParam(value = "inline", required = false) Boolean inline,
			HttpServletResponse resp) {
		ResponseEntity<byte[]> response = placesService.getPhoto(
				photoreference, maxwidth, maxheight, sensor);
		// System.out.println("headers:"+ new
		// Gson().toJson(response.getHeaders()));
		return response;
	}

}
