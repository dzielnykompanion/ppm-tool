package io.agileinteligence.ppmtool.repositories;

import io.agileinteligence.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentiferOrderByPriority(String id);

    ProjectTask findByProjectSequence(String sequence);
}
