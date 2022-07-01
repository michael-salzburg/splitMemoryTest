package org.example;

import org.apache.camel.builder.RouteBuilder;
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

        from("scheduler:testScheduler?repeatCount=1")
                .routeId("test-route")
                .log("Starting route test-route")
                .process(exchange -> {
                    Iterator<String> infiniteIter = new Iterator<>() {
                        private int integer = 0;

                        @Override
                        public boolean hasNext() {
                            return (integer < 3000000);
                        }

                        @Override
                        public String next() {
                            try {
                                if (integer % 50000 == 1)
                                    Thread.sleep(1000);
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

}
