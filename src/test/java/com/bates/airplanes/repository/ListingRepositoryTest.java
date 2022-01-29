package com.bates.airplanes.repository;

import com.bates.airplanes.model.Listing;
import com.bates.airplanes.repository.ListingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingRepositoryTest {

    @InjectMocks
    ListingRepository listingRepository;

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    @Test
    public void testTest() {
        Assertions.assertEquals(1,1);
    }

    @Test
    public void testRepositoryGetMethod() {
        //Arrange
        when(entityManager.createQuery((String) any())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        //Act
        listingRepository.get();

        //Assert
        verify(entityManager, times(1)).createQuery((String) any());
        verify(query, times(1)).getResultList();
    };

    @Test
    public void testRepositorySaveMethod() {
        //Arrange
        List<Listing> listings = new ArrayList<>();
        listings.add(null);
        listings.add(null);
        doNothing().when(entityManager).persist(any());

        //Act
        listingRepository.save(listings);

        //Assert persist was called the same number of times as the number of items in the list
        verify(entityManager, times(2)).persist(any());
    };

    @Test
    public void testRepositoryDeleteMethod() {
        //Arrange
        List<Listing> listings = new ArrayList<>();
        listings.add(null);
        listings.add(null);
        doNothing().when(entityManager).remove(any());

        //Act
        listingRepository.delete(listings);

        //Assert persist was called the same number of times as the number of items in the list
        verify(entityManager, times(2)).remove(any());
    };




}
