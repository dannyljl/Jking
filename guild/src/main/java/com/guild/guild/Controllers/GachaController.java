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
    public String getResult(@PathVariable String maxNumber) throws IOException {
        String url = "https://jking-functions-20200617112822840.azurewebsites.net/api/gacha?max-number=" + maxNumber;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("does it come here?");
        String result = restTemplate.getForObject(url, String.class);
        System.out.println("result is" + result);
        return result;
    }
}
