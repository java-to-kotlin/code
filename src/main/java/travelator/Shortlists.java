package travelator;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Shortlists {
    public static <T> List<T> sorted(
        List<T> shortlist,
        Comparator<? super T> ordering
    ) {
        return shortlist.stream()
            .sorted(ordering)
            .collect(toUnmodifiableList());
    }

    public static <T> List<T> removeItemAt(List<T> shortlist, int index) {
        return Stream.concat(
            shortlist.stream().limit(index),
            shortlist.stream().skip(index + 1)
        ).collect(toUnmodifiableList());
    }

    public static Comparator<HasRating> byRating() {
        return comparingDouble(HasRating::getRating).reversed();
    }

    public static Comparator<HasPrice> byPriceLowToHigh() {
        return comparing(HasPrice::getPrice);
    }

    public static <T extends HasPrice & HasRating> Comparator<T> byValue() {
        return comparingDouble((T t) -> t.getRating() / t.getPrice()).reversed();
    }

    public static Comparator<HasRelevance> byRelevance() {
        return comparingDouble(HasRelevance::getRelevance).reversed();
    }
}
