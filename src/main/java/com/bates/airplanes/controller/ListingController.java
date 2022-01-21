package com.bates.airplanes.controller;

import com.bates.airplanes.model.Listing;
import com.bates.airplanes.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping("/fetch-listings")
    public List<Listing> getActiveListings() {
        List<Listing> result = listingService.getActiveListings();
        return result;
    }

}
