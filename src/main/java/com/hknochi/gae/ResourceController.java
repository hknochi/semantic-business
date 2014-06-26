package com.hknochi.gae;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hknochi.gae.model.rdf.AddressResource;
import com.hknochi.gae.model.rdf.LocationResource;
import com.hknochi.gae.model.rdf.PlacesResource;
import com.hknochi.gae.model.rdf.RDFResource;
import com.hknochi.gae.service.GooglePlacesService;
import com.hknochi.gae.service.RDFService;

@Controller
@RequestMapping(value = "/resource")
@SessionAttributes
public class ResourceController {

	@Autowired
	private RDFService rdfService;

	@RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getPlacesResource(@PathVariable(value = "id") String resrouceId) {
		return rdfService.getResource(PlacesResource.class, resrouceId)
				.getRdfContent().getValue();
	}

	@RequestMapping(value = "/org/{id}/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getPlacesResourceAsFile(
			@PathVariable(value = "id") String resrouceId) {

		return getResourceAsFile(resrouceId,
				rdfService.getResource(LocationResource.class, resrouceId));
	}

	@RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLocationResource(@PathVariable(value = "id") String resrouceId) {
		return rdfService.getResource(LocationResource.class, resrouceId)
				.getRdfContent().getValue();
	}

	@RequestMapping(value = "/location/{id}/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getLocationResourceAsFile(
			@PathVariable(value = "id") String resrouceId) {

		return getResourceAsFile(resrouceId,
				rdfService.getResource(LocationResource.class, resrouceId));
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getAddressResource(@PathVariable(value = "id") String resrouceId) {
		return rdfService.getResource(AddressResource.class, resrouceId)
				.getRdfContent().getValue();
	}

	@RequestMapping(value = "/address/{id}/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAddressResourceAsFile(
			@PathVariable(value = "id") String resrouceId) {

		return getResourceAsFile(resrouceId,
				rdfService.getResource(AddressResource.class, resrouceId));
	}

	@RequestMapping(value = "/media/photo/{id}", method = RequestMethod.GET)
	public String getPhoto(
			@PathVariable(value = "id") String resrouceId) {
		return "redirect: /geolocation/places/photo?photoreference="+resrouceId;
	}

	private HttpEntity<byte[]> getResourceAsFile(String fileName,
			RDFResource resource) {
		byte[] documentBody = resource.getRdfContent().getValue().getBytes();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("text", "turtle"));
		header.set("Content-Disposition", "attachment; filename=" + fileName
				+ ".txt");
		header.setContentLength(documentBody.length);

		return new HttpEntity<byte[]>(documentBody, header);
	}

}
