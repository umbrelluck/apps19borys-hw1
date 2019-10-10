package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

public class TemperatureSeriesAnalysis {

    static private double[] temperatures;
    static private int size;

    public TemperatureSeriesAnalysis() {
        temperatures = new double[10];
    }

    private void not_null(double[] arr){
        if (size==0) throw new IllegalArgumentException("There are no temperatures to calculate.");
    }

    private void check_temp(@org.jetbrains.annotations.NotNull double[] arr){
        for (double el : arr) if (el<-273) throw new InputMismatchException("Temperature is below absolute zero");
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        check_temp(temperatureSeries);
        temperatures= Arrays.copyOf(temperatureSeries,temperatureSeries.length);
        size=temperatureSeries.length;
    }

    public double average() {
        not_null(temperatures);
        return Arrays.stream(temperatures).sum()/temperatures.length;
    }

    public double deviation() {
        not_null(temperatures);
        double avg=average();
        int sum=0;
        for (double temperature : temperatures) sum += Math.pow((temperature - avg), 2);
        return Math.sqrt(sum/(double)temperatures.length);
    }

    public double min() {
        check_temp(temperatures);
        return Arrays.stream(temperatures).min().getAsDouble();
    }

    public double max() {
        check_temp(temperatures);
        return Arrays.stream(temperatures).max().getAsDouble();
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        check_temp(temperatures);
        double temp=0, delta= Double.MAX_VALUE;
        for(double elem : temperatures)
            if (Math.abs(elem-tempValue)<delta){
                delta=Math.abs(elem-0);
                temp=elem;
            }
        return temp;
    }

    public double[] findTempsLessThen(double tempValue) {
        int count=0,pt=0;
        for(double temp:temperatures) if (temp<tempValue) count+=1;
        double[] result=new double[count];
        for (double temp:temperatures) if (temp<tempValue){
            result[pt]=temp;
            pt++;
        }
        return  result;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int count=0,pt=0;
        for(double temp:temperatures) if (temp>tempValue) count+=1;
        double[] result=new double[count];
        for (double temp:temperatures) if (temp>tempValue){
            result[pt]=temp;
            pt++;
        }
        return  result;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(),deviation(),min(),max());
    }

    public int addTemps(double... temps) {
        check_temp(temps);
        double[] tmp=Arrays.copyOf(temperatures,size);
        while(temps.length>temperatures.length-size)temperatures=new double[temperatures.length*2];
        for(int i=0;i<tmp.length+temps.length;i++)
            if (i<tmp.length) temperatures[i]=tmp[i];
            else temperatures[i]=temps[i-tmp.length];
        size=temps.length+tmp.length;
        return 0;
    }
}
