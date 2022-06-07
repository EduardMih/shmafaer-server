package com.licenta.shmafaerserver.service.github;

import com.licenta.shmafaerserver.dto.github.SearchResultSimplifiedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubSearchService {
    private static final String BASE_API = "https://api.github.com/search/repositories?q={q}&sort=stars&order=desc&per_page={s}&page={p}";

    private final RestTemplate restTemplate;

    public SearchResultSimplifiedDTO searchRepos(String searchText, Integer per_page, Integer page)
    {
        ResponseEntity<SearchResultSimplifiedDTO> response;

        try
        {
            response = restTemplate.getForEntity(BASE_API, SearchResultSimplifiedDTO.class, searchText, per_page, page);

            return response.getBody();

        }
        catch(HttpStatusCodeException e)
        {
            log.info("Could not fetch github search result " + e.getMessage());

            return null;

        }
    }
}
