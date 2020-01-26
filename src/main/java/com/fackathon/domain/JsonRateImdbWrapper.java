package com.fackathon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonRateImdbWrapper {

    private List<JsonRateImdb> ratings;

    public static List<List<Rate>> convertToRateList(final JsonRateImdbWrapper rateList) {
        final List<List<Rate>> converted = new ArrayList<>();
        for (int i = 0; i < rateList.ratings.size(); i++) {
            final List<Rate> localRates = new ArrayList<>();
            converted.add(localRates);
            final long totalLocalRatings = Arrays.stream(rateList.ratings.get(i).getVotes()).sum();
            for (int j = 0; j < rateList.ratings.get(i).getVotes().length; j++) {
                localRates.add(new Rate(Rating.values()[j], 100f * rateList.ratings.get(i).getVotes()[j] / totalLocalRatings));
            }
        }
        return converted;
    }

}
