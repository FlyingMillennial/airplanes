package com.bates.airplanes.model;

import java.util.List;

public class FetchedListings {

    private List<Listing> newListingsFromWeb;

    private List<Listing> existingListingsFromDatabase;

    public FetchedListings(List<Listing> newListingsFromWeb, List<Listing> existingListingsFromDatabase) {
        this.newListingsFromWeb = newListingsFromWeb;
        this.existingListingsFromDatabase = existingListingsFromDatabase;
    }

    public List<Listing> getNewListingsFromWeb() {
        return newListingsFromWeb;
    }

    public void setNewListingsFromWeb(List<Listing> newListingsFromWeb) {
        this.newListingsFromWeb = newListingsFromWeb;
    }

    public List<Listing> getExistingListingsFromDatabase() {
        return existingListingsFromDatabase;
    }

    public void setExistingListingsFromDatabase(List<Listing> existingListingsFromDatabase) {
        this.existingListingsFromDatabase = existingListingsFromDatabase;
    }
}
