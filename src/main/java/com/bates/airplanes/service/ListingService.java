package com.bates.airplanes.service;

import com.bates.airplanes.model.FetchedListings;
import com.bates.airplanes.model.Listing;
import com.bates.airplanes.model.ScrapeSource;
import com.bates.airplanes.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService {

    @Autowired
    ScrapeServiceFactory scrapeServiceFactory;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ListingCalculationService listingCalculationService;

    @Transactional
    public List<Listing> fetchActiveListings() {
        FetchedListings fetchedListings = fetchListings();
        saveNewWebListings(fetchedListings);
        deleteExpiredSavedListings(fetchedListings);
        List<Listing> activeListings = listingRepository.get();
        return activeListings;
    }

    @Transactional
    private FetchedListings fetchListings() {
        // Scrape listings from the web from different sources
        List<Listing> webListings = new ArrayList<>();
        for (ScrapeSource source : ScrapeSource.values()) {
            ScrapeService service = scrapeServiceFactory.getScrapeService(source);
            List<Listing> webListingsFromCurrentSource = service.getWebListings();
            webListings.addAll(webListingsFromCurrentSource);
        }
        // Fetch listings from database
        List<Listing> databaseListings = listingRepository.get();

        //Create and return FetchedListings
        FetchedListings fetchedListings = new FetchedListings(webListings, databaseListings);
        return fetchedListings;
    }

    @Transactional
    private void saveNewWebListings(FetchedListings fetchedListings) {
        List<Listing> newWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);
        listingRepository.save(newWebListings);
    }

    @Transactional
    private void deleteExpiredSavedListings(FetchedListings fetchedListings) {
        List<Listing> expiredDatabaseListings = listingCalculationService.calculateExpiredSavedListings(fetchedListings);
        listingRepository.delete(expiredDatabaseListings);
    }

}
