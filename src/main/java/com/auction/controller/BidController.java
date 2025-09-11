package com.auction.controller;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.service.AuctionService;
import com.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
@CrossOrigin
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/{auctionId}")
    public ResponseEntity<List<Bid>> getBidsByAuction(@PathVariable Long auctionId) {
        Auction auction = auctionService.getAuctionById(auctionId);
        if (auction == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Bid> bids = bidService.getBidsForAuction(auction);
        return ResponseEntity.ok(bids);
    }
}
