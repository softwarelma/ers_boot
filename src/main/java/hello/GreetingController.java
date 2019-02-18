package hello;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private final AtomicLong counter = new AtomicLong();
	private List<Greeting> list = new LinkedList<Greeting>();

	public GreetingController() {
		this.list.add(this.newInstance("a"));
		this.list.add(this.newInstance("b"));
		this.list.add(this.newInstance("c"));
	}

	@GetMapping("/rest/getAll")
	public List<Greeting> getAll() {
		System.out.println("getAll");
		return this.list;
	}

	@GetMapping("/rest/get/{id}")
	public Greeting getOne(@PathVariable Long id) {
		System.out.println("get/" + id);
		for (Greeting g : this.list) {
			if (g.getId() == id.longValue())
				return g;
		}
		return null;
	}

	@PostMapping("/rest/post")
	public Greeting postNew(@RequestBody Greeting newEmployee) {
		System.out.println("post/" + newEmployee.getId());
		this.list.add(newEmployee);
		return newEmployee;
	}

	@PutMapping("/rest/put")
	public Greeting putExisting(@RequestBody Greeting newEmployee) {
		System.out.println("put/" + newEmployee.getId());
		this.list.add(newEmployee);
		return newEmployee;
	}

	@DeleteMapping("/rest/del/{id}")
	public Greeting delete(@PathVariable Long id) {
		System.out.println("del/" + id);
		int ind = -1;
		for (int i = 0; i < this.list.size(); i++) {
			Greeting g = this.list.get(i);
			if (g.getId() == id.longValue()) {
				ind = i;
				break;
			}
		}
		if (ind != -1) {
			return this.list.remove(ind);
		} else {
			return null;
		}
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

	// @RequestMapping("/greeting")
	// public Greeting greeting(@RequestParam(value = "name", defaultValue =
	// "World") String name) {
	// return this.newInstance(name + " (req)");
	// }

	// @GetMapping("/greeting/{id}")
	// public Greeting getGreeting(@PathVariable(value = "name") String name) {
	// return this.newInstance(name + " (get)");
	// }

	// @PutMapping("/greeting/{id}")
	// public Greeting getGreeting(@RequestBody Greeting g, @PathVariable(value =
	// "name") String name) {
	// return this.newInstance(name + " (get)");
	// }

	private Greeting newInstance(String name) {
		return new Greeting(counter.incrementAndGet(), name);
	}

}