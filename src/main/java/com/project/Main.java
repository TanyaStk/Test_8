package com.project;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Item> shop = new ArrayList<>();
        try (Scanner scannerUsers = new Scanner(new File("users.txt"));
             Scanner scannerShop = new Scanner(new File("shop.txt"))) {
            while (scannerUsers.hasNext()) {
                User user = new User(scannerUsers.nextLine());
                users.add(user);
            }
            while (scannerShop.hasNext()) {
                Item item = new Item(scannerShop.nextLine());
                shop.add(item);
            }
            usersActing(users, shop);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void usersActing(List<User> users, List<Item> shop) {
        String act;
        System.out.println("Enter category to do:\n" +
                "1 - registration\n" +
                "2 - login\n" +
                "3 - view all products\n" +
                "4 - searching products by number (only for ADMIN)\n" +
                "5 - top-N items by frequency\n" +
                "6 - average frequency\n" +
                "7 - exit.");
        try (Scanner scanner = new Scanner(System.in);
             FileWriter usersFile = new FileWriter(new File("users.txt"), true)) {
            User enteredUser = new User();
            while (!(act = scanner.next()).equals("7")) {
                switch (act) {
                    case "1" -> {
                        System.out.println("Enter user information:");
                        String inf = scanner.next();
                        User user = new User(inf);
                        if (!users.contains(user)) {
                            users.add(user);
                            usersFile.write('\n' + user.toString());
                            System.out.println("New user was added.");
                        } else {
                            System.out.println("User's already exists.");
                        }
                        System.out.println("Enter category to do:");
                    }
                    case "2" -> {
                        boolean fail = true;
                        while (fail) {
                            System.out.println("Enter login:");
                            String login = scanner.next();
                            System.out.println("Enter password:");
                            String pass = scanner.next();
                            int index = findUser(login, users);
                            if ((index != -1)) {
                                if (users.get(index).getPassword().equals(pass)) {
                                    fail = false;
                                    enteredUser = users.get(index);
                                    System.out.println("You're logged in!");
                                }
                            }
                        }
                        System.out.println("Enter category to do:");
                    }
                    case "3" -> {
                        for (Item item : shop) {
                            System.out.println(item.toString());
                        }
                        System.out.println("Enter category to do:");
                    }
                    case "4" -> {
                        if (enteredUser.getRole().equals("ADMIN")) {
                            System.out.println("Enter item number:");
                            String number = scanner.next();
                            for (Item item : shop) {
                                if (item.getNumber().equals(number)) {
                                    System.out.println(shop.get(shop.indexOf(item)));
                                }
                            }
                        }
                        else{
                            System.out.println("You're not ADMIN");
                        }
                        System.out.println("Enter category to do:");
                    }
                    case "5" -> {
                        System.out.println("Enter topN:");
                        int topN = scanner.nextInt();
                        shop.sort(Comparator.comparing(Item::getFrequencyOFSearching));
                        for (int i = 0; i < topN; i++) {
                            System.out.println(shop.get(i));
                        }
                    }
                    case "6" -> {
                        System.out.println("Enter category:");
                        String category = scanner.next();
                        System.out.println("Enter date of delivery e.g. 01.01.2020:");
                        Date date = new SimpleDateFormat(
                                "dd.MM.yyyy").parse(scanner.next());
                        double averageFrequency = 0;
                        int amount = 0;
                        for (Item item : shop) {
                            if (item.getCategory().equalsIgnoreCase(category) &&
                                    item.getDeliveryDate().equals(date)) {
                                averageFrequency += item.getFrequencyOFSearching();
                                amount++;
                            }
                        }
                        System.out.println("Average frequency = " + averageFrequency / amount);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int findUser(String login, List<User> users) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

}
