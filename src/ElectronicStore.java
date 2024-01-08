//Kai Sundararaj
//101240325
//Hope your exams go well :)

//Class representing an electronic store
//Has an array of products that represent the items the store can sell


import java.util.*;
import java.util.ArrayList;



public class ElectronicStore {
    //Initializing attributes
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    public final int NUM_POP_PRODUCTS = 3;
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private Product[] allStock; //Array for all of the stock, even the ones that have run out
    private double revenue;
    //Added cart array to hold all items in cart
    private Product[] cart;
    //Added attribute to keep track of how many diff product in cart
    private int curCart;
    //Added array attribute to keep track of how many of each product in list
    private HashMap<Product,Integer> numEachProductInCart;
    //Added attribute to keep track of money in cart
    private double cartMoney;
    //Added sales attribute to count number of sales
    private int sales;


    //Constructor
    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        allStock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cart = new Product[MAX_PRODUCTS];
        curCart = 0;
        numEachProductInCart = new HashMap<Product, Integer>();
        cartMoney = 0;
        sales = 0;
    }





    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            if(isAlreadyThere(allStock, newProduct) == false){
                allStock[curProducts] = newProduct;
            }
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    //Method to remove a product from the stock when it runs out
    public boolean removeProduct(Product p, Boolean isStock){
        //If removing from stock, doing the according things
        if(isStock){
            for(int i = 0; i < MAX_PRODUCTS; i++){
                if(stock[i] == p){
                    stock[i] = null;
                    curProducts--;
                    stock = noNullArray(stock);
                    return true;
                }
            }
        }
        //If not removing from stock (removing from cart) then removing the appropriate things
        else{
            for(int i = 0; i < MAX_PRODUCTS; i++){
                if(cart[i] == p){
                    cart[i] = null;
                    curCart--;
                    numEachProductInCart.remove(p);
                    cart = noNullArray(cart);
                    return true;
                }
            }
        }
        return false;
    }

    //Pre made method to initialize store
    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    //method to return an array as a list with no null entries
    public ArrayList<Product> noNull(Product[] items){
        ArrayList<Product> noNullItems = new ArrayList<Product>();
        for(int i = 0; i < MAX_PRODUCTS; i++){
            if(items[i] != null){
                noNullItems.add(items[i]);
            }
        }

        return noNullItems;
    }

    //Method to return an array as an array with no null entries
    public Product[] noNullArray(Product[] items){
        Product[] noNullItems = new Product[MAX_PRODUCTS];

        int j = 0;

        for(int i = 0; i < MAX_PRODUCTS; i++){
            if(items[i] != null){
                noNullItems[j] = items[i];
                j++;
            }
        }

        return noNullItems;

    }



    //using method to get list with no null entries to return list of stock with no null entries
    public ArrayList<Product> getStockNoNull(){
        ArrayList<Product> noNullStock = new ArrayList<Product>(noNull(stock));
        return noNullStock;
    }

    //using method to get list with no null entries to return list of cart with no null entries and method to add the numbers of each in a string
    public ArrayList<Product> getCartNoNull(){
        ArrayList<Product> noNullCart = new ArrayList<Product>(noNull(cart));
        return noNullCart;
    }


    //Getters
    public Product[] getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public int getSales() {
        return sales;
    }

    public double getRevenue() {
        return revenue;
    }

    public HashMap<Product, Integer> getNumEachProductInCart() {
        return numEachProductInCart;
    }

    public double getCartMoney() {
        return cartMoney;
    }

    //Setters
    public void setCartMoney(double cartMoney) {
        this.cartMoney = cartMoney;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void setRevenue(double r){
        revenue = r;
    }

    public void setCurCart(int c){
        curCart = c;
    }


    public Product[] getCart() {
        return cart;
    }

    //Method to get the 3 most popular items using a treeset
    public Product[] get3PopItems() {
        //Creating arraylist from array
        ArrayList<Product> stockInList = new ArrayList<Product>();
        for(int i = 0; i < MAX_PRODUCTS; i++){
            if(allStock[i] != null){
                stockInList.add(allStock[i]);
            }
        }

        //creating treeset from arraylist
        TreeSet<Product> popItems = new TreeSet<Product>(stockInList);

        //creating organized arraylist from treeset
        ArrayList<Product> intermediateList = new ArrayList<Product>(popItems);



        //Creating array of top 3 organized
        Product[] top3List = new Product[NUM_POP_PRODUCTS];

        for(int i = 0; i < NUM_POP_PRODUCTS; i++){
            top3List[i] = intermediateList.get(i);
        }
        return top3List;
    }


    //Method to add products to the cart
    public void addToCart(Product p){

        //If the product is already there, then increasing the amount in the cart by 1, and decreasing the amount that was in store by 1
        if(isAlreadyThere(cart, p)) {
            p.setQuantityStillThere(p.getQuantityStillThere() - 1);
            int numWasThere = numEachProductInCart.get(p);
            numEachProductInCart.put(p, numWasThere + 1);


        }
        else{
            //If the product wasn't already there, making a new spot for the product and increasing and decreasing the same things
            p.setQuantityStillThere(p.getQuantityStillThere() - 1);
            cart[curCart] = p;
            numEachProductInCart.put(p, 1);
            curCart++;
        }

        //If the amount in the store hits 0, removing the product from the store
        if (p.getQuantityStillThere() == 0) {
            removeProduct(p, true);
        }



    }



    //Method to check if a product is already in the array
    public boolean isAlreadyThere(Product[] products, Product p){
        boolean isThere = false;
        for(int i = 0; i < MAX_PRODUCTS; i++){
            if(p.equals(products[i])){
                isThere = true;
            }
        }
        return isThere;
    }

    //Method to make an arraylist of the cart that includes the amount of each and the specs
    public ArrayList<String> getCartWords(){
        ArrayList<String> cartWithAmount = new ArrayList<String>();

        for(int i = 0; i < getCartNoNull().size(); i++){
            cartWithAmount.add(numEachProductInCart.get(getCartNoNull().get(i)) + " x " + getCartNoNull().get(i).toString());
        }

        return cartWithAmount;
    }

    //Method to clear the cart of all its products and the hashmap of number of products of all its entries
    public void clearCart(){
        for(int i = 0; i < MAX_PRODUCTS; i++){
            cart[i] = null;
        }

        numEachProductInCart.clear();


    }
} 