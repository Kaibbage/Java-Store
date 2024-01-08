//Kai Sundararaj
//101240325
//Hope your exams go well :)

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.collections.FXCollections;


public class ElectronicStoreView extends Pane{

    //Creating attributes for each of the things being displayed
    private ListView<Product> popularItemsList, allItemsList;
    private ListView<String> itemsInCartList;
    private Button resetStoreButton, addToCartButton, removeFromCartButton, completeSaleButton;
    private TextField salesField, revenueField, $saleField;
    private ElectronicStore model;
    private Label label3;

    //Update method
    public void update(){
        label3.setText("Current Cart: ($" + String.format("%.2f", model.getCartMoney()) + "):");
        allItemsList.setItems(FXCollections.observableArrayList(model.getStockNoNull()));
        itemsInCartList.setItems(FXCollections.observableArrayList(model.getCartWords()));
        popularItemsList.setItems(FXCollections.observableArrayList(model.get3PopItems()));

        salesField.setText(Integer.toString(model.getSales()));
        revenueField.setText(String.format("%.2f",(model.getRevenue())));

        if(model.getRevenue() == 0 && model.getSales() == 0){
            $saleField.setText("N/A");
        }
        else{
            $saleField.setText(String.format("%.2f",(model.getRevenue()/model.getSales())));
        }

    }


    //Initializing the view
    public ElectronicStoreView(ElectronicStore model1) {
        model = model1;
        //Creating the Labels
        Label label1 = new Label("Store Summary:");
        label1.relocate(40, 15);
        Label label2 = new Label("Store Stock:");
        label2.relocate(290, 15);
        label3 = new Label("Current Cart: ($" + String.format("%.2f", model.getCartMoney()) + "):");
        label3.relocate(575, 15);
        Label label4 = new Label("# Sales:");
        label4.relocate(20, 55);
        Label label5 = new Label("Revenue:");
        label5.relocate(13, 95);
        Label label6 = new Label("$ / Sale:");
        label6.relocate(20, 135);
        Label label7 = new Label("Most Popular Items:");
        label7.relocate(35, 168);

        // Create the lists
        popularItemsList = new ListView<Product>();
        popularItemsList.relocate(10, 190);
        popularItemsList.setPrefSize(150,140);

        allItemsList = new ListView<Product>();
        allItemsList.relocate(170, 40);
        allItemsList.setPrefSize(300,290);

        itemsInCartList = new ListView<String>();
        itemsInCartList.relocate(480, 40);
        itemsInCartList.setPrefSize(300,290);

        //Creating the Buttons, setting the ones that need to be disabled to be disabled at first
        resetStoreButton = new Button("Reset Store");
        resetStoreButton.setStyle("-fx-font: 12 arial");
        resetStoreButton.relocate(10, 340);
        resetStoreButton.setPrefSize(150,50);

        addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-font: 12 arial");
        addToCartButton.relocate(245, 340);
        addToCartButton.setPrefSize(150,50);
        addToCartButton.setDisable(true);

        removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.setStyle("-fx-font: 12 arial;");
        removeFromCartButton.relocate(480, 340);
        removeFromCartButton.setPrefSize(150,50);
        removeFromCartButton.setDisable(true);

        completeSaleButton = new Button("Complete Sale");
        completeSaleButton.setStyle("-fx-font: 12 arial;");
        completeSaleButton.relocate(630, 340);
        completeSaleButton.setPrefSize(150,50);
        completeSaleButton.setDisable(true);

        //Creating the Text Fields
        salesField = new TextField();
        salesField.relocate(70, 50);
        salesField.setPrefSize(90, 30);

        revenueField = new TextField();
        revenueField.relocate(70, 90);
        revenueField.setPrefSize(90, 30);

        $saleField = new TextField();
        $saleField.relocate(70, 130);
        $saleField.setPrefSize(90, 30);


        //Adding all the components to the pane
        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, popularItemsList, allItemsList, itemsInCartList, resetStoreButton, addToCartButton, removeFromCartButton, completeSaleButton, salesField, revenueField, $saleField);

        //Setting the size of the window
        setPrefSize(800, 400);
    }

    //Getters
    public ListView<Product> getAllItemsList() {
        return allItemsList;
    }

    public ListView<String> getItemsInCartList() {
        return itemsInCartList;
    }

    public ListView<Product> getPopularItemsList() {
        return popularItemsList;
    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getCompleteSaleButton() {
        return completeSaleButton;
    }

    public Button getRemoveFromCartButton() {
        return removeFromCartButton;
    }

    public Button getResetStoreButton() {
        return resetStoreButton;
    }

    public TextField get$saleField() {
        return $saleField;
    }

    public TextField getRevenueField() {
        return revenueField;
    }

    public TextField getSalesField() {
        return salesField;
    }

    //setter
    public void setModel(ElectronicStore model) {
        this.model = model;
    }
}
