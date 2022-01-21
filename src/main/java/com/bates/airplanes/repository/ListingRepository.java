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

    public List<Listing> get() {
        Query query = entityManager.createQuery("SELECT l from Listing l");
        List<Listing> result = query.getResultList();
        return result;
    }

    public List<Listing> save(List<Listing> listings) {
        listings.stream().forEach(listing -> save(listing));
        return listings;
    }

    private Listing save(Listing listing) {
        entityManager.persist(listing);
        return listing;
    }

    public void delete(List<Listing> listings) {
        listings.stream().forEach(listing -> entityManager.remove(listing));
    }

}
