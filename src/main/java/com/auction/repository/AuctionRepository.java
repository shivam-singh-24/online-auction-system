package com.auction.repository;

import com.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findByStatus(String status);

    Optional<Auction> findByTitle(String title);

    List<Auction> findBySellerUsername(String username);

    // ✅ Used by scheduler to find expired active auctions
    List<Auction> findByEndTimeBeforeAndStatus(LocalDateTime time, String status);
}
