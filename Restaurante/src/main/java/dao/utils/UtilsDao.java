package dao.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class UtilsDao {
    public String serializeParams(ArrayList<String> fields) {
        String params = "";
        int numberOfParameters = fields.size();
        for(int i = 0; i < numberOfParameters - 1; i++) {
            params += fields.get(i) + " = ? and ";
        }
        params += fields.get(numberOfParameters - 1) + " = ?";
        return params;
    }

    public String serializeFields(ArrayList<String> fields) {
        String serializedFields = "(";
        int numberOfFields = fields.size();
        for(int i = 0; i < numberOfFields - 1; i++) {
            serializedFields += fields.get(i) + ", ";
        }
        serializedFields += fields.get(numberOfFields - 1) + ")";
        return  serializedFields;
    }

    public String createValuesWithQuoteMarks(int size) {
        String quoted = "(";
        for(int i = 0; i < size - 1; i++) {
            quoted += "?, ";
        }
        quoted += "?)";
        return  quoted;
    }

    public void printResults(ResultSet results) throws SQLException {
        while(results.next()) {
            System.out.println("--------------------");
            IntStream.rangeClosed(1, results.getMetaData().getColumnCount())
                    .forEach(i -> {
                        try {
                            System.out.println(results.getString(i));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
        }

    }
}
