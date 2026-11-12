package dev.voska.JobScheduler.repository;

import dev.voska.JobScheduler.entity.JobSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface JobScheduleRepository extends JpaRepository<JobSchedule, UUID> {

    @Query("""
            SELECT schedule FROM JobSchedule schedule
            WHERE schedule.nextExecutionAt <= :now
              AND schedule.job.jobState = dev.voska.JobScheduler.entity.enums.JobState.ACTIVE
            """)
    List<JobSchedule> findDueSchedules(@Param("now") Instant now);
}
