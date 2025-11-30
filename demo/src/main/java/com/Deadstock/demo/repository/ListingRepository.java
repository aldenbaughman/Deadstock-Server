package com.Deadstock.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Deadstock.demo.model.Listing;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByTitleContainingIgnoreCase(String title);
    Optional<Listing> findByImageLink(String imageLink);
    List<Listing> findByPrice(double price);
    List<Listing> findByColorsContaining(com.Deadstock.demo.model.Color color);
    boolean existsByImageLink(String imageLink);
}
