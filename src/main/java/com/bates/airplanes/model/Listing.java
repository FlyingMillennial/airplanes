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

    private Long sourceId;

    private String source;

    private LocalDate dateScraped;

    //Default for Hibernate
    public Listing() {}

    public Listing(Long sourceId, String source, LocalDate dateScraped, Boolean currentlyActive) {
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDateScraped() {
        return dateScraped;
    }

    public void setDateScraped(LocalDate dateScraped) {
        this.dateScraped = dateScraped;
    }

}
