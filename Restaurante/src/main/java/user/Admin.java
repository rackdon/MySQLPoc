package user;

import dao.AdminDao;
import dao.utils.UtilsDao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Admin {
    private String SQL_FILE_PATH = "./files/restaurante.sql";
    private String PLATES_TABLE = "plates";
    private String INGREDIENT_TABLE = "ingredients";
    private String CATEGORY_TABlE = "categories";
    private String RECIPE_TABlE = "recipes";

    private AdminDao adminDao;
    private UtilsDao utilsDao;
    private Map<String, String> userData;
    private Scanner keyboard;

    public Admin(Connection connection, Map<String, String> userData) {
        adminDao = new AdminDao(connection);
        utilsDao = new UtilsDao();
        this.userData = userData;
        keyboard = new Scanner(System.in);
    }

    public void showOptions() {
        System.out.println("1- Add category");
        System.out.println("2- Add ingredient");
        System.out.println("3- Add plate");
        System.out.println("4- Make recipe");
        System.out.println("5- Reset Database");
        System.out.println("6- Print table metadata");
        System.out.println("7- Print column metadata");
    }

    public void selectOption() {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please select an option: ");
        int option = keyboard.nextInt();
        try {
            switch (option) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    addIngredient();
                    break;
                case 3:
                    addPlate();
                    break;
                case 4:
                    createRecipe();
                    break;
                case 5:
                    resetDDBB();
                    break;
                case 6:
                    utilsDao.printResults(getTableMetadata());
                    break;
                case 7:
                    utilsDao.printResults(getColumnMetadata());
                    break;
                default:
                    System.out.println("This option is not available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCategory() throws SQLException{
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        System.out.print("Please introduce the name of the category: ");
        String name = keyboard.nextLine();
        System.out.print("Please introduce the name of the owner: ");
        String owner = keyboard.nextLine();
        System.out.print("Please introduce the description: ");
        String description = keyboard.nextLine();

        fields.add("c_name");
        fields.add("ordered");
        fields.add("description");

        values.add(name);
        values.add(owner);
        values.add(description);

        adminDao.addResource(CATEGORY_TABlE, fields, values);
    }

    private void addIngredient() throws SQLException{
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        System.out.print("Please introduce the name of the ingredient: ");
        String name = keyboard.nextLine();
        System.out.print("Please introduce the stock: ");
        Double stock = Double.valueOf(keyboard.nextLine());
        System.out.print("Please introduce the unit of measurement: ");
        String unit = keyboard.nextLine();

        fields.add("i_name");
        fields.add("stock");
        fields.add("unit_of_weight");

        values.add(name);
        values.add(stock);
        values.add(unit);

        adminDao.addResource(INGREDIENT_TABLE, fields, values);
    }

    private void addPlate() throws SQLException{
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        System.out.print("Please introduce the name of the plate: ");
        String name = keyboard.nextLine();
        System.out.print("Please introduce the description: ");
        String description = keyboard.nextLine();
        System.out.print("Please introduce the difficulty: ");
        String difficulty = keyboard.nextLine();
        System.out.print("Please introduce the photo: ");
        String photo = keyboard.nextLine();
        System.out.print("Please introduce the price: ");
        Double price = Double.valueOf(keyboard.nextLine());
        System.out.print("Please introduce the category: ");
        String category = keyboard.nextLine();

        fields.add("p_name");
        fields.add("description");
        fields.add("difficulty");
        fields.add("photo");
        fields.add("price");
        fields.add("category");

        values.add(name);
        values.add(description);
        values.add(difficulty);
        values.add(photo);
        values.add(price);
        values.add(category);

        adminDao.addResource(PLATES_TABLE, fields, values);
    }

    private void createRecipe() throws SQLException{
        String add;
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        System.out.print("Please introduce the name of the plate: ");
        String plateName = keyboard.nextLine();
        do {
            System.out.print("Please introduce the name of the ingredient: ");
            String ingredientName = keyboard.nextLine();
            System.out.print("Please introduce the amount: ");
            Double amount = Double.valueOf(keyboard.nextLine());

            fields.add("p_name");
            fields.add("i_name");
            fields.add("amount");

            values.add(plateName);
            values.add(ingredientName);
            values.add(amount);

            adminDao.addResource(RECIPE_TABlE, fields, values);

            fields.clear();
            values.clear();

            System.out.print("Do yo want to add another ingredient? (y/n");
            add = keyboard.nextLine();
        } while (add.equalsIgnoreCase("y"));
    }

    private void resetDDBB() throws SQLException{
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(SQL_FILE_PATH));
            bufferedReader.lines().reduce((a,b) -> {
                if(a.endsWith(";")) {
                    try {
                        adminDao.executeStatement(a.substring(0, a.length() - 1));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return b;
                } else {
                    return a + b;
                }
            }).map(i -> {
                try {
                    adminDao.executeStatement(i.substring(0, i.length() - 1));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getTableMetadata() throws SQLException{
        System.out.print("Please introduce the name of the table: ");
        String table = keyboard.nextLine();
        return adminDao.getTableMetadata(table);
    }

    private ResultSet getColumnMetadata() throws SQLException{
        System.out.print("Please introduce the name of the table: ");
        String table = keyboard.nextLine();
        return adminDao.getColumnMetadata(table);
    }
}
