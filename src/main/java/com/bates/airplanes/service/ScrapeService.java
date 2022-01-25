package com.bates.airplanes.service;

import com.bates.airplanes.model.Listing;
import com.bates.airplanes.model.ScrapeSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ScrapeService {

    private String sourceUrl;
    private String sourceRegex;
    private ScrapeSource scrapeSource;

    //Needed to keep spring happy...
    public ScrapeService() {}

    public ScrapeService(String sourceUrl, String sourceRegex, ScrapeSource scrapeSource) {
        this.sourceUrl = sourceUrl;
        this.sourceRegex = sourceRegex;
        this.scrapeSource = scrapeSource;
    }

    public List<Listing> getWebListings() {
        //New up httpClient here to facilitate testing
        HttpClient httpClient = HttpClient.newHttpClient();
        return getWebListings(httpClient);
    }

    protected List<Listing> getWebListings(HttpClient httpClient) {
        String webPageHtml = getWebPage(httpClient);
        List<String> ids = getListingIds(webPageHtml);
        return convertIdsToListings(ids);
    }

    private String getWebPage(HttpClient httpClient) {
        try {
            URI uri = URI.create(sourceUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String webPage = response.body();
            return webPage;
        } catch(InterruptedException e) {
            throw new RuntimeException();
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

    private List<String> getListingIds(String htmlFromWebListings) {
        List<String> results = Pattern.compile(sourceRegex)
                .matcher(htmlFromWebListings)
                .results()
                .map(MatchResult -> MatchResult.group(1) )
                .distinct()
                .collect(Collectors.toList());
        return results;
    }

    private List<Listing> convertIdsToListings(List<String> ids) {
        List<Listing> listings = new ArrayList<>();
        ids.stream().forEach(sourceId -> listings.add(
            new Listing(sourceId, scrapeSource, LocalDate.now())
        ));
        return listings;
    }

}
