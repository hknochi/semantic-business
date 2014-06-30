package com.hknochi.gae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.hknochi.gae.model.location.PlacesSearchResponse;
import com.hknochi.gae.service.GooglePlacesService;
import com.hknochi.gae.util.GooglePlacesTypes;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private GooglePlacesService placesService;

	@RequestMapping(value = "about", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "convert/nearby", method = RequestMethod.GET)
	public String getNeabyConverter(Model model) throws Exception {
		model.addAttribute("types", GooglePlacesTypes.list());
		return "nearby";
	}

	@RequestMapping(value = "convert/search", method = RequestMethod.GET)
	public String getConverter() throws Exception {
		return "search";
	}
	
	@RequestMapping(value = "map", method = RequestMethod.GET)
	public String getMap() throws Exception {
		return "map";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "q") String query,
			@RequestParam(value = "location", required = false, defaultValue = "") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor,
			Model model) throws Exception {
		PlacesSearchResponse resp = placesService.getPlacesText(query,
				location, radius, types, sensor);
		model.addAttribute("results", resp);
		if (resp.getNextPageToken() != null
				&& !resp.getNextPageToken().isEmpty()) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.addAsync(TaskOptions.Builder.withHeader("Accept", "text/turtle")
				.countdownMillis(5000).method(TaskOptions.Method.GET)
				.url("/geolocation/places/textsearch").param("query", query)
				.param("location", location));
		}
		return "search";
	}

	@RequestMapping(value = "nearby", method = RequestMethod.GET)
	public String nearby(
			@RequestParam(value = "location") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor,
			Model model) throws Exception {
		PlacesSearchResponse resp =placesService.getPlacesNearby(location,
				radius, types, name, sensor);
		model.addAttribute("results", resp);
		model.addAttribute("types", GooglePlacesTypes.list());
		
		if (resp.getNextPageToken() != null
				&& !resp.getNextPageToken().isEmpty()) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.addAsync(TaskOptions.Builder.withHeader("Accept", "text/turtle")
				.countdownMillis(5000).method(TaskOptions.Method.GET)
				.url("/geolocation/places/nearbysearch").param("types", types)
				.param("location", location).param("radius", ""+radius));
		}
		return "nearby";
	}

	@RequestMapping(value = "nearby/next", method = RequestMethod.GET)
	public String nearbyNext(
			@RequestParam(value = "t") String token,
			@RequestParam(value = "p", required = false, defaultValue = "0") int page,
			Model model) throws Exception {
		model.addAttribute("page", page);
		model.addAttribute("results", placesService.getNextPlacesNearby(token));
		model.addAttribute("types", GooglePlacesTypes.list());
		return "nearby";
	}

	@RequestMapping(value = "search/next", method = RequestMethod.GET)
	public String searchNext(
			@RequestParam(value = "t") String token,
			@RequestParam(value = "p", required = false, defaultValue = "0") int page,
			Model model) throws Exception {
		model.addAttribute("page", page);
		model.addAttribute("results", placesService.getNextPlaces(token));
		return "search";
	}
}
