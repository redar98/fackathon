package com.fackathon.service;

import com.fackathon.domain.PlatformTrust;
import com.fackathon.domain.Rate;
import com.fackathon.domain.Rating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RateCalculationServiceTest {

    private final List<Rate> flatRates = new ArrayList<Rate>() {{
        add(new Rate(Rating.ONE, 5));
        add(new Rate(Rating.TWO, 10));
        add(new Rate(Rating.THREE, 15));
        add(new Rate(Rating.FOUR, 15));
        add(new Rate(Rating.FIVE, 10));
        add(new Rate(Rating.SIX, 10));
        add(new Rate(Rating.SEVEN, 10));
        add(new Rate(Rating.EIGHT, 10));
        add(new Rate(Rating.NINE, 5));
        add(new Rate(Rating.TEN, 10));
    }};

    private final List<Rate> spontaneousRates = new ArrayList<Rate>() {{
        add(new Rate(Rating.ONE, 5));
        add(new Rate(Rating.TWO, 10));
        add(new Rate(Rating.THREE, 15));
        add(new Rate(Rating.FOUR, 20));
        add(new Rate(Rating.FIVE, 10));
        add(new Rate(Rating.SIX, 5));
        add(new Rate(Rating.SEVEN, 5));
        add(new Rate(Rating.EIGHT, 5));
        add(new Rate(Rating.NINE, 15));
        add(new Rate(Rating.TEN, 10));
    }};

    private final List<Rate> negativeRates = new ArrayList<Rate>() {{
        add(new Rate(Rating.ONE, 20));
        add(new Rate(Rating.TWO, 10));
        add(new Rate(Rating.THREE, 5));
        add(new Rate(Rating.FOUR, 0));
        add(new Rate(Rating.FIVE, 5));
        add(new Rate(Rating.SIX, 10));
        add(new Rate(Rating.SEVEN, 10));
        add(new Rate(Rating.EIGHT, 10));
        add(new Rate(Rating.NINE, 15));
        add(new Rate(Rating.TEN, 15));
    }};

    private final List<Rate> positiveRates = new ArrayList<Rate>() {{
        add(new Rate(Rating.ONE, 0));
        add(new Rate(Rating.TWO, 10));
        add(new Rate(Rating.THREE, 5));
        add(new Rate(Rating.FOUR, 10));
        add(new Rate(Rating.FIVE, 15));
        add(new Rate(Rating.SIX, 15));
        add(new Rate(Rating.SEVEN, 20));
        add(new Rate(Rating.EIGHT, 10));
        add(new Rate(Rating.NINE, 5));
        add(new Rate(Rating.TEN, 10));
    }};

    private RateCalculationService rateCalculationService;

    @Before
    public void setup() {
        rateCalculationService = new RateCalculationService();
    }

    @Test
    public void shouldCountNeutralGraphChanges() {
        final PlatformTrust trust = rateCalculationService.getTrustLevelForRates(flatRates);

        assertEquals(PlatformTrust.NEUTRAL, trust);
    }

    @Test
    public void shouldCountSpontaneousGraphChanges() {
        final PlatformTrust trust = rateCalculationService.getTrustLevelForRates(spontaneousRates);

        assertEquals(PlatformTrust.NEGATIVE, trust);
    }

    @Test
    public void shouldCountNegativeGraphChanges() {
        final PlatformTrust trust = rateCalculationService.getTrustLevelForRates(negativeRates);

        assertEquals(PlatformTrust.NEGATIVE, trust);
    }

    @Test
    public void shouldCountPositiveGraphChanges() {
        final PlatformTrust trust = rateCalculationService.getTrustLevelForRates(positiveRates);

        assertEquals(PlatformTrust.POSITIVE, trust);
    }

}
