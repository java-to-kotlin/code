package travelator

import java.util.*
import java.util.Comparator.comparing
import java.util.Comparator.comparingDouble
import java.util.stream.Collectors.toUnmodifiableList
import java.util.stream.Stream

object Shortlists {

    @JvmStatic
    fun <T> removeItemAt(shortlist: List<T>, index: Int): List<T> {
        return Stream.concat(
            shortlist.stream().limit(index.toLong()),
            shortlist.stream().skip((index + 1).toLong())
        ).collect(toUnmodifiableList())
    }

    @JvmStatic
    fun byRating(): Comparator<HasRating> {
        return comparingDouble(HasRating::rating).reversed()
    }

    @JvmStatic
    fun byPriceLowToHigh(): Comparator<HasPrice> {
        return comparing(HasPrice::price)
    }


    @JvmStatic
    fun <T> byValue(): Comparator<T> where T : HasPrice, T : HasRating {
        return comparingDouble { t: T -> t.rating / t.price }.reversed()
    }

    @JvmStatic
    fun byRelevance(): Comparator<HasRelevance> {
        return comparingDouble(HasRelevance::relevance).reversed()
    }
}

@JvmStatic
fun <T> sorted(shortlist: List<T>, ordering: Comparator<in T>): List<T> {
    return shortlist.stream().sorted(ordering)
        .collect(toUnmodifiableList())
}