package es.ucm.gdv.TFG.REST_API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.ucm.gdv.TFG.REST_API.platform2D.PlatformQuery;

@RestController
public class GreetingController {


	@PostMapping("/platform")
	public PlatformQuery prueba(@RequestBody PlatformQuery i) {
		//return new Greeting(1, "a");
		return new PlatformQuery(i.getCharacterinfo(),i.getCharacterProgress(),i.getCollectable(),
				i.getHabilities(),i.getHealth(),i.getLevelProgress(),i.getScore(),i.getShields(),i.getTime(),i.getWeapons());
	}
}