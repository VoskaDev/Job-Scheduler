
CREATE TABLE jobs (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    target_url VARCHAR(2048) NOT NULL,
    http_method VARCHAR(20) NOT NULL,
    request_body TEXT,
    job_state VARCHAR(20) NOT NULL,
    max_retry INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    client_application_id UUID NOT NULL,

    CONSTRAINT fk_jobs_client_application
        FOREIGN KEY (client_application_id)
        REFERENCES client_applications(id),

    CONSTRAINT chk_jobs_max_retry
        CHECK (max_retry >= 0)
);

CREATE INDEX idx_jobs_client_application
    ON jobs(client_application_id);
