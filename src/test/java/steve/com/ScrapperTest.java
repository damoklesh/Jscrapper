package steve.com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Scrapper class test class.
 * @author Steve leon
 *
 */
public class ScrapperTest {

	@Test
    void paramateInitTest() {
        
		// Given
		String[] args = {"url","1","99","item selector","pic selector","targetDir"};
		
		// when
		Scrapper scrapper = new Scrapper(List.of(args));
		
		// then
		assertEquals("url", scrapper.getSiteUrl());
		assertEquals(1, scrapper.getStartPageNumber());
		assertEquals(99, scrapper.getEndPageNumber());
		assertEquals("item selector", scrapper.getItemSelector());
		assertEquals("pic selector", scrapper.getImgSelector());
		assertEquals("targetDir", scrapper.getTargetDir());
        
    }
}
