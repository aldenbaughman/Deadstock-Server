package com.Deadstock.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Deadstock.demo.model.Listing;
import com.Deadstock.demo.repository.ListingRepository;

@Service
public class ListingService {
    private final ListingRepository repo;

    public ListingService(ListingRepository repo){
        this.repo = repo;
    }

        public Listing createListing(String title,
                                  String description,
                                  String category,
                                  String size,
                                  String brand,
                                  String condition,
                                  //String color,
                                  double price){
        
        validateListingInputs(title, description, category, price);

        Listing l = new Listing();
        l.setTitle(title);
        l.setDescription(description);
        l.setCategory(category);
        l.setSizing(size);
        l.setBrand(brand);
        l.setCondition(condition);
        //l.setColor(color);
        l.setPrice(price);
        //How does Image stuff get saved??
        double calculatedSavings = calculateCarbonSavings(category, condition, size, brand);
        l.setCarbonSavings(calculatedSavings);

        return repo.save(l);
    }

    public List<Listing> getAllListings() {
    return repo.findAll();
    }

    public List<Listing> searchListings(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repo.findAll();
        }
        return repo.findByTitleContainingIgnoreCase(keyword);
    }

    private void validateListingInputs(String title, String description, String category, double price) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException("Title is a required field");
        }

        if (title.length() > 100) {
            throw new RuntimeException("Title must be 100 characters or less");
        }

        if (price <= 0.00) {
            throw new RuntimeException("Price must be greater than $0.00");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new RuntimeException("Description is required");
        }

        if (category == null || category.trim().isEmpty()) {
            throw new RuntimeException("Category is required");
        }
    }
    private double calculateCarbonSavings(String category, String condition, String size, String brand) {
        double baseValue = getBaseValue(category);
        double conditionAdj = getConditionAdjustment(condition);
        double sizeAdj = getSizeAdjustment(size);
        double brandAdj = getBrandAdjustment(brand);

        // Formula is (Base * (1 + ConditionAdj + SizeAdj + BrandAdj))
        double totalSavings = baseValue * (1.0 + conditionAdj + sizeAdj + brandAdj);

        // Rounds to 2 decimal places
        return Math.round(totalSavings * 100.0) / 100.0;
    }

    private double getBaseValue(String category) {
        if (category == null) return 10.0;
        
        switch (category) {
            case "Outerwear (Coats, Jackets)":
                return 40.0;
            case "Bottoms (Jeans, Trousers, Shorts)":
                return 30.0;
            case "Footwear":
                return 22.0;
            case "Dresses/Jumpsuits":
                return 15.0;
            case "Tops (T-Shirts, Hoodies, Blouses, Sweaters)":
                return 13.0; // Weighted average
            default:
                return 10.0;
        }
    }

    private double getConditionAdjustment(String condition) {
        if (condition == null) return 0.0;

        switch (condition) {
            case "New with Tags (NWT)":
            case "Like New":
                return 0.10;
            case "Excellent":
                return 0.05;
            case "Very Good":
                return 0.00;
            case "Good":
            case "Fair":
                return -0.05;
            default:
                return 0.00;
        }
    }

    private double getSizeAdjustment(String size) {
        if (size == null) return 0.0;

        switch (size) {
            // Small Sizes
            case "XS":
            case "S":
            case "W28 L30":
            case "W30 L30":
                return -0.10;
            
            // Medium/Standard Sizes
            case "M":
            case "L":
            case "One Size":
            case "W32 L30":
            case "W34 L32":
                return 0.00;
            
            // Large/Plus Sizes
            case "XL":
            case "XXL":
            case "3XL+":
            case "W36 L32":
            case "W38 L34":
            case "W40 L34":
                return 0.10;
            
            default:
                return 0.00;
        }
    }

    private double getBrandAdjustment(String brand) {
        if (brand == null) return 0.0;

        switch (brand) {
            case "Luxury/High-End (Gucci, Prada)":
                return 0.10;
            case "Sustainable Focused (Patagonia, Tentree)":
                return -0.05;
            case "Mid-Tier (Levi's, Zara)":
            case "Fast Fashion (H&M, SHEIN)":
            case "Vintage/Unbranded":
                return 0.00;
            default:
                return 0.00;
        }
    }

}
