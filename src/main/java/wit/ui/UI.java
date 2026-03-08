package wit.ui;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;

import wit.Main;
import wit.transit.MBTA;
import wit.transit.Route;
import wit.transit.Stop;
import wit.transit.TrainArrival;



public class UI extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // MBTA API Object
    private MBTA mbta;

    // Theme Colors
    private final Color BACKGROUND_BLACK = new Color(25, 25, 25);
    private final Color ACCENT_YELLOW = new Color(255, 179, 0);
    private final Color TEXT_WHITE = Color.WHITE;



    public UI() {
        // Initialize MBTA and pull initial data
        mbta = new MBTA();
        mbta.setApiKey(Main.API_KEY); //
        mbta.pullData(); // Maps API data to HashMaps

        setTitle("Student Information Hub");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_BLACK);

        cardPanel.add(createMainMenu(), "Menu");
        cardPanel.add(createTransitScreen(), "Transit");

        // Add Screens
        cardPanel.add(createMainMenu(), "Menu");
        cardPanel.add(createTransitScreen(), "Transit");
        cardPanel.add(createDiningScreen(), "Dining");
        cardPanel.add(createLibraryScreen(), "Library");
        cardPanel.add(createFitnessScreen(), "Fitness");

        add(cardPanel);
        setVisible(true);
    }


    // --- REUSABLE STYLING HELPERS ---
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(ACCENT_YELLOW);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // This ensures the button is centered and doesn't stretch infinitely
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 45));
        return btn;
    }

    private JLabel createLabel(String text, int fontSize, boolean bold) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(TEXT_WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createBasePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        return panel;
    }
    private void styleComboBox(JComboBox<String> box) {
        box.setBackground(new Color(45, 45, 45));
        box.setForeground(TEXT_WHITE);
        box.setMaximumSize(new Dimension(300, 35));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleResultsArea(JTextArea area) {
        area.setBackground(new Color(40, 40, 40));
        area.setForeground(ACCENT_YELLOW);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBorder(BorderFactory.createLineBorder(ACCENT_YELLOW));
    }


    // --- MAIN MENU ---
    private JPanel createMainMenu() {
        JPanel panel = createBasePanel();
        panel.add(createLabel("Student Information Hub", 22, true));
        panel.add(Box.createVerticalStrut(40));

        //Links button to specific Card Name
        JButton btnTransit = createButton("Public Transit Info");
        btnTransit.addActionListener(e -> cardLayout.show(cardPanel, "Transit"));

        JButton btnDining = createButton("Dining");
        btnDining.addActionListener(e -> cardLayout.show(cardPanel, "Dining"));

        JButton btnLibrary = createButton("Library");
        btnLibrary.addActionListener(e -> cardLayout.show(cardPanel, "Library"));

        JButton btnFitness = createButton("Fitness");
        btnFitness.addActionListener(e -> cardLayout.show(cardPanel, "Fitness"));

        panel.add(btnTransit);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnDining);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnLibrary);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnFitness);

        return panel;
    }

    // --- PUBLIC TRANSIT SECTION ---
    private JPanel createTransitScreen() {
        JPanel panel = createBasePanel();
        panel.add(createLabel("<html>PUBLIC TRANSIT <font color='#00FF00'>● LIVE</font></html>", 22, true));
        panel.add(Box.createVerticalStrut(20));

        // Get Routes from MBTA object
        Map<String, Route> allRoutes = mbta.getAllRoutes(); //
        // Sort keys for a better user experience
        String[] routeNames = new TreeMap<>(allRoutes).keySet().toArray(new String[0]);

        JComboBox<String> lineBox = new JComboBox<>(routeNames);
        styleComboBox(lineBox);

        panel.add(createLabel("Select Train Line:", 14, false));
        panel.add(lineBox);
        panel.add(Box.createVerticalStrut(15));

        panel.add(createLabel("Select Stop:", 14, false));
        JComboBox<String> stopBox = new JComboBox<>();
        styleComboBox(stopBox);
        panel.add(stopBox);
        panel.add(Box.createVerticalStrut(25));

        // Update Stops based on selected Route
        lineBox.addActionListener(e -> {
            String selectedName = (String) lineBox.getSelectedItem();
            Route selectedRoute = allRoutes.get(selectedName); //

            if (selectedRoute != null) {
                // Fetch list of stops for this route
                ArrayList<Stop> stops = mbta.getStopsReachable(selectedRoute); //
                stopBox.removeAllItems();
                for (Stop s : stops) {
                    stopBox.addItem(s.getName()); //
                }
            }
        });

        JButton submit = createButton("Find Next Trains");
        JTextArea results = new JTextArea(8, 20);
        styleResultsArea(results);

        // Fetch Real-Time Arrivals
        submit.addActionListener(e -> {
            String selectedRouteName = (String) lineBox.getSelectedItem();
            String selectedStopName = (String) stopBox.getSelectedItem();

            // Find Route by comparing names robustly
            Route route = null;
            for (Route r : mbta.getAllRoutes().values()) {
                if (r.getName().equalsIgnoreCase(selectedRouteName) || r.getId().equalsIgnoreCase(selectedRouteName)) {
                    route = r;
                    break;
                }
            }

            // Find Stop by comparing names robustly
            Stop stop = null;
            for (Stop s : mbta.getAllStops().values()) {
                if (s.getName().equalsIgnoreCase(selectedStopName) || s.getId().equalsIgnoreCase(selectedStopName)) {
                    stop = s;
                    break;
                }
            }

            if (route != null && stop != null) {
                // Fetch data (Note: this clears the internal list first)
                ArrayList<TrainArrival> arrivals = mbta.getTrainArrivals(stop, route);

                StringBuilder sb = new StringBuilder("REAL-TIME ARRIVALS:\n");
                sb.append("----------------------------\n");

                if (arrivals == null || arrivals.isEmpty()) {
                    sb.append("No upcoming trains found for this stop.");
                } else {
                    for (TrainArrival a : arrivals) {
                        try {
                            // Parse the ISO 8601 string (e.g., 2026-03-07T22:39:01-05:00)
                            java.time.ZonedDateTime arrivalTime = java.time.ZonedDateTime.parse(a.getArrivalTime());
                            java.time.ZonedDateTime now = java.time.ZonedDateTime.now();

                            // Calculate the difference in minutes
                            long minutesAway = java.time.Duration.between(now, arrivalTime).toMinutes();

                            String display;
                            if (minutesAway <= 0) {
                                display = "Arriving now";
                            } else if (minutesAway == 1) {
                                display = "1 minute away";
                            } else {
                                display = minutesAway + " minutes away";
                            }

                            // Add the status
                            sb.append("• ").append(display).append(" (").append(a.getDepartureTimes()).append(")\n");

                        } catch (Exception ex) {
                            // Fallback in case of parsing errors
                            sb.append("• ").append(a.getArrivalTime()).append("\n");
                        }
                    }
                }
                results.setText(sb.toString());
            } else {
                // Display exactly why it failed to help you debug
                results.setText("Error: Data mapping failed.\n" +
                        "Selected Route: " + selectedRouteName + " (Found: " + (route != null) + ")\n" +
                        "Selected Stop: " + selectedStopName + " (Found: " + (stop != null) + ")");
            }
        });

        panel.add(submit);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JScrollPane(results));
        panel.add(Box.createVerticalGlue());
        panel.add(backToMenuBtn());

        return panel;
    }

    // --- DINING SECTION ---
    private JPanel createDiningScreen() {
        JPanel panel = createBasePanel();
        panel.add(createLabel("Dining Information", 20, true));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("<html>Beatty Dining Hall: <font color='#00FF00'>OPEN</font></html>", 16, true));
        panel.add(createLabel("Today: 7:00 AM - 9:00 PM", 14, false));
        panel.add(createLabel("Weekly: Mon-Sun 7AM-9PM", 14, false));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Menu: Grilled Chicken, Roasted Veggies", 14, false));

        panel.add(Box.createVerticalStrut(30));
        JButton btnLink = createButton("View Full Menu Online");

        btnLink.addActionListener(e -> {
            try {
                // Use Desktop class to open the default browser
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new java.net.URI("https://eatatcof.sodexomyway.com/en-us/locations/beatty-dining-commons"));
                }
            } catch (Exception ex) {
                // Fallback: Show an error if the link can't open
                JOptionPane.showMessageDialog(this, "Could not open browser.");
            }
        });

        panel.add(btnLink);
        panel.add(Box.createVerticalGlue());
        panel.add(backToMenuBtn());
        return panel;
    }

    // --- LIBRARY SECTION ---
    private JPanel createLibraryScreen() {
        JPanel panel = createBasePanel();
        JLabel statusLabel = createLabel("<html>Status: <font color='#00FF00'>OPEN</font></html>", 16, true);
        panel.add(createLabel("Library Information", 20, true));
        panel.add(Box.createVerticalStrut(30));

        panel.add(statusLabel);
        panel.add(createLabel("Today's Hours: 8:00 AM - 12:00 AM", 14, false));
        panel.add(Box.createVerticalStrut(30));

        JButton btnLink = createButton("Open Study Room Booking");
        btnLink.addActionListener(e -> {
            try {
                // Use Desktop class to open the default browser
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new java.net.URI("https://wit.libcal.com/reserve/study-room"));
                }
            } catch (Exception ex) {
                // Fallback: Show an error if the link can't open
                JOptionPane.showMessageDialog(this, "Could not open browser.");
            }
        });


        JButton btnLink1 = createButton("Visit Library Website");
        btnLink1.addActionListener(e -> {
            try {
                // Use Desktop class to open the default browser
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new java.net.URI("https://library.wit.edu/home"));
                }
            } catch (Exception ex) {
                // Fallback: Show an error if the link can't open
                JOptionPane.showMessageDialog(this, "Could not open browser.");
            }
        });

        panel.add(Box.createVerticalStrut(60));
        panel.add(btnLink1);
        panel.add(Box.createVerticalStrut(30));
        panel.add(btnLink);
        panel.add(Box.createVerticalGlue());
        panel.add(backToMenuBtn());
        return panel;
    }

    // --- FITNESS SECTION ---
    private JPanel createFitnessScreen() {
        JPanel panel = createBasePanel();
        panel.add(createLabel("Fitness Center", 20, true));
        panel.add(Box.createVerticalStrut(30));
        panel.add(createLabel("<html>Schumann Fitness Center: <font color='#00FF00'>OPEN</font></html>", 16, true));
        panel.add(createLabel("Today: 6:00 AM - 11:00 PM", 14, false));
        panel.add(createLabel("Weekly: Mon-Fri 6AM-11PM, Sat-Sun 10AM-8PM", 14, false));
        panel.add(createLabel("Capacity: 75%", 14, false));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("<html>Tansey Basketball Court: <font color='#00FF00'>OPEN</font></html>", 16, true));
        panel.add(createLabel("Today: 6:00 AM - 11:00 PM", 14, false));
        panel.add(createLabel("Weekly: Mon-Fri 6AM-11PM, Sat-Sun 10AM-8PM", 14, false));
        panel.add(createLabel("Capacity: 40%", 14, false));
        panel.add(Box.createVerticalStrut(30));

        panel.add(createLabel("<html>Tansey Gymnasium: <font color='#00FF00'>OPEN</font></html>", 16, true));
        panel.add(createLabel("Today: 6:00 AM - 11:00 PM", 14, false));
        panel.add(createLabel("Weekly: Mon-Fri 6AM-11PM, Sat-Sun 10AM-8PM", 14, false));
        panel.add(createLabel("Capacity: 50%", 14, false));

        panel.add(Box.createVerticalGlue());
        panel.add(backToMenuBtn());
        return panel;
    }

    private JButton backToMenuBtn() {
        JButton back = new JButton("← Return to Main Menu");
        back.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        back.setForeground(ACCENT_YELLOW);
        back.setBackground(BACKGROUND_BLACK);
        back.setBorderPainted(false);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));
        return back;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new UI());
//    }
}