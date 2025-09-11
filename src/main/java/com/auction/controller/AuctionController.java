package com.auction.controller;

import com.auction.model.Auction;
import com.auction.model.Bid;
import com.auction.model.User;
import com.auction.dto.BidRequest;
import com.auction.repository.AuctionRepository;
import com.auction.repository.BidRepository;
import com.auction.repository.UserRepository;
import com.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auctions")
@CrossOrigin
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    // ✅ Create new auction
    @PostMapping("/create")
    public Auction createAuction(@RequestBody Auction auction) {
        return auctionService.createAuction(auction);
    }

    // ✅ Get active auctions
    @GetMapping("/active")
    public List<Auction> getActiveAuctions() {
        return auctionService.getActiveAuctions();
    }

    // ✅ Get all auctions
    @GetMapping("/all")
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    // ✅ Get closed auctions
    @GetMapping("/closed")
    public List<Auction> getClosedAuctions() {
        return auctionRepository.findByStatus("CLOSED");
    }

    // ✅ Get auctions by seller
    @GetMapping("/seller/{username}")
    public List<Auction> getAuctionsBySeller(@PathVariable String username) {
        return auctionRepository.findBySellerUsername(username);
    }

    // ✅ Get auction by ID with winner info
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuctionById(@PathVariable Long id) {
        Auction auction = auctionService.getAuctionById(id);
        if (auction == null) return ResponseEntity.notFound().build();

        Map<String, Object> response = new HashMap<>();
        response.put("auction", auction);
        response.put("winner", auction.getWinner() != null ? auction.getWinner().getUsername() : null);

        return ResponseEntity.ok(response);
    }

    // ✅ Place a bid
    @PostMapping("/{id}/bid")
    public ResponseEntity<?> placeBid(@PathVariable Long id, @RequestBody BidRequest bidRequest) {
        Auction auction = auctionService.getAuctionById(id);
        if (auction == null || !"ACTIVE".equals(auction.getStatus())) {
            return ResponseEntity.badRequest().body("Auction not active or not found.");
        }

        if (bidRequest.getAmount() <= auction.getCurrentBid()) {
            return ResponseEntity.badRequest().body("Bid must be higher than current bid.");
        }

        User bidder = userRepository.findByUsername(bidRequest.getUsername());
        if (bidder == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        Bid bid = new Bid();
        bid.setAmount(bidRequest.getAmount());
        bid.setAuction(auction);
        bid.setBidder(bidder);
        bid.setTimestamp(LocalDateTime.now());

        bidRepository.save(bid);

        auction.setCurrentBid(bidRequest.getAmount());
        auctionRepository.save(auction);

        return ResponseEntity.ok(auction);
    }

    // ✅ DTO for bid history
    public record BidDTO(double amount, String bidderUsername, LocalDateTime timestamp) {}

    // ✅ Get bid history
    @GetMapping("/{id}/bids")
    public ResponseEntity<?> getBidHistory(@PathVariable Long id) {
        try {
            Auction auction = auctionService.getAuctionById(id);
            if (auction == null) return ResponseEntity.status(404).body("Auction not found");

            List<Bid> bids = bidRepository.findByAuctionOrderByTimestampDesc(auction);

            List<BidDTO> dtoList = bids.stream()
                    .map(b -> new BidDTO(
                            b.getAmount(),
                            b.getBidder() != null ? b.getBidder().getUsername() : "Unknown",
                            b.getTimestamp()
                    ))
                    .toList();

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching bid history: " + e.getMessage());
        }
    }

    // ✅ Extend auction end time (admin only)
    @PutMapping("/{id}/extend")
    public ResponseEntity<?> extendAuction(@PathVariable Long id, @RequestParam("newEndTime") String newEndTimeStr) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction == null) return ResponseEntity.status(404).body("Auction not found");

        try {
            LocalDateTime newEndTime = LocalDateTime.parse(newEndTimeStr);
            if (newEndTime.isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("End time must be in the future");
            }

            auction.setEndTime(newEndTime);

// ✅ Force status update if time is in future
            if ("CLOSED".equals(auction.getStatus()) && newEndTime.isAfter(LocalDateTime.now())) {
                auction.setStatus("ACTIVE");
                auction.setWinner(null); // Optional: clear winner
            }

// ✅ Log before save
            System.out.println("Before Save: " + auction.getStatus());

            auctionRepository.save(auction);

// ✅ Confirm after save
            Auction updated = auctionRepository.findById(id).orElse(null);
            System.out.println("After Save: " + (updated != null ? updated.getStatus() : "Not found"));

            return ResponseEntity.ok("Auction end time updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format");
        }
    }


    // ✅ Manually close auction (admin only)
    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeAuction(@PathVariable Long id) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction == null) return ResponseEntity.status(404).body("Auction not found");

        auction.setStatus("CLOSED");

        List<Bid> bids = bidRepository.findByAuctionOrderByAmountDesc(auction);
        if (!bids.isEmpty()) {
            auction.setWinner(bids.get(0).getBidder());
        }

        auctionRepository.save(auction);
        return ResponseEntity.ok("Auction closed and winner declared");
    }
}
