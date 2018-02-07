package com.scc.events.source;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.scc.daemon.model.AgriaDog;
import com.scc.events.models.AgriaChangeModel;

@Component
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    private Tracer tracer;
    
    @Autowired
    public SimpleSourceBean(Source source){
        this.source = source;
    }

    public void publishDogChange(String action, AgriaDog dog){
        Span newSpan = tracer.createSpan("publishDogChange");
    	Instant instant = Instant.now();
        logger.debug("Sending Kafka message {} for Dog Id: {} at {} ", action, dog.toString(), instant);

    	try {
	       
	    	AgriaChangeModel change =  new AgriaChangeModel(
	    		   AgriaChangeModel.class.getTypeName(),
	                action,
	                dog,
	                tracer.getCurrentSpan().traceIdString(),
	                instant.toEpochMilli());
	
	        source.output().send(MessageBuilder.withPayload(change).build());
    
        }
        finally{
            newSpan.tag("peer.service", "kafka");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);          	
        }
    	
    }
}
