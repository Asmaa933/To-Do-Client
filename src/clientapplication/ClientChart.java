/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.json.JsonObject;
import network.JsonUtil;
import network.RequestHandler;

public class ClientChart extends Application implements Initializable{

    private static int allLists;
    private static int allTasks;
    private static int todoTasks;
    private static int inprogressTasks;
    private static int doneTasks;
     

    public static void setInputs(int loginUserID) {
        JsonObject request = JsonUtil.fromStats(loginUserID);
        JsonObject response = new RequestHandler().makeRequest(request);
         allLists = response.getInt("all_lists");
         allTasks = response.getInt("all_tasks");
         todoTasks = response.getInt("todo_tasks");
         inprogressTasks = response.getInt("in_progress_tasks");
         doneTasks = response.getInt("done_tasks");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Statistics Of Your Activity");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Your Activity");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Numbers");

        StackedBarChart stackedBarChart = new StackedBarChart(xAxis, yAxis);
        stackedBarChart.setCategoryGap(20);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("All Lists");
        dataSeries1.setNode(yAxis);
        dataSeries1.getData().add(new XYChart.Data("All Lists", allLists));

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("All Tasks");
        dataSeries2.setNode(yAxis);
        dataSeries2.getData().add(new XYChart.Data("All Tasks", allTasks));

        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Todo Tasks");
        dataSeries3.setNode(yAxis);
        dataSeries3.getData().add(new XYChart.Data("Todo Tasks", todoTasks));

        XYChart.Series dataSeries4 = new XYChart.Series();
        dataSeries4.setName("Inprogress Tasks");
        dataSeries4.setNode(yAxis);
        dataSeries4.getData().add(new XYChart.Data("InprogressTasks", inprogressTasks));

        XYChart.Series dataSeries5 = new XYChart.Series();
        dataSeries5.setName("Done Tasks");
        dataSeries5.setNode(yAxis);
        dataSeries5.getData().add(new XYChart.Data("Done Tasks", doneTasks));

        stackedBarChart.getData().addAll(dataSeries1, dataSeries2, dataSeries3, dataSeries4, dataSeries5);

        VBox vbox = new VBox(stackedBarChart);

        Scene scene = new Scene(vbox, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setHeight(400);
        primaryStage.setWidth(400);

        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

  
}
