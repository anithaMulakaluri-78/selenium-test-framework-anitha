package com.selenium.listners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestResult;

public class HTMLReportGenerator {

    public void generateReport(ITestContext context) {

        Set<ITestResult> passedTests =
                context.getPassedTests().getAllResults();

        Set<ITestResult> failedTests =
                context.getFailedTests().getAllResults();

        Set<ITestResult> skippedTests =
                context.getSkippedTests().getAllResults();

        int passed = passedTests.size();
        int failed = failedTests.size();
        int skipped = skippedTests.size();

        int total = passed + failed + skipped;

        String suiteName =
                context.getSuite().getName();

        String buildNumber =
                System.getenv("BUILD_NUMBER");

        String buildUrl =
                System.getenv("BUILD_URL");

        StringBuilder html = new StringBuilder();

        html.append("""
                <!DOCTYPE html>
                <html>
                <head>

                    <meta charset="UTF-8">

                    <title>Automation Test Report</title>

                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

                    <style>

                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f6f9;
                            margin: 0;
                            padding: 30px;
                        }

                        .container {
                            max-width: 1400px;
                            margin: auto;
                        }

                        h1 {
                            text-align: center;
                            margin-bottom: 30px;
                        }

                        .info {
                            background: white;
                            padding: 20px;
                            border-radius: 10px;
                            margin-bottom: 25px;
                            box-shadow: 0 2px 8px #ddd;
                        }

                        .summary {
                            display: grid;
                            grid-template-columns:
                                repeat(4, 1fr);
                            gap: 20px;
                            margin-bottom: 30px;
                        }

                        .card {
                            background: white;
                            padding: 25px;
                            text-align: center;
                            border-radius: 10px;
                            box-shadow: 0 2px 8px #ddd;
                        }

                        .card h3 {
                            margin: 0 0 10px;
                        }

                        .number {
                            font-size: 36px;
                            font-weight: bold;
                        }

                        .chart-container {
                            background: white;
                            padding: 25px;
                            border-radius: 10px;
                            margin-bottom: 30px;
                            box-shadow: 0 2px 8px #ddd;
                        }

                        .chart {
                            max-width: 450px;
                            margin: auto;
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                            background: white;
                            box-shadow: 0 2px 8px #ddd;
                        }

                        th {
                            background: #343a40;
                            color: white;
                            padding: 14px;
                            text-align: left;
                        }

                        td {
                            padding: 12px;
                            border-bottom: 1px solid #ddd;
                        }

                        .PASS {
                            color: green;
                            font-weight: bold;
                        }

                        .FAIL {
                            color: red;
                            font-weight: bold;
                        }

                        .SKIP {
                            color: orange;
                            font-weight: bold;
                        }

                        .failure {
                            background: white;
                            margin-top: 30px;
                            padding: 20px;
                            border-radius: 10px;
                            box-shadow: 0 2px 8px #ddd;
                        }

                        .failure-item {
                            border-bottom: 1px solid #ddd;
                            padding: 15px 0;
                        }

                        .screenshot {
                            margin-top: 10px;
                        }

                        a {
                            color: #007bff;
                            text-decoration: none;
                            font-weight: bold;
                        }

                    </style>

                </head>

                <body>

                <div class="container">

                <h1>Automation Test Execution Report</h1>

                <div class="info">

                    <p>
                        <strong>Suite:</strong>
                """);

        html.append(suiteName);

        html.append("""
                    </p>

                    <p>
                        <strong>Build:</strong>
                """);

        html.append(
                buildNumber != null
                        ? buildNumber
                        : "Local Execution");

        html.append("""
                    </p>
                """);

        if (buildUrl != null) {

            html.append("""
                    <p>
                        <a href="
                """);

            html.append(buildUrl);

            html.append("""
                        ">
                        View Jenkins Build
                        </a>
                    </p>
                    """);
        }

        html.append("""
                </div>

                <div class="summary">

                    <div class="card">
                        <h3>Total</h3>
                        <div class="number">
                """);

        html.append(total);

        html.append("""
                        </div>
                    </div>

                    <div class="card">
                        <h3>Passed</h3>
                        <div class="number PASS">
                """);

        html.append(passed);

        html.append("""
                        </div>
                    </div>

                    <div class="card">
                        <h3>Failed</h3>
                        <div class="number FAIL">
                """);

        html.append(failed);

        html.append("""
                        </div>
                    </div>

                    <div class="card">
                        <h3>Skipped</h3>
                        <div class="number SKIP">
                """);

        html.append(skipped);

        html.append("""
                        </div>
                    </div>

                </div>

                <div class="chart-container">

                    <h2>Execution Summary</h2>

                    <div class="chart">

                        <canvas id="resultChart"></canvas>

                    </div>

                </div>

                <h2>Test Results</h2>

                <table>

                    <tr>
                        <th>Test Name</th>
                        <th>Class</th>
                        <th>Status</th>
                        <th>Error</th>
                        <th>Screenshot</th>
                    </tr>
                """);

        appendResults(
                html,
                passedTests,
                "PASS");

        appendResults(
                html,
                failedTests,
                "FAIL");

        appendResults(
                html,
                skippedTests,
                "SKIP");

        html.append("""
                </table>
                """);

        if (!failedTests.isEmpty()) {

            html.append("""
                    <div class="failure">

                    <h2>Failure Details</h2>
                """);

            for (ITestResult result : failedTests) {

                html.append("""
                        <div class="failure-item">

                        <h3>
                    """);

                html.append(result.getName());

                html.append("""
                        </h3>

                        <p>
                    """);

                if (result.getThrowable() != null) {

                    html.append(
                            escapeHtml(
                                result.getThrowable()
                                          .toString()));
                }

                html.append("""
                        </p>

                        </div>
                    """);
            }

            html.append("""
                    </div>
                    """);
        }

        html.append("""
                </div>

                <script>

                    const ctx =
                        document
                        .getElementById(
                            'resultChart'
                        );

                    new Chart(ctx, {

                        type: 'doughnut',

                        data: {

                            labels: [
                                'Passed',
                                'Failed',
                                'Skipped'
                            ],

                            datasets: [{

                                data: [
                """);

        html.append(passed);
        html.append(",");
        html.append(failed);
        html.append(",");
        html.append(skipped);

        html.append("""
                                ]

                            }]

                        },

                        options: {

                            responsive: true,

                            plugins: {

                                legend: {
                                    position: 'bottom'
                                }

                            }

                        }

                    });

                </script>

                </body>
                </html>
                """);

        writeReport(html.toString());
    }

    private void appendResults(
            StringBuilder html,
            Set<ITestResult> results,
            String status) {

        for (ITestResult result : results) {

            html.append("<tr>");

            html.append("<td>")
                    .append(result.getName())
                    .append("</td>");

            html.append("<td>")
                    .append(
                        result.getTestClass()
                              .getName())
                    .append("</td>");

            html.append("<td class='")
                    .append(status)
                    .append("'>")
                    .append(status)
                    .append("</td>");

            String error = "-";

            if (result.getThrowable() != null) {

                error =
                        result.getThrowable()
                              .getMessage();

                if (error == null) {
                    error =
                        result.getThrowable()
                              .toString();
                }
            }

            html.append("<td>")
                    .append(
                        escapeHtml(error))
                    .append("</td>");

            html.append("<td>");

            if ("FAIL".equals(status)) {

                String screenshot =
                        "screenshots/"
                        + result.getName()
                        + ".png";

                File screenshotFile =
                        new File(
                            "test-output/"
                            + screenshot);

                if (screenshotFile.exists()) {

                    html.append(
                        "<a href='")
                        .append(screenshot)
                        .append(
                            "'>View Screenshot</a>");
                } else {

                    html.append("-");
                }

            } else {

                html.append("-");
            }

            html.append("</td>");

            html.append("</tr>");
        }
    }

    private void writeReport(String html) {

        File directory =
                new File("test-output");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File report =
                new File(
                    directory,
                    "custom-emailable-report.html");

        try (FileWriter writer =
                     new FileWriter(report)) {

            writer.write(html);

            System.out.println(
                    "Custom HTML report generated: "
                    + report.getAbsolutePath());

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to generate HTML report",
                    e);
        }
    }

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}