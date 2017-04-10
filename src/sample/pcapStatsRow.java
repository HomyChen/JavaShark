package sample;

/**
 * Created by Iris-book on 4/9/2017.
 */
public class pcapStatsRow {

    private String statsParam;
    private String theValue;

    public pcapStatsRow(String statsParam, String theValue){
        this.statsParam = statsParam;
        this.theValue = theValue;
    }

    public void setStatsParam(String statsParam) {
        this.statsParam = statsParam;
    }

    public String getStatsParam() {
        return statsParam;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue;
    }

    public String getTheValue() {
        return theValue;
    }
}
