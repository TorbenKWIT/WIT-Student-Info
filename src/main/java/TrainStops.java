public enum TrainStops {
    BOSTON_COLLEGE("Green-B", 70129),
    SOUTH_STREET("Green-B"),
    CHESTNUT_HILL_AVE("Green-b"),
    CHISWICK_RD("Green-B", ),
    SUTHERLAND_RD("Green-B", ),
    WASHINGTON_ST("Geen_B", ),
    WARREN_ST("Green-B"),
    ALLSTON_ST("Green-B", ),
    GRIGGS_ST("Green-B"),
    HARVARD_AVE("Green_B",),
    PACKARDS_CORNER("Green_B"),
    BABCOCK_ST("Green-B"),
    AMORY_STREET("Green-B"),
    BOSTON_UNIVERSITY_CENTRAL("GREEN-B"),
    BOSTON_UNIVERSITY_EAST("Green-B"),
    BLANDFORD_ST("Green-B"),

    CLEVELAND_CIRCLE("Green-C"),
    ENGLEWOOD_AVE("Green-C"),
    DEAN_RD("Green-C"),
    TAPPAN_ST("Green-C"),
    WASHINGTON_SQ("Green-C"),
    FAIRBANKS_ST("Green-C"),
    BRANDON_HALL("Green-C"),
    SUMMIT_AVE("Green-C"),
    COOLIDGE_CORNER("Green-C"),
    ST_PAUL_ST()




    ;


    final String line;
    final int stopId;

    TrainStops(String line) {
        this.line = line;
    }

    TrainStops(int stopId, ){
        this.stopId = stopId;
    }
}
