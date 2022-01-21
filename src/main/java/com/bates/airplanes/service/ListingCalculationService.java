package com.bates.airplanes.service;

import com.bates.airplanes.model.FetchedListings;
import com.bates.airplanes.model.Listing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingCalculationService {

    public List<Listing> calculateNewWebListings(FetchedListings fetchedListings) {
        List<Listing> originalListingsFromWeb = findListingsInListOneNotInListTwoBySourceId(
                fetchedListings.getNewListingsFromWeb(),
                fetchedListings.getExistingListingsFromDatabase()
        );
        return originalListingsFromWeb;
    }

    public List<Listing> calculateExpiredSavedListings(FetchedListings fetchedListings) {
        List<Listing> staleListingsFromDatabase = findListingsInListOneNotInListTwoBySourceId(
                fetchedListings.getExistingListingsFromDatabase(),
                fetchedListings.getNewListingsFromWeb()
        );
        return staleListingsFromDatabase;
    }

    private List<Listing> findListingsInListOneNotInListTwoBySourceId(List<Listing> listOne, List<Listing> listTwo) {
        List<Listing> listingsFromListOneNotFoundInListTwo = new ArrayList<>();

        //TODO: Implement binary search; this is exponential and will produce bad performance at higher volumes
        for(int listOneIndex = 0; listOneIndex < listOne.size(); listOneIndex++) {
            Boolean listOneListingNotFoundInListTwo = true;
            Listing listOneListing = listOne.get(listOneIndex);
            String listOneListingId = listOneListing.getSourceId();
            for(int listTwoIndex = 0; listTwoIndex < listTwo.size(); listTwoIndex++) {
                String listTwoListingId = listTwo.get(listTwoIndex).getSourceId();
                if (listOneListingId == listTwoListingId) {
                    listOneListingNotFoundInListTwo = false;
                    break;
                }
            }
            if (listOneListingNotFoundInListTwo) {
                listingsFromListOneNotFoundInListTwo.add(listOneListing);
            }
        }

        return listingsFromListOneNotFoundInListTwo;
    }

}
