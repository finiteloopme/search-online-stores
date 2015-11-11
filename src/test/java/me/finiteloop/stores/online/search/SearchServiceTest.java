/**
 * 
 */
package me.finiteloop.stores.online.search;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import junit.framework.Assert;

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
		Exchange exchange = template.send("direct:getsearch-criteria", new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setBody("Testing");
				
			}
		});
		Assert.assertEquals("200", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
	}
}
