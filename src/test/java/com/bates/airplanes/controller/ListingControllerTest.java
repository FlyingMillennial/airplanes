package com.bates.airplanes.controller;

import com.bates.airplanes.service.ListingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingControllerTest {


    @InjectMocks
    ListingController listingController;

    @Mock
    ListingService listingService;

    @Test
    public void getActiveListingsShouldCallFetchListings() {
        //Arrange
        when(listingService.fetchActiveListings()).thenReturn(new ArrayList<>());

        //Act
        listingController.getActiveListings();

        //Assert
        verify(listingService, times(1)).fetchActiveListings();
    }
}
