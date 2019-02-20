package com.softwarelma.ers_boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticoloController {

	private static final String SOURCE_FILE = "articoli.ser";
	private static ArticoloDao articoloDao;
	private static final Logger logger = Logger.getLogger(ArticoloController.class.getName());

	static {
		if (!new File(SOURCE_FILE).exists()) {
			ArticoloController.articoloDao = new ArticoloDao();
			logger.log(Level.INFO, "new ArticoloDao()");
			ArticoloController.save();
		}

		ObjectInputStream ois = null;

		try {
			FileInputStream fis = new FileInputStream(SOURCE_FILE);
			ois = new ObjectInputStream(fis);
			ArticoloDao articoloDao = (ArticoloDao) ois.readObject();
			ArticoloController.articoloDao = articoloDao;
			logger.log(Level.INFO, "ArticoloDao red");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Reading object", e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Reading object, closing the input stream", e);
				}
			}
		}
	}

	public static void save() {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;

		try {
			fout = new FileOutputStream(SOURCE_FILE, false);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(((ArticoloDao) ArticoloController.articoloDao));
			logger.log(Level.INFO, "ArticoloDao wrote");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Writing object", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Writing object, closing the output stream", e);
				}
			}
		}
	}

	public ArticoloController() {
	}

	@GetMapping("/rest/getAll")
	public ArticoloListResponse getAll() {
		return articoloDao.getAll();
	}

	@GetMapping("/rest/get/{id}")
	public ArticoloResponse get(@PathVariable Long id) {
		return articoloDao.get(id);
	}

	@PostMapping("/rest/post")
	public ArticoloResponse postNew(@RequestBody Articolo articolo) {
		ArticoloResponse articoloResponse = articoloDao.addNew(articolo);
		if (articoloResponse.getError() == null)
			ArticoloController.save();
		return articoloResponse;
	}

	@PutMapping("/rest/put")
	public ArticoloResponse putExisting(@RequestBody Articolo articolo) {
		ArticoloResponse articoloResponse = articoloDao.putExisting(articolo);
		if (articoloResponse.getError() == null)
			ArticoloController.save();
		return articoloResponse;
	}

	@PutMapping("/rest/put/upd-ins")
	public ArticoloResponse putExistingOrNew(@RequestBody Articolo articolo) {
		ArticoloResponse articoloResponse = articoloDao.putExistingOrNew(articolo);
		if (articoloResponse.getError() == null)
			ArticoloController.save();
		return articoloResponse;
	}

	@DeleteMapping("/rest/del/{id}")
	public ArticoloResponse delete(@PathVariable Long id) {
		ArticoloResponse articoloResponse = articoloDao.delete(id);
		if (articoloResponse.getError() == null)
			ArticoloController.save();
		return articoloResponse;
	}

}