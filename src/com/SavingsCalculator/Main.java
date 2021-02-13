package com.SavingsCalculator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello WRLD");
        Savings(480);
        System.out.println("total Savings: "+ totalSavings);

    }

    //static int Years = 40;
    //static int Months = Years*12;
    static int monthlySavings = 500;
    static double totalSavings = 0;
    static double yearlyInterest = 0.07;
    static double monthlyInterest = yearlyInterest/12;

    static void Savings(int Months) {
        if(Months>0)
        {
            totalSavings = totalSavings*(1+monthlyInterest)+monthlySavings;
            System.out.println("total Savings: "+ totalSavings);
            Savings(--Months);
        }
    }
}
