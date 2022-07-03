package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.impl.engine.MDCUnitOfWork;
import org.apache.camel.spi.InflightRepository;

public class CustomUnitOfWork extends MDCUnitOfWork {

    private static final String pattern = "";

    public CustomUnitOfWork(Exchange exchange, InflightRepository inflightRepository, boolean allowUseOriginalMessage, boolean useBreadcrumb) {
        super(exchange, inflightRepository, pattern, allowUseOriginalMessage, useBreadcrumb);
    }

    @Override
    public boolean isBeforeAfterProcess() {
        return false;
    }

}