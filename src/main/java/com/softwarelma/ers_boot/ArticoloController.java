package com.softwarelma.ers_boot;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticoloController {

	private final ArticoloDao articoloDao = new ArticoloDao();

	public ArticoloController() {
	}

	@GetMapping("/rest/getAll")
	public List<Articolo> getAll() {
		return this.articoloDao.getAll();
	}

	@GetMapping("/rest/get/{id}")
	public Articolo getOne(@PathVariable Long id) {
		return this.articoloDao.get(id);
	}

	@PostMapping("/rest/post")
	public Articolo postNew(@RequestBody Articolo articolo) {
		return this.articoloDao.addNew(articolo);
	}

	@PutMapping("/rest/put")
	public Articolo putExisting(@RequestBody Articolo articolo) {
		return this.articoloDao.putExisting(articolo);
	}

	@PutMapping("/rest/put/upd-ins")
	public Articolo putExistingOrNew(@RequestBody Articolo articolo) {
		return this.articoloDao.putExistingOrNew(articolo);
	}

	@DeleteMapping("/rest/del/{id}")
	public Articolo delete(@PathVariable Long id) {
		return this.articoloDao.delete(id);
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