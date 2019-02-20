package com.softwarelma.ers_boot;

import java.io.Serializable;
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

	// TODO logger

	public ArticoloListResponse getAll() {
		logger.log(Level.INFO, "getAll - begin");
		ArticoloListResponse articoloListResponse = new ArticoloListResponse(this.listArticolo, null);
		logger.log(Level.INFO, "getAll - end");
		return articoloListResponse;
	}

	public ArticoloResponse get(Long id) {
		logger.log(Level.INFO, "get - begin - id = " + id);
		Articolo articolo = this.mapIdAndArticolo.get(id);
		ArticoloResponse articoloResponse;

		if (articolo == null) {
			articoloResponse = new ArticoloResponse(null, "Articolo non trovato");
		} else {
			articoloResponse = new ArticoloResponse(articolo, null);
		}

		logger.log(Level.INFO, "get - end - id = " + id);
		return articoloResponse;
	}

	public ArticoloResponse addNew(Articolo articolo) {
		long id = this.counter.incrementAndGet();
		logger.log(Level.INFO, "addNew - begin - id = " + id);
		if (this.mapIdAndArticolo.containsKey(id))
			return new ArticoloResponse(null, "Articolo non inserito");
		articolo.setId(id);
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(id, articolo);
		ArticoloResponse articoloResponse = new ArticoloResponse(articolo, null);
		logger.log(Level.INFO, "addNew - end - id = " + id);
		return articoloResponse;
	}

	public ArticoloResponse putExisting(Articolo articolo) {
		logger.log(Level.INFO, "putExisting - begin - id = " + articolo.getId());
		if (!this.mapIdAndArticolo.containsKey(articolo.getId()))
			return new ArticoloResponse(null, "Articolo non salvato");
		int ind = this.listArticolo.indexOf(articolo);
		if (ind < 0)
			return new ArticoloResponse(null, "Articolo non salvato, non presente nella lista");
		this.listArticolo.set(ind, articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		ArticoloResponse articoloResponse = new ArticoloResponse(articolo, null);
		logger.log(Level.INFO, "putExisting - begin - id = " + articolo.getId());
		return articoloResponse;
	}

	public ArticoloResponse putExistingOrNew(Articolo articolo) {
		ArticoloResponse articoloResponse;

		if (this.mapIdAndArticolo.containsKey(articolo.getId())) {
			articoloResponse = this.putExisting(articolo);
		} else {
			articoloResponse = this.addNew(articolo);
		}

		if (articoloResponse.getError() != null)
			articoloResponse.setError(articoloResponse.getError() + ", esistente o nuovo");
		return articoloResponse;
	}

	public ArticoloResponse delete(Long id) {
		if (!this.mapIdAndArticolo.containsKey(id))
			return new ArticoloResponse(null, "Articolo non cancellato, non trovato");
		Articolo articolo = this.mapIdAndArticolo.remove(id);

		if (this.listArticolo.remove(articolo)) {
			return new ArticoloResponse(articolo, null);
		} else {
			return new ArticoloResponse(null, "Articolo non cancellato");
		}
	}

}
