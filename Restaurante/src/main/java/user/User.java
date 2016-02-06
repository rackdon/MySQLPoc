package user;

import dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String TABLE = "users";

    private Connection connection;
    private UserDao userDao;
    private Map<String, String> userData;

    public User(Connection connection, String name, String password) {
        this.connection = connection;
        userDao = new UserDao(connection);
        userData = new HashMap<>();
        userData.put("name", name);
        userData.put("password", password);
    }


    public Map<String, String> getUser() throws SQLException{
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        fields.add("username");
        fields.add("password");
        values.add(userData.get("name"));
        values.add(userData.get("password"));
        ResultSet resultSet = userDao.getResource(TABLE, fields, values);

        if(resultSet.next()) {
            userData.put("name", resultSet.getString(1));
            userData.put("password", resultSet.getString(2));
            userData.put("fullName", resultSet.getString(3));
            userData.put("category", resultSet.getString(4));
            return userData;
        } else {
            throw new SQLException("User does not exists");
        }
    }

    public void welcomeUser(String fullName) {
        System.out.println("Welcome " + fullName);
    }

    public Object makeUserCategoryEvaluation(String type) throws Exception {
        if(type.equalsIgnoreCase("admin")) {
            return new Admin(connection, userData);
        } else if (type.equalsIgnoreCase("client")) {
            return new Client(connection, userData);
        } else {
            throw new Exception("This user has no rigths over anything");
        }
    }
}
