package wit;

import wit.ui.UI;

import javax.swing.*;

public class Main {


    /**
     * BEFORE BUILDING THE PROJECT PLEASE ADD THE MBTA API KEY HERE
     * MORE DETAILS ARE IN THE README.MD File
     */
    public static final String API_KEY = "";  // INSERT MBTA API KEY HERE


    public static void main(String[] args) {

        SwingUtilities.invokeLater(UI::new); // runs the UI


    }
}