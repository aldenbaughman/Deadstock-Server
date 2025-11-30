package com.Deadstock.demo.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private double carbon_savings;
    private double price;
    private String imageLink;

    @ElementCollection
    private List<Color> colors;

    private String category;
    private String sizing;
    private String brand;
    private String condition;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getCarbonSavings() { return carbon_savings; }
    public void setCarbonSavings(double carbon_savings) { this.carbon_savings = carbon_savings; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    /* 
    public List<Color> getColors() { return colors; }
    public void setColors(List<Color> colors) { this.colors = colors; }
    */
   
    public String getSizing() { return sizing; }
    public void setSizing(String sizing) { this.sizing = sizing; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
