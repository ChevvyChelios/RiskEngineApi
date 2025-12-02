package com.sourav.riskevalute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@CrossOrigin(origins = "*")   // for CORS
public class RiskController {

    @Autowired
    private RiskEngineService riskService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzePortfolio(@RequestParam("file") MultipartFile file) {
        // 1. Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        // 2. Check extension
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Please upload a valid CSV file.");
        }

        try {
            List<CustomerRecord> results = riskService.processFile(file);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing CSV: " + e.getMessage());
        }
    }
    // @PostMapping("/analyze")
    // public ResponseEntity<?> analyzePortfolio(@RequestParam("file") MultipartFile
    // file) {
    // // Check for Excel extension
    // if (!file.getOriginalFilename().endsWith(".xlsx")) {
    // return ResponseEntity.badRequest().body("Please upload a valid Excel (.xlsx)
    // file.");
    // }

    // try {
    // // Call processFile method
    // List<CustomerRecord> results = riskService.processFile(file);
    // return ResponseEntity.ok(results);
    // } catch (Exception e) {
    // return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
    // }
}