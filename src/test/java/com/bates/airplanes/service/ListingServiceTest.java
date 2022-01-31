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
        //Arrange service list
        ArrayList<ScrapeService> services = new ArrayList<>();
        services.add(scrapeService);
        services.add(scrapeService);

        //Arrange Mocks
        when(scrapeService.getWebListings()).thenReturn(new ArrayList<>());
        when(scrapeServiceFactory.getAllScrapeServices()).thenReturn(services);
        when(listingRepository.get()).thenReturn(new ArrayList<>());
        when(listingCalculationService.calculateExpiredSavedListings(any())).thenReturn(new ArrayList<>());
        when(listingCalculationService.calculateNewWebListings(any())).thenReturn(new ArrayList<>());

        //Act
        listingService.fetchActiveListings();

        //Assert
        verify(scrapeServiceFactory, times(1)).getAllScrapeServices();
        verify(listingRepository, times(2)).get();
        verify(listingCalculationService, times(1)).calculateNewWebListings(any());
        verify(listingCalculationService, times(1)).calculateExpiredSavedListings(any());
        verify(scrapeService, times(2)).getWebListings();

    }

}
