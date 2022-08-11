package vttp.ssf.Day18ShoppingCart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.Day18ShoppingCart.models.Giphy;
import vttp.ssf.Day18ShoppingCart.services.GiphyServices;

@Controller
@RequestMapping("/giphy")
public class GiphyController {
    
    @Autowired
    private GiphyServices gifSvc;

    @GetMapping
    public String getResults(@RequestParam(name="q") String query, @RequestParam Integer limit, @RequestParam String rating, Model model) {
        List<Giphy> results = gifSvc.getGifs(query, limit, rating);
        
        model.addAttribute("gifs", results);
        model.addAttribute("q", query);
        model.addAttribute("limit", limit);
        model.addAttribute("rating", rating);

        return "giphy";
    } 
}
