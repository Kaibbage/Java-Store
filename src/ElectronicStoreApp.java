//Kai Sundararaj
//101240325
//Hope your exams go well :)

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;

public class ElectronicStoreApp extends Application{
    //Model
    ElectronicStore model;

    //Constructor to initialize model
    public ElectronicStoreApp() {
        model = ElectronicStore.createStore();
    }

    //Creating window
    public void start(Stage primaryStage) {
        Pane  eStorePane = new Pane();

        ElectronicStoreView view = new ElectronicStoreView(model);
        view.setModel(model);
        eStorePane.getChildren().add(view);
        view.update();


        primaryStage.setTitle(model.getName());
        //Making window non resizable
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(eStorePane));
        primaryStage.show();


        //Enables add button when something is selected in stock
        view.getAllItemsList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(view.getAllItemsList().getSelectionModel().getSelectedItem() != null){
                    view.getAddToCartButton().setDisable(false);
                }

            }
        });

        //Actions for when add to cart button is clicked
        view.getAddToCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                //Adding stuff to the cart, if all is added of that product then removing that product
                model.addToCart(view.getAllItemsList().getSelectionModel().getSelectedItem());
                if(view.getAllItemsList().getSelectionModel().getSelectedItem().getQuantityStillThere() == 0){
                    view.getAddToCartButton().setDisable(true);
                }


                //If there are items in cart then allowing the complete sale button to be pressed
                if(view.getItemsInCartList() != null){
                    view.getCompleteSaleButton().setDisable(false);
                }

                //Updating the amount of money in the cart by increasing it by the price of one unit of the product being added
                model.setCartMoney(model.getCartMoney() + view.getAllItemsList().getSelectionModel().getSelectedItem().getPrice());

                //Updating the view
                view.update();


            }
        });

        //Enables remove button when something is selected in cart
        view.getItemsInCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(view.getItemsInCartList().getSelectionModel().getSelectedItem() != null){
                    view.getRemoveFromCartButton().setDisable(false);
                }

            }
        });


        //Actions for when remove button is pressed
        view.getRemoveFromCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Checking for the correct product to be removed inside the string
                for(int i = 0; i < model.getCartNoNull().size(); i++){
                    if(view.getItemsInCartList().getSelectionModel().getSelectedItem().contains(model.getCartNoNull().get(i).toString())){
                        //Setting the correct product to be removed to be named rightProduct
                        Product rightProduct = model.getCartNoNull().get(i);

                        //Removing one unit of the rightProduct from the cart (tracked in hashmap)
                        int numThereBefore = model.getNumEachProductInCart().get(rightProduct);
                        model.getNumEachProductInCart().put(rightProduct, numThereBefore - 1);

                        //If the product being removed from the cart had none left in stock, returning the product to the stock with one unit (done below this if statement)
                        if(rightProduct.getQuantityStillThere() == 0){
                            model.addProduct(rightProduct);
                        }

                        //Adding one additional unit back to the stock
                        rightProduct.setQuantityStillThere(rightProduct.getQuantityStillThere() + 1);

                        //If that was the only unit of that product remaining in the cart, removing it from the cart
                        if(numThereBefore == 1){
                            model.removeProduct(rightProduct, false);
                        }

                        //Decreasing the amount of money the cart costs by the price of the product
                        model.setCartMoney(model.getCartMoney() - rightProduct.getPrice());



                        //Updating the view
                        view.update();

                        //If there is nothing selected in the cart list, disabling the remove button
                        if(view.getItemsInCartList().getSelectionModel().getSelectedItem() == null){
                            view.getRemoveFromCartButton().setDisable(true);
                        }

                        //If after this item is removed there is nothing left in the cart then disabling the complete sale button
                        if(model.getCart()[0] == null){
                            view.getCompleteSaleButton().setDisable(true);
                        }

                        //Breaking after found right item to remove
                        break;
                    }

                }

            }
        });


        //Actions for when complete sale button is clicked
        view.getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Selling the units and changing the number sold and number in stock for each of the products in the cart according to the number of each in the cart
                for(int i = 0; i < model.MAX_PRODUCTS; i++){
                    if(model.getNumEachProductInCart().get(model.getCart()[i]) != null){
                        model.getCart()[i].sellUnits(model.getNumEachProductInCart().get(model.getCart()[i]));
                    }
                }
                //Clearing the cart using the clearCart method
                model.clearCart();

                //Setting the the number of sales to be one more
                model.setSales(model.getSales() + 1);

                //Increasing the revenue by the amount the cart was worth
                model.setRevenue(model.getRevenue() + model.getCartMoney());

                //Setting the amount the cart is worth to 0
                model.setCartMoney(0);

                //Setting the amount of items in the cart to 0
                model.setCurCart(0);

                //Disabling the remove from cart button as there is nothing to remove
                view.getRemoveFromCartButton().setDisable(true);

                //Updating the view
                view.update();

                //Disabling the complete sale button after the sale has been completed
                view.getCompleteSaleButton().setDisable(true);

            }
        });


        //Displays in console the amount of items sold if you click on one of the 3 most popular items
        view.getPopularItemsList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println(view.getPopularItemsList().getSelectionModel().getSelectedItem() + " Has sold " + view.getPopularItemsList().getSelectionModel().getSelectedItem().getSoldQuantity() + " units.");

            }
        });

        //Button to reset store to original state
        view.getResetStoreButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model = ElectronicStore.createStore();
                view.setModel(model);
                view.update();

                //Disabling the add button after the reset
                view.getAddToCartButton().setDisable(true);
            }
        });




    }

    //main
    public static void main(String[] args) {
        launch(args);
    }

}
