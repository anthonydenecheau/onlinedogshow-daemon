package com.scc.events.models;

import com.scc.daemon.model.OdsBreeder;

public class OdsBreederChangeModel {

    private String type;
    private String action;
    private OdsBreeder breeder;
    private String traceId;
    private long timestamp;

    public  OdsBreederChangeModel(String type, String action, OdsBreeder breeder, String traceId, long timestamp) {
        super();
        this.type   = type;
        this.action = action;
        this.breeder = breeder;
        this.traceId = traceId;
        this.timestamp = timestamp;
    }

	public String getType() { return type; }
    public void setType(String type) { this.type = type;}

    public String getAction() { return action;}
    public void setAction(String action) {this.action = action; }

    public OdsBreeder getBreeder() { return breeder; }
    public void setBreeder(OdsBreeder breeder) { this.breeder = breeder;}

    public String getTraceId() { return traceId; }
	public void setTraceId(String traceId) { this.traceId = traceId; }

	public long getTimestamp() { return timestamp; }
	public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

}
