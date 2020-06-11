package steve.com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * This class is responsible for connecting to a given url and crawl the a item
 * list matching the item selector. For each item found, it will navigate to
 * that link and retrieve all the images matching the picture selector.
 * 
 * @author Steve Leon
 *
 */
@Accessors
@Getter
@AllArgsConstructor
public class Scrapper {

	private String siteUrl;
	private int startPageNumber;
	private int endPageNumber;
	private String itemSelector;
	private String imgSelector;
	private String targetDir;

	/**
	 * The Args Array can contain the following data. 0: Target Site full Url to
	 * scrap images from 1: Start Page number (int value) 2: The end page number. 3:
	 * Item selector: This item should have a href link pointing to the final page
	 * containing. 3179 4: Image selector: The html image tag containing the src
	 * attribute. 5: The target directory where the images are saved.
	 * 
	 */
	public Scrapper(List<String> argums) {
		log(argums.size() + " Parameters found.");
		
		siteUrl = argums.get(0);
		startPageNumber = Integer.parseInt(argums.get(1));
		endPageNumber = Integer.parseInt(argums.get(2));
		itemSelector = argums.get(3);
		imgSelector = argums.get(4);
		targetDir = argums.get(5);

		log("Site: " + siteUrl);
		log("Target dir : " + targetDir);
		log("Start page: " + startPageNumber);
		log("End page: " + endPageNumber);
		log("Item selector: " + itemSelector);
		log("Img selector: " + imgSelector);
	}

	public void scrap() {
		boolean thereAreMorePages = true;
		while (thereAreMorePages && startPageNumber <= endPageNumber) {

			try {
				Document doc = Jsoup.connect(siteUrl + startPageNumber).maxBodySize(0).timeout(5000).get();

				startPageNumber++;

				log(doc.title() + "Page " + (startPageNumber - 1));
				Elements items = doc.select(itemSelector);

				for (Element cardItem : items) {
					processItem(cardItem);
				}
			} catch (HttpStatusException e) {
				e.printStackTrace();
				// if the fetching of the page failed, we consider that there are no more pages
				// to fetch.
				thereAreMorePages = false;
				log("Ended at page " + (startPageNumber - 1));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		log("End of pages achieved. " + startPageNumber + " pages scrapped.");

	}

	private void processItem(Element cardItem) throws IOException, FileNotFoundException {
		log("Item");

		String cardUrl = cardItem.absUrl("href");
		Document cardDoc = Jsoup.connect(cardUrl).maxBodySize(0).timeout(0).get();
		Elements cardPics = cardDoc.getElementsByTag(imgSelector);

		log(cardUrl);
		log(cardDoc.title());

		for (Element picture : cardPics) {
			downloadPicture(picture);
		}
	}

	private void downloadPicture(Element picture) throws IOException, FileNotFoundException {
		log("Item picture");

		String picUrl = picture.absUrl("src");
		log(picUrl);

		// Download image
		// Open a URL Stream
		Response resultImageResponse = Jsoup.connect(picUrl).ignoreContentType(true).execute();

		// output here
		String picName = picUrl.substring(picUrl.lastIndexOf("/") + 1);
		FileOutputStream out = (new FileOutputStream(new java.io.File(targetDir + picName)));

		out.write(resultImageResponse.bodyAsBytes());
		out.close();
	}

	private void log(String msg) {
		Logger.info(msg);
	}

}
