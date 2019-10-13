package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private static double[] temperatures;
    private static int size;

    public TemperatureSeriesAnalysis() {
        temperatures = new double[10];
        size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkTemp(temperatureSeries);
        temperatures = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        size = temperatureSeries.length;
    }

    public double[] getTemperatures() {
        return temperatures;
    }

    public int getSize() {
        return size;
    }

    private void notNull(double[] arr) {
        if (size == 0|| arr.length==0) throw new IllegalArgumentException("There are no temperatures to calculate.");
    }

    private void checkTemp(@org.jetbrains.annotations.NotNull double[] arr) {
        for (double el : arr) if (el < -273) throw new InputMismatchException("Temperature is below absolute zero");
    }


    public double average() {
        notNull(temperatures);
        return Arrays.stream(temperatures).sum() / temperatures.length;
    }

    public double deviation() {
        notNull(temperatures);
        double avg = average();
        int sum = 0;
        for (double temperature : temperatures) sum += (temperature - avg)*(temperature-avg);
        return Math.sqrt(sum / (double) temperatures.length);
    }

    public double min() {
        checkTemp(temperatures);
        return Arrays.stream(temperatures).min().getAsDouble();
    }

    public double max() {
        checkTemp(temperatures);
        return Arrays.stream(temperatures).max().getAsDouble();
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkTemp(temperatures);
        double temp = 0, delta = Double.MAX_VALUE;
        for (double elem : temperatures)
            if (Math.abs(elem - tempValue) < delta) {
                delta = Math.abs(elem - tempValue);
                temp = elem;
            }
        return temp;
    }

    public double[] findTempsLessThen(double tempValue) {
        int count = 0, pt = 0;
        for (double temp : temperatures) if (temp < tempValue) count += 1;
        double[] result = new double[count];
        for (double temp : temperatures)
            if (temp < tempValue) {
                result[pt] = temp;
                pt++;
            }
        Arrays.sort(result);
        return result;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int count = 0, pt = 0;
        for (double temp : temperatures) if (temp > tempValue) count += 1;
        double[] result = new double[count];
        for (double temp : temperatures)
            if (temp > tempValue) {
                result[pt] = temp;
                pt++;
            }
        Arrays.sort(result);
        return result;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        checkTemp(temps);
        double[] tmp = Arrays.copyOf(temperatures, size);
        while (temps.length > temperatures.length - size) temperatures = new double[temperatures.length * 2];
        for (int i = 0; i < tmp.length + temps.length; i++)
            if (i < tmp.length) temperatures[i] = tmp[i];
            else temperatures[i] = temps[i - tmp.length];
        size = temps.length + tmp.length;
        return 0;
    }
}
