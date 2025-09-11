package com.auction.service;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getBidsForAuction(Auction auction) {
        return bidRepository.findByAuctionOrderByTimestampDesc(auction);
    }

    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }
}
