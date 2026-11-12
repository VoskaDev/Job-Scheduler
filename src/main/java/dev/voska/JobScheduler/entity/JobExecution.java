package dev.voska.JobScheduler.entity;

import dev.voska.JobScheduler.entity.enums.JobExecutionState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_executions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false)
    private Instant scheduledAt;

    private Instant startedAt;

    private Instant finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobExecutionState jobExecutionState;

    @Column(nullable = false)
    private int attemptCount;

    @OneToMany(
            mappedBy = "jobExecution",
            fetch = FetchType.LAZY
    )
    @Setter(AccessLevel.NONE)
    private List<JobAttempt> jobAttempts = new ArrayList<>();
}
