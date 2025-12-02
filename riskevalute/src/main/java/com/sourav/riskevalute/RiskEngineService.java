package com.sourav.riskevalute;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiskEngineService {

    public List<CustomerRecord> processFile(MultipartFile file) throws IOException, CsvValidationException {
        List<CustomerRecord> analyzedData = new ArrayList<>();

        // Use BufferedReader and CSVReader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {

            // Read header row (and ignore it)
            String[] header = csvReader.readNext();

            String[] record;
            while ((record = csvReader.readNext()) != null) {
                // CSV Record Structure:
                // [0]CustID, [1]Limit, [2]Util, [3]PayRatio, [4]MinDue, [5]Mix, [6]Cash, [7]SpendChange
                
                // Safe Parsing Logic
                String custId = record[0];
                double utilisation = parseSafeDouble(record[2]);
                double paymentRatio = parseSafeDouble(record[3]);
                double minDueFreq = parseSafeDouble(record[4]);
                double cashWithdrawal = parseSafeDouble(record[6]);
                double spendChange = parseSafeDouble(record[7]);

                CustomerRecord customer = new CustomerRecord(
                    custId, utilisation, paymentRatio, minDueFreq, cashWithdrawal, spendChange
                );

                customer.setRiskFlag(calculateRisk(customer));
                analyzedData.add(customer);
            }
        }
        return analyzedData;
    }

    // Helper to handle dirty CSV data (e.g. "12%", "NA", or empty strings)
    private double parseSafeDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            // Remove common Excel formatting artifacts like % or ,
            String cleanVal = value.replace("%", "").replace(",", "").trim();
            return Double.parseDouble(cleanVal);
        } catch (NumberFormatException e) {
            return 0.0; // Default to 0 if data is bad
        }
    }

    private String calculateRisk(CustomerRecord c) {
        // --- FORMULA ---
        // RED: Spend < -20 AND Cash > 8 OR Pay < 40 AND MinDue > 50
        if (((c.getSpendChange() < -20) && (c.getCashWithdrawal() > 8)) || 
            ((c.getAvgPaymentRatio() < 40) && (c.getMinDueFreq() > 50))) {
            return "RED";
        } 
        // AMBER: Cash > 15 OR Util > 80
        else if ((c.getCashWithdrawal() > 15) || (c.getUtilisation() > 80)) {
            return "AMBER";
        }
        return "GREEN";
    }
}