package com.bates.airplanes.service;

import com.bates.airplanes.model.FetchedListings;
import com.bates.airplanes.model.Listing;
import com.bates.airplanes.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    //TODO: Delete this
    @Transactional
    public List<Listing> getListings() {
        List<Listing> result = listingRepository.get();
        return result;
    }

    //----------------------------------------//

    public List<Listing> fetchActiveListings() {
        return null;
    }

    private FetchedListings fetchedListings() {
        return null;
    }

    private List<Listing> saveNewWebListings(FetchedListings allListings) {
        return null;
    }

    private void deleteExpiredSavedListings(FetchedListings allListings) {

    }






}
