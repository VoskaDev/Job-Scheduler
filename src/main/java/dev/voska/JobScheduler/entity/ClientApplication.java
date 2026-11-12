package dev.voska.JobScheduler.entity;

import dev.voska.JobScheduler.entity.enums.ApplicationState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client_applications")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String apiKeyHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationState applicationState;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    private Instant lastUsedAt;

    @OneToMany(
            mappedBy = "clientApplication",
            fetch = FetchType.LAZY
    )
    @Setter(AccessLevel.NONE)
    private List<Job> jobs = new ArrayList<>();
}
