import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Created by cdisp on 11/8/2016.
 */
public class InventoryItem  implements java.io.Serializable{
    private char item = ' ';
    private String title = " ";
    private String description = " ";
    private String date = " ";
    private double price = 0.00;
    DecimalFormat df = new DecimalFormat("#0.00");
    private int quantity = 0;
    private static double totalDailySales = 0.00;

    /*Default Constructor*/
    public InventoryItem() {
        this('n', "Title", "Description", "Date", 0.00, 0);
    }

    /*Construct an inventory object*/
    public InventoryItem(char item, String title, String description, String date,
                         double price, int quantity){
        this.item = item;
        this.title = title;
        this.description = description;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
    }

    /*Get item type*/
    public char getItemType(){
        return item;
    }

    /*Set item type*/
    public void setItemType(char item){
        this.item = item;
    }

    /*Get item title*/
    public String getItemTitle(){
        return title;
    }

    /*Set item title*/
    public void setItemType(String title){
        this.title = title;
    }
    /*Get item description*/
    public String getItemDescription(){
        return description;
    }

    /*Set item description*/
    public void setItemDescription(String description){
        this.description = description;
    }

    /*Get item date*/
    public String getItemDate(){
        return date;
    }

    /*Set item date*/
    public void setItemDate(String date){
        this.date = date;
    }

    /*Get item price*/
    public double getItemPrice(){
        return price;
    }

    /*Set item price*/
    public void setItemPrice(double price){
        this.price = price;
    }

    /*Get item quantity*/
    public int getItemQuantity(){
        return quantity;
    }

    /*Set item quantity*/
    public void setItemQuantity(int quantity){
        this.quantity = quantity;
    }

    public void sellItem(int numCopies){
        quantity -= numCopies;
        totalDailySales = totalDailySales + (price * numCopies);
    }

    public double totalDailySales(){
        return totalDailySales;
    }

    public void discardAllQuantity(){
        quantity = 0;
    }

    public void restockItem(int numCopies){
        quantity += numCopies;
    }

    public InventoryItem addNewItem(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter item information");
        System.out.println("Enter item type: n or m:");
        String itemChar = input.nextLine();
        item = itemChar.charAt(0);
        System.out.println("Enter item title:");
        title = input.nextLine();
        System.out.println("Enter item description:");
        description = input.nextLine();
        System.out.println("Enter item publication date: format for newspapers 01 jan 2017 , format for magazines jan 2017 ");
        date = input.nextLine();
        System.out.println("Enter item price:");
        price = input.nextDouble();
        input.nextLine();
        System.out.println("Enter item quantity:");
        quantity = input.nextInt();
        input.nextLine();
        return new InventoryItem(item, title, description, date, price, quantity);
    }

    public String toString() {

        return item + " " + title + " " + description + " " + date + " " + df.format(price) + " " + quantity + '\n';
    }
}
