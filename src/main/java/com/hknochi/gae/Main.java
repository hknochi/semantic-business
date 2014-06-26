package com.hknochi.gae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hknochi.gae.service.CrawlerService;

public class Main {
	
	private static final Logger logger = LoggerFactory
			.getLogger(Main.class);

	public static void main(String[] args) throws Exception{
		logger.info("start");
		CrawlerService service = new CrawlerService();
		service.crawl();
	}
}
