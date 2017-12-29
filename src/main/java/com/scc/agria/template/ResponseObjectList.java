package com.scc.agria.template;

import java.util.List;

public class ResponseObjectList<T> {

	private int nbResults;

	private List<T> objectList;
	
	public ResponseObjectList(int nbResults, List<T> objectList) {
		super();
		this.nbResults = nbResults;
		this.objectList = objectList;
	}

	public int getNbResults() {
		return nbResults;
	}
	public void setNbResults(int nbResults) {
		this.nbResults = nbResults;
	}

	public List<T> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}

	public int size() {
		return this.objectList.size();
	}
}
