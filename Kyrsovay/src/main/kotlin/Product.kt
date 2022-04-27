sealed class Product {
    open val weight: Int = 0
    open val loading_time = 0
    open val productName = ""

    abstract class FoodProducts : Product() {
        override val weight: Int = 0
        override val loading_time = 0

        class Bread : Product.FoodProducts() {
            override val weight: Int = 3
            override val loading_time: Int = 30
            override val productName = "Хлеб"
        }

        class Milk : Product.FoodProducts() {
            override val weight: Int = 2
            override val loading_time: Int = 20
            override val productName = "Молоко"
        }

        class Potato : Product.FoodProducts() {
            override val weight: Int = 1
            override val loading_time: Int = 10
            override val productName = "Картофель"
        }
    }

    // *******************************************
    abstract class BulkyGoods : Product() {
        override val weight: Int = 0
        override val loading_time = 0

        class Bed : Product.BulkyGoods() {
            override val weight: Int = 15
            override val loading_time: Int = 150
            override val productName = "Кровать"
        }

        class Table : Product.BulkyGoods() {
            override val weight: Int = 14
            override val loading_time: Int = 140
            override val productName = "Стол"
        }

        class Cupboard : Product.BulkyGoods() {
            override val weight: Int = 13
            override val loading_time: Int = 130
            override val productName = "Шкаф"
        }
    }

    // *******************************************
    abstract class MediumSizedGoods : Product() {
        override val weight: Int = 0
        override val loading_time = 0

        class Chair : Product.MediumSizedGoods() {
            override val weight: Int = 12
            override val loading_time: Int = 120
            override val productName = "Стул"
        }

        class Printer : Product.MediumSizedGoods() {
            override val weight: Int = 11
            override val loading_time: Int = 110
            override val productName = "Принтер"
        }

        class Conditioner : Product.MediumSizedGoods() {
            override val weight: Int = 11
            override val loading_time: Int = 110
            override val productName = "Кондиционер"
        }
    }

    // *******************************************
    abstract class SmallSizedGoods : Product() {
        override val weight: Int = 0
        override val loading_time = 0

        class Laptop : Product.SmallSizedGoods() {
            override val weight: Int = 8
            override val loading_time: Int = 80
            override val productName = "Ноутбук"
        }

        class Display : Product.SmallSizedGoods() {
            override val weight: Int = 7
            override val loading_time: Int = 70
            override val productName = "Дисплей"
        }

        class Teapot : Product.SmallSizedGoods() {
            override val weight: Int = 6
            override val loading_time: Int = 60
            override val productName = "Чайник"
        }
    }
}





