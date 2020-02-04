package com.softwarelma.ers_boot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticoloDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ArticoloDao.class.getName());
	private final List<Articolo> listArticolo = new LinkedList<>();
	private final Map<Long, Articolo> mapIdAndArticolo = new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	public ArticoloDao() {
	}

	public ArticoloListResponse getAll() {
		logger.log(Level.INFO, "getAll - begin");
		ArticoloListResponse articoloListResponse = new ArticoloListResponse(this.listArticolo, null);
		if (this.listArticolo.size() == 1)
			logger.log(Level.INFO, "getAll - trovato 1 articolo");
		else
			logger.log(Level.INFO, "getAll - trovati " + this.listArticolo.size() + " articoli");
		logger.log(Level.INFO, "getAll - end");
		return articoloListResponse;
	}

	public ArticoloResponse get(Long id) {
		logger.log(Level.INFO, "get - begin - id = " + id);
		Articolo articolo = this.mapIdAndArticolo.get(id);
		ArticoloResponse articoloResponse = new ArticoloResponse();
		int ind = this.validateAndGetInd(articoloResponse, articolo);
		if (ind < 0) {
			logger.log(Level.INFO, "get - end - id = " + id);
			return articoloResponse;
		}
		logger.log(Level.INFO, "get - trovato l'articolo con id: " + id);
		logger.log(Level.INFO, "get - end - id = " + id);
		return new ArticoloResponse(articolo, null);
	}

	public ArticoloResponse addNew(Articolo articolo) {
		logger.log(Level.INFO, "addNew - begin");
		if (articolo == null) {
			logger.log(Level.INFO, "addNew - non trovato l'articolo");
			logger.log(Level.INFO, "addNew - end");
			return new ArticoloResponse(null, "Non trovato l'articolo");
		} else if (articolo.getId() != 0) {
			logger.log(Level.INFO, "addNew - id articolo non valido: " + articolo.getId() + ", atteso: 0");
			logger.log(Level.INFO, "addNew - end - id = " + articolo.getId());
			return new ArticoloResponse(null, "Id articolo non valido: " + articolo.getId() + ", atteso: 0");
		}
		long id;
		do {
			id = this.counter.incrementAndGet();
		} while (this.mapIdAndArticolo.containsKey(id));
		if (id < 1)
			this.counter.incrementAndGet();
		articolo.setId(id);
		articolo.setDataPubblicazione(new Date());
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(id, articolo);
		logger.log(Level.INFO, "addNew - creato articolo con id = " + id);
		ArticoloResponse articoloResponse = new ArticoloResponse(articolo, null);
		logger.log(Level.INFO, "addNew - end - id = " + id);
		return articoloResponse;
	}

	public ArticoloResponse putExisting(Articolo articolo) {
		logger.log(Level.INFO, "putExisting - begin");
		ArticoloResponse articoloResponse = new ArticoloResponse(null, null);
		int ind = this.validateAndGetInd(articoloResponse, articolo);
		if (ind < 0) {
			logger.log(Level.INFO, "putExisting - end");
			return articoloResponse;
		}
		this.listArticolo.set(ind, articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		articoloResponse = new ArticoloResponse(articolo, null);
		logger.log(Level.INFO, "putExisting - end - id = " + articolo.getId());
		return articoloResponse;
	}

	private int validateAndGetInd(ArticoloResponse articoloResponse, Articolo articolo) {
		articoloResponse.setArticolo(null);
		articoloResponse.setError(null);
		if (articolo == null) {
			logger.log(Level.INFO, "validateAndGetInd - non trovato l'articolo");
			articoloResponse.setError("Non trovato l'articolo");
			return -1;
		} else if (articolo.getId() < 1) {
			logger.log(Level.INFO, "validateAndGetInd - id articolo non valido: " + articolo.getId());
			articoloResponse.setError("Id articolo non valido: " + articolo.getId());
			return -1;
		} else if (!this.mapIdAndArticolo.containsKey(articolo.getId())) {
			logger.log(Level.INFO, "validateAndGetInd - non trovato il mapping dell'articolo con id: " + articolo.getId());
			articoloResponse.setError("Non trovato il mapping dell'articolo con id: " + articolo.getId());
			return -1;
		}
		int ind = this.listArticolo.indexOf(articolo);
		if (ind < 0) {
			logger.log(Level.INFO, "validateAndGetInd - non trovato nella lista l'articolo con id: " + articolo.getId());
			articoloResponse.setError("Non trovato nella lista l'articolo con id: " + articolo.getId());
			return -1;
		}
		return ind;
	}

	public ArticoloResponse putExistingOrNew(Articolo articolo) {
		logger.log(Level.INFO, "putExistingOrNew - operation non abilitata");
		return new ArticoloResponse(null, "Operation non abilitata");
	}

	public ArticoloResponse patchExisting(Articolo articolo) {
		logger.log(Level.INFO, "patchExisting - begin");
		ArticoloResponse articoloResponse = new ArticoloResponse(null, null);
		int ind = this.validateAndGetInd(articoloResponse, articolo);
		if (ind < 0) {
			logger.log(Level.INFO, "patchExisting - end");
			return articoloResponse;
		}
		Articolo articoloSalvato = this.mapIdAndArticolo.get(articolo.getId());
		if (articolo.getArraySottotitolo() != null && !articolo.getArraySottotitolo().isEmpty()) {
			logger.log(Level.INFO, "patchExisting - settando arraySottotitolo");
			articoloSalvato.setArraySottotitolo(articolo.getArraySottotitolo());
		}
		if (articolo.getDataPubblicazione() != null) {
			logger.log(Level.INFO, "patchExisting - settando dataPubblicazione");
			articoloSalvato.setDataPubblicazione(articolo.getDataPubblicazione());
		}
		if (articolo.getImmagine() != null && !articolo.getImmagine().isEmpty()) {
			logger.log(Level.INFO, "patchExisting - settando immagine");
			articoloSalvato.setImmagine(articolo.getImmagine());
		}
		if (articolo.getNomeImmagine() != null && !articolo.getNomeImmagine().isEmpty()) {
			logger.log(Level.INFO, "patchExisting - settando nomeImmagine");
			articoloSalvato.setNomeImmagine(articolo.getNomeImmagine());
		}
		if (articolo.getTesto() != null && !articolo.getTesto().isEmpty()) {
			logger.log(Level.INFO, "patchExisting - settando testo");
			articoloSalvato.setTesto(articolo.getTesto());
		}
		if (articolo.getTitolo() != null && !articolo.getTitolo().isEmpty()) {
			logger.log(Level.INFO, "patchExisting - settando titolo");
			articoloSalvato.setTitolo(articolo.getTitolo());
		}
		articoloResponse = new ArticoloResponse(articoloSalvato, null);
		logger.log(Level.INFO, "patchExisting - end - id = " + articolo.getId());
		return articoloResponse;
	}

	public ArticoloResponse delete(Long id) {
		logger.log(Level.INFO, "delete - begin - id = " + id);
		Articolo articolo = this.mapIdAndArticolo.get(id);
		ArticoloResponse articoloResponse = new ArticoloResponse();
		int ind = this.validateAndGetInd(articoloResponse, articolo);
		if (ind < 0) {
			logger.log(Level.INFO, "delete - end - id = " + id);
			return articoloResponse;
		}
		this.mapIdAndArticolo.remove(id);
		this.listArticolo.remove(ind);
		logger.log(Level.INFO, "delete - cancellato l'articolo con id: " + id);
		logger.log(Level.INFO, "delete - end - id = " + id);
		return new ArticoloResponse(articolo, null);
	}

}
