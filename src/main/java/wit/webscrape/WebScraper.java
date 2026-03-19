package wit.webscrape;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

    public static void scrapeLibrary() {

        try {

            String url = "https://library.wit.edu/home";

            Document doc = Jsoup.connect(url).get();

            System.out.println("===== WIT LIBRARY =====");
            System.out.println("Page Title: " + doc.title());

            // Get headings
            Elements headings = doc.select("h1, h2, h3");

            System.out.println("\nHeadings:");

            for (Element heading : headings) {
                System.out.println(heading.text());
            }

            // Get links
            Elements links = doc.select("a[href]");

            System.out.println("\nLinks:");

            for (Element link : links) {

                String text = link.text();
                String href = link.absUrl("href");

                if (!text.isEmpty()) {
                    System.out.println(text + " -> " + href);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Scrape Campus Dining page
    public static void scrapeDining() {

        try {

            String url = "https://wit.edu/about/campus-dining";

            Document doc = Jsoup.connect(url).get();

            System.out.println("\n===== CAMPUS DINING =====");
            System.out.println("Page Title: " + doc.title());

            // Get headings
            Elements headings = doc.select("h1, h2, h3");

            System.out.println("\nDining Sections:");

            for (Element heading : headings) {
                System.out.println(heading.text());
            }

            // Get paragraph info
            Elements paragraphs = doc.select("p");

            System.out.println("\nDining Information:");

            for (Element p : paragraphs) {

                String text = p.text();

                if (text.length() > 50) {
                    System.out.println(text);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
