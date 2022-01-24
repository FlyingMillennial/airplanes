package com.bates.airplanes.service;

import com.bates.airplanes.model.FetchedListings;
import com.bates.airplanes.model.Listing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListingCalculationServiceTest {

    private static ListingCalculationService listingCalculationService;
    private static FetchedListings fetchedListings;

    @BeforeAll
    public static void init() {
        //Arrange
        List<Listing> newListingsFromWeb = new ArrayList<>();
            newListingsFromWeb.add(new Listing("123", "TAP", LocalDate.now()));
            newListingsFromWeb.add(new Listing("456", "TAP", LocalDate.now()));

        List<Listing> fetchedListingsFromDatabase = new ArrayList<>();
            fetchedListingsFromDatabase.add(new Listing("123", "TAP", LocalDate.now()));
            fetchedListingsFromDatabase.add(new Listing("789", "TAP", LocalDate.now()));

        fetchedListings = new FetchedListings(newListingsFromWeb, fetchedListingsFromDatabase);
        listingCalculationService = new ListingCalculationService();
    }

    @Test
    public void calculateNewWebListingsShouldReturnListingsNotInDatabaseList() {
        //Act
        List<Listing> uniqueNewWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);

        //Assert
        Assertions.assertEquals(uniqueNewWebListings.get(0).getSourceId(), "456");
    }

    @Test
    public void calculateExpiredSavedListingsShouldReturnListingsNotInNewListingsFromWeb() {
        //Act
        List<Listing> expiredListingsFromDatabase = listingCalculationService.calculateExpiredSavedListings(fetchedListings);

        //Assert
        Assertions.assertEquals(expiredListingsFromDatabase.get(0).getSourceId(), "789");
    }

}
