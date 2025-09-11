package com.auction.scheduler;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.repository.AuctionRepository;
import com.auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuctionExpiryScheduler {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    // ✅ Runs every minute to close expired auctions
    @Scheduled(fixedRate = 60000)
    public void closeExpiredAuctions() {
        List<Auction> expiredAuctions = auctionRepository
                .findByEndTimeBeforeAndStatus(LocalDateTime.now(), "ACTIVE");

        for (Auction auction : expiredAuctions) {
            auction.setStatus("CLOSED");

            // ✅ Optional: Declare winner based on highest bid
            List<Bid> bids = bidRepository.findByAuctionOrderByAmountDesc(auction);
            if (!bids.isEmpty()) {
                auction.setWinner(bids.get(0).getBidder());
            }

            auctionRepository.save(auction);
        }
    }
}
