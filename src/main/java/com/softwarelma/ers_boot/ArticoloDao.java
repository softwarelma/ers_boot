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
	}

	// TODO logger
	// TODO return wrappers
	// TODO validations

	public List<Articolo> getAll() {
		return this.listArticolo;
	}

	public Articolo get(Long id) {
		return this.mapIdAndArticolo.get(id);
	}

	public Articolo addNew(Articolo articolo) {
		articolo.setId(this.counter.incrementAndGet());
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		return articolo;
	}

	public Articolo putExisting(Articolo articolo) {
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		return articolo;
	}

	public Articolo putExistingOrNew(Articolo articolo) {
		this.listArticolo.add(articolo);
		this.mapIdAndArticolo.put(articolo.getId(), articolo);
		return articolo;
	}

	public Articolo delete(Long id) {
		Articolo articolo = this.mapIdAndArticolo.remove(id);
		return this.listArticolo.remove(articolo) ? articolo : null;
	}

}
