package com.licenta.shmafaerserver.service.softwareheritage;

import com.licenta.shmafaerserver.dto.github.SearchResultSimplifiedDTO;
import com.licenta.shmafaerserver.dto.response.DownloadResponseDTO;
import com.licenta.shmafaerserver.dto.softwareheritage.*;
import com.licenta.shmafaerserver.exception.CustomExceptions.SoftwareHeritageCommunicationException;
import com.licenta.shmafaerserver.model.ProjectStatus;
import com.licenta.shmafaerserver.model.enums.EProjectStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Objects;

@Service @Transactional @RequiredArgsConstructor @Slf4j
public class ArchivingService {
    private final static String BASE_SCHEME = "swh:1:";
    private final static String BASE_API = "https://archive.softwareheritage.org/api/1/";

    private final static String SAVE_ROUTE = BASE_API + "origin/save/git/url/";
    private final RestTemplate restTemplate;

    public EProjectStatus sendArchivingRequest(String projectRepoLink) throws SoftwareHeritageCommunicationException
    {
        URI uri = URI.create(SAVE_ROUTE + projectRepoLink + "/");
        ResponseEntity<SaveResponse> response;
        SaveResponse respBody;

        try
        {
            response = restTemplate.postForEntity(uri, null, SaveResponse.class);
            respBody = response.getBody();

            if((respBody != null) && (Objects.equals(respBody.getSave_request_status(), "accepted")))
            {

                return convertStatus(respBody.getSave_task_status());

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong!");
        }
        catch (HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getResponseBodyAsString());
        }
    }

    public EProjectStatus getArchivingStatus(String projectRepoLink) throws SoftwareHeritageCommunicationException
    {
        URI uri = URI.create(SAVE_ROUTE + projectRepoLink + "/");
        String status;

        try
        {
            ResponseEntity<SaveResponse[]> response = restTemplate.getForEntity(
                    uri, SaveResponse[].class
            );
            SaveResponse[] respBody = response.getBody();

            if((respBody != null) && (respBody.length != 0))
            {
                status = respBody[0].getSave_task_status();

               return convertStatus(status);

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong!");
        }
        catch (HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getResponseBodyAsString());
        }
    }

    private EProjectStatus convertStatus(String status)
    {
        if(Objects.equals(status, "succeeded"))
        {

            return EProjectStatus.SUCCEEDED;

        }

        else

        {

            if (Objects.equals(status, "not created") ||
                    (Objects.equals(status, "not yet scheduled") ||
                            (Objects.equals(status, "scheduled"))))
            {

                return EProjectStatus.PENDING;

            }

            if (Objects.equals(status, "failed"))
            {

                return EProjectStatus.FAILED;

            }
        }

        return null;

    }

    public DownloadResponseDTO getDownloadInfo(String projectRepoLink) throws SoftwareHeritageCommunicationException
    {
        ResponseEntity<DownloadResponse> response;
        String snapshotID = getSnapshotID(projectRepoLink);
        String headTargetUrl = getHEADRevisionURL(snapshotID);
        String directoryID = getDirectoryID(headTargetUrl);
        URI uri = URI.create(BASE_API + "vault/flat/" + BASE_SCHEME + "dir:" + directoryID + "/");

        try
        {
            response = restTemplate.postForEntity(uri, null,  DownloadResponse.class);

            if(response.getBody() != null)
            {

                return new DownloadResponseDTO(response.getBody().getStatus(), response.getBody().getFetch_url());

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong");
        }
        catch(HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getMessage());
        }
    }

    public boolean isArchivedBySH(String origin)
    {
        URI uri = URI.create(BASE_API + "origin/" + origin + "/get");

        try
        {
            restTemplate.getForObject(uri, String.class);
            log.info(Thread.currentThread().getName() + " " + origin + " -> FOUND ON SH");

            return true;

        }
        catch(HttpStatusCodeException e)
        {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
            {

                return false;

            }

            log.error("Could not check if github repo is archived " + origin);

            return false;

        }

    }

    private String getSnapshotID(String projectRepoLink) throws SoftwareHeritageCommunicationException
    {
        URI uri = URI.create(BASE_API + "origin/" + projectRepoLink + "/visit/latest?require_snapshot=true");
        ResponseEntity<LatestVisitDTO> response;

        try
        {
            response = restTemplate.getForEntity(uri, LatestVisitDTO.class);
            if(response.getBody() != null)
            {

                return response.getBody().getSnapshot();

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong");
        }
        catch(HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getMessage());
        }
    }

    private String getHEADRevisionURL(String snapshotID) throws SoftwareHeritageCommunicationException
    {
        URI uri = URI.create(BASE_API + "snapshot/" + snapshotID);
        ResponseEntity<SnapshotDetailsDTO> response;

        try
        {
            response = restTemplate.getForEntity(uri, SnapshotDetailsDTO.class);
            if(response.getBody() != null)
            {

                return response.getBody().getBranches().get("HEAD").getTarget_url();

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong");
        }
        catch(HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getMessage());
        }
    }

    private String getDirectoryID(String headTargetURL) throws SoftwareHeritageCommunicationException
    {
        URI uri = URI.create(headTargetURL);
        ResponseEntity<DirectoryDataDTO> response;

        try
        {
            response = restTemplate.getForEntity(uri, DirectoryDataDTO.class);
            if(response.getBody() != null)
            {

                return response.getBody().getDirectory();

            }
            throw new SoftwareHeritageCommunicationException("Something went wrong");
        }
        catch(HttpStatusCodeException e)
        {
            throw new SoftwareHeritageCommunicationException(e.getMessage());
        }
    }
}
