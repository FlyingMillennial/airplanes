package com.bates.airplanes.service;

import com.bates.airplanes.model.Listing;
import com.bates.airplanes.model.ScrapeSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapeServiceFactory {

    public List<ScrapeService> getAllScrapeServices() {
        List<ScrapeService> services = new ArrayList<>();
        for (ScrapeSource source : ScrapeSource.values()) {
            ScrapeService service = getScrapeService(source);
            services.add(service);
        }
        return services;
    }

    public ScrapeService getScrapeService(ScrapeSource source) {

        ScrapeService service = null;

        switch (source) {
            case TRADEAPLANE:
                service = new ScrapeService(
                    "https://www.trade-a-plane.com/search?category_level1=Single+Engine+Piston&make=PIPER&s-type=aircraft&s-page_size=96&s-sort_key=price&s-sort_order=asc",
                    "data-listing_id=\"(.*?)\"",
                    ScrapeSource.TRADEAPLANE
                );
                break;
            case CONTROLLER:
                service = new ScrapeService(
                    "https://www.controller.com/listings/search?Category=6&Manufacturer=PIPER",
                    "data-listing-id=\"(.*?)\"",
                    ScrapeSource.CONTROLLER
                );
                break;
            case BARNSTORMERS:
                service = new ScrapeService(
                        "https://www.barnstormers.com/category-21147-Piper.html",
                        "data-adid=\"(.*?)\"",
                        ScrapeSource.BARNSTORMERS
                );
                break;
            default:
                throw new RuntimeException(source + " is not a valid ScrapeService source.");
        }

        return service;
    }

}
