package com.fackathon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JsonRateMetaWrapper {

    private List<JsonRateMeta> ratings;

    public static List<List<Rate>> convertToRateList(final JsonRateMetaWrapper rateList) {
        final List<List<Rate>> converted = new ArrayList<>();
        for (int i = 0; i < rateList.ratings.size(); i++) {
            final List<Rate> localRates = new ArrayList<>();
            converted.add(localRates);

            final long totalRatings = rateList.ratings.get(i).getRates().length;
            for (int rate = 0; rate < 10; rate++) {
                final int numToFilter = rate + 1;
                final long occurences = Arrays.stream(rateList.ratings.get(i).getRates()).filter(r -> r == numToFilter).count();
                final float percentage = 100f * occurences / totalRatings;
                localRates.add(new Rate(Rating.values()[rate], percentage));
            }
        }

        return converted;
    }

}
