package com.Deadstock.demo.controller.controllerRequests;

public record RegisterRequest(String username, 
                              String firstname, 
                              String lastname, 
                              String email, 
                              String password) {}