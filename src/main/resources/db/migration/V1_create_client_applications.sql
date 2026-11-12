CREATE TABLE client_applications (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    api_key_hash VARCHAR(255) NOT NULL UNIQUE,
    application_state VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    last_used_at TIMESTAMPTZ
);


ALTER TABLE client_applications
ADD CONSTRAINT chk_application_state
CHECK (application_state IN ('ACTIVE', 'DISABLED'));