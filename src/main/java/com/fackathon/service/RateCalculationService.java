package com.fackathon.service;

import com.fackathon.domain.JsonRate;
import com.fackathon.domain.Rate;
import com.fackathon.utils.FileManager;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateCalculationService {

    @Value("${metacritic.rates.sample.file}")
    private String ratesJson;

    public List<Rate> computeRates() {
        String fileContent = FileManager.getFileContents(ratesJson);

        try {
            JsonRate parsed = new Gson().fromJson(fileContent, JsonRate.class);
            return parsed.toRateList();
        } catch (JsonParseException ex) {
            return null;
        }
    }

}
