package com.bates.airplanes.service;

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

    @Transactional
    public List<Listing> getActiveListings() {
        List<Listing> result = listingRepository.getActiveListings();
        return result;
    }

    @Transactional
    public Listing saveListing(Listing listing) {
        Listing result = listingRepository.saveListing(listing);
        return result;
    }

    public List<String> getActiveListingIds() {
        List<String> result = getActiveListings()
                .stream()
                .map(Listing -> Listing.getSourceId())
                .collect(Collectors.toList());
        return result;
    }

}
