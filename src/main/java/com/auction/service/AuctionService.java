package com.auction.service;

import com.auction.model.Auction;
import com.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    // ✅ Create a new auction
    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    // ✅ Get all active auctions
    public List<Auction> getActiveAuctions() {
        return auctionRepository.findByStatus("ACTIVE");
    }

    // ✅ Get all auctions (admin view)
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    // ✅ Get auction by ID
    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id).orElse(null);
    }

    // ✅ Update auction (used for bidding or closing)
    public Auction updateAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    // ✅ Close auction manually (admin action)
    public Auction closeAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElse(null);
        if (auction == null || !"ACTIVE".equals(auction.getStatus())) {
            return null;
        }
        auction.setStatus("CLOSED");
        return auctionRepository.save(auction);
    }
}
