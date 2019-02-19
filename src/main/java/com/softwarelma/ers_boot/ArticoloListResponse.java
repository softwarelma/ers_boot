package com.softwarelma.ers_boot;

import java.io.Serializable;
import java.util.List;

public class ArticoloListResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Articolo> listArticolo;
	private String error;

	public ArticoloListResponse() {
		super();
	}

	public ArticoloListResponse(List<Articolo> listArticolo, String error) {
		super();
		this.listArticolo = listArticolo;
		this.error = error;
	}

	public List<Articolo> getListArticolo() {
		return listArticolo;
	}

	public void setListArticolo(List<Articolo> listArticolo) {
		this.listArticolo = listArticolo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
