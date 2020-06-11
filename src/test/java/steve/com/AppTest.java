package steve.com;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit test for The main App.
 */
public class AppTest {

	@Test
    void checkArgTest() {
       
		// Given
		String[] args = {"url",null,"99","item selector","pic selector","targetDir"};
		
		// Then 
		assertThrows(RuntimeException.class, () -> App.checkArgs(args));
    }

}
