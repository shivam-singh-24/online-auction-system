package com.auction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private String status;
    private String title;

    private String description;
    private double startingPrice;
    private double currentBid;

    private LocalDateTime startTime;
    private LocalDateTime endTime;





    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;


    @Column(name = "image_url")
    private String imageUrl;



    // ✅ Constructor for seed data with image
    public Auction(String title, String description, double startingPrice, double currentBid,
                   LocalDateTime startTime, LocalDateTime endTime, String status,
                   User seller, User winner, String imageUrl) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentBid = currentBid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.seller = seller;
        this.winner = winner;
        this.imageUrl = imageUrl;
    }

    // ✅ Default constructor (required by JPA)
    public Auction() {}

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
