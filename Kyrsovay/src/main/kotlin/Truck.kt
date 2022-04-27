class Truck(val capacity: Int) {

    var workload = capacity
    var productsInside = mutableListOf<Product>()
}