package me.finiteloop.stores.online.search;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * 
 */

/**
 * @author klimaye
 *
 */
@Path("search")
public class SearchService
	extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		
		// Properties resolution allows URI to be substituted at runtime.
		// Use of colon ':' allows specification of a default value.
		
		restConfiguration().component("restlet").host("{{sys:jboss.bind.address:localhost}}")
			.port("{{jboss.web.http.port:8182}}").bindingMode(RestBindingMode.auto);
		
		rest("/search")
			.consumes("application/json")
			.produces("application/json")
		.get("/{search-criteria}")
			.to("direct:get-search-criteria");
				
		from("direct:get-search-criteria")
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					Message msgIn = exchange.getIn();
					String searchCriteria = (String) msgIn.getHeader("search-criteria");
					if(searchCriteria!=null && !searchCriteria.trim().equalsIgnoreCase("")){
						exchange.getOut().setBody(searchCriteria);
					}
				}
			})
			.log("Search criteria is: ${body}")
			.setHeader("Accept", constant("text/plain"))
			.recipientList(
					simple("http4://api.img4me.com/?bcolor=B20000&fcolor=FFFFFF&font=comic&size=18&type=png&text=${body}")
			)
			.log("The image is: ${body}")
			// Convert the status code to String
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.Status.OK.getStatusCode() + ""));
	}
	
}
