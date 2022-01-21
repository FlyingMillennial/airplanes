package com.bates.airplanes.repository;

import com.bates.airplanes.model.Listing;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ListingRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Listing> getActiveListings() {
        Query query = entityManager.createQuery("SELECT l from Listing l");
        List<Listing> result = query.getResultList();
        return result;
    }

    public Listing saveListing(Listing listing) {
        entityManager.persist(listing);
        return listing;
    }

}
