package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDao extends DaoImpl{

    public ClientDao(Connection connection) {
        super(connection);
    }

    @Override
    public void executeStatement(String statement) throws SQLException {
        throw new SQLException("Unauthorized");
    }

    @Override
    public ResultSet getTableMetadata(String table) throws SQLException {
        throw  new SQLException("Unauthorized");
    }

    @Override
    public ResultSet getColumnMetadata(String table) throws SQLException {
        throw  new SQLException("Unauthorized");
    }
}
