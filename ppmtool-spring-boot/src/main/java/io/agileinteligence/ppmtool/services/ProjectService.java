package io.agileinteligence.ppmtool.services;

import io.agileinteligence.ppmtool.domain.Backlog;
import io.agileinteligence.ppmtool.domain.Project;
import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.exceptions.ProjectIdException;
import io.agileinteligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileinteligence.ppmtool.repositories.BacklogRepository;
import io.agileinteligence.ppmtool.repositories.ProjectRepository;
import io.agileinteligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;


    public Project saveOrUpdateProject(Project project, String username) {


        if ( project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifer((project.getProjectIdentifer()));

            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifer() +"' cannot be updated becouse it doesn't exist");
            }
        }


        try {

            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifer(project.getProjectIdentifer().toUpperCase());

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifer(project.getProjectIdentifer().toUpperCase());
            }

            if(project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifer(project.getProjectIdentifer().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID: " + project.getProjectIdentifer().toUpperCase() + " already exist in database!");
        }
    }

    public Project findProjectByIdentifier(String projectId, String username) {

        Project project = projectRepository.findByProjectIdentifer(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID: " + projectId + " does not exist!");
        }

        if (!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }



        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {


        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}
