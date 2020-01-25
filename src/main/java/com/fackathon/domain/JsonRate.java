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
public class JsonRate {

    private int[] rates;

    public List<Rate> toRateList() {
        final long totalRatings = rates.length;

        List<Rate> rateList = new ArrayList<>();
        for (int rate = 0; rate < 10; rate++) {
            final int numToFilter = rate + 1;
            final long occurences = Arrays.stream(rates).filter(r -> r == numToFilter).count();
            final float percentage = 100f * occurences / totalRatings;
            rateList.add(new Rate(Rating.values()[rate], percentage));
        }

        return rateList;
    }

}
