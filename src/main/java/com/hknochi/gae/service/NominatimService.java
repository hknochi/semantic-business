package com.hknochi.gae.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;

@Service
public class NominatimService {

	private static final Logger logger = LoggerFactory
			.getLogger(NominatimService.class);

	private static final String EMAIL = "hknochi@gmail.com";

	private JsonNominatimClient nominatimClient;

	@PostConstruct
	public void init() throws UnsupportedEncodingException {
		final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", new PlainSocketFactory(), 80));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);
        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);
        
        nominatimClient = new JsonNominatimClient(httpClient, EMAIL);
	}

	public Address reverse(BigDecimal lat, BigDecimal lng) throws IOException {
		return nominatimClient.getAddress(lng.doubleValue(), lat.doubleValue());
	}

	public List<Address> search(String query) throws IOException {
		return nominatimClient.search(query);
	}

}
