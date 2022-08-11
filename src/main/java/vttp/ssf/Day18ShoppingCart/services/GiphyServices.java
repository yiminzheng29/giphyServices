package vttp.ssf.Day18ShoppingCart.services;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.Day18ShoppingCart.models.Giphy;
import vttp.ssf.Day18ShoppingCart.repositories.GiphyRepository;

@Service
public class GiphyServices {
    
    private static final String URL = "https://api.giphy.com/v1/gifs/search";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private GiphyRepository gifRepo;

    public List<Giphy> getGifs(String query, Integer limit, String rating) {
        Optional<String> opt = gifRepo.get(query);
        String payload;

        if (opt.isEmpty()) {
            System.out.println("Getting resource from Giphy API");

            try {
                String url = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("api_key", key)
                    .queryParam("q", URLEncoder.encode(query, "UTF-8"))
                    .queryParam("limit", limit)
                    .queryParam("rating", rating)
                    .toUriString();

                RequestEntity<Void> req = RequestEntity.get(url).build();
                RestTemplate rtemp = new RestTemplate();
                ResponseEntity<String> resp = rtemp.exchange(req, String.class);
                payload = resp.getBody();
                System.out.printf("From API: %s\n", payload);
                gifRepo.save(query, payload);
            } catch (Exception ex) {
                System.err.printf("Error: %s/n", ex.getMessage());
                return Collections.emptyList();
            }
        }
            else {
                payload = opt.get();
                System.out.printf("From cache: %s\n", payload);
            }
            JsonReader jr = Json.createReader(new StringReader(payload));
            JsonObject jo = jr.readObject();
            JsonArray arr = jo.getJsonArray("data");
            List<Giphy> results = new LinkedList<>();
            for (int i = 0; i < arr.size(); i++) {
                JsonObject j = arr.getJsonObject(i);
                results.add(Giphy.create(j));
            }
            return results;
        }
    }

