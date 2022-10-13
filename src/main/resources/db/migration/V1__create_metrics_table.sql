create table metrics (
    id VARCHAR(255) PRIMARY KEY,
    url text not null,
    response_time bigint not null,
    status_code smallint not null
);

CREATE INDEX idx_url ON  metrics (url);