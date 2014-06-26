package com.hknochi.gae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hknochi.gae.service.GooglePlacesService;

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

	@RequestMapping(value = "convert", method = RequestMethod.GET)
	public String getConverter() throws Exception {
		return "convert";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "q") String query,
			@RequestParam(value = "location", required = false, defaultValue = "") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor,
			Model model) throws Exception {
		model.addAttribute("results", placesService.getPlacesText(query,
				location, radius, types, sensor));
		return "convert";
	}

	@RequestMapping(value = "nearby", method = RequestMethod.GET)
	public String nearby(
			@RequestParam(value = "location") String location,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") Long radius,
			@RequestParam(value = "types", required = false, defaultValue = "") String types,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "sensor", required = false, defaultValue = "false") String sensor,
			Model model) throws Exception {
		model.addAttribute("results", placesService.getPlacesNearby(location,
				radius, types, name, sensor));
		return "convert";
	}

	@RequestMapping(value = "next", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "t") String token,
			@RequestParam(value = "p", required = false, defaultValue = "0") int page,
			Model model) throws Exception {
		model.addAttribute("page", page);
		model.addAttribute("results", placesService.getNextPlaces(token));
		return "convert";
	}
}
