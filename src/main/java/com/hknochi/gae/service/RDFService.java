package com.hknochi.gae.service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Text;
import com.hknochi.gae.model.location.PlaceGeometry;
import com.hknochi.gae.model.location.PlacePhoto;
import com.hknochi.gae.model.location.Places;
import com.hknochi.gae.model.location.PlacesSearchResponse;
import com.hknochi.gae.model.rdf.AddressResource;
import com.hknochi.gae.model.rdf.LocationResource;
import com.hknochi.gae.model.rdf.PlacesResource;
import com.hknochi.gae.model.rdf.RDFResource;
import com.hknochi.gae.util.RDFRepository;
import com.hknochi.gae.util.RDFTypes;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import fr.dudie.nominatim.model.Address;
import fr.dudie.nominatim.model.AddressElement;

@Service
public class RDFService {

	private static final Logger logger = LoggerFactory
			.getLogger(RDFService.class);

	private static final String SCHEMA = "http://schema.org/";
	private static final String LGD = "http://linkedgeodata.org/triplify/";
	// private static final String NS =
	// "http://semantic-business.appspot.com/resource/";
	// private static final String SERVICE =
	// "http://semantic-business.appspot.com/geolocation/places/details?searchId=";
	private static final String NS = "http://localhost:8080/resource/";
	private static final String SERVICE = "http://localhost:8080/geolocation/places/details?searchId=";

	@Autowired
	private NominatimService geocoderService;

	@Autowired
	private RDFRepository repo;

	private static final int DEFAULT_EXPIRATION_TIME = 86400;

	private StringWriter writer = new StringWriter();

	public String convert(PlacesSearchResponse resp, RDFTypes rdfType)
			throws IOException {
		List<Places> places = resp.getResults();

		for (Places place : places) {
			convert(place, rdfType).getRdfContent();
		}
		return writer.toString();
	}

	public RDFResource getResource(Class clazz, String resrouceId) {
		return repo.getEntityManager().find(clazz, resrouceId);
	}

	public PlacesResource convert(Places place, RDFTypes rdfType)
			throws IOException {
		StringWriter out;

		Model model = ModelFactory.createDefaultModel();

		Resource res = model.createResource(NS + "org/" + place.getId());
		res.addProperty(RDF.type, model.createResource(SCHEMA + "Place"));
		res.addProperty(RDFS.label, place.getName());
		res.addProperty(model.createProperty(SCHEMA + "name"), place.getName());
		res.addProperty(
				model.createProperty(SCHEMA + "geo"),
				model.createResource(NS + "location/"
						+ place.getGeometry().hashCode()));
		for (PlacePhoto photo : place.getPhotos()) {
			res.addProperty(
					model.createProperty(SCHEMA + "image"),
					model.createResource(NS + "media/photo/"
							+ photo.getReference()));
		}
		for (String type : place.getTypes()) {
			res.addProperty(model.createProperty(SCHEMA + "additionalType"),
					model.createResource(NS + "type/" + type));
		}

		res.addProperty(model.createProperty(SCHEMA + "url"),
				SERVICE + place.getReference());

		PlacesResource placesResource = new PlacesResource();
		placesResource.setId(place.getId());
		placesResource.setUri(res.getURI().toString());

		LocationResource locationResource = convert(place.getGeometry(),
				rdfType);
		res.addProperty(
				model.createProperty(SCHEMA + "address"),
				model.createResource(NS
						+ "address/"
						+ getResource(AddressResource.class,
								locationResource.getAddressResourceId())
								.getId()));

		out = new StringWriter();
		model.write(out, rdfType.type());
		model.close();

		placesResource.setRdfContent(new Text(out.toString()));
		placesResource.setLocationResourceId(locationResource.getId());

		repo.getEntityManager().persist(placesResource);
		writer.append(out.toString());
		return placesResource;
	}

	private LocationResource convert(PlaceGeometry geometry, RDFTypes rdfType)
			throws IOException {
		Model model = ModelFactory.createDefaultModel();

		BigDecimal lat = new BigDecimal(geometry.getLocation().getLat());
		BigDecimal lng = new BigDecimal(geometry.getLocation().getLng());
		Address addr = geocoderService.reverse(lat, lng);
		AddressResource addrResource = convert(addr, rdfType);

		Resource geoRes = model.createResource(NS + "location/"
				+ geometry.hashCode());
		geoRes.addProperty(RDF.type,
				model.createResource(SCHEMA + "GeoCoordinates"));
		geoRes.addProperty(model.createProperty(SCHEMA + "latitude"), geometry
				.getLocation().getLat());
		geoRes.addProperty(model.createProperty(SCHEMA + "longitude"), geometry
				.getLocation().getLng());
		geoRes.addProperty(model.createProperty(SCHEMA + "sameAs"),
				model.createResource(LGD + addr.getOsmType() + addr.getOsmId()));

		StringWriter out = new StringWriter();
		model.write(out, rdfType.type());
		model.close();

		LocationResource locationResource = new LocationResource();
		locationResource.setId("" + geometry.hashCode());
		locationResource.setUri(geoRes.getURI().toString());
		locationResource.setRdfContent(new Text(out.toString()));

		locationResource.setAddressResourceId(addrResource.getId());

		repo.getEntityManager().persist(locationResource);
		writer.append(out.toString());
		return locationResource;
	}

	private AddressResource convert(Address address, RDFTypes rdfType)
			throws IOException {
		Model model = ModelFactory.createDefaultModel();

		Resource addrRes = model.createResource(NS + "address/"
				+ address.getPlaceId());

		addrRes.addProperty(RDF.type,
				model.createResource(SCHEMA + "PostalAddress"));
		for (AddressElement element : address.getAddressElements()) {
			switch (element.getKey()) {
			case "country": {
				addrRes.addProperty(
						model.createProperty(SCHEMA + "addressCountry"),
						element.getValue());
				break;
			}
			case "city": {
				addrRes.addProperty(
						model.createProperty(SCHEMA + "addressLocality"),
						element.getValue());
				break;
			}
			case "state": {
				addrRes.addProperty(
						model.createProperty(SCHEMA + "addressRegion"),
						element.getValue());
				break;
			}
			case "postcode": {
				addrRes.addProperty(
						model.createProperty(SCHEMA + "postalCode"),
						element.getValue());
				break;
			}
			case "road": {
				addrRes.addProperty(
						model.createProperty(SCHEMA + "streetAddress"),
						element.getValue());
				break;
			}
			}
		}

		StringWriter out = new StringWriter();
		model.write(out, rdfType.type());
		model.close();

		AddressResource addressResource = new AddressResource();
		addressResource.setId("" + address.getPlaceId());
		addressResource.setUri(addrRes.getURI().toString());
		addressResource.setRdfContent(new Text(out.toString()));

		repo.getEntityManager().persist(addressResource);
		writer.append(out.toString());
		return addressResource;

	}

	public Collection getAllResource(Class clazz) {
		Query q = repo.getEntityManager().createQuery(
				"Select a from "+clazz.getName()+" a",clazz );
		return q.getResultList();

	}
}
