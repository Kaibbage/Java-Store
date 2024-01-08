//Kai Sundararaj
//101240325
//Hope your exams go well :)

//Base class for all products the store will sell
public abstract class Product implements Comparable<Product>{
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int quantityStillThere;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        soldQuantity = 0;
        quantityStillThere = initQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityStillThere() {
        return quantityStillThere;
    }

    public void setQuantityStillThere(int quantityStillThere) {
        this.quantityStillThere = quantityStillThere;
    }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }



    //Comparing how much each product has sold for the TreeSet of most popular items
    public int compareTo(Product p){
        //Special case for if it's comparing the same number to the same number by adding 1 to break the tie in that instance
        if(soldQuantity  == p.getSoldQuantity()){
            return Double.compare(p.getSoldQuantity() + 1, soldQuantity);
        }
        return Double.compare(p.getSoldQuantity(), soldQuantity);
    }


}