package com.guild.guild.Controllers;

import javassist.compiler.ast.InstanceOfExpr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/gacha")
public class GachaController {

    //gacha rng
    @GetMapping("/{maxNumber}")
    public String getResult(@PathVariable Long maxNumber) throws IOException {
        String url = "https://jking-functions-20200617112822840.azurewebsites.net/api/gacha?max-number=" + maxNumber;
        RestTemplate restTemplate = new RestTemplate();
        String urlWhiteListed = "https://jking-functions";
        if(!url.startsWith(urlWhiteListed)){
            throw new IOException();
        }
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
