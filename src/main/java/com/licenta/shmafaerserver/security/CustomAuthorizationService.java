package com.licenta.shmafaerserver.security;

import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectID;
import com.licenta.shmafaerserver.exception.CustomExceptions.UnknownProjectRepoLink;
import com.licenta.shmafaerserver.model.AppUser;
import com.licenta.shmafaerserver.model.Project;
import com.licenta.shmafaerserver.model.enums.EProjectType;
import com.licenta.shmafaerserver.model.enums.ERole;
import com.licenta.shmafaerserver.repository.ProjectRepository;
import com.licenta.shmafaerserver.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("customAuthorizationService")
@RequiredArgsConstructor
public class CustomAuthorizationService {
    private final AuthenticationFacade authenticationFacade;
    private final ProjectRepository projectRepository;

    public boolean canUpdateProject(Long id) throws UnknownProjectID
    {
        String currentUserEmail = authenticationFacade.getAuthenticatedUser().getEmail();
        Project project = projectRepository.findById(id).
                orElseThrow(() -> new UnknownProjectID(Long.toString(id)));

        return isAdmin() || isOwner(project, currentUserEmail) || isCollaborator(project, currentUserEmail);

    }

    public boolean canSendToSH(String repoLink) throws UnknownProjectRepoLink
    {
        Project project = projectRepository.findByRepoLink(repoLink)
            .orElseThrow(() -> new UnknownProjectRepoLink("Unknown project" + repoLink));
        String currentUserEmail = authenticationFacade.getAuthenticatedUser().getEmail();

        if(Objects.equals(project.getProjectType().getName(),EProjectType.RESEARCH))
        {
            for(AppUser collaborator: project.getCollaborators())
            {
                if(Objects.equals(collaborator.getEmail(), currentUserEmail))
                {

                    return true;

                }
            }
        }

        if(Objects.equals(project.getCoordinator().getEmail(), currentUserEmail))
        {

            return true;

        }

        return false;

    }

    private boolean isCollaborator(Project project, String currentUserEmail)
    {

        return project.getCollaborators().stream().anyMatch(
                (appUser) -> appUser.getEmail().equals(currentUserEmail));

    }

    private boolean isOwner(Project project, String currentUserEmail)
    {

        return Objects.equals(project.getOwner().getEmail(), currentUserEmail);

    }

    private boolean isAdmin()
    {

        return authenticationFacade.getAuthenticatedUser().getAuthorities().stream()
                .anyMatch((grantedAuthority) -> grantedAuthority.getAuthority().equals(ERole.ADMIN.name()));

    }
}
