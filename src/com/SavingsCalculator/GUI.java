package com.SavingsCalculator;

import javafx.scene.Group;

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

    private static final boolean DEBUG = false;
    public static void main(String[] args) {
        new GUI();
    }

    JLabel  label_TFSA,
            label_RRSP,
            label_Savings,
            label_currentAge,
            label_retirementAge,
            label_InitialDeposit;

    JTextField TextField_TFSA,
               TextField_RRSP,
               TextField_currentAge,
               TextField_retirementAge,
               TextField_initialDeposit;

    JPanel panel = new JPanel();
    JButton buttonCal = new JButton("calculate savings");
    JFrame frame = new JFrame();

    public GUI() {
        setTextFields();
        buttonCal.addActionListener(this);
        setLabels();
        configurePanel();
        addToPanel();
        configureFrame();
    }

    public void setTextFields()
    {
        TextField_TFSA = new JTextField(20);
        TextField_TFSA.setText("500");
        TextField_RRSP = new JTextField(20);
        TextField_RRSP.setText("500");
        TextField_currentAge = new JTextField(20);
        TextField_currentAge.setText("18");
        TextField_retirementAge = new JTextField(20);
        TextField_retirementAge.setText("65");
        TextField_initialDeposit = new JTextField(20);
        TextField_initialDeposit.setText("10000");
    }

    public void setLabels()
    {
        label_TFSA = new JLabel("TFSA Monthly Contribution: ");
        label_RRSP = new JLabel("RRSP Monthly Contribution: ");
        label_currentAge = new JLabel("current age");
        label_retirementAge = new JLabel( "retirement age");
        label_Savings = new JLabel("Savings");
        label_InitialDeposit = new JLabel("Initial Deposit");
    }

    public void addToPanel()
    {
        panel.add(label_TFSA);
        panel.add(TextField_TFSA);
        panel.add(label_RRSP);
        panel.add(TextField_RRSP);
        panel.add(label_InitialDeposit);
        panel.add(TextField_initialDeposit);
        panel.add(label_currentAge);
        panel.add(TextField_currentAge);
        panel.add(label_retirementAge);
        panel.add(TextField_retirementAge);
        panel.add(buttonCal);
        panel.add(label_Savings);
    }

    public void configurePanel()
    {
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
    }

    public void configureFrame()
    {
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parseInput();
        Savings(currentAgeI,retirementAgeI);
        label_Savings.setText(String.valueOf(totalSavings));
        launch(null);
    }

    public void parseInput()
    {
        monthlySaving_TFSA = Integer.parseInt(TextField_TFSA.getText());
        monthlySaving_RRSP = Integer.parseInt(TextField_RRSP.getText());
        currentAgeI = Integer.parseInt(TextField_currentAge.getText())*12;
        retirementAgeI = Integer.parseInt(TextField_retirementAge.getText())*12;
    }

    public void start(Stage stage) {

        //Defining the x axis
        NumberAxis xAxis = new NumberAxis(0, 840, 1);
        xAxis.setLabel("Years");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis   (0, 4000000, 100000);
        yAxis.setLabel("No.of schools");

        //Creating the line chart
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series seriesTFSA = new XYChart.Series();
        XYChart.Series seriesRRSP = new XYChart.Series();
        XYChart.Series seriesTotal = new XYChart.Series();
        seriesTFSA.setName("TFSA");
        seriesRRSP.setName("RRSP");
        seriesTotal.setName("Total");

        //series.getData().add(new XYChart.Data(1970, 15));
        for(int i = 0; i < 840; i++)
        {
            seriesTFSA.getData().add(new XYChart.Data(i, totalSavings_TFSA[i]));
            seriesRRSP.getData().add(new XYChart.Data(i, totalSavings_RRSP[i]));
            seriesTotal.getData().add(new XYChart.Data(i, totalSavings_TFSA[i] + totalSavings_RRSP[i]));
        }



        //Setting the data to Line chart
        linechart.getData().add(seriesTFSA);
        linechart.getData().add(seriesRRSP);
        linechart.getData().add(seriesTotal);

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
    static int currentAgeI = 500;
    static int retirementAgeI = 500;
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
    static int Months = 840;
    static int i = 0;

    static void Savings(int currentAgeI,int retirementAgeI) {
        System.out.println("dpsp: "+monthlySaving_RRSP_DPSP);
        i = currentAgeI;
        if(i<retirementAgeI)
        {
            totalSavings_TFSA[i] = SavingsTFSA();
            totalSavings_RRSP[i] = SavingsRRSP();
            totalSavings = totalSavings_TFSA[i] + totalSavings_RRSP[i];
            if(DEBUG) {
                System.out.println("total Savings TFSA: " + totalSavings_TFSA[i]);
                System.out.println("total Savings RRSP: " + totalSavings_RRSP[i]);
                System.out.println("total Savings     : " + totalSavings);
            }
            i++;
            Savings(currentAgeI+1, retirementAgeI);
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
