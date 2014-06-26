package com.hknochi.gae.crawler;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hknochi.gae.HomeController;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|pdf))$");

	private String domain = "http://leipzig.stadtbranchenbuch.com/";

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& href.startsWith(domain);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        logger.info("Docid: " + docid);
        logger.info("URL: " + url);
        logger.info("Domain: '" + domain + "'");
        logger.info("Sub-domain: '" + subDomain + "'");
        logger.info("Path: '" + path + "'");
        logger.info("Parent page: " + parentUrl);
        logger.info("Anchor text: " + anchor);
        
        
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			// String text = htmlParseData.getText();
			// String html = htmlParseData.getHtml();
			List<WebURL> links = htmlParseData.getOutgoingUrls();

			// System.out.println("Text length: " + text.length());
			// System.out.println("Html length: " + html.length());
			logger.info("Number of outgoing links: " + links.size());
		}
	}

	public synchronized final String getDomain() {
		return domain;
	}

	public synchronized final void setDomain(String domain) {
		this.domain = domain;
	}

}
