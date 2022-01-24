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

    @Test
    public void allMatchesShouldReturnNoData() {
        //Arrange
        List<Listing> newListingsFromWeb = new ArrayList<>();
        newListingsFromWeb.add(new Listing("123", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("456", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("789", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("012", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("345", "TAP", LocalDate.now()));

        List<Listing> fetchedListingsFromDatabase = new ArrayList<>();
        fetchedListingsFromDatabase.add(new Listing("123", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("456", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("789", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("012", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("345", "TAP", LocalDate.now()));

        fetchedListings = new FetchedListings(newListingsFromWeb, fetchedListingsFromDatabase);
        listingCalculationService = new ListingCalculationService();

        //Act
        List<Listing> uniqueNewWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);
        List<Listing> databaseListingsToBeDeleted = listingCalculationService.calculateExpiredSavedListings(fetchedListings);

        //Assert
        Assertions.assertEquals(uniqueNewWebListings.size(), 0);
        Assertions.assertEquals(databaseListingsToBeDeleted.size(), 0);
    }

    @Test
    public void noMatchesShouldReturnAllData() {
        //Arrange
        List<Listing> newListingsFromWeb = new ArrayList<>();
        newListingsFromWeb.add(new Listing("123", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("456", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("789", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("012", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("345", "TAP", LocalDate.now()));

        List<Listing> fetchedListingsFromDatabase = new ArrayList<>();
        fetchedListingsFromDatabase.add(new Listing("987", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("654", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("321", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("098", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("765", "TAP", LocalDate.now()));

        fetchedListings = new FetchedListings(newListingsFromWeb, fetchedListingsFromDatabase);
        listingCalculationService = new ListingCalculationService();

        //Act
        List<Listing> uniqueNewWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);
        List<Listing> databaseListingsToBeDeleted = listingCalculationService.calculateExpiredSavedListings(fetchedListings);

        //Assert
        Assertions.assertEquals(uniqueNewWebListings.get(0).getSourceId(), "123");
        Assertions.assertEquals(uniqueNewWebListings.get(1).getSourceId(), "456");
        Assertions.assertEquals(uniqueNewWebListings.get(2).getSourceId(), "789");
        Assertions.assertEquals(uniqueNewWebListings.get(3).getSourceId(), "012");
        Assertions.assertEquals(uniqueNewWebListings.get(4).getSourceId(), "345");
        Assertions.assertEquals(uniqueNewWebListings.size(), 5);

        Assertions.assertEquals(databaseListingsToBeDeleted.get(0).getSourceId(), "987");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(1).getSourceId(), "654");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(2).getSourceId(), "321");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(3).getSourceId(), "098");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(4).getSourceId(), "765");
        Assertions.assertEquals(databaseListingsToBeDeleted.size(), 5);
    }

    @Test
    public void someMatchesShouldReturnSomeData() {
        //Arrange
        List<Listing> newListingsFromWeb = new ArrayList<>();
        newListingsFromWeb.add(new Listing("123", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("456", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("789", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("012", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("345", "TAP", LocalDate.now()));

        List<Listing> fetchedListingsFromDatabase = new ArrayList<>();
        fetchedListingsFromDatabase.add(new Listing("123", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("654", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("789", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("012", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("765", "TAP", LocalDate.now()));

        fetchedListings = new FetchedListings(newListingsFromWeb, fetchedListingsFromDatabase);
        listingCalculationService = new ListingCalculationService();

        //Act
        List<Listing> uniqueNewWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);
        List<Listing> databaseListingsToBeDeleted = listingCalculationService.calculateExpiredSavedListings(fetchedListings);

        //Assert
        Assertions.assertEquals(uniqueNewWebListings.get(0).getSourceId(), "456");
        Assertions.assertEquals(uniqueNewWebListings.get(1).getSourceId(), "345");
        Assertions.assertEquals(uniqueNewWebListings.size(), 2);

        Assertions.assertEquals(databaseListingsToBeDeleted.get(0).getSourceId(), "654");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(1).getSourceId(), "765");
        Assertions.assertEquals(databaseListingsToBeDeleted.size(), 2);
    }

    @Test
    public void matchesFromMixedSourcesShouldNotCount() {
        //Arrange
        List<Listing> newListingsFromWeb = new ArrayList<>();
        newListingsFromWeb.add(new Listing("123", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("456", "TAP", LocalDate.now()));
        newListingsFromWeb.add(new Listing("789", "CONTROLLER", LocalDate.now()));
        newListingsFromWeb.add(new Listing("012", "CONTROLLER", LocalDate.now()));

        List<Listing> fetchedListingsFromDatabase = new ArrayList<>();
        fetchedListingsFromDatabase.add(new Listing("123", "CONTROLLER", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("456", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("789", "TAP", LocalDate.now()));
        fetchedListingsFromDatabase.add(new Listing("012", "CONTROLLER", LocalDate.now()));

        fetchedListings = new FetchedListings(newListingsFromWeb, fetchedListingsFromDatabase);
        listingCalculationService = new ListingCalculationService();

        //Act
        List<Listing> uniqueNewWebListings = listingCalculationService.calculateNewWebListings(fetchedListings);
        List<Listing> databaseListingsToBeDeleted = listingCalculationService.calculateExpiredSavedListings(fetchedListings);

        //Assert
        Assertions.assertEquals(uniqueNewWebListings.get(0).getSourceId(), "123");
        Assertions.assertEquals(uniqueNewWebListings.get(0).getSource(), "TAP");
        Assertions.assertEquals(uniqueNewWebListings.get(1).getSourceId(), "789");
        Assertions.assertEquals(uniqueNewWebListings.get(1).getSource(), "CONTROLLER");
        Assertions.assertEquals(uniqueNewWebListings.size(), 2);

        Assertions.assertEquals(databaseListingsToBeDeleted.get(0).getSourceId(), "123");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(0).getSource(), "CONTROLLER");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(1).getSourceId(), "789");
        Assertions.assertEquals(databaseListingsToBeDeleted.get(1).getSource(), "TAP");
        Assertions.assertEquals(databaseListingsToBeDeleted.size(), 2);
    }

}
