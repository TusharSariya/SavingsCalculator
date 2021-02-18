package com.SavingsCalculator;

import javafx.scene.Group;
import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GUI extends Application implements ActionListener {


    public static void main(String[] args) {
        // write your code here
        System.out.println("hello WRLD");
        new GUI();
        launch(args);
    }

    int count = 0;
    JLabel labelTFSA,labelRRSP,labelSavings;
    JTextField TextFieldTFSA,TextFieldRRSP;

    public GUI() {
        JFrame frame = new JFrame();

        TextFieldTFSA = new JTextField(20);
        TextFieldTFSA.setText("500");
        TextFieldRRSP = new JTextField(20);
        TextFieldRRSP.setText("500");
        JButton buttonCal = new JButton("calculate savings");

        buttonCal.addActionListener(this);
        labelTFSA = new JLabel("TFSA Monthly Contribution: ");
        labelRRSP = new JLabel("RRSP Monthly Contribution: ");
        labelSavings = new JLabel("Savings");
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(labelTFSA);
        panel.add(TextFieldTFSA);
        panel.add(labelRRSP);
        panel.add(TextFieldRRSP);
        panel.add(buttonCal);
        panel.add(labelSavings);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        monthlySaving_TFSA = Integer.parseInt(TextFieldTFSA.getText());
        monthlySaving_RRSP = Integer.parseInt(TextFieldRRSP.getText());
        Savings();
        labelSavings.setText(String.valueOf(totalSavings));
    }

    public void start(Stage stage) {

        //Defining the x axis
        NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
        xAxis.setLabel("Years");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis   (0, 350, 50);
        yAxis.setLabel("No.of schools");

        //Creating the line chart
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series = new XYChart.Series();
        series.setName("No of schools in an year");

        series.getData().add(new XYChart.Data(1970, 15));
        series.getData().add(new XYChart.Data(1980, 30));
        series.getData().add(new XYChart.Data(1990, 60));
        series.getData().add(new XYChart.Data(2000, 120));
        series.getData().add(new XYChart.Data(2013, 240));
        series.getData().add(new XYChart.Data(2014, 300));

        //Setting the data to Line chart
        linechart.getData().add(series);

        //Creating a Group object
        Group root = new Group(linechart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage
        stage.setTitle("Line Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    static int Salary = 70000;
    static int monthlySaving_TFSA = 500;
    static int monthlySaving_RRSP = 500;
    static double RRSP_ROOM = Math.min(27230, Salary*0.18);
    static double RRSP_Company_MAX_YEARLY = Salary*0.08;
    static double RRSP_Company_MAX_MONTLHY = RRSP_Company_MAX_YEARLY/12;
    static double monthlySaving_RRSP_DPSP = Math.min(RRSP_Company_MAX_MONTLHY,monthlySaving_RRSP*0.5);
    static int StockPurchaseProgram;
    static double[] TFSASavingsOverTime;
    static double[] RRSPSavingsOverTime;

    static double[] totalSavings_TFSA = new double[1000];
    static double[] totalSavings_RRSP = new double[1000];
    static double totalSavings = 0;
    static double yearlyInterest = 0.07;
    static double monthlyInterest = yearlyInterest/12;//change this for a proper calculation
    static int Months = 480;
    static int i = 0;

    static void Savings() {
        System.out.println("dpsp: "+monthlySaving_RRSP_DPSP);
        if(i<Months)
        {
            totalSavings_TFSA[i] = SavingsTFSA();
            totalSavings_RRSP[i] = SavingsRRSP();
            totalSavings = totalSavings_TFSA[i] + totalSavings_RRSP[i];
            System.out.println("total Savings TFSA: "+ totalSavings_TFSA[i]);
            System.out.println("total Savings RRSP: "+ totalSavings_RRSP[i]);
            System.out.println("total Savings     : "+ totalSavings);
            i++;
            Savings();
        }
    }

    static double SavingsTFSA() {
        if (i == 0) {
            return monthlySaving_TFSA;
        } else {
            return totalSavings_TFSA[i - 1] * (1 + monthlyInterest) + monthlySaving_TFSA;
        }
    }

    static double SavingsRRSP() {
        if (i == 0){
            return monthlySaving_RRSP;
        }
        else{
            return totalSavings_RRSP[i - 1] * (1 + monthlyInterest) + monthlySaving_RRSP + monthlySaving_RRSP_DPSP;
        }
    }
}
