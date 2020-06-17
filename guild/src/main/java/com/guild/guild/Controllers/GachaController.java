package com.guild.guild.Controllers;

import com.guild.guild.classes.Guild;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/gacha")
public class GachaController {

    @GetMapping("/{maxNumber}")
    public int getResult(@PathVariable String maxNumber){
        RestTemplate restTemplate = new RestTemplate();
        int result = restTemplate.getForObject("https://jking-functions-20200617112822840.azurewebsites.net/api/gacha?max-number=" + maxNumber, Integer.class);
        return result;
    }
}
