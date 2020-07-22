package travelator.itinerary

typealias Route = List<Journey>

fun Route.addCostsTo(calculator: CostSummaryCalculator) {
    forEach { journey -> calculator.addCost(journey.price) }
}