package com.softwarelma.ers_boot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ArticoloDao implements ArticoloDaoInterface {

	private static final long serialVersionUID = 1L;
	private final List<Articolo> listArticolo = new LinkedList<>();
	private final Map<Long, Articolo> mapIdAndArticolo = new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	public ArticoloDao() {
		// TODO leggere/scrivere da/a disco
		// this.counter.set();
	}

	// TODO logger

	@Override
	public ArticoloListResponse getAll() {
		return new ArticoloListResponse(this.listArticolo, null);
	}

	@Override
	public ArticoloResponse get(Long id) {
		Articolo articolo = this.mapIdAndArticolo.get(id);

		if (articolo == null) {
			return new ArticoloResponse(null, "Articolo non trovato");
		} else {
			return new ArticoloResponse(articolo, null);
		}
	}

	@Override
	public ArticoloResponse addNew(Articolo articolo) {
		long id = this.counter.incrementAndGet();
		if (this.mapIdAndArticolo.containsKey(id))
			return new ArticoloResponse(null, "Articolo non inserito");
		articolo.setId(id);
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(id, articolo);
		return new ArticoloResponse(articolo, null);
	}

	@Override
	public ArticoloResponse putExisting(Articolo articolo) {
		if (!this.mapIdAndArticolo.containsKey(articolo.getId()))
			return new ArticoloResponse(null, "Articolo non salvato");
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		return new ArticoloResponse(articolo, null);
	}

	@Override
	public ArticoloResponse putExistingOrNew(Articolo articolo) {
		ArticoloResponse articoloResponse;

		if (this.mapIdAndArticolo.containsKey(articolo.getId())) {
			articoloResponse = this.addNew(articolo);
		} else {
			articoloResponse = this.putExisting(articolo);
		}

		if (articoloResponse.getError() != null)
			articoloResponse.setError(articoloResponse.getError() + ", esistente o nuovo");
		return articoloResponse;
	}

	@Override
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
