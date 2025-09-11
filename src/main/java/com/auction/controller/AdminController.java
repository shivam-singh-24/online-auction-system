package com.auction.controller;

import com.auction.model.Auction;
import com.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AuctionService auctionService;

    @PutMapping("/declare-winner/{id}")
    public Auction declareWinner(@PathVariable Long id) {
        return auctionService.closeAuction(id);
    }
}
