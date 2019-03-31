package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
	
	public boolean authenticate(String name, String password)
            throws Exception {
        boolean isUser = false;
        try {
        	/*Conexion con la bd*/
        	/*
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("select NAME, PASSWORD from DB_user where NAME=? and PASSWORD=?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                isUser = true;
                System.out.println("User authenticated successfully");
            } else {
                System.out.println("Invalid username or password");
            }*/
        } catch (Exception e) {
            System.out.println("Error in the DataBase!!");
            e.printStackTrace();
        }
        return isUser;
    }

}
