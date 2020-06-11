package steve.com;

import java.util.List;
import java.util.Objects;

/**
 * JScrapper Main Class
 *
 */
public class App {

 	public static void main(String[] args) {

		checkArgs(args);

		log("Initializing Image Scrapping");
		
		Scrapper scrapper = new Scrapper(List.of(args));
		scrapper.scrap();
	}
		

	static void checkArgs(String[] args) {

		List<String> argums = List.of(args);
		if (argums.stream().anyMatch(e -> Objects.isNull(e))) {
			throw new RuntimeException("Invalid argument list");
		}
	}

	private static void log(String msg) {
		Logger.info(msg);
	}
	
	
}
