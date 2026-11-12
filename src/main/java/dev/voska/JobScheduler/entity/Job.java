
package dev.voska.JobScheduler.entity;

import dev.voska.JobScheduler.entity.enums.HttpMethod;
import dev.voska.JobScheduler.entity.enums.JobState;
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
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 2048)
    private String targetUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HttpMethod httpMethod;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobState jobState;

    @Column(nullable = false)
    private int maxRetry;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_application_id", nullable = false)
    private ClientApplication clientApplication;

    @OneToOne(
            mappedBy = "job",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private JobSchedule jobSchedule;

    @OneToMany(
            mappedBy = "job",
            fetch = FetchType.LAZY
    )
    @Setter(AccessLevel.NONE)
    private List<JobExecution> jobExecutions = new ArrayList<>();
}
