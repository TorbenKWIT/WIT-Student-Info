package wit;

import wit.ui.UI;

import javax.swing.*;

//import static wit.webscrape.WebScraper.scrapeDining;
//import static wit.webscrape.WebScraper.scrapeLibrary;

public class Main {


    /**
     * BEFORE BUILDING THE PROJECT PLEASE ADD THE MBTA API KEY HERE
     * MORE DETAILS ARE IN THE README.MD File
     */
    public static final String API_KEY = "17b8054b5f10426bb73017a70a8f5700";  // INSERT MBTA API KEY HERE


    public static void main(String[] args) {
        //scrapeLibrary();
        //scrapeDining();
        SwingUtilities.invokeLater(UI::new); // runs the UI


    }
}