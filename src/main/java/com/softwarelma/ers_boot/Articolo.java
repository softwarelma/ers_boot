package com.softwarelma.ers_boot;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Articolo implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String titolo;
	private String immagine;
	private String nomeImmagine;
	private List<String> arraySottotitolo;
	private String testo;
	private Date dataPubblicazione;

	public Articolo() {
		super();
	}

	public Articolo(long id, String titolo, String immagine, String nomeImmagine, List<String> arraySottotitolo,
			String testo, Date dataPubblicazione) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.immagine = immagine;
		this.nomeImmagine = nomeImmagine;
		this.arraySottotitolo = arraySottotitolo;
		this.testo = testo;
		this.dataPubblicazione = dataPubblicazione;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public String getNomeImmagine() {
		return nomeImmagine;
	}

	public void setNomeImmagine(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	public List<String> getArraySottotitolo() {
		return arraySottotitolo;
	}

	public void setArraySottotitolo(List<String> arraySottotitolo) {
		this.arraySottotitolo = arraySottotitolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

}