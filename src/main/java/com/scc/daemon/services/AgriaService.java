package com.scc.daemon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.daemon.model.AgriaDog;
import com.scc.daemon.model.AgriaSyncDog;
import com.scc.daemon.repository.AgriaDogRepository;
import com.scc.daemon.repository.DogRepository;
import com.scc.events.source.SimpleSourceBean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AgriaService {

    private static final Logger logger = LoggerFactory.getLogger(AgriaService.class);

    @Autowired
    private Tracer tracer;
    
    @Autowired
    private SimpleSourceBean simpleSourceBean;
    
    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private AgriaDogRepository agriaDogRepository;

    
    public List<AgriaSyncDog> getAllDogs(){
        Span newSpan = tracer.createSpan("getAllDogs");
        logger.debug("In the agriaService.getAllDogs() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return agriaDogRepository.findByTransfert("N");
        }
        finally{
            newSpan.tag("peer.service", "oracle");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }

    }
    
    public AgriaDog getDogById(int dogId){
        Span newSpan = tracer.createSpan("getDogById");
        logger.debug("In the agriaService.getDogById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return dogRepository.findById(dogId);
        }
        finally{
            newSpan.tag("peer.service", "postgres");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }
    
    public AgriaSyncDog saveDog(AgriaSyncDog dog){
        Span newSpan = tracer.createSpan("saveDog");
        logger.debug("In the agriaService.saveDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        
        try {
        	return agriaDogRepository.save(dog);
        }
        finally{
            newSpan.tag("peer.service", "oracle");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	        	
        }
    }
    
    public void refreshDog(int idDog, String action){
        Span newSpan = tracer.createSpan("refreshDog");
        logger.debug("In the agriaService.refreshDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        // Lecture des données maj.
        AgriaDog dog = new AgriaDog(); 
        		
    	try {

    		switch(action){
            	case "U":
            		dog = getDogById(idDog);
            		simpleSourceBean.publishDogChange("UPDATE", dog);
            		break;
            	case "I":
            		dog = getDogById(idDog);
            		simpleSourceBean.publishDogChange("SAVE", dog);
            		break;
            	case "D":
            		dog.setId(idDog);
            		simpleSourceBean.publishDogChange("DELETE", dog);
            		break;
                default:
                    logger.error("Received an UNKNOWN event from the agria service of type {}", action);
                    break;      
    		}
        }
        finally{
            newSpan.tag("peer.service", "kafka");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);          	
        }
    	
    }
}
