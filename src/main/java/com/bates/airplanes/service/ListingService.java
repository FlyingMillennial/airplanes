package com.bates.airplanes.service;

import com.bates.airplanes.model.FetchedListings;
import com.bates.airplanes.model.Listing;
import com.bates.airplanes.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ScrapeService scrapeService;

    @Autowired
    ListingCalculationService listingCalculationService;

    //TODO: Delete this
    @Transactional
    public List<Listing> getListings() {
        List<Listing> result = listingRepository.get();
        return result;
    }

    //----------------------------------------//

    @Transactional
    public List<Listing> fetchActiveListings() {

        //TODO: Figure out why it's deleting from the database and reinserting everything

        FetchedListings fetchedListings = fetchListings();
        saveNewWebListings(fetchedListings);
        deleteExpiredSavedListings(fetchedListings);
        List<Listing> activeListings = listingRepository.get();
        return activeListings;
    }

    @Transactional
    private FetchedListings fetchListings() {
        List<Listing> webListings = scrapeService.getWebListings();
        List<Listing> databaseListings = listingRepository.get();
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
