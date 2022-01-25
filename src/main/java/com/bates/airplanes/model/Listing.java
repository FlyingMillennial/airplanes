package com.bates.airplanes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Listing {

    @Id
    @GeneratedValue
    private Long id;

    private String sourceId;

    private ScrapeSource source;

    private LocalDate dateScraped;

    //Default for Hibernate
    public Listing() {}

    public Listing(String sourceId, ScrapeSource source, LocalDate dateScraped) {
        this.sourceId = sourceId;
        this.source = source;
        this.dateScraped = dateScraped;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public ScrapeSource getSource() {return source;}

    public void setSource(ScrapeSource source) {
        this.source = source;
    }

    public LocalDate getDateScraped() {
        return dateScraped;
    }

    public void setDateScraped(LocalDate dateScraped) {
        this.dateScraped = dateScraped;
    }

}
