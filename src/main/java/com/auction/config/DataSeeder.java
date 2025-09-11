package com.auction.config;

import com.auction.model.Auction;
import com.auction.model.User;
import com.auction.repository.AuctionRepository;
import com.auction.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, AuctionRepository auctionRepo) {
        return args -> {

            // ✅ Create dummy sellers if not exist
            if (userRepo.count() == 0) {
                userRepo.save(new User("seller1", "seller1@example.com", "password"));
                userRepo.save(new User("seller2", "seller2@example.com", "password"));
            }

            User seller1 = userRepo.findById(1L).orElse(null);
            User seller2 = userRepo.findById(2L).orElse(null);

            if (seller1 == null || seller2 == null) {
                System.out.println("❌ Seller users not found. Skipping auction seeding.");
                return;
            }

            // ✅ Smart insert: only add if title doesn't exist
            addAuctionIfNotExists(auctionRepo, "Canon EOS R7 Camera", "Mirrorless, 32MP, 4K video", 85000, seller2, "/images/canon.jpg");
            addAuctionIfNotExists(auctionRepo, "Nike Air Jordan 1", "Retro High OG, size 9", 18000, seller1, "/images/jordan.jpg");
            addAuctionIfNotExists(auctionRepo, "Samsung Galaxy S23 Ultra", "256GB, Phantom Black", 105000, seller2, "/images/s23.jpg");
            addAuctionIfNotExists(auctionRepo, "Yamaha Acoustic Guitar", "F310 model, natural finish", 9500, seller1, "/images/guitar.jpg");
            addAuctionIfNotExists(auctionRepo, "Kindle Paperwhite", "11th Gen, 6.8\" display, waterproof", 12000, seller2, "/images/kindle.jpg");
        };
    }

    private void addAuctionIfNotExists(AuctionRepository repo, String title, String desc, int price, User seller, String imageUrl) {
        if (repo.findByTitle(title).isEmpty()) {
            repo.save(new Auction(
                    title,
                    desc,
                    price,
                    price,
                    LocalDateTime.now().minusHours(1),
                    LocalDateTime.now().plusDays(3),
                    "ACTIVE",
                    seller,
                    null,
                    imageUrl
            ));
            System.out.println("✅ Auction added: " + title);
        } else {
            System.out.println("⏩ Auction already exists: " + title);
        }
    }
}
