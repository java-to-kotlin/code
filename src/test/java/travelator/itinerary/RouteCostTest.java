package travelator.itinerary;

import org.junit.jupiter.api.Test;
import travelator.money.ExchangeRates;
import travelator.money.ExchangeRatesViaBaseCurrency;
import travelator.money.Money;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static travelator.money.Currencies.*;


public class RouteCostTest {
    ExchangeRates fx = new ExchangeRatesViaBaseCurrency(
        EUR,
        Map.entry(GBP, new BigDecimal("0.8")),
        Map.entry(USD, new BigDecimal("1.6")),
        Map.entry(JOD, new BigDecimal("100"))
    );

    public CostSummary costs(Route r) {
        CostSummaryCalculator calculator = new CostSummaryCalculator(GBP, fx);
        r.addCostsTo(calculator);
        return calculator.summarise();
    }

    @Test
    public void an_empty_route_costs_nothing() throws IOException {
        var r = new Route(emptyList());

        var costs = costs(r);

        assertEquals(Money.zero(GBP), costs.getTotal());
    }

    @Test
    public void route_in_local_currency() throws IOException {
        var r = new Route(List.of(
            journeyCosting(Money.of("80", GBP)),
            journeyCosting(Money.of("35", GBP))));

        var costs = costs(r);

        assertEquals(Money.of("115", GBP), costs.getTotal());
    }

    @Test
    public void route_in_one_foreign_currency() throws IOException {
        var r = new Route(List.of(
            journeyCosting(Money.of("100", EUR)),
            journeyCosting(Money.of("50", EUR))));

        var costs = costs(r);

        assertEquals(Money.of("120", GBP), costs.getTotal());
    }

    @Test
    public void route_in_foreign_and_local_currency() throws IOException {
        var r = new Route(List.of(
            journeyCosting(Money.of("100", EUR)),
            journeyCosting(Money.of("50", GBP))));

        var costs = costs(r);

        assertEquals(Money.of("130", GBP), costs.getTotal());
    }

    @Test
    public void route_in_multiple_foreign_and_local_currencies() throws IOException {
        var r = new Route(List.of(
            journeyCosting(Money.of("250", EUR)),
            journeyCosting(Money.of("10000", JOD)),
            journeyCosting(Money.of("750", GBP))));

        var costs = costs(r);

        assertEquals(Money.of("1030", GBP), costs.getTotal());
    }

    private Journey journeyCosting(Money cost) {
        Journey j = new Journey();
        j.setPrice(cost);
        return j;
    }
}