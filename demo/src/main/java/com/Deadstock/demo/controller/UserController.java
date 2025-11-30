package com.Deadstock.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Deadstock.demo.controller.controllerRequests.ListingRequest;
import com.Deadstock.demo.controller.controllerRequests.LoginRequest;
import com.Deadstock.demo.controller.controllerRequests.RegisterRequest;
import com.Deadstock.demo.controller.controllerRequests.SearchRequest;
import com.Deadstock.demo.controller.controllerRequests.TokenResponse;
import com.Deadstock.demo.repository.AccountRepository;
import com.Deadstock.demo.repository.ListingRepository;
import com.Deadstock.demo.security.JwtService;
import com.Deadstock.demo.service.ListingService;
import com.Deadstock.demo.service.UserService;
import com.Deadstock.demo.user.User;
import com.google.gson.Gson;


@RestController
@RequestMapping("/demo")
public class UserController {
    private final UserService userService;
    private final ListingService listingService;
    private final AccountRepository accountRepo;
    private final ListingRepository listingRepo;
    private final JwtService jwt;

    public UserController(UserService userService, ListingService listingService, AccountRepository accountRepo, ListingRepository listingRepo, JwtService jwt) {
        this.userService = userService;
        this.listingService = listingService;
        this.accountRepo = accountRepo;
        this.listingRepo = listingRepo;
        this.jwt = jwt;
    }
    /*
    Formats for all the JSON request can be found in the 
    record files in the controllerRequest package
    */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
        userService.register(req.username(), req.firstname(), req.lastname(), req.email(), req.password());
            return ResponseEntity.ok("Registered");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            User user = accountRepo.findByEmail(req.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(new TokenResponse(jwt.generateToken(user.getUsername())));
        } catch (RuntimeException e){
            //"User not found"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
    @PostMapping("/createListing")
    public ResponseEntity<?> createListing(@RequestBody ListingRequest req) {
        try {
            listingService.createListing(req.title(), req.description(), req.category(), req.size(), req.brand(), req.condition(), req.price());
            return ResponseEntity.ok("Listing Successfully Created");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //Add to security if you want ppl to browse w/o an account
    //but that might make it complicated b/c envery action would then
    //have to lead back to a login page
    @GetMapping("/getAllListings")
    public ResponseEntity<?> getListings() {
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(listingService.getAllListings()));
    }


    @GetMapping("/getSearchListings")
    public ResponseEntity<?> searchListings(@RequestBody SearchRequest req) {
        
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unimplemented");
    }
    

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok("Authenticated!");
    }
}
