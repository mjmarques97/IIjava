package database;

import java.sql.*;

import org.postgresql.Driver;
/***
 * (user="up201506597", password="JsY5w3sWP", host="db.fe.up.pt", port="5432")
 * Ainda nao funciona
 */
public class Database{
    private Connection connection;
    public Database() {
        connection=null;
        try{
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection("jdbc:postgresql://db.fe.up.pt:5432/up201506597",
                    "up201506597", "JsY5w3sWP");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeConnection(){

        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void query(String query){

        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(query);
            System.out.println("ID COUNT");
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                int count=resultSet.getInt("count");
                System.out.printf("%d %d\n",id,count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Database database=new Database();
        database.query("SELECT * from \"II\".armazem");
        database.closeConnection();
    }

}
