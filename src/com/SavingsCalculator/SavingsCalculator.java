package com.SavingsCalculator;

public class SavingsCalculator {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello WRLD");
        Savings(480);
        new GUI();
    }

    //static int Years = 40;
    //static int Months = Years*12;
    static int Salary = 70000;
    static int monthlySaving_TFSA = 500;
    static int monthlySaving_RRSP = 500;
    static double RRSP_ROOM = Math.min(27230, Salary*0.18);
    static double RRSP_Company_MAX_YEARLY = Salary*0.08;
    static double RRSP_Company_MAX_MONTLHY = RRSP_Company_MAX_YEARLY/12;
    static double monthlySaving_RRSP_DPSP = Math.min(RRSP_Company_MAX_MONTLHY,monthlySaving_RRSP*0.5);
    static int StockPurchaseProgram;

    static double totalSavings_TFSA = 0;
    static double totalSavings_RRSP = 0;
    static double totalSavings = 0;
    static double yearlyInterest = 0.07;
    static double monthlyInterest = yearlyInterest/12;//change this for a proper calculation

    static void Savings(int Months) {
        System.out.println("dpsp: "+monthlySaving_RRSP_DPSP);
        if(Months>0)
        {
            totalSavings_TFSA = SavingsTFSA();
            totalSavings_RRSP = SavingsRRSP();
            totalSavings = totalSavings_TFSA + totalSavings_RRSP;
            System.out.println("total Savings TFSA: "+ totalSavings_TFSA);
            System.out.println("total Savings RRSP: "+ totalSavings_RRSP);
            System.out.println("total Savings     : "+ totalSavings);
            Savings(--Months);
        }
    }

    static double SavingsTFSA()
    {

        return totalSavings_TFSA*(1+monthlyInterest)+monthlySaving_TFSA;
    }

    static double SavingsRRSP()
    {

        return totalSavings_RRSP*(1+monthlyInterest)+monthlySaving_RRSP+monthlySaving_RRSP_DPSP;
    }
}
