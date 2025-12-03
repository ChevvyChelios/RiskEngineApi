# 🏦 Early Risk Signal System (ERSS)

### A Microservice for Proactive Credit Delinquency Detection

**Status:** Prototype Complete  
**Tech Stack:** Java 25, Spring Boot, Docker, HTML5/JS  

---

## 📖 Overview
The **Early Risk Signal System (ERSS)** is a lightweight "Decision Engine" designed to identify credit card customers who are at risk of delinquency *before* they miss a payment. 

Unlike traditional models that rely on lag indicators (like Days Past Due), this engine analyzes **Lead Indicators**—specifically behavioral shifts in spending and cash utilization—to flag high-risk accounts in real-time.

## 🚀 Key Features
* **Microservice Architecture:** Stateless REST API built with Spring Boot.
* **Rule-Based Logic:** Transparent "White-box" decisioning (no black-box AI).
* **Dockerized:** Container-ready for easy deployment on any infrastructure.
* **Frontend Dashboard:** Simple web interface for Operations teams to upload batch files.

## ⚙️ Tech Stack
* **Language:** Java 25
* **Framework:** Spring Boot 4.0.0
* **Containerization:** Docker (Alpine Linux base)
* **Data Processing:** OpenCSV Library
* **Frontend:** HTML5, CSS3, Vanila JavaScript

---

## 🛠️ How to Run (The Easy Way: Docker)

### **Prerequisite**

You must have **Docker** installed on your machine

### **Step 1: Build the Image**

Open your terminal in the project root directory and run:

    docker build -t risk-api .

### **Step 2: Run the Container**

Start the API on port 8080: 

    docker run -p 8080:8080 risk-api

### **Step 3: Access the Dashboard**

Open the `client.html` file in your browser (or use "Live Server" in VS Code).

---

## 🔌 API Documentation
The system exposes a single POST endpoint for batch processing.

**Endpoint:** `POST /api/risk/analyze`  
**Payload:** Multipart File (`.csv`)

### **Input CSV Format**
The input file must follow this specific column structure (headers are required):
```
Customer ID, Credit Limit, Utilisation %, Avg Payment Ratio, Min Due Paid Frequency, Merchant Mix Index, Cash Withdrawal %, Recent Spend Change %
C001, 165000, 12, 32, 66, 0.73, 12, -21
```
### **Output JSON Response**

The API returns a JSON array of customer object enriched with a `riskFlag`:
```
[
    {
        "customerId": "C001",
        "utilisation": 12.0,
        "avgPaymentRatio": 32.0,
        "minDueFreq": 66.0,
        "cashWithdrawal": 12.0,
        "spendChange": -21.0,
        "riskFlag": "RED"
    },
    {
        "customerId": "C002",
        "utilisation": 10.0,
        ...
        "riskFlag": "AMBER"
    }
]
```
---
## 📂 Project Structure
```
├──riskevaluate
|    ├── src/main/java        # Spring Boot Source Code (RiskEngineService.java)
|    ├── Dockerfile           # Docker Build Instructions
|    └── pom.xml              # Maven Dependencies
├── client.html
└── README.md            # Documentation
```
