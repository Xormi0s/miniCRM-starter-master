package com.crm.miniCRM;


import com.crm.miniCRM.model.persistence.PersistenceConfig;
import org.hibernate.boot.model.relational.Database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class pdfData {

    public ArrayList<String> output() throws SQLException {
        PersistenceConfig persistenceConfig = new PersistenceConfig();
        Connection connection = persistenceConfig.dataSource().getConnection();
        String sql = "select person.first_name, person.last_name, address.street, address.number, address.zip, address.country from person, person_address, address where person.id = person_address.person_id and address.id = person_address.address_id";
        PreparedStatement statement = connection.prepareStatement(sql);

        ArrayList<String> output = new ArrayList<>();
        try  {
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                for(int x = 1; x < 7; x++){
                    output.add(set.getString(x));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception "+sqlException.getSQLState());
            throw new SQLException(sqlException);
        }

        return output;
    }
}
