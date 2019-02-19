package com.softwarelma.ers_boot;

import java.io.Serializable;

public class ArticoloResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Articolo articolo;
	private String error;

	public ArticoloResponse() {
		super();
	}

	public ArticoloResponse(Articolo articolo, String error) {
		super();
		this.articolo = articolo;
		this.error = error;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
