package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.MulticastProcessor;
import org.apache.camel.spi.UnitOfWork;
import org.apache.camel.spi.UnitOfWorkFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Camel Java DSL Router
 */
@Component
public class MyRouteBuilder extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(MyRouteBuilder.class);


    /**
     * Let's configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() {

        getCamelContext().adapt(ExtendedCamelContext.class).setUnitOfWorkFactory(new MyUnitOfWorkFactory());

        from("scheduler:testScheduler?repeatCount=1")
                .routeId("test-route")
                .log("Starting route test-route")
                .process(exchange -> {
                    Iterator<String> infiniteIter = new Iterator<>() {
                        private int integer = 0;

                        @Override
                        public boolean hasNext() {
                            return (integer < 500000);
                        } //3000000 //20

                        @Override
                        public String next() {
                            try {
                                if (integer % 50000 == 1)
                                    Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return String.valueOf(integer++);
                        }
                    };
                    exchange.getMessage().setBody(infiniteIter);
                })
                .split().body().streaming()
                    .log("inside split: ${body}")
                .end()
                .log("test-route finishes");
    }
//
//    private class MyUnitOfWorkFactory implements UnitOfWorkFactory {
//
//        @Override
//        public UnitOfWork createUnitOfWork(Exchange exchange) {
//            return new CustomUnitOfWork(exchange);
//        }
//    }

}
