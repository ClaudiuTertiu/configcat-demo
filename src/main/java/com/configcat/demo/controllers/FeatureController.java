package com.configcat.demo.controllers;

import com.configcat.ConfigCatClient;
import com.configcat.PollingModes;
import com.configcat.demo.utils.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {
    @GetMapping("/feature")
    public String testFeature() {
        Logger logger = LoggerFactory.getLogger(FeatureController.class);

        ConfigCatClient client = ConfigCatClient.newBuilder()
                .mode(PollingModes.LazyLoad(
                        10,
                        true
                        )
                )
                .build(ApplicationConstants.CONFIG_CAT_KEY);
        boolean isMyAwesomeFeatureEnabled = client.getValue(Boolean.class, "isMyFirstFeatureEnabled", false);

        if(isMyAwesomeFeatureEnabled) {
            return "There's a 20% of being ON and 80% of being OFF: \n You were lucky.";
        } else {
            return "There's a 20% of being ON and 80% of being OFF: \n Bad luck, try again.";
        }
    }
}
