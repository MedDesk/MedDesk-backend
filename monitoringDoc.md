# Monitoring Stack Documentation

This document explains how to use the monitoring and management tools included in the MedDesk project. The stack consists of tools to check system health, view performance metrics, track request paths, and manage the database.

## Service Overview

| Service Name | Access URL | Purpose |
| :--- | :--- | :--- |
| Spring Boot App | http://localhost:8080 | The main backend API |
| pgAdmin | http://localhost:5050 | Database management interface |
| Zipkin | http://localhost:9411 | Distributed tracing (tracking requests) |
| Prometheus | http://localhost:9090 | Metric collection and storage |
| Grafana | http://localhost:3000 | Visual dashboards and graphs |

---

## 1. Spring Boot Actuator (The Data Source)

The application uses Spring Boot Actuator to expose internal information. These endpoints are used by Prometheus and for manual health checks.

**Key Endpoints:**
* **Health Check:** `http://localhost:8080/actuator/health`
    * Use this to see if the app and database are "UP".
* **Prometheus Metrics:** `http://localhost:8080/actuator/prometheus`
    * This is a raw text page containing all the numbers (CPU, memory, login counts) that Prometheus reads.

**Example:**
If the application is running correctly, visiting the health endpoint should return:
`{"status": "UP"}`

---

## 2. Prometheus (Data Collector)

Prometheus visits your application every 5 seconds to collect metrics. It stores these numbers over time so you can see trends.

**How to use it:**
1. Open `http://localhost:9090`.
2. Click on **Status** -> **Targets**.
3. Ensure the state for `meddesk-app-monitoring` is **UP**.

**Example Query:**
In the "Expression" box on the main page, type:
`http_server_requests_seconds_count`
This will show you how many total requests your API has handled.

---

## 3. Grafana (Visual Dashboard)

Grafana connects to Prometheus to turn numbers into visual charts.

**How to use it:**
1. Open `http://localhost:3000`.
2. Login with Username: `admin` / Password: `admin`.
3. Add a Data Source:
    * Select **Prometheus**.
    * Set the URL to `http://meddesk-prometheus:9090`.
    * Click **Save & Test**.
4. Create or Import a Dashboard to see graphs for CPU usage, memory, and HTTP errors.

---

## 4. Zipkin (Request Tracing)

Zipkin helps you find bottlenecks. It shows you exactly how long a request took to travel from the controller to the database.

**How to use it:**
1. Open `http://localhost:9411`.
2. Click **Run Query**.
3. You will see a list of recent requests.
4. Click on a specific request to see the "Trace".

**Example:**
If a "Get All Patients" request is slow, Zipkin will show a bar chart. If the database part of the bar is very long, you know you need to optimize your SQL query.

---

## 5. pgAdmin (Database Management)

This is a web-based tool to manage your PostgreSQL database without using the command line.

**How to use it:**
1. Open `http://localhost:5050`.
2. Login using the credentials defined in your `.env` file (`PGADMIN_DEFAULT_EMAIL` and `PGADMIN_DEFAULT_PASSWORD`).
3. Add a new server:
    * **Host:** `meddesk-postgres`
    * **Port:** `5432`
    * **Username:** (Your `POSTGRES_USER`)
    * **Password:** (Your `POSTGRES_PASSWORD`)

---

## Troubleshooting Commands

If a service is not working, use these terminal commands:

**Check if containers are running:**
`docker compose ps`

**View logs for the application:**
`docker compose logs app -f`

**Restart the monitoring stack:**
`docker compose restart prometheus grafana zipkin`

**Verify Prometheus configuration:**
Ensure the `prometheus.yml` file is in the same folder as your `docker-compose.yml` before starting the containers. The target must match the container name: `meddesk-app:8080`.