package io.aiven.monitor.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="metrics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsEntity {
    @Id
    private String id;
    @Column(name="url", nullable=false)
    private String siteUrl;
    @Column(name="response_time", nullable=false)
    private long responseTime;
    @Column(name="status_code", nullable=false)
    private int statusCode;
    @Column(name="matches_regex", nullable=true)
    private Boolean matchesRegex;
}
