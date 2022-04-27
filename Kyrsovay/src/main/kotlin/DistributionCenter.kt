import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object DistributionCenter {
    val unloading_Port_1 = UnloadingPort()
    val unloading_Port_2 = UnloadingPort()
    val unloading_Port_3 = UnloadingPort()
    val download_Port_1 = DownloadPort()
    val download_Port_2 = DownloadPort()
    val download_Port_3 = DownloadPort()
    val download_Port_4 = DownloadPort()
    val download_Port_5 = DownloadPort()
    private val mutex = Mutex()

    //Food_products Warehouses
    val bredWarehouse = mutableListOf<Product.FoodProducts.Bread>()
    val milkWarehouse = mutableListOf<Product.FoodProducts.Milk>()
    val potatoWarehouse = mutableListOf<Product.FoodProducts.Potato>()

    //Bulky_goods Warehouses
    val bedWarehouse = mutableListOf<Product.BulkyGoods.Bed>()
    val tableWarehouse = mutableListOf<Product.BulkyGoods.Table>()
    val cupboardWarehouse = mutableListOf<Product.BulkyGoods.Cupboard>()

    //MediumSized_goods
    val chairWarehouse = mutableListOf<Product.MediumSizedGoods.Chair>()
    val printerWarehouse = mutableListOf<Product.MediumSizedGoods.Printer>()
    val conditionerWarehouse = mutableListOf<Product.MediumSizedGoods.Conditioner>()

    //SmallSized_goods Warehouses
    val laptopWarehouse = mutableListOf<Product.SmallSizedGoods.Laptop>()
    val displayWarehouse = mutableListOf<Product.SmallSizedGoods.Display>()
    val teapotWarehouse = mutableListOf<Product.SmallSizedGoods.Teapot>()


    fun toWorehause(port: UnloadingPort) {
        for (i in port.unload) {
            when (i) {
                is Product.FoodProducts.Bread -> bredWarehouse.add(i)
                is Product.FoodProducts.Milk -> milkWarehouse.add(i)
                is Product.FoodProducts.Potato -> potatoWarehouse.add(i)
                is Product.BulkyGoods.Bed -> bedWarehouse.add(i)
                is Product.BulkyGoods.Table -> tableWarehouse.add(i)
                is Product.BulkyGoods.Cupboard -> cupboardWarehouse.add(i)
                is Product.MediumSizedGoods.Chair -> chairWarehouse.add(i)
                is Product.MediumSizedGoods.Printer -> printerWarehouse.add(i)
                is Product.MediumSizedGoods.Conditioner -> conditionerWarehouse.add(i)
                is Product.SmallSizedGoods.Laptop -> laptopWarehouse.add(i)
                is Product.SmallSizedGoods.Display -> displayWarehouse.add(i)
                else -> teapotWarehouse.add(i as Product.SmallSizedGoods.Teapot)
            }
        }
    }


    suspend fun fromWorehouse(port: DownloadPort, truck: Truck) {
        mutex.withLock {
            runBlocking {

                val goods = when ((1..12).random()) { // Выбераем загружаемый товар
                    1 -> bredWarehouse
                    2 -> milkWarehouse
                    3 -> potatoWarehouse
                    4 -> bedWarehouse
                    5 -> tableWarehouse
                    6 -> cupboardWarehouse
                    7 -> chairWarehouse
                    8 -> printerWarehouse
                    9 -> conditionerWarehouse
                    10 -> laptopWarehouse
                    11 -> displayWarehouse
                    else -> teapotWarehouse
                }
                var check = true
                delay(100)               // Цикл крутится до тех пор, пока грузовик не заполниться полностью 1 товаром
                async {
                    while (check) {
                        port.dowload.addAll(goods)
                        delay(100)
                        var x = 0
                        for (i in port.dowload) {
                            if (truck.workload - i.weight >= 0) {
                                truck.productsInside.add(i)
                                truck.workload -= i.weight
                                x++
                            } else {
                                check = false
                                break
                            }
                        }
                        var k = 0

                        repeat(x) {                       // Имитация продолжительности загрузки и освобождения склада
                            delay((goods[0].loading_time).toLong())
                            goods.removeAt(0)
                            k++
                        }
                        port.dowload.clear()
                    }
                }
            }
        }
    }

    fun whatDoWeHaveOnOurCoolestWarehouse() {
        println("${bredWarehouse.size} - хлеб")
        println("${milkWarehouse.size} - молоко")
        println("${potatoWarehouse.size} - картофель")
        println("${bedWarehouse.size} - кровать")
        println("${tableWarehouse.size} - стол")
        println("${cupboardWarehouse.size} - шкаф")
        println("${chairWarehouse.size} - стул")
        println("${printerWarehouse.size} - принтер")
        println("${conditionerWarehouse.size} - кондиционер")
        println("${laptopWarehouse.size} - ноутбук")
        println("${displayWarehouse.size} - дисплей")
        println("${teapotWarehouse.size} - чайник")

    }
}