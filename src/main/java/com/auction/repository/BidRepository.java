package com.auction.repository;

import com.auction.model.Bid;
import com.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    // ✅ For bid history (by time)
    List<Bid> findByAuctionOrderByTimestampDesc(Auction auction);

    // ✅ For winner logic (by amount)
    List<Bid> findByAuctionOrderByAmountDesc(Auction auction);
}
