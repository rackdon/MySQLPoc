import dao.DBConnection;
import user.Admin;
import user.Client;
import user.User;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnection.connect();
        Scanner keyboard = new Scanner(System.in);
        Map<String, String> userData;

        System.out.print("Enter the name: ");
        String name = keyboard.nextLine();

        System.out.print("Enter the password: ");
        String password = keyboard.nextLine();

        try {
            Connection connection = DBConnection.getConnection();
            User userHandler = new User(connection, name, password);
            userData = userHandler.getUser();
            userHandler.welcomeUser(userData.get("fullName"));
            Object  user = userHandler.makeUserCategoryEvaluation(userData.get("category"));
            if(user instanceof Admin) {
                ((Admin) user).showOptions();
                ((Admin) user).selectOption();
            } else {
                ((Client) user).showOptions();
                ((Client) user).selectOption();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DBConnection.disconnect();
    }
}
