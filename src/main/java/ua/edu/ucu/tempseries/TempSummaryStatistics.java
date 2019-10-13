package ua.edu.ucu.tempseries;

public class TempSummaryStatistics {

    private double avgTemp, devTemp, minTemp, maxTemp;

    public TempSummaryStatistics(double a, double b, double c, double d) {
        avgTemp = a;
        devTemp = b;
        minTemp = c;
        maxTemp = d;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDevTemp() {
        return devTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String toString() {
        return "TempSummaryStatistics{" +
                "avgTemp=" + avgTemp +
                ", devTemp=" + devTemp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                '}';
    }
}
