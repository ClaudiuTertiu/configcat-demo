package com.configcat.demo.controllers;

import com.configcat.ConfigCatClient;
import com.configcat.PollingModes;
import com.configcat.User;
import com.configcat.demo.utils.ApplicationConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {
    @GetMapping("/feature")
    public String testFeature(@RequestParam(value = "country", required = false) String country) {
        if(country == null) return "Romania or UK?";

        ConfigCatClient client = ConfigCatClient.newBuilder()
                .mode(PollingModes.LazyLoad(
                        10,
                        true
                        )
                )
                .build(ApplicationConstants.CONFIG_CAT_KEY);

        if(country.equals("romania")) {
            User userObject = User.newBuilder()
                .build("romania-user");
                boolean isFromRomania = client.getValue(Boolean.class, "testFeature", userObject,false);
                if(isFromRomania) return "Ce faci?";
                else {
                    return "Salut, feature-ul este disabled.";
                }
        }
        else if (country.equals("uk")) {
            User userObject2 = User.newBuilder()
                    .build("uk-user");
            boolean isFromUk = client.getValue(Boolean.class, "testFeature", userObject2,false);
            if(isFromUk) {
                return "What do you do?";
            }
            else {
                return "Hello, the feature is disabled";
            }
        }
        return null;
    }
}
