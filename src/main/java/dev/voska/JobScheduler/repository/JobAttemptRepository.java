package dev.voska.JobScheduler.repository;

import dev.voska.JobScheduler.entity.JobAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobAttemptRepository extends JpaRepository<JobAttempt, UUID> {
}
