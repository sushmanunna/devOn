package testt;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

enum CoinType {
    CENTS_5(0.05),
    CENTS_10(0.10),
    CENTS_20(0.20),
    CENTS_50(0.50),
    DOLLAR_1(1.0),
    DOLLAR_2(2.0);

    private final double value;

    CoinType(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

class Coin {
    private final CoinType type;

    public Coin(CoinType type) {
        this.type = type;
    }

    public double getValue() {
        return type.getValue();
    }
}

class Drink {
    private final String name;
    private final double price;

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class DrinkMachine {
    private final Map<Integer, Drink> drinks;
    private double userBalance;

    public DrinkMachine() {
        this.drinks = new HashMap<>();
        this.userBalance = 0;
        initializeDefaultDrinks();
    }

    private void initializeDefaultDrinks() {
        addDrink(1, new Drink("Coca", 1.0));
        addDrink(2, new Drink("Redbull", 1.25));
        addDrink(3, new Drink("Water", 0.5));
        addDrink(4, new Drink("Orange Juice", 1.95));
    }

    public void addDrink(int code, Drink drink) {
        drinks.put(code, drink);
    }

    public void displayStock() {
        System.out.println("Available drinks:");
        drinks.forEach((code, drink) -> System.out.println(code + ": " + drink.getName()));
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void insertCoin(Coin coin) {
        userBalance += coin.getValue();
    }

    public void cancelOrder() {
        System.out.println("Order cancelled. Your money will be refunded: " + userBalance);
        userBalance = 0;
    }

    public void dispenseDrink(int code) {
        if (drinks.containsKey(code)) {
            Drink selectedDrink = drinks.get(code);
            double drinkPrice = selectedDrink.getPrice();

            if (userBalance >= drinkPrice) {
                System.out.println("Dispensing " + selectedDrink.getName());

                // Calculate and return change
                double change = userBalance - drinkPrice;
                if (change > 0) {
                    System.out.println("Returning change: " + change);
                }

                // Reset user balance
                userBalance = 0;
            } else {
                System.out.println("Insufficient balance. Please insert more coins.");
            }
        } else {
            System.out.println("Invalid drink code. Please select a valid drink.");
        }
    }
}

public class DevOn {
    public static void main(String[] args) {
        DrinkMachine drinkMachine = new DrinkMachine();

        // Display available drinks
        drinkMachine.displayStock();

        // Example user interaction
        drinkMachine.insertCoin(new Coin(CoinType.DOLLAR_2));
        System.out.println("Your current balance: " + drinkMachine.getUserBalance());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter drink code or type 'cancel' to cancel the order:");
        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("cancel")) {
            drinkMachine.cancelOrder();
        } else {
            int drinkCode = Integer.parseInt(userInput);
            drinkMachine.dispenseDrink(drinkCode);
        }
    }
}
