package io.agileinteligence.ppmtool.services;

import io.agileinteligence.ppmtool.domain.Backlog;

import io.agileinteligence.ppmtool.domain.Project;
import io.agileinteligence.ppmtool.domain.ProjectTask;

import io.agileinteligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileinteligence.ppmtool.repositories.BacklogRepository;
import io.agileinteligence.ppmtool.repositories.ProjectRepository;
import io.agileinteligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class ProjectTaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifer, ProjectTask projectTask, String username) {

        try {
            // PTs to be added to a specific project, not null project, BL exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifer, username).getBacklog();
                    //backlogRepository.findByProjectIdentifer(projectIdentifer);

            // set the backlog to project task
            projectTask.setBacklog(backlog);
            // we want our project sequence to be like this: IDPRO-1 IDPRO-2 ..100 101
            Integer BacklogSequence = backlog.getPTSequence();
            // update the BL SEQUENCE
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);

            // Add sequence to project task
            projectTask.setProjectSequence(projectIdentifer+"-"+BacklogSequence);
            projectTask.setProjectIdentifer(projectIdentifer);

            // INITIAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0){
                projectTask.setPriority(3); // if you dont even care about priority -  it is low
            }
            // INITIAL status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
                    }

            } catch (Exception e){
                throw new ProjectNotFoundException("Project not found");
            }

            return projectTaskRepository.save(projectTask);
        }

    public Iterable<ProjectTask>findBacklogById(String projectIdentifer, String username){

        projectService.findProjectByIdentifier(projectIdentifer, username);

        return projectTaskRepository.findByProjectIdentiferOrderByPriority(projectIdentifer);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        // make sure we are searching on rexisting backlog
        projectService.findProjectByIdentifier(backlog_id, username);
        /*
        Backlog backlog = backlogRepository.findByProjectIdentifer(backlog_id);
        if(backlog == null) {
            throw new ProjectNotFoundException("Project with id: " + backlog_id + " doesnt exist");
        }
        */ // no longer needed as we have validation in projectService.findProjectByIdentifer

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task : " + pt_id + " not found.");
        }

        // make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifer().equals(backlog_id)){
            throw new ProjectNotFoundException("Project task: " + pt_id + " doesnt exist in project: " + backlog_id + ".");
        }

        return projectTaskRepository.findByProjectSequence(pt_id);
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
       ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }
}
