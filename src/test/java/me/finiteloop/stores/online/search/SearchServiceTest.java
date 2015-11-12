/**
 * 
 */
package me.finiteloop.stores.online.search;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import org.junit.Assert;

/**
 * @author klimaye
 *
 */
public class SearchServiceTest 
	extends CamelTestSupport{
	
	@Override
	protected RouteBuilder createRouteBuilder(){
		return new SearchService();
	}
	
	@Test
	public void testDirectSearch(){
		Exchange exchange = template.send("direct:get-search-criteria", new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setBody("Testing");
				
			}
		});
		Assert.assertEquals("200", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
	}
	
	@Test
	public void testHTTPSearch(){
		Client client = ClientBuilder.newBuilder().newClient();
		WebTarget url = client.target("http://localhost:8182/search");
		Invocation.Builder builder = url.path("/").request(MediaType.APPLICATION_JSON);
		
		Response response = builder.get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
}
