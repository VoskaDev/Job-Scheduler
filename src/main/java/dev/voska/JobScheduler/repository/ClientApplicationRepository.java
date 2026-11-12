package dev.voska.JobScheduler.repository;

import dev.voska.JobScheduler.entity.ClientApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientApplicationRepository extends JpaRepository<ClientApplication, UUID> {

    Optional<ClientApplication> findByApiKeyHash(String apiKeyHash);
}
