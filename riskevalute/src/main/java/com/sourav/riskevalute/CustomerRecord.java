package com.sourav.riskevalute;

public class CustomerRecord {
    // Input Fields
    private String customerId;
    private double utilisation;
    private double avgPaymentRatio;
    private double minDueFreq;
    private double cashWithdrawal;
    private double spendChange;

    // Output Field
    private String riskFlag;

    // Constructor
    public CustomerRecord(String customerId, double utilisation, double avgPaymentRatio, 
                          double minDueFreq, double cashWithdrawal, double spendChange) {
        this.customerId = customerId;
        this.utilisation = utilisation;
        this.avgPaymentRatio = avgPaymentRatio;
        this.minDueFreq = minDueFreq;
        this.cashWithdrawal = cashWithdrawal;
        this.spendChange = spendChange;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public double getUtilisation() { return utilisation; }
    public double getAvgPaymentRatio() { return avgPaymentRatio; }
    public double getMinDueFreq() { return minDueFreq; }
    public double getCashWithdrawal() { return cashWithdrawal; }
    public double getSpendChange() { return spendChange; }
    
    public String getRiskFlag() { return riskFlag; }
    public void setRiskFlag(String riskFlag) { this.riskFlag = riskFlag; }
}