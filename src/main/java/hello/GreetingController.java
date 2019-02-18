package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return this.newInstance(name + " (req)");
	}

	@GetMapping("/greeting/{id}")
	public Greeting getGreeting(@PathVariable(value = "name") String name) {
		return this.newInstance(name + " (get)");
	}

	@PutMapping("/greeting/{id}")
	public Greeting getGreeting(@RequestBody Greeting g, @PathVariable(value = "name") String name) {
		return this.newInstance(name + " (get)");
	}

	private Greeting newInstance(String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

}