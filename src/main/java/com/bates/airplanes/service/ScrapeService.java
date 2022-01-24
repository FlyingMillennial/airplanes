package com.bates.airplanes.service;

import com.bates.airplanes.model.Listing;
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

    private final String tapUrl = "https://www.trade-a-plane.com/search?category_level1=Single+Engine+Piston&make=PIPER&s-type=aircraft&s-page_size=96&s-sort_key=price&s-sort_order=asc";;

    private final String tradeAPlaneRegex = "data-listing_id=\"(.*?)\"";

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
            URI uri = URI.create(tapUrl);
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
        List<String> results = Pattern.compile(tradeAPlaneRegex)
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
            new Listing(sourceId, "TAP", LocalDate.now())
        ));
        return listings;
    }

}
