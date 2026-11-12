
CREATE TABLE job_executions (
    id UUID PRIMARY KEY,
    job_id UUID NOT NULL,

    scheduled_at TIMESTAMPTZ NOT NULL,
    started_at TIMESTAMPTZ,
    finished_at TIMESTAMPTZ,

    job_execution_state VARCHAR(20) NOT NULL,
    attempt_count INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT fk_job_executions_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id),

    CONSTRAINT chk_execution_attempt_count
        CHECK (attempt_count >= 0),

    CONSTRAINT chk_execution_time
        CHECK (
            finished_at IS NULL
            OR (
                started_at IS NOT NULL
                AND finished_at >= started_at
            )
        ),

    CONSTRAINT uq_execution_job_scheduled
        UNIQUE (job_id, scheduled_at)
);

CREATE INDEX idx_executions_job_history
    ON job_executions(job_id, scheduled_at DESC);

CREATE INDEX idx_executions_state
    ON job_executions(job_execution_state);
