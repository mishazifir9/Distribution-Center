import DistributionCenter.bedWarehouse
import DistributionCenter.bredWarehouse
import DistributionCenter.chairWarehouse
import DistributionCenter.conditionerWarehouse
import DistributionCenter.cupboardWarehouse
import DistributionCenter.displayWarehouse
import DistributionCenter.download_Port_1
import DistributionCenter.download_Port_2
import DistributionCenter.download_Port_3
import DistributionCenter.download_Port_4
import DistributionCenter.download_Port_5
import DistributionCenter.fromWorehouse
import DistributionCenter.laptopWarehouse
import DistributionCenter.milkWarehouse
import DistributionCenter.potatoWarehouse
import DistributionCenter.printerWarehouse
import DistributionCenter.tableWarehouse
import DistributionCenter.teapotWarehouse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() {

    val distributionCenter = DistributionCenter
    var countTrucks = 1  // Центр может принять только 100 машин за день
    val trucksLimit = 100

    // Этап 1. Создается поток грузовиков для разгрузки
    runBlocking {
        val roadToUnloading = Channel<Truck>(capacity = 100)
        launch {
            while (countTrucks <= trucksLimit) {
                val i = create_Truck_for_Delivery()
                roadToUnloading.send(i)
                println("К центру подьезжает грузовик №$countTrucks вместимостью ${i.capacity}")
                countTrucks++
                delay(30_000) // грузовик создается каждые 30 секунд
                yield()
                if (countTrucks % 10 == 0) {
                    println("Промежуточный учет товаров на складе, после каждых 10 машин")
                    distributionCenter.whatDoWeHaveOnOurCoolestWarehouse()
                }
            }
        }

        // этап 2. Разгружаем машины
        //unloading_Port_1
        launch {
            while (countTrucks <= trucksLimit - 2) {
                val keep = roadToUnloading.receive()
                val unload = distributionCenter.unloading_Port_1.unload
                unload.addAll(keep.productsInside)
                distributionCenter.toWorehause(distributionCenter.unloading_Port_1) // распределяем товар по складу
                val list = unload.toList()
                println(
                    "В порту №1 происходит разгрузка следующих товаров: ${
                        list.groupingBy { it.productName }.eachCount()
                    }"
                )
                val delayTime =
                    ((distributionCenter.unloading_Port_1.unload.sumOf { it.loading_time })) // вычисляем время разгрузки
                delay(delayTime.toLong())
                unload.clear() // - освобождаем порт разгрузки
            }
        }
        //unloading_Port_2
        launch {
            while (countTrucks <= trucksLimit - 2) {
                val keep = roadToUnloading.receive()
                val unload = distributionCenter.unloading_Port_2.unload
                unload.addAll(keep.productsInside)
                distributionCenter.toWorehause(distributionCenter.unloading_Port_2) // распределяем товар по складу
                val list = unload.toList()
                println(
                    "В порту №2 происходит разгрузка следующих товаров: ${
                        list.groupingBy { it.productName }.eachCount()
                    }"
                )
                val delayTime =
                    ((distributionCenter.unloading_Port_2.unload.sumOf { it.loading_time })) // вычисляем время разгрузки
                delay(delayTime.toLong())
                unload.clear() // - освобождаем порт разгрузки
            }
        }
        //unloading_Port_3
        launch {
            while (countTrucks <= trucksLimit - 2) {
                val keep = roadToUnloading.receive()
                val unload = distributionCenter.unloading_Port_3.unload
                unload.addAll(keep.productsInside)
                distributionCenter.toWorehause(distributionCenter.unloading_Port_3) // распределяем товар по складу
                val list = unload.toList()
                println(
                    "В порту №3 происходит разгрузка следующих товаров: ${
                        list.groupingBy { it.productName }.eachCount()
                    }"
                )
                val delayTime =
                    ((distributionCenter.unloading_Port_3.unload.sumOf { it.loading_time })) // вычисляем время разгрузки
                delay(delayTime.toLong())
                unload.clear() // - освобождаем порт разгрузки
            }
        }


        // Этап 3. Создаем грузовики для загрузки
        val roadToDowliading = Channel<Truck>()
        launch {
            withTimeout(4_000_000) {       // Рабочий день, по истечению времени, машины на загрузку не допускаются
                while (true) {
                    val truck = create_Truck_for_Dowload()
                    roadToDowliading.send(truck)
                    println("Готов грузовик для загрузки вместительностью - ${truck.capacity}")
                }
            }
        }

        // Этап 4. Загружаем грузовик
        launch {
            withTimeout(4_000_000) {
                while (true) {
                    val send = roadToDowliading.receive()
                    delay(10)
                    println("---------------------------------------------------------------------------------------------------------")
                    println("В порт загрузки 1 прибыл грузовик  вместимостью ${send.capacity}")
                    fromWorehouse(download_Port_1, send)
                    val list = send.productsInside.toList()
                    println("---------------------------------------------------------------------------------------------------------")
                    println(
                        "В грузовик загружены следующие товары: ${list.groupingBy { it.productName }.eachCount()} " +
                                "\nГрузовик отправлен в магазин."
                    )
                    yield()
                    delay(1000)
                }
            }
        }
        // Второй порт загрузки
        launch {
            withTimeout(4_000_000) {
                while (true) {
                    val send = roadToDowliading.receive()
                    delay(10)
                    println("---------------------------------------------------------------------------------------------------------")
                    println("В порт загрузки 2 прибыл грузовик  вместимостью ${send.capacity}")
                    fromWorehouse(download_Port_2, send)
                    val list = send.productsInside.toList()
                    println("---------------------------------------------------------------------------------------------------------")
                    println(
                        "В грузовик загружены следующие товары: ${list.groupingBy { it.productName }.eachCount()} " +
                                "\nГрузовик отправлен в магазин."
                    )
                    yield()
                    delay(1000)
                }
            }
        }

        // Третий порт загрузки
        launch {
            withTimeout(4_000_000) {
                while (true) {
                    val send = roadToDowliading.receive()
                    delay(10)
                    println("---------------------------------------------------------------------------------------------------------")
                    println("В порт загрузки 3 прибыл грузовик  вместимостью ${send.capacity}")
                    fromWorehouse(download_Port_3, send)
                    val list = send.productsInside.toList()
                    println("---------------------------------------------------------------------------------------------------------")
                    println(
                        "В грузовик загружены следующие товары: ${list.groupingBy { it.productName }.eachCount()} " +
                                "\nГрузовик отправлен в магазин."
                    )
                    yield()
                    delay(1000)
                }
            }
        }

        // Четвертый порт загрузки
        launch {
            withTimeout(4_000_000) {
                while (true) {
                    val send = roadToDowliading.receive()
                    delay(10)
                    println("---------------------------------------------------------------------------------------------------------")
                    println("В порт загрузки 4 прибыл грузовик  вместимостью ${send.capacity}")
                    fromWorehouse(download_Port_4, send)
                    val list = send.productsInside.toList()
                    println("---------------------------------------------------------------------------------------------------------")
                    println(
                        "В грузовик загружены следующие товары: ${list.groupingBy { it.productName }.eachCount()} " +
                                "\nГрузовик отправлен в магазин."
                    )
                    yield()
                    delay(1000)
                }
            }
        }

        // Пятый порт загрузки
        launch {
            withTimeout(4_000_000) {
                while (true) {
                    val send = roadToDowliading.receive()
                    delay(10)
                    println("---------------------------------------------------------------------------------------------------------")
                    println("В порт загрузки 5 прибыл грузовик  вместимостью ${send.capacity}")
                    fromWorehouse(download_Port_5, send)
                    val list = send.productsInside.toList()
                    println("---------------------------------------------------------------------------------------------------------")
                    println(
                        "В грузовик загружены следующие товары: ${list.groupingBy { it.productName }.eachCount()} " +
                                "\nГрузовик отправлен в магазин."
                    )
                    yield()
                    delay(1000)
                }
            }
        }
    }
    distributionCenter.whatDoWeHaveOnOurCoolestWarehouse()
}


// Создаем грузовик для разгрузки
fun create_Truck_for_Delivery(): Truck {
    val capacityLuck = (1..3).random()
    val truck = Truck(
        when (capacityLuck) {
            1 -> CarryingCapacity.LOW_LOAD_CAPACITY.capacity
            2 -> CarryingCapacity.AVERAGE_LOAD_CAPACITY.capacity
            else -> CarryingCapacity.LARGE_LOAD_CAPACITY.capacity
        }
    )
    val productType = (1..7).random()  // Выбираем тип товара

    // Заполняем грузовик
    if (productType == 1 || productType == 2) {  // Продукты едут только с продуктами
        loop@ for (i in 1..5) {
            val howMuch = (0..10).random()

            val food = when ((1..3).random()) { // Выбираем продукт
                1 -> Product.FoodProducts.Bread()
                2 -> Product.FoodProducts.Milk()
                else -> Product.FoodProducts.Potato()
            }
            for (a in 0..howMuch) {           // Выбираем количество продукта
                if (truck.workload - food.weight <= 0) break@loop
                truck.productsInside.add(food)
                truck.workload -= food.weight
            }
        }
    } else {
        loop@ for (i in 1..5) {
            val howMuch = (0..10).random()
            val goods = when ((1..9).random()) { // Выбираем продукт
                1 -> Product.BulkyGoods.Bed()
                2 -> Product.BulkyGoods.Table()
                3 -> Product.BulkyGoods.Cupboard()
                4 -> Product.MediumSizedGoods.Chair()
                5 -> Product.MediumSizedGoods.Printer()
                6 -> Product.MediumSizedGoods.Conditioner()
                7 -> Product.SmallSizedGoods.Display()
                8 -> Product.SmallSizedGoods.Laptop()
                else -> Product.SmallSizedGoods.Teapot()
            }
            for (a in 0..howMuch) { // Выбираем количество продукта
                if (truck.workload - goods.weight <= 0) break@loop
                truck.productsInside.add(goods)
                truck.workload -= goods.weight
            }
        }
    }
    return truck
}

// Создает грузовик на загрузку
fun create_Truck_for_Dowload(): Truck {
    val capacityLuck = (1..2).random()
    val truck = Truck(
        when (capacityLuck) {
            1 -> CarryingCapacity.LOW_LOAD_CAPACITY.capacity
            else -> CarryingCapacity.AVERAGE_LOAD_CAPACITY.capacity
        }
    )
    return truck
}

