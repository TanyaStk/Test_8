package com.project;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("Enter statement to do:");
        try (Scanner scanner = new Scanner(System.in);
             FileWriter usersFile = new FileWriter(new File("users.txt"), true)) {
            while (!(act = scanner.next()).equals("0")) {
                User enteredUser = new User();
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
                    }
                    case "2" -> {
                        boolean fail = true;
                        while (fail) {
                            System.out.println("Enter login:");
                            String login = scanner.next();
                            System.out.println("Enter password:");
                            String pass = scanner.next();
                            int index = findUser(login, users);
                            if((index != -1)) {
                                if (users.get(users.indexOf(index)).getPassword().equals(pass)) {
                                    fail = false;
                                    enteredUser = users.get(users.indexOf(index));
                                }
                            }
                        }
                    }
                    case "3" -> {
                        for (Item item : shop) {
                            System.out.println(item.toString());
                        }
                    }
                    case "4" -> {
                        if (enteredUser.getRole().equals("ADMIN")) {
                            String number = scanner.next();
                            if (shop.contains(number)) {
                                System.out.println(shop.get(shop.indexOf(number)));
                            }
                        }
                    }
                }
            }
            System.out.println("Enter statement to do:");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int findUser(String login, List<User> users){
        for(User user : users){
            if(user.getLogin().equals(login)){
                return users.indexOf(user);
            }
        }
        return -1;
    }
}
