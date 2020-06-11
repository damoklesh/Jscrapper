package steve.com;

import java.time.LocalDateTime;

public class Logger {
	
	public static void info(String msg) {
		
		StringBuilder str = new StringBuilder();
		str.append("Log Info : ");
		str.append("Time : ");
		str.append(LocalDateTime.now());
		str.append(" : ");
		str.append(msg);
		System.out.println(str.toString());
	}

}
