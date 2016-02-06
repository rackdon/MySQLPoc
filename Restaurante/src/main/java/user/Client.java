package user;

import dao.ClientDao;
import dao.utils.UtilsDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private String PLATES_TABLE = "plates";
    private String CATEGORY_TABlE = "categories";
    private String ORDER_TABLE = "orders";
    private String SINGLE_ORDER_TABLE = "single_order";

    private ClientDao clientDao;
    private Map<String, String> userData;
    private Scanner keyboard;
    private UtilsDao utilsDao;

    public Client(Connection connection, Map<String, String> userData) {
        clientDao = new ClientDao(connection);
        utilsDao = new UtilsDao();
        this.userData = userData;
        keyboard = new Scanner(System.in);
    }


    public void showOptions() {
        System.out.println("1- Show plates");
        System.out.println("2- Show categories");
        System.out.println("3- Show plates from category");
        System.out.println("4- Make order");
    }

    public void selectOption() {
        System.out.print("Please select an option: ");
        int option = Integer.valueOf(keyboard.nextLine());
        try {
            switch (option) {
                case 1:
                    utilsDao.printResults(getPlates());
                    break;
                case 2:
                    utilsDao.printResults(getCategories());
                    break;
                case 3:
                    utilsDao.printResults(getPlatesFromCategory());
                    break;
                case 4:
                    makeOrder();
                    break;
                default:
                    System.out.println("This option is not available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getPlates() throws SQLException{
        return clientDao.getTable(PLATES_TABLE);
    }

    public ResultSet getCategories() throws SQLException{
        return clientDao.getTable(CATEGORY_TABlE);
    }

    public ResultSet getPlatesFromCategory() throws SQLException{
        System.out.print("Please introduce the category: ");
        String category = keyboard.nextLine();
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        fields.add("category");
        values.add(category);
        return clientDao.getResource(PLATES_TABLE, fields, values);
    }

    private void makeOrder() throws SQLException{
        String finish;
        String orderNumber = userData.get("password");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        double orderPrice = 0;
        int plateUnits;

        ArrayList<String> singleOrderFields = new ArrayList<>();
        ArrayList<Object> singleOrderValues = new ArrayList<>();
        ArrayList<String> orderFields = new ArrayList<>();
        ArrayList<Object> orderValues = new ArrayList<>();

        orderFields.add("order_number");
        orderFields.add("username");
        orderFields.add("date");
        orderFields.add("price");

        orderValues.add(orderNumber);
        orderValues.add(userData.get("name"));
        orderValues.add(date);

        utilsDao.printResults(getPlates());
        do {
            try {
                System.out.print("Please introduce the name of the plate: ");
                String plateName = String.valueOf(keyboard.nextLine());
                System.out.print("Please introduce the number of units: ");
                plateUnits = Integer.valueOf(keyboard.nextLine());

                orderPrice += getPlatePrice(plateName);

                singleOrderFields.add("order_number");
                singleOrderFields.add("plate");
                singleOrderFields.add("amount");

                singleOrderValues.add(orderNumber);
                singleOrderValues.add(plateName);
                singleOrderValues.add(plateUnits);

                clientDao.addResource(SINGLE_ORDER_TABLE, singleOrderFields, singleOrderValues);

                singleOrderFields.clear();
                singleOrderValues.clear();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.print("Have you finished your order? (y/n): ");
            finish = keyboard.nextLine();
        } while (finish.equalsIgnoreCase("n"));


        if(orderPrice > 0) {
            orderValues.add(orderPrice);
            clientDao.addResource(ORDER_TABLE, orderFields, orderValues);
        }

    }

    public double getPlatePrice(String plateName) throws SQLException{
        ArrayList<String> plateFields = new ArrayList<>();
        ArrayList<String> plateValues = new ArrayList<>();
        plateFields.add("p_name");
        plateValues.add(plateName);
        ResultSet resultSet = clientDao.getResource(PLATES_TABLE, plateFields, plateValues);
        if(resultSet.next()) {
            return resultSet.getDouble("precio");
        } else {
            throw new SQLException("That plate does not exist");
        }
    }
}
