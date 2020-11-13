package com.project;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, User> users = new HashMap<>();
        Map<String, Item> shop = new HashMap<>();
        try (Scanner scannerUsers = new Scanner(new File("users.txt"));
             Scanner scannerShop = new Scanner(new File("shop.txt"))) {
            while (scannerUsers.hasNext()) {
                User user = new User(scannerUsers.nextLine());
                users.put(user.getLogin(), user);
            }
            while (scannerShop.hasNext()) {
                Item item = new Item(scannerShop.nextLine());
                shop.put(item.getNumber(), item);
            }
            usersActing(users, shop);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void usersActing(Map<String, User> users,
                            Map<String, Item> shop) {
        String act;
        System.out.println("Choose category to do:\n" +
                "1 - registration\n" +
                "2 - login\n" +
                "3 - view all products\n" +
                "4 - searching products by number (only for ADMIN)\n" +
                "5 - top-N items by frequency\n" +
                "6 - average frequency\n" +
                "7 - exit.");
        try (Scanner scanner = new Scanner(System.in);
             FileWriter usersFile = new FileWriter(
                     new File("users.txt"), true)) {
            User enteredUser = new User();
            while (!(act = scanner.next()).equals("7")) {
                switch (act) {
                    case "1" -> {
                        System.out.println("Enter user information:");
                        String inf = scanner.next();
                        User user = new User(inf);
                        if (!users.containsKey(user.getLogin())) {
                            users.put(user.getLogin(), user);
                            usersFile.write('\n' + user.toString());
                            System.out.println("New user was added.");
                        } else {
                            System.out.println("User's already exists.");
                        }
                        System.out.println("Choose category to do:");
                    }
                    case "2" -> {
                        boolean fail = true;
                        while (fail) {
                            System.out.println("Enter login:");
                            String login = scanner.next();
                            System.out.println("Enter password:");
                            String pass = scanner.next();
                            if (users.containsKey(login)) {
                                while (fail) {
                                    if (users.get(login).getPassword().equals(pass)) {
                                        fail = false;
                                        enteredUser = users.get(login);
                                        System.out.println("You're logged in!");
                                    } else {
                                        System.out.println("Wrong password! Try again.");
                                        pass = scanner.next();
                                    }
                                }
                            }
                        }
                        System.out.println("Choose category to do:");
                    }
                    case "3" -> {
                        if (!enteredUser.getLogin().isEmpty()) {
                            for (Map.Entry<String, Item> item : shop.entrySet()) {
                                System.out.println(item.getValue().toString());
                            }
                            System.out.println("Choose category to do:");
                        } else {
                            System.out.println("You haven't logged in." +
                                    "Register or log in");
                        }
                    }
                    case "4" -> {
                        if (enteredUser.getRole().equals(Role.ADMIN)) {
                            System.out.println("Enter item number:");
                            String number = scanner.next();
                            for (Map.Entry<String, Item> item : shop.entrySet()) {
                                if (item.getKey().equals(number)) {
                                    System.out.println(item.getValue() + "\n");
                                }
                            }
                        } else {
                            System.out.println("You're not ADMIN");
                        }
                        System.out.println("Choose category to do:");
                    }
                    case "5" -> {
                        if (!enteredUser.getLogin().isEmpty()) {
                            System.out.println("Enter topN:");
                            int topN = scanner.nextInt();
                            Map<String, Item> result = shop.entrySet()
                                    .stream()
                                    .sorted(Map.Entry.<String, Item>comparingByValue().reversed())
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            Map.Entry::getValue,
                                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                            for (Map.Entry<String, Item> item : result.entrySet()) {
                                System.out.println(item.getValue());
                                topN--;
                                if (topN == 0) {
                                    break;
                                }
                            }
                        } else {
                            System.out.println("You haven't logged in. " +
                                    "Register or log in");
                        }
                        System.out.println("Choose category to do:");
                    }
                    case "6" -> {
                        if (!enteredUser.getLogin().isEmpty()) {
                            System.out.println("Enter category:");
                            String category = scanner.next();
                            System.out.println("Enter date of delivery e.g. 01.01.2020:");
                            Date date = new SimpleDateFormat(
                                    "dd.MM.yyyy").parse(scanner.next());
                            double averageFrequency = 0;
                            int amount = 0;
                            for (Map.Entry<String, Item> item : shop.entrySet()) {
                                if (item.getValue().getCategory()
                                        .equalsIgnoreCase(category) &&
                                        item.getValue()
                                                .getDeliveryDate().equals(date)) {
                                    averageFrequency +=
                                            item.getValue().getFrequencyOFSearching();
                                    amount++;
                                }
                            }
                            System.out.println("Average frequency = "
                                    + averageFrequency / amount);
                            System.out.println("Choose category to do:");
                        } else {
                            System.out.println("You haven't logged in. " +
                                    "Register or log in");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
