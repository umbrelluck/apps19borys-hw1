package ua.edu.ucu.tempseries;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class TemperatureSeriesAnalysisTest {
    private static TemperatureSeriesAnalysis analys;

    @Before
    public void setUp() throws Exception {
        double[] norm_mas = new double[]{5, 9, 32, 1, 589, 324, 56, -78, -5, -15, -75, 1, -95, -25, 1, 0.9, -0.05};
        analys = new TemperatureSeriesAnalysis(norm_mas);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkEmpty() {
        TemperatureSeriesAnalysis empty = new TemperatureSeriesAnalysis();
        double avg = empty.average();
        System.out.println(avg + "js");
    }

    @Test()
    public void average() {
        double avg = analys.average();
        assertEquals(42.697058824, avg, 0.0001);
    }

    @Test
    public void deviation() {
        double dev = analys.deviation();
        assertEquals(162.113613029, dev, 0.0001);
    }

    @Test
    public void min() {
        double mini = analys.min();
        assertEquals(-95, mini, 0.00000001);
    }

    @Test
    public void max() {
        double maxi = analys.max();
        assertEquals(589, maxi, 0.00000001);
    }

    @Test
    public void findTempClosestToZero() {
        double closest = analys.findTempClosestToZero();
        assertEquals(-0.05, closest, 0.0000000001);
    }

    @Test
    public void findTempClosestToValue() {
        double closest = analys.findTempClosestToValue(32);
        assertEquals(32, closest, 0.00000001);
        closest = analys.findTempClosestToValue(0.8);
        assertEquals(0.9, closest, 0.000001);
    }

    @Test
    public void findTempsLessThen() {
        double[] temps = analys.findTempsLessThen(-74);
        assertArrayEquals(new double[]{-95, -78, -75}, temps, 0.000001);
//        assertArrayEquals();
    }

    @Test
    public void findTempsGreaterThen() {
        double[] temps = analys.findTempsGreaterThen(56);
        assertArrayEquals(new double[]{324, 589}, temps, 0.00001);
    }

    @Test
    public void summaryStatistics() {
        TempSummaryStatistics stats = analys.summaryStatistics();
        String msg = "TempSummaryStatistics{avgTemp=42.69705882352941, devTemp=162.11361302929427, minTemp=-95.0, maxTemp=589.0}";
        assertEquals(msg, stats.toString());
    }

    @Test()
    public void addTemps() {
        analys.addTemps(5, 6, 9, 8);
        double[] mas = Arrays.copyOf(analys.getTemperatures(), analys.getSize());
        double[] correct = {5, 9, 32, 1, 589, 324, 56, -78, -5, -15, -75, 1, -95, -25, 1, 0.9, -0.05, 5, 6, 9, 8};
        assertArrayEquals(correct, mas, 0.00000001);
    }

    @Test(expected = InputMismatchException.class)
    public void addTemps_error() {
        analys.addTemps(-8, -512);
    }
}