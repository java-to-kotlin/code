package travelator.itinerary;

import travelator.money.ExchangeRates;
import travelator.money.Money;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparing;

public class CostSummaryCalculator {
    private final Currency userCurrency;
    private final ExchangeRates exchangeRates;
    private final Map<Currency, Money> currencyTotals = new HashMap<>();

    public CostSummaryCalculator(
        Currency userCurrency,
        ExchangeRates exchangeRates
    ) {
        this.userCurrency = userCurrency;
        this.exchangeRates = exchangeRates;
    }
    
    public void addCost(Money cost) {
        currencyTotals.merge(cost.getCurrency(), cost, Money::plus);
    }

    public CostSummary summarise() {
        var totals = new ArrayList<>(currencyTotals.values());
        totals.sort(comparing(m -> m.getCurrency().getCurrencyCode()));

        CostSummary summary = new CostSummary(userCurrency);
        for (var total : totals) {
            summary.addLine(exchangeRates.convert(total, userCurrency));
        }

        return summary;
    }

    public void reset() {
        currencyTotals.clear();
    }
}