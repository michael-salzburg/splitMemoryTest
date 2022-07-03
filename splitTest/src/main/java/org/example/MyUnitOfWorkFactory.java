package org.example;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.engine.DefaultUnitOfWork;
import org.apache.camel.impl.engine.MDCUnitOfWork;
import org.apache.camel.spi.InflightRepository;
import org.apache.camel.spi.UnitOfWork;
import org.apache.camel.spi.UnitOfWorkFactory;


public class MyUnitOfWorkFactory implements UnitOfWorkFactory {

    private InflightRepository inflightRepository;
    private boolean usedMDCLogging;
    private String mdcLoggingKeysPattern;
    private boolean allowUseOriginalMessage;
    private boolean useBreadcrumb;

    @Override
    public UnitOfWork createUnitOfWork(Exchange exchange) {
        return new CustomUnitOfWork(exchange, inflightRepository, allowUseOriginalMessage, useBreadcrumb);
    }

    @Override
    public void afterPropertiesConfigured(CamelContext camelContext) {
        // optimize to read configuration once
        inflightRepository = camelContext.getInflightRepository();
        usedMDCLogging = camelContext.isUseMDCLogging() != null && camelContext.isUseMDCLogging();
        mdcLoggingKeysPattern = camelContext.getMDCLoggingKeysPattern();
        allowUseOriginalMessage
                = camelContext.isAllowUseOriginalMessage() != null ? camelContext.isAllowUseOriginalMessage() : false;
        useBreadcrumb = camelContext.isUseBreadcrumb() != null ? camelContext.isUseBreadcrumb() : false;
    }

}
