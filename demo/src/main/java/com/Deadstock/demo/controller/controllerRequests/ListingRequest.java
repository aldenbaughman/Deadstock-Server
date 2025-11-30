package com.Deadstock.demo.controller.controllerRequests;

public record ListingRequest(
    String title,
    String description,
    String category,
    String size,
    String brand,
    String condition,
    String color,
    float price
) {}