package com.ec.assignment3.jMetal_Runners;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class NSGAIIexample extends Application {

    final double max = 600;
    final double min = 300;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(min, max, (max - min) / 10);
        final NumberAxis yAxis = new NumberAxis(min, max, (max - min) / 10);
        final ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis,
                yAxis);
        xAxis.setLabel("f1");
        yAxis.setLabel("f2");
        sc.setTitle("Three Pareto Front");
        ObservableList<Data<Number, Number>> dataList = new ObservableListWrapper<>(
                new ArrayList<>());
        double[][] arr = {
                {390.8984730095904	,512.6255178890424},
                {372.00938179214756	,534.4294616062148},
                {380.5350542723867	,521.0138017153554},
                {370.119906661677	,542.997419114595},
                {337.50737492248726	,549.4443496647564},
                {402.99024049662734	,503.93359952037315},
                {312.99696337182775	,548.0214446639204},
                {375.9633743202088	,519.8478439682635},
                {371.9688365110269	,530.0905038565631},
                {383.0500781479371	,506.24017097444647},
                {355.0871509420348	,542.7398282763169},
                {358.8859349965758	,536.7645041059654},
                {360.54155992973466	,536.5804987380657},
                {392.13306301475694	,483.7652023756827},
                {395.6119243694401	,463.6109604202503},
                {393.6335852886241	,431.41121768143876},
                {391.8430130731924	,476.4781860081614},
                {391.9499712386586	,467.86128651629093},
                {386.28221728370323	,492.6864927550069},
                {384.4272320501176	,496.53554977795324},
        };
        for(double[] a: arr){
            dataList.add(new Data<Number, Number>(a[0], a[1]));
        }
        Series<Number, Number> series1 = new Series<>();
        series1.setName("Algorithm1");
        series1.setData(dataList);

        ObservableList<Data<Number, Number>> dataList1 = new ObservableListWrapper<>(
                new ArrayList<>());
        double[][] arr1 = {
                {390.8984730095904	,512.6255178890424},
                {372.00938179214756	,534.4294616062148},
                {380.5350542723867	,521.0138017153554},
                {370.119906661677	,542.997419114595},
                {337.50737492248726	,549.4443496647564},
                {402.99024049662734	,503.93359952037315},
        };
        for(double[] a: arr1){
            dataList1.add(new Data<Number, Number>(a[0], a[1]));
        }

        Series<Number, Number> series2 = new Series<>();
        series2.setName("Algorithm2");
        series2.setData(dataList1);

        Series<Number, Number> series3 = new Series<>();
        series3.setName("Algorithm3");
        series3.setData(generateData());

        sc.getData().addAll(series1,series2);
//        sc.getData().addAll(series1, series2, series3);
        Scene scene = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @return Generates random data resembling a Pareto front
     */
    public ObservableList<Data<Number, Number>> generateData() {
        ObservableList<Data<Number, Number>> dataList = new ObservableListWrapper<>(
                new ArrayList<>());
        Random rand = new Random();

        double x = min+rand.nextDouble();
        double y = max-rand.nextDouble();

        while (x < max && y > min) {
            x += rand.nextDouble();
            y -= rand.nextDouble();
            dataList.add(new Data<Number, Number>(x, y));
        }
        System.out.println(dataList);
        return dataList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
