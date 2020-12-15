package travelator

import travelator.HasRating.rating
import travelator.HasPrice.price
import java.util.stream.Collectors
import travelator.HasRating
import travelator.HasPrice
import java.util.function.ToDoubleFunction
import travelator.HasRelevance
import java.util.Comparator
import java.util.stream.Stream

object Shortlists {
    @JvmStatic
    fun <T> sorted(shortlist: List<T>, ordering: Comparator<in T>?): List<T> {
        return shortlist.stream().sorted(ordering).collect(Collectors.toUnmodifiableList())
    }

    fun <T> removeItemAt(shortlist: List<T>, index: Int): List<T> {
        return Stream.concat(shortlist.stream().limit(index.toLong()), shortlist.stream().skip((index + 1).toLong()))
            .collect(Collectors.toUnmodifiableList())
    }

    @JvmStatic
    fun byRating(): Comparator<HasRating> {
        return Comparator.comparingDouble(HasRating::rating).reversed()
    }

    @JvmStatic
    fun byPriceLowToHigh(): Comparator<HasPrice> {
        return Comparator.comparing(HasPrice::price)
    }


    @JvmStatic
    fun <T> byValue(): Comparator<T> where T : HasPrice?, T : HasRating? {
        return Comparator.comparingDouble { t: T -> t!!.rating / t.price }.reversed()
    }

    @JvmStatic
    fun byRelevance(): Comparator<HasRelevance> {
        return Comparator.comparingDouble(HasRelevance::relevance).reversed()
    }
}