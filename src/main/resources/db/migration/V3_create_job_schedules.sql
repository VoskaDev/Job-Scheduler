
CREATE TABLE job_schedules (
    id UUID PRIMARY KEY,
    job_id UUID NOT NULL UNIQUE,

    schedule_type VARCHAR(20) NOT NULL,
    execute_at TIMESTAMPTZ,
    interval_seconds BIGINT,
    cron_expression VARCHAR(255),
    timezone VARCHAR(255),

    next_execution_at TIMESTAMPTZ,
    max_repeat INTEGER,
    execution_count INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT fk_job_schedules_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id),

    CONSTRAINT chk_schedule_type
        CHECK (
            schedule_type IN (
                'ONCE',
                'FIXED_INTERVAL',
                'CRON'
            )
        ),

    CONSTRAINT chk_schedule_configuration
        CHECK (
            (
                schedule_type = 'ONCE'
                AND execute_at IS NOT NULL
            )
            OR
            (
                schedule_type = 'FIXED_INTERVAL'
                AND interval_seconds IS NOT NULL
                AND interval_seconds > 0
            )
            OR
            (
                schedule_type = 'CRON'
                AND cron_expression IS NOT NULL
                AND TRIM(cron_expression) <> ''
                AND timezone IS NOT NULL
                AND TRIM(timezone) <> ''
            )
        ),

    CONSTRAINT chk_schedule_max_repeat
        CHECK (max_repeat IS NULL OR max_repeat > 0),

    CONSTRAINT chk_schedule_execution_count
        CHECK (execution_count >= 0),

    CONSTRAINT chk_schedule_repeat_limit
        CHECK (
            max_repeat IS NULL
            OR execution_count <= max_repeat
        )
);

CREATE INDEX idx_schedules_next_execution
    ON job_schedules(next_execution_at)
    WHERE next_execution_at IS NOT NULL;
