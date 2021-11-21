package es.ucm.gdv.TFG.REST_API;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/*@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}*/
	
	@GetMapping("/prueba")
	public Greeting prueba(@RequestBody Greeting input) {
		//return new Greeting(1, "a");
		return new Greeting(input.getId(), input.getContent(), input.getPrueba());
	}
}