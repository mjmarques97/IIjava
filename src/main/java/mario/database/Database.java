package mario.database;

import java.sql.*;

/***
 * (user="up201506597", password="JsY5w3sWP", host="db.fe.up.pt", port="5432")
 *
 *
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

    /***
     * Faz queries à base de dados
     * @param query Query a executar
     */
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
            System.out.println("Error connecting to the mario.database. Check if you are connected to the FEUP network, if yes, try again with a VPN connection");
           // e.printStackTrace();
        }
    }


}
