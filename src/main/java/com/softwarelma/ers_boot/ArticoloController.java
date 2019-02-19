package com.softwarelma.ers_boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	private static ArticoloDaoInterface articoloDao;

	static {
		if (!new File(SOURCE_FILE).exists()) {
			ArticoloController.articoloDao = new ArticoloDao();
			ArticoloController.save(false);
		}

		ObjectInputStream ois = null;

		try {
			FileInputStream fis = new FileInputStream(SOURCE_FILE);
			ois = new ObjectInputStream(fis);
			ArticoloDao articoloDao = (ArticoloDao) ois.readObject();
			ArticoloController.articoloDao = articoloDao;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void save(boolean append) {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;

		try {
			fout = new FileOutputStream(SOURCE_FILE, append);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(((ArticoloDao) ArticoloController.articoloDao));
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			ArticoloController.save(true);
		return articoloResponse;
	}

	@PutMapping("/rest/put")
	public ArticoloResponse putExisting(@RequestBody Articolo articolo) {
		ArticoloResponse articoloResponse = articoloDao.putExisting(articolo);
		if (articoloResponse.getError() == null)
			ArticoloController.save(true);
		return articoloResponse;
	}

	@PutMapping("/rest/put/upd-ins")
	public ArticoloResponse putExistingOrNew(@RequestBody Articolo articolo) {
		ArticoloResponse articoloResponse = articoloDao.putExistingOrNew(articolo);
		if (articoloResponse.getError() == null)
			ArticoloController.save(true);
		return articoloResponse;
	}

	@DeleteMapping("/rest/del/{id}")
	public ArticoloResponse delete(@PathVariable Long id) {
		ArticoloResponse articoloResponse = articoloDao.delete(id);
		if (articoloResponse.getError() == null)
			ArticoloController.save(true);
		return articoloResponse;
	}

	// @RequestMapping(value = "del/{authorizationUrl}", method =
	// RequestMethod.DELETE)
	// public @ResponseBody void deleteAuthorizationServer(@RequestHeader(value =
	// "Authorization") String authorization,
	// @PathVariable("authorizationUrl") String authorizationUrl) {
	// System.out.printf("Testing: You tried to delete %s using %s\n",
	// authorizationUrl, authorization);
	// }

	/////////////////////

	// @RequestMapping("/Articolo")
	// public Articolo Articolo(@RequestParam(value = "name", defaultValue =
	// "World") String name) {
	// return this.newInstance(name + " (req)");
	// }

	// @GetMapping("/Articolo/{id}")
	// public Articolo getArticolo(@PathVariable(value = "name") String name) {
	// return this.newInstance(name + " (get)");
	// }

	// @PutMapping("/Articolo/{id}")
	// public Articolo getArticolo(@RequestBody Articolo g, @PathVariable(value =
	// "name") String name) {
	// return this.newInstance(name + " (get)");
	// }

	// private Articolo newInstance(String name) {
	// return new Articolo(counter.incrementAndGet(), name);
	// }

}