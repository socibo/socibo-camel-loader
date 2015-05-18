package com.github.dulanov.cbrru

import org.apache.camel.EndpointInject
import org.apache.camel.Produce
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.language.groovy.GroovyRouteBuilder
import org.springframework.util.ResourceUtils
// import org.apache.camel.test.CamelTestSupport

// class CamelRouteBuilderTest extends CamelTestSupport {
//     @EndpointInject(uri = 'mock:result')
//     MockEndpoint resultEndpoint

//     @Produce(uri = 'direct:start')
//     ProducerTemplate template
    
//     void testProcessXML() {
//         resultEndpoint.expectedBodiesReceived([USD: 31.1252, EUR: 43.9052] as CurrencyRates)
//         template.sendBody(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + 'cbr_ru/rates_03072009.xml').newDataInputStream())
//         resultEndpoint.assertIsSatisfied()
//     }
    
//     RouteBuilder[] createRouteBuilders() {
//         [new CamelRouteBuilder(), new GroovyRouteBuilder() {
//             void configure() {
//                 from('direct:start').transform(body(CurrencyRates.class)).to('mock:result')
//             }
//         }]
//     }
// }
