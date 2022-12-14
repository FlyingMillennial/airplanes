package com.bates.airplanes.service;

import com.bates.airplanes.model.Listing;
import com.bates.airplanes.model.ScrapeSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ScrapeServiceTest {

    ScrapeService scrapeService = new ScrapeService(
        "https://www.trade-a-plane.com/search?category_level1=Single+Engine+Piston&make=PIPER&s-type=aircraft&s-page_size=96&s-sort_key=price&s-sort_order=asc",
        "data-listing_id=\"(.*?)\"",
        ScrapeSource.TRADEAPLANE
    );

    @Mock
    HttpClient httpClient;

    @Mock
    HttpResponse httpResponse;

    private final String simpleWebPageResponse = "blah data-listing_id=\"123\" blah data-listing_id=\"456\"";
    private final String webPageResponseWithDuplicates = "blah data-listing_id=\"123\" blah data-listing_id=\"456\" blah data-listing_id=\"123\"";

    @Test
    public void getWebListingsShouldReturnAListOfListings() throws IOException, InterruptedException {
        //Arrange
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(simpleWebPageResponse);

        //Act
        List<Listing> listings = scrapeService.getWebListings(httpClient);

        //Assert
        Assertions.assertEquals(listings.get(0).getSourceId(),"123");
        Assertions.assertEquals(listings.get(1).getSourceId(),"456");
    }

    @Test
    public void getWebListingsShouldIgnoreDuplicateListings() throws IOException, InterruptedException {
        //Arrange
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(webPageResponseWithDuplicates);

        //Act
        List<Listing> listings = scrapeService.getWebListings(httpClient);

        //Assert
        Assertions.assertEquals(listings.size(), 2);
    }

    @Test
    public void getWebListingsShouldThrowARuntimeExceptionForIOExceptions() throws IOException, InterruptedException {
        //Arrange
        when(httpClient.send(any(), any())).thenThrow(new IOException());
        Exception thrownException = null;

        //Act
        try {
            scrapeService.getWebListings(httpClient);
        } catch (RuntimeException e) {
            thrownException = e;
        } finally {
            if (thrownException == null) {
                thrownException = new Exception();
            }
        }

        //Assert
        boolean isRuntimeException = thrownException instanceof RuntimeException;
        Assertions.assertTrue(isRuntimeException);
    }

    @Test
    public void getWebListingsShouldThrowARuntimeExceptionForInterruptedExceptions() throws IOException, InterruptedException {
        //Arrange
        when(httpClient.send(any(), any())).thenThrow(new InterruptedException());
        Exception thrownException = null;

        //Act
        try {
            scrapeService.getWebListings(httpClient);
        } catch (RuntimeException e) {
            thrownException = e;
        } finally {
            if (thrownException == null) {
                thrownException = new Exception();
            }
        }

        //Assert
        boolean isRuntimeException = thrownException instanceof RuntimeException;
        Assertions.assertTrue(isRuntimeException);
    }

}
