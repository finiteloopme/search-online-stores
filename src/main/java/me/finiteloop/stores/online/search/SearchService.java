package me.finiteloop.stores.online.search;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * 
 */

/**
 * @author klimaye
 *
 */
public class SearchService
	extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		restConfiguration().component("restlet").host("localhost").port(8182).bindingMode(RestBindingMode.auto);
		
		rest("/search")
			.consumes("application/json")
			.produces("application/json")
		.get("/")
			.to("direct:get-search-criteria");
		
		
		from("direct:getsearch-criteria")
			.log("Search criteria is: ${body}")
			.setBody(constant(""))
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant("200"));
	}

	
}
