import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class WebsiteDownloader implements Callable<WebsiteDownloader> {
	String address;
	public String html;
	public String output;
	
	WebsiteDownloader(String address) {
		this.address = address;
	}
	
    public WebsiteDownloader call()  {
		try {
			long start = System.currentTimeMillis();
			Document website = Jsoup.connect(address).get();
			html = website.html();
			String keywords = "---"; 
			String title = "---";
			
			if(website.select("meta[name=\"keywords\"]").size() > 0) {
				keywords = website.select("meta[name=\"keywords\"]").attr("content");
			}
			if(website.select("title").size() > 0) {
				title = website.select("title").first().html();
			}
			
			output =  "<html>" +
				"title: " + title + "<br>" +
				"keywords: " + keywords + "<br>" +
				"links: " + website.select("a").size() + "<br>" +
				"Loading time: " + (System.currentTimeMillis() - start) + "ms <br>" +
				"</html>";
		} catch (java.lang.IllegalArgumentException e) {
			output = "<html>Please make sure that you've typed website address correctly</html>";
		} catch(org.jsoup.HttpStatusException e) {
			output = "<html>Website not found. HTTP Status: 404</html>";
		} catch(java.net.UnknownHostException e) {
			output = "<html>Wrong host. Checkout your address.</html>";
		} catch(java.net.SocketTimeoutException e) {
			output = "<html> Socket timeout, check your address and internet connection </html>";
		} catch (Exception e) {
//			e.printStackTrace();
			output = "<html>Something went wrong, please check your address and internet connection.</html>";
		}
		
		return this;
    }
}
