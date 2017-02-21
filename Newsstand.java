import java.io.*;
import java.util.*;

/**
 * Created by cdisp on 11/8/2016.
 */
public class Newsstand extends InventoryItem {
    private static final String[] MENU = {"list total inventory", "list newspapers", "list magazines", "sell an item",
            "show an item’s description", "report the total of today's sales", "discard all copies of an item", "restock an item", "add a new item to the inventory",
            "terminate the Newsstand at the end of the day", "terminate the Newsstand at the end of the month"};

    public static void main(String[] args) throws ClassNotFoundException, NoSuchElementException, IOException {

        //create file names
        File newMonth = new File("monthstart.txt");
        File newDay = new File("daystart.txt");

        //create ArrayList of InventoryItems for day
        ArrayList<InventoryItem> inventoryDailyList = new ArrayList<>();

        //scanner for user input
        Scanner userInput = new Scanner(System.in);

        try {
            if (!newDay.exists()) {
                try (
                        //create daily inventory file
                        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(newDay))
                ) {
                    //input data from monthly inventory file
                    Scanner input = new Scanner(newMonth);
                    while (input.hasNext()) {
                        String itemChar = input.nextLine();
                        char item = itemChar.charAt(0);
                        String title = input.nextLine();
                        String description = input.nextLine();
                        String date = input.nextLine();
                        double price = input.nextDouble();
                        input.nextLine();
                        int quantity = input.nextInt();
                        input.nextLine();

                        //write the objects to the daily inventory file
                        output.writeObject(new InventoryItem(item, title, description, date, price, quantity));
                    }
                    input.close();
                }
            }
            if (newDay.exists()) {

                InventoryItem inventoryObjects;
                try (
                        ObjectInputStream inputS = new ObjectInputStream(new FileInputStream(newDay))
                ) {
                    while (true) {
                        //get inventory objects from daily inventory and add to inventory list
                        inventoryObjects = (InventoryItem) inputS.readObject();
                        inventoryDailyList.add(inventoryObjects);
                    }
                }
           }
        } catch (EOFException ex) {
            System.out.println("Current inventory has been read into newsstand and newsstand is now open");
            System.out.println();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //display menu
        int userChoice = -1;
        userChoice = Utils.userChooseListing(userInput, MENU);

        while (userChoice != -1) {
            Scanner input = new Scanner(System.in);
            double totalDaySales = 0.00;
            int x;
            int numOfCopies;
            switch (userChoice) {
                case 0:
                    //display daily inventory
                    System.out.println("Current Inventory:");
                    Utils.listing(inventoryDailyList);
                    break;
                case 1:
                    //display newspapers
                    System.out.println("Current Newspapers Inventory:");
                    ArrayList<InventoryItem> inventoryNewspaperList = new ArrayList<>();
                    for (InventoryItem inventoryDailyListItem : inventoryDailyList)
                        if (inventoryDailyListItem.getItemType() == 'n')
                            inventoryNewspaperList.add(inventoryDailyListItem);
                    Utils.listing(inventoryNewspaperList);
                    break;
                case 2:
                    //display magazines
                    System.out.println("Current Magazine Inventory:");
                    ArrayList<InventoryItem> inventoryMagazineList = new ArrayList<>();
                    for (InventoryItem inventoryDailyListItem : inventoryDailyList)
                        if (inventoryDailyListItem.getItemType() == 'm')
                            inventoryMagazineList.add(inventoryDailyListItem);
                    Utils.listing(inventoryMagazineList);
                    break;
                case 3:
                    //sell an item
                    x = Utils.userChoose(input, inventoryDailyList, "Choose an item to sell");
                    System.out.println("Enter number of copies to sell:");
                    numOfCopies = input.nextInt();
                    inventoryDailyList.get(x).sellItem(numOfCopies);
                    System.out.println("You have sold " + numOfCopies + " copies of the " + inventoryDailyList.get(x).getItemDate() +
                            " publication date of the " + inventoryDailyList.get(x).getItemTitle());
                    System.out.println("Quantity of " + inventoryDailyList.get(x).getItemTitle() + " is now " + inventoryDailyList.get(x).getItemQuantity());
                    break;
                case 4:
                    //display item description
                    x = Utils.userChoose(input, inventoryDailyList, "Choose an item to see description");
                    System.out.println(inventoryDailyList.get(x).getItemTitle() + " is a " + inventoryDailyList.get(x).getItemDescription());
                    break;
                case 5:
                    //display total daily sales
                    for (InventoryItem inventoryDailyListItem : inventoryDailyList)
                        totalDaySales = inventoryDailyListItem.totalDailySales();
                    System.out.printf("The total daily sales are $%,.2f", totalDaySales);
                    break;
                case 6:
                    //discard all copies of an item
                    x = Utils.userChoose(input, inventoryDailyList, "Choose an item to discard");
                    inventoryDailyList.get(x).discardAllQuantity();
                    System.out.println("All copies of " + inventoryDailyList.get(x).getItemTitle() + " have been discarded and " +
                            "the quantity of " + inventoryDailyList.get(x).getItemTitle() + " is now " + inventoryDailyList.get(x).getItemQuantity());
                    break;
                case 7:
                    //restock an item
                    x = Utils.userChoose(input, inventoryDailyList, "Choose an item to restock");
                    System.out.println("Enter number of copies to order:");
                    numOfCopies = input.nextInt();
                    inventoryDailyList.get(x).restockItem(numOfCopies);
                    System.out.println(inventoryDailyList.get(x).getItemTitle() + " has been restocked and " +
                            " the quantity of " + inventoryDailyList.get(x).getItemTitle() + " is now " + inventoryDailyList.get(x).getItemQuantity());
                    break;
                case 8:
                    //add new item to inventory
                    InventoryItem newItem = new InventoryItem().addNewItem();
                    inventoryDailyList.add(newItem);
                    System.out.println("The " + newItem.getItemDate() + " publication date of " + newItem.getItemTitle() +
                            " with a sales price of " + newItem.getItemPrice() + " has been added to the inventory with " +
                            newItem.getItemQuantity() + " copies in stock");
                    break;
                case 9:
                    //save end of day inventory file
                        try (
                                ObjectOutputStream outputDay = new ObjectOutputStream(new FileOutputStream(newDay))
                        ) {
                            for (InventoryItem inventoryDailyListItem : inventoryDailyList) {
                                outputDay.writeObject(inventoryDailyListItem);
                            }
                        }
                    System.out.println("You have closed out the day");
                    System.out.println("Newsstand is now closed");
                    System.out.println("Thank you, goodbye");
                    System.exit(0);
                    break;
                case 10:
                    //delete daystart.txt file
                    String deleteConfirm;
                    //confirm that you have deleted daystart.txt
                    //and closed out the month
                    if (newDay.delete()) {
                        deleteConfirm = "You have closed out the month. \n " +
                                "Newsstand is now closed. \n" +
                                "Thank you, goodbye";
                        System.out.println(deleteConfirm);
                        System.exit(0);
                    }
                    else {
                        deleteConfirm = "You have not closed out the month. \n" +
                        "please try again";
                        System.out.println(deleteConfirm);
                    }
                    break;
                default:
                    break;
            }
                System.out.println();
                userChoice = Utils.userChooseListing(userInput, MENU);
        }
    }
}
