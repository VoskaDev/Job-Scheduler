
CREATE TABLE job_attempts (
    id UUID PRIMARY KEY,
    job_execution_id UUID NOT NULL,

    attempt_number INTEGER NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    finished_at TIMESTAMPTZ,

    job_attempt_state VARCHAR(20) NOT NULL,
    http_status_code INTEGER,
    error_message TEXT,

    CONSTRAINT fk_job_attempts_execution
        FOREIGN KEY (job_execution_id)
        REFERENCES job_executions(id),

    CONSTRAINT chk_attempt_number
        CHECK (attempt_number > 0),

    CONSTRAINT chk_attempt_time
        CHECK (
            finished_at IS NULL
            OR finished_at >= started_at
        ),

    CONSTRAINT chk_attempt_http_status
        CHECK (
            http_status_code IS NULL
            OR http_status_code BETWEEN 100 AND 599
        ),

    CONSTRAINT uq_execution_attempt_number
        UNIQUE (job_execution_id, attempt_number)
);
