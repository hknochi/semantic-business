package com.hknochi.gae;

import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "/org/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAllPlacesAsFile() {
		List<PlacesResource> results = new ArrayList<PlacesResource>(
				rdfService.getAllResource(PlacesResource.class));
		StringBuffer resp= new StringBuffer();
		for (PlacesResource resSring : results) {
			resp.append(resSring.getRdfContent().getValue());
		}
		return getResourceAsFile("PlacesResource", resp);
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

	@RequestMapping(value = "/location/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAllLocationsAsFile() {
		List<LocationResource> results = new ArrayList<LocationResource>(
				rdfService.getAllResource(LocationResource.class));
		StringBuffer resp= new StringBuffer();
		for (LocationResource resSring : results) {
			resp.append(resSring.getRdfContent().getValue());
		}
		return getResourceAsFile("PlacesResource", resp);
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
	
	@RequestMapping(value = "/address/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAllAddressesAsFile() {
		List<AddressResource> results = new ArrayList<AddressResource>(
				rdfService.getAllResource(AddressResource.class));
		StringBuffer resp= new StringBuffer();
		for (AddressResource resSring : results) {
			resp.append(resSring.getRdfContent().getValue());
		}
		return getResourceAsFile("PlacesResource", resp);
	}
	

	@RequestMapping(value = "/address/{id}/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAddressResourceAsFile(
			@PathVariable(value = "id") String resrouceId) {

		return getResourceAsFile(resrouceId,
				rdfService.getResource(AddressResource.class, resrouceId));
	}

	@RequestMapping(value = "/media/photo/{id}", method = RequestMethod.GET)
	public String getPhoto(@PathVariable(value = "id") String resrouceId) {
		return "redirect: /geolocation/places/photo?photoreference="
				+ resrouceId;
	}
	
	@RequestMapping(value = "/all/file", method = RequestMethod.GET)
	public @ResponseBody
	HttpEntity<byte[]> getAllResourcesAsFile() {
		List<AddressResource> addressResource = new ArrayList<AddressResource>(
				rdfService.getAllResource(AddressResource.class));
		List<LocationResource> locationResource = new ArrayList<LocationResource>(
				rdfService.getAllResource(LocationResource.class));
		List<PlacesResource> placesResource = new ArrayList<PlacesResource>(
				rdfService.getAllResource(PlacesResource.class));
		StringBuffer resp= new StringBuffer();
		for (AddressResource resSring : addressResource) {
			resp.append(resSring.getRdfContent().getValue());
		}
		for (LocationResource resSring : locationResource) {
			resp.append(resSring.getRdfContent().getValue());
		}
		for (PlacesResource resSring : placesResource) {
			resp.append(resSring.getRdfContent().getValue());
		}
		return getResourceAsFile("all", resp);
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

	private HttpEntity<byte[]> getResourceAsFile(String fileName,
			StringBuffer resource) {
		byte[] documentBody = resource.toString().getBytes();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("text", "turtle"));
		header.set("Content-Disposition", "attachment; filename=" + fileName
				+ ".txt");
		header.setContentLength(documentBody.length);

		return new HttpEntity<byte[]>(documentBody, header);
	}

}
