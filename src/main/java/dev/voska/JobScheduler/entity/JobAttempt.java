package dev.voska.JobScheduler.entity;

import dev.voska.JobScheduler.entity.enums.JobAttemptState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "job_attempts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_execution_id", nullable = false)
    private JobExecution jobExecution;

    @Column(nullable = false)
    private int attemptNumber;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobAttemptState jobAttemptState;

    private Integer httpStatusCode;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}
