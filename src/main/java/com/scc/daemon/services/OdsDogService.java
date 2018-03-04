package com.scc.daemon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.daemon.model.OdsBreeder;
import com.scc.daemon.model.OdsDog;
import com.scc.daemon.model.OdsOwner;
import com.scc.daemon.model.OdsParent;
import com.scc.daemon.model.OdsPedigree;
import com.scc.daemon.model.OdsSyncData;
import com.scc.daemon.model.OdsTitle;
import com.scc.daemon.repository.BreederRepository;
import com.scc.daemon.repository.DogRepository;
import com.scc.daemon.repository.OdsDataRepository;
import com.scc.daemon.repository.OwnerRepository;
import com.scc.daemon.repository.ParentRepository;
import com.scc.daemon.repository.PedigreeRepository;
import com.scc.daemon.repository.TitleRepository;
import com.scc.events.source.SimpleSourceBean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OdsDogService {

    private static final Logger logger = LoggerFactory.getLogger(OdsDogService.class);

    @Autowired
    private Tracer tracer;
    
    @Autowired
    private SimpleSourceBean simpleSourceBean;

    @Autowired
    private ParentRepository parentRepository;    

    @Autowired
    private PedigreeRepository pedigreeRepository;    
    
    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BreederRepository breederRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private OdsDataRepository odsDataRepository;

    
    public List<OdsSyncData> getAllDogs(){
//        Span newSpan = tracer.createSpan("getAllDogs");
//        logger.debug("In the odsDogService.getAllDogs() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","CHIEN");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    }
    
    public List<OdsSyncData> getAllBreeders(){
//        Span newSpan = tracer.createSpan("getAllBreeders");
//        logger.debug("In the odsDogService.getAllBreeders() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","ELEVEUR");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    }
    
    public List<OdsSyncData> getAllOwners(){
//        Span newSpan = tracer.createSpan("getAllOwners");
//        logger.debug("In the odsDogService.getAllOwners() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","PROPRIETAIRE");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    }    
    
    public List<OdsSyncData> getAllTitles(){
//        Span newSpan = tracer.createSpan("getAllTitles");
//        logger.debug("In the odsDogService.getAllTitles() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	List<OdsSyncData> _titles = new ArrayList<OdsSyncData>();

        try {
        	_titles  = odsDataRepository.findByTransfertAndDomaine("N","TITRE_FRANCAIS");
        	_titles.addAll(odsDataRepository.findByTransfertAndDomaine("N","TITRE_ETRANGER"));
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }
        
        return _titles;

    }    
    
    public List<OdsSyncData> getAllPedigree(){
//        Span newSpan = tracer.createSpan("getAllPedigree");
//        logger.debug("In the odsDogService.getAllPedigree() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","LIVRE");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    } 
    
    public List<OdsSyncData> getAllParent(){
//        Span newSpan = tracer.createSpan("getAllParent");
//        logger.debug("In the odsDogService.getAllParent() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

        try {
        	return odsDataRepository.findByTransfertAndDomaine("N","GENITEUR");
        }
        finally{
//            newSpan.tag("peer.service", "odsscheduler");
//            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
//            tracer.close(newSpan);        	
        }

    } 
    
    public OdsDog getDogById(long dogId){
        Span newSpan = tracer.createSpan("getDogById");
        logger.debug("In the odsDogService.getDogById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return dogRepository.findById(dogId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }
    
    public OdsSyncData saveDog(OdsSyncData dog){
        Span newSpan = tracer.createSpan("saveDog");
        logger.debug("In the odsDogService.saveDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        
        try {
        	return odsDataRepository.save(dog);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	        	
        }
    }
    
    public void refreshDog(OdsDog dog, String action){
        Span newSpan = tracer.createSpan("publishDogChange");
        logger.debug("In the odsDogService.refreshDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (dog != null) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishDogChange("UPDATE", dog);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishDogChange("SAVE", dog);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishDogChange("DELETE", dog);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the ods dog service of type {}", action);
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
    
    public void refreshBreeder(OdsBreeder dog, String action){
        Span newSpan = tracer.createSpan("publishBreederChange");
        logger.debug("In the odsDogService.refreshBreeder() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (dog != null) {
    			
    			List<OdsBreeder> breeders = new ArrayList<OdsBreeder>();
    			breeders.add(dog);
    			
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
	                    logger.error("Received an UNKNOWN event from the ods breeder service of type {}", action);
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
    
    public OdsBreeder getBreederByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getBreederByIdDog");
        logger.debug("In the odsDogService.getBreederByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return breederRepository.findByIdDog(dogId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }    
    
    
    public void refreshOwner(OdsOwner dog, String action){
        Span newSpan = tracer.createSpan("publishOwnerChange");
        logger.debug("In the odsDogService.refreshOwner() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (dog != null) {
    			
    			List<OdsOwner> owners = new ArrayList<OdsOwner>();
    			owners.add(dog);
    			
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
	                    logger.error("Received an UNKNOWN event from the ods owner service of type {}", action);
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
    
    public OdsOwner getOwnerByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getOwnerByIdDog");
        logger.debug("In the odsDogService.getOwnerByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return ownerRepository.findByIdDog(dogId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }   
    
    public void refreshTitle(OdsTitle title, String action){
        Span newSpan = tracer.createSpan("publishTitleChange");
        logger.debug("In the odsDogService.refreshTitle() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if ( title != null ) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishTitleChange("UPDATE", title);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishTitleChange("SAVE", title);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishTitleChange("DELETE", title);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the ods title service of type {}", action);
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
    
    public OdsTitle getTitleById(long id){
        Span newSpan = tracer.createSpan("getTitleById");
        logger.debug("In the odsDogService.getTitleById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return titleRepository.findById(id);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }    
    
    public void refreshPedigree(OdsPedigree pedigree, String action){
        Span newSpan = tracer.createSpan("publishPedigreeChange");
        logger.debug("In the odsDogService.refreshPedigree() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (pedigree != null) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishPedigreeChange("UPDATE", pedigree);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishPedigreeChange("SAVE", pedigree);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishPedigreeChange("DELETE", pedigree);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the ods pedigree service of type {}", action);
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
        
    public OdsPedigree getPedigreeById(long id){
        Span newSpan = tracer.createSpan("getPedigreeByIdDog");
        logger.debug("In the odsDogService.getPedigreeByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return pedigreeRepository.findById(id);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }        
    
    public void refreshParent(OdsParent dog, String action){
        Span newSpan = tracer.createSpan("publishParentChange");
        logger.debug("In the odsDogService.refreshParent() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
    	try {

    		if (dog != null) {
	    		switch(action){
	            	case "U":
	            		simpleSourceBean.publishParentChange("UPDATE", dog);
	            		break;
	            	case "I":
	            		simpleSourceBean.publishParentChange("SAVE", dog);
	            		break;
	            	case "D":
	            		simpleSourceBean.publishParentChange("DELETE", dog);
	            		break;
	                default:
	                    logger.error("Received an UNKNOWN event from the ods pedigree service of type {}", action);
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
            
    public OdsParent getParentByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getParentByIdDog");
        logger.debug("In the odsDogService.getParentByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
    	
        try {
        	return parentRepository.findById(dogId);
        }
        finally{
            newSpan.tag("peer.service", "odsscheduler");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);        	
        }
    }     
    
    public void deleteDog (OdsSyncData dog) {
        try {
        	odsDataRepository.delete(dog);
        }
        finally{
        }
    }

    
    public void syncDogInfos() {
    	
    	List<OdsSyncData> dogList = new ArrayList<OdsSyncData>();
    	long idDog = 0;
    	
        try {

        	dogList = getAllDogs();
	    	if (dogList.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncDogInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("syncDogInfos :: dogList {}", dogList.size());

	    		// [[Boucle]] s/ le chien
		    	for (OdsSyncData syncDog : dogList) {
		    		
		            try {
		    				
		    	    	// 1. Maj du chien de la table (ODS_SYNC_DATA)
		    			idDog = syncDog.getId();
		    			syncDog.setTransfert("O");
		    			saveDog(syncDog);
		    			
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_CHIEN (Oracle) == image de la table ODS_CHIEN (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsDog dog = new OdsDog();
		    			if (!syncDog.getAction().equals("D")) {
		    				dog = getDogById(idDog);
		    				if (dog == null ) {
		    					deleteDog(syncDog);
		    					continue;		    	
		    				}		    			
		    			} else
		    				dog.withId(idDog);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			// Pour le chien lui-même, ses titres, ses livres, ses parents, son proprietaire ?	
		    			refreshDog(dog, syncDog.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idDog {} : {}", idDog, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]
		    	
    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}

        }
        finally{
	    	dogList.clear();
        }
    	
    }
    
    public void syncBreederInfos() {

    	List<OdsSyncData> dogList = new ArrayList<OdsSyncData>();
    	int idDog = 0;
    	
        try {

	    	dogList = getAllBreeders();
	    	if (dogList.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncBreederInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("syncBreederInfos :: dogList {}", dogList.size());

	    		// [[Boucle]] s/ le chien
		    	for (OdsSyncData syncDog : dogList) {

		            try {

		    	    	// 1. Maj du chien de la table (ODS_SYNC_DATA)
		    			idDog = (int) syncDog.getId();
		    			syncDog.setTransfert("O");
		    			saveDog(syncDog);
	
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_ELEVEUR (Oracle) == image de la table ODS_ELEVEUR (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsBreeder breeder = new OdsBreeder();
		    			if (!syncDog.getAction().equals("D")) {
		    				breeder = getBreederByIdDog(idDog);
		    				if (breeder == null ) {
		    					deleteDog(syncDog);
		    					continue;		    	
		    				}		    			
		    			} else
		    				breeder.withIdDog(idDog);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			refreshBreeder(breeder, syncDog.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idDog {} : {}", idDog, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]

    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}

        }
        finally{
	    	dogList.clear();
        }
        	
    }
    
    
    public void syncOwnerInfos() {
    
    	List<OdsSyncData> dogList = new ArrayList<OdsSyncData>();
    	int idDog = 0;
    	
        try {
        	
	    	dogList = getAllOwners();
	    	if (dogList.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncOwnerInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("getAllOwners :: dogList {}", dogList.size());

	    		// [[Boucle]] s/ le chien
		    	for (OdsSyncData syncDog : dogList) {

		            try {

		    	    	// 1. Maj du chien de la table (ODS_SYNC_DATA)
		    			idDog = (int) syncDog.getId();
		    			syncDog.setTransfert("O");
		    			saveDog(syncDog);
	
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_PROPRIETAIRE (Oracle) == image de la table ODS_PROPRIETAIRE (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsOwner owner = new OdsOwner();
		    			if (!syncDog.getAction().equals("D")) {
		    				owner = getOwnerByIdDog(idDog);
		    				if (owner == null ) {
		    					deleteDog(syncDog);
		    					continue;		    	
		    				}		    			
		    			} else
		    				owner.withId(idDog);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			refreshOwner(owner, syncDog.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idDog {} : {}", idDog, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]

    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}
        	
        }
        finally{
	    	dogList.clear();
        }
        	
    }
    
    public void syncTitleInfos() {
    	
    	List<OdsSyncData> titles = new ArrayList<OdsSyncData>();
    	long idTitle = 0;
    	String country ="";
    	
        try {
            	
        	titles = getAllTitles();
	    	if (titles.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncTitleInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("getAllTitles :: titles {}", titles.size());

	    		// [[Boucle]] s/ les titres
		    	for (OdsSyncData syncTitle : titles) {

		            try {

		    	    	// 1. Maj du titre de la table (ODS_SYNC_DATA)
		            	idTitle = syncTitle.getId();
		            	country = (syncTitle.getDomaine().equals("TITRE_FRANCAIS") ? "FR" : "ETR");
		            	syncTitle.setTransfert("O");
		    			saveDog(syncTitle);
	
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_TITRES (Oracle) == image de la table ODS_TITRES (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsTitle title = new OdsTitle();
		    			if (!syncTitle.getAction().equals("D")) {
		    				title = getTitleById(idTitle);
		    				if (title == null) {
		    					deleteDog(syncTitle);
		    					continue;		    	
		    				}		    			
		    			} else
		    				title.withId(idTitle);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			refreshTitle(title, syncTitle.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idTitle {} : {}", idTitle, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]

    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}
        	
        }
        finally{
        	titles.clear();
        }

    }  
    
    public void syncPedigreeInfos(){
    	
    	List<OdsSyncData> pedigrees = new ArrayList<OdsSyncData>();
    	long idPedigree = 0;
    	
        try {

        	pedigrees = getAllPedigree();
	    	if (pedigrees.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncPedigreeInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("getAllPedigree :: pedigrees {}", pedigrees.size());

	    		// [[Boucle]] s/ le chien
		    	for (OdsSyncData syncPedigree : pedigrees) {

		            try {

		    	    	// 1. Maj du chien de la table (ODS_SYNC_DATA)
		    			idPedigree = syncPedigree.getId();
		    			syncPedigree.setTransfert("O");
		    			saveDog(syncPedigree);
	
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_LIVRES (Oracle) == image de la table ODS_LIVRES (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsPedigree pedigree = new OdsPedigree();
		    			if (!syncPedigree.getAction().equals("D")) {
		    				pedigree = getPedigreeById(idPedigree);
		    				if (pedigree == null) {
		    					deleteDog(syncPedigree);
		    					continue;		    	
		    				}		    			
		    			} else
		    				pedigree.withId(idPedigree);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			refreshPedigree(pedigree, syncPedigree.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idPedigree {} : {}", idPedigree, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]

    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}
        	
        
        }
        finally{
        	pedigrees.clear();
        }

    }

    public void syncParentInfos(){
    	
    	List<OdsSyncData> dogList = new ArrayList<OdsSyncData>();
    	int idDog = 0;
    	
        try {

	    	dogList = getAllParent();
	    	if (dogList.size() > 0 ) {

	    		Span newSpan = tracer.createSpan("odsScheduler");
	            logger.debug("In the odsDogService.syncParentInfos() call, trace id: {}", tracer.getCurrentSpan().traceIdString());

	    		logger.debug("getAllParent :: dogList {}", dogList.size());

	    		// [[Boucle]] s/ le chien
		    	for (OdsSyncData syncDog : dogList) {

		            try {

		    	    	// 1. Maj du chien de la table (ODS_SYNC_DATA)
		    			idDog = (int) syncDog.getId();
		    			syncDog.setTransfert("O");
		    			saveDog(syncDog);
	
		    	    	// 2. Lecture des infos pour le chien à synchroniser 
		    			// Note : vue ODS_GENITEURS (Oracle) == image de la table ODS_GENITEURS (PostGRE)
		    			// Si UPDATE/INSERT et dog == null alors le chien n'est pas dans le périmètre -> on le supprime de la liste
		    			// + DELETE, dog == null -> on publie uniquement l'id à supprimer
		    			OdsParent parent = new OdsParent();
		    			if (!syncDog.getAction().equals("D")) {
		    				parent = getParentByIdDog(idDog);
		    				if (parent == null ) {
		    					deleteDog(syncDog);
		    					continue;		    	
		    				}		    			
		    			} else
		    				parent.withId(idDog);	
		    			
		    	    	// 3. Envoi du message à ods-service pour maj Postgre
		    			refreshParent(parent, syncDog.getAction());
		    			
		    		} catch (Exception e) {
		    			logger.error(" idDog {} : {}", idDog, e.getMessage());
		    		} finally {
		    		}
		    	}	    	
		    	// [[Boucle]]

    			newSpan.tag("peer.service", "odsscheduler");
    			newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
    			tracer.close(newSpan);		    			

	    	}
        	
        
        }
        finally{
	    	dogList.clear();
        }

    }
    
}
