package com.fackathon.service;

import com.fackathon.domain.*;
import com.fackathon.utils.FileManager;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateCalculationService {

    @Value("${metacritic.rates.sample.file}")
    private String metacriticRatesJson;

    @Value("${imdb.rates.sample.file}")
    private String imdbRatesJson;

    public List<List<Rate>> computeMetacriticRates() {
        String fileContent = FileManager.getFileContents(metacriticRatesJson);

        try {
            JsonRateMetaWrapper parsed = new Gson().fromJson(fileContent, JsonRateMetaWrapper.class);
            return JsonRateMetaWrapper.convertToRateList(parsed);
        } catch (JsonParseException ex) {
            return null;
        }
    }

    public List<List<Rate>> computeImdbRates() {
        String fileContent = FileManager.getFileContents(imdbRatesJson);

        try {
            JsonRateImdbWrapper parsed = new Gson().fromJson(fileContent, JsonRateImdbWrapper.class);
            return JsonRateImdbWrapper.convertToRateList(parsed);
        } catch (Exception ex) {
            return null;
        }
    }

    public Reviewer getProbableReviewerForRates(final List<Rate> rates) {
        final float errorPercentage = 10f;

        double midAverage = 0;
        double edgeAverage = 0;
        for (int i = 0; i < rates.size(); i++) {
            final float currPercentage = rates.get(i).getPercentage();
            if (i >= 3 && i <= 7) {
                midAverage += currPercentage;
            } else {
                edgeAverage += currPercentage;
            }
        }

        if (midAverage < edgeAverage - errorPercentage) {
            return Reviewer.USER;
        } else if (midAverage > edgeAverage + errorPercentage) {
            return Reviewer.CRITIC;
        } else {
            return Reviewer.FLAT;
        }
    }

    public PlatformTrust getTrustLevelForRates(final List<Rate> rates) {
        final int graphChanges = countGraphChanges(rates);

        if (graphChanges > 1) {
            return PlatformTrust.NEGATIVE;
        } else if (graphChanges == 0) {
            return PlatformTrust.NEUTRAL;
        } else if (graphChanges == 1) {
            final Reviewer probableReviewer = getProbableReviewerForRates(rates);
            return probableReviewer == Reviewer.CRITIC ? PlatformTrust.POSITIVE : PlatformTrust.NEGATIVE;
        }

        return PlatformTrust.NEUTRAL;
    }

    private int countGraphChanges(final List<Rate> rates) {
        final float error = 7f;
        int counter = 0;

        float prevPercentage = rates.get(1).getPercentage();
        boolean goingUp = rates.get(1).getPercentage() >= rates.get(0).getPercentage();
        for (int i = 2; i < rates.size(); i++) {
            if (rates.get(i).getPercentage() >= prevPercentage + error && !goingUp) {
                counter++;
                goingUp = true;
                prevPercentage = rates.get(i).getPercentage();
            } else if (goingUp && rates.get(i).getPercentage() >= prevPercentage - error) {
                // TODO: probably need another check like 2 else below...
                boolean shouldChange = rates.get(i).getPercentage() < prevPercentage && i < rates.size() - 1 && rates.get(i + 1).getPercentage() < prevPercentage - error;
                if (shouldChange) {
                    counter++;
                }
                prevPercentage = rates.get(i).getPercentage();
            } else if (rates.get(i).getPercentage() < prevPercentage - error && goingUp) {
                counter++;
                goingUp = false;
                prevPercentage = rates.get(i).getPercentage();
            } else if (!goingUp && rates.get(i).getPercentage() < prevPercentage + error) {
                goingUp = rates.get(i).getPercentage() > prevPercentage && i < rates.size() - 1 && rates.get(i + 1).getPercentage() > prevPercentage + error;
                if (goingUp) {
                    counter++;
                }
                prevPercentage = rates.get(i).getPercentage();
            }
        }

        return counter;
    }
}
