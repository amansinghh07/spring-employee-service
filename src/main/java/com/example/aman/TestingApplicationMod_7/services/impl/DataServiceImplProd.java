package com.example.aman.TestingApplicationMod_7.services.impl;

import com.example.aman.TestingApplicationMod_7.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceImplProd implements DataService {
    @Override
    public String getData() {
        return "Prod data";
    }
}
