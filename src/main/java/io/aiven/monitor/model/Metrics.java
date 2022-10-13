package io.aiven.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Metrics {
    private String siteUrl;
    private long responseTime;
    private int statusCode;
    private Boolean matchesRegex;
}
