package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends DaoImpl {

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public void addResource(String table, ArrayList<String> fields, ArrayList<Object> values) throws SQLException{
        throw new SQLException("Unauthorized");
    }

    @Override
    public void executeStatement(String statement) throws SQLException {
        throw new SQLException("Unauthorized");
    }
}
