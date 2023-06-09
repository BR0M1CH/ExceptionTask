package ExceptionsTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = new ArrayList<>();
        while (true) {
            System.out.println("Enter user data, format is:\n Lastname Firstname Middlename Birthdate Phonenumber Gender:\n ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            User user;
            try {
                user = User.parseUser(input);
            } catch (InvalidFormatException e) {
                System.out.println("Invalid data format: " + e.getMessage());
                continue;
            }
            users.add(user);
        }
        try {
            User.writeUsersToFile(users);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
