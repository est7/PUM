package pl.udu.uwr.pum.carsyappkotlin.data

import java.time.LocalDate
import java.util.*
import kotlin.random.Random

object DataProvider {

    val cars = listOf(
        Car("Domowy", "Skoda", "Fabia", 2002, generalCosts(100)),
        Car("Służbowy", "BMW", "Coupe", 2015, generalCosts(50)),
        Car("Kolekcjonerski", "Fiat", "125p", 1985, generalCosts(10)),
        Car("Sportowy", "Lamborghini", "Murcielago", 2012, generalCosts(46)),
        Car("Zapasowy", "Skoda", "Superb", 2010, generalCosts(34)),
        Car("SUV", "Skoda", "Kodiaq", 2020, generalCosts(19))
    )

    fun getTimeLineList(costs: List<Cost>): List<CostListItem>{
        val groupedCostMap: Map<String, List<Cost>> = costs.sortedBy { it.date.month }.groupBy { it.date.month.toString() }
        val list = mutableListOf<CostListItem>()
        for (date: String in groupedCostMap.keys) {
            val groupItems: List<Cost>? = groupedCostMap[date]
            groupItems?.sortedBy { it.date.dayOfMonth }?.forEach {
                list.add(CostGeneralItem(it))
            }
            list.add(CostDateItem(date))
        }
        return list
    }

    private fun  generalCosts(size: Int) = List(size) {
        Cost(
            CostType.values()[Random.nextInt(CostType.values().size)],
            LocalDate.of(2022, Random.nextInt(1,13), Random.nextInt(1,28)),
            Random.nextInt(5000)
        )
    }

}