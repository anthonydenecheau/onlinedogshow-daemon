package com.scc.daemon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.daemon.model.OdsBreeder;
import com.scc.daemon.model.OdsOwner;
import com.scc.daemon.model.OdsSyncData;
import com.scc.daemon.repository.OwnerRepository;
import com.scc.daemon.repository.BreederRepository;
import com.scc.daemon.repository.OdsDataRepository;
import com.scc.events.source.SimpleSourceBean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OdsPersonService {

    private static final Logger logger = LoggerFactory.getLogger(OdsPersonService.class);

    @Autowired
    private Tracer tracer;
    
    @Autowired
    private SimpleSourceBean simpleSourceBean;
    
    @Autowired
    private OdsDataRepository odsDataRepository;

    @Autowired
    private BreederRepository breederRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    
    public List<OdsSyncData> getAllPersons(){
//        Span newSpan = tracer.createSpan("getAllPersons");
//        logger.debug("In the odsPersonService.getAllPersons() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","PERSONNE");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    }
    
    public OdsSyncData savePerson(OdsSyncData person){
        Span newSpan = tracer.createSpan("savePerson");
        logger.debug("In the odsPersonService.savePerson() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        
        try {
        	return odsDataRepository.save(person);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	        	
        }
    }

    public List<OdsBreeder> getBreederById(int personId){
        Span newSpan = tracer.createSpan("getBreederById");
        logger.debug("In the odsPersonService.getBreederById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return breederRepository.findById(personId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }
    
    public void refreshBreeder(List<OdsBreeder> breeders, String action){
        Span newSpan = tracer.createSpan("publishBreederChange");
        logger.debug("In the odsPersonService.refreshBreeder() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (breeders != null && breeders.size()>0) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishBreederChange("UPDATE", breeders);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishBreederChange("SAVE", breeders);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishBreederChange("DELETE", breeders);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the agria service of type {}", action);
	                    break;      
	    		}
    		}
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);          	
        }
    	
    }
    
    public List<OdsOwner> getOwnerById(int personId){
        Span newSpan = tracer.createSpan("getOwnerById");
        logger.debug("In the odsPersonService.getOwnerById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return ownerRepository.findById(personId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }
    

    public void refreshOwner(List<OdsOwner> owners, String action){
        Span newSpan = tracer.createSpan("publishOwnerChange");
        logger.debug("In the odsPersonService.refreshOwner() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (owners != null && owners.size()>0) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishOwnerChange("UPDATE", owners);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishOwnerChange("SAVE", owners);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishOwnerChange("DELETE", owners);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the ods person service of type {}", action);
	                    break;      
	    		}
    		}
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);          	
        }
    	
    }
    
    public void deletePerson (OdsSyncData person) {
        try {
        	odsDataRepository.delete(person);
        }
        finally{
        }
    }
}
