package com.bates.airplanes.service;

import com.bates.airplanes.model.ScrapeSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ScrapeServiceFactoryTest {

    static ScrapeServiceFactory scrapeServiceFactory;

    @BeforeAll
    public static void init() {
        //Arrange
        scrapeServiceFactory = new ScrapeServiceFactory();
    }

    @Test
    public void returnsCorrectServiceInstances() {
        //Act
        ScrapeService tradeAPlaneService = scrapeServiceFactory.getScrapeService(ScrapeSource.TRADEAPLANE);
        ScrapeService controllerService = scrapeServiceFactory.getScrapeService(ScrapeSource.CONTROLLER);;
        ScrapeService barnstormersService = scrapeServiceFactory.getScrapeService(ScrapeSource.BARNSTORMERS);

        //Assert
        Assertions.assertEquals(tradeAPlaneService.getScrapeSource(), ScrapeSource.TRADEAPLANE);
        Assertions.assertEquals(controllerService.getScrapeSource(), ScrapeSource.CONTROLLER);
        Assertions.assertEquals(barnstormersService.getScrapeSource(), ScrapeSource.BARNSTORMERS);
    }

}
