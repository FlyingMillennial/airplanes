package com.bates.airplanes.service;

import com.bates.airplanes.repository.ListingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {

    @InjectMocks
    ListingService listingService;

    @Mock
    ListingCalculationService listingCalculationService;

    @Mock
    ListingRepository listingRepository;

    @Mock
    ScrapeServiceFactory scrapeServiceFactory;

    @Mock
    ScrapeService scrapeService;

    @Test
    public void testServiceCallsCorrectOtherServices() {
        //Arrange Mocks
        when(scrapeService.getWebListings()).thenReturn(new ArrayList<>());
        when(scrapeServiceFactory.getScrapeService(any())).thenReturn(scrapeService);
        when(listingRepository.get()).thenReturn(new ArrayList<>());
        when(listingCalculationService.calculateExpiredSavedListings(any())).thenReturn(new ArrayList<>());
        when(listingCalculationService.calculateNewWebListings(any())).thenReturn(new ArrayList<>());

        //Act
        listingService.fetchActiveListings();

        //Assert
        verify(scrapeServiceFactory, atLeast(1)).getScrapeService(any());
        verify(listingRepository, times(2)).get();
        verify(listingCalculationService, times(1)).calculateNewWebListings(any());
        verify(listingCalculationService, times(1)).calculateExpiredSavedListings(any());
        verify(scrapeService, atLeast(1)).getWebListings();

    }

}
