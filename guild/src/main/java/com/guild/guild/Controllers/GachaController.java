package com.guild.guild.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/gacha")
public class GachaController {

    //gacha
    @GetMapping("/{maxNumber}")
    public String getResult(@PathVariable String maxNumber){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://jking-functions-20200617112822840.azurewebsites.net/api/gacha?max-number=" + maxNumber, String.class);
        return result;
    }
}
