package group11project.demo.controller;

import java.sql.*;

/**
 * This class contains the methods that interacts with the database and
 * make changes to the two tables in the database. Some insert functions
 * are overloaded to adapt to different situations.
 */
public class testDB {
    /**
     * The Statement is a necessary component for interactions to the database,
     * it's generated from the connection
     */
    public Statement statement;
    /**
     * The connection is the connection to the database, a connection has to be
     * established in order to make any changes to the database.
     */
    public Connection connection;
    /**
     * The resultSet is a set to store information pulled from the database after
     * executing a query to the database.
     */
    public ResultSet resultSet;
    /**
     * The url that represents the database
     */
    public String url = "localhost:3306";
    /**
     * The username used to log in to the database
     */
    public String username = "root";
    /**
     * The password corresponds to the username used to log in to the database.
     */
    public String password = "DudeJin043!";

    /**
     * The constructor for MySQLDatabase_Class, it establish a connection to the
     * database online and initialize class data members: connection, statement.
     */
    public testDB() {

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            this.connection = connection;
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This method is used to clear everything inside the Test table, it deletes every single entry.
     */
    private void clearTheTable(){
        try{
            String sqlDelete = "delete from Test ";
            int countDeleted = statement.executeUpdate(sqlDelete);
            System.out.println(countDeleted + " records deleted.\n");
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This method prints out information in every column of every entry within the Test table
     */
    private void printOutUserInfo(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM Test ");
            while (resultSet.next()) {
                String Username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String sellItem = resultSet.getString("sellItem");
                float sellPrice = resultSet.getFloat("sellPrice");
                String buyItem= resultSet.getString("buyItem");
                float buyPrice = resultSet.getFloat("buyPrice");
                System.out.println(Username + "\t" + password +
                        "\t" + sellItem + "\t" + sellPrice +
                        "\t" + buyItem + "\t" + buyPrice);
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This function will insert an entry in the history table in the database, three arguments are
     * needed in this method, to store the item information and the user's name corresponding to
     * the transaction.
     * @param idusername The user's name involving in the transaction
     * @param itemName The item's name that was traded
     * @param itemPrice The trading price of the item
     * @throws SQLException
     */
    public void insertToHistoryDatabase(String idusername, String itemName, float itemPrice) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            this.connection = connection;
            statement = connection.createStatement();

            String sqlInsert = "insert into History(Username, ItemBought,boughtPrice) " // need a space
                    + "values ('" + idusername + "', '" + itemName + "', '" + itemPrice + "')";
            int countInserted = statement.executeUpdate(sqlInsert);



        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(url, username, password)){
            this.connection = connection;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Test WHERE sellItem='"+itemName+"' and sellPrice='"+itemPrice+"'");
            String Username = "sb";
            while(resultSet.next()){
                Username = resultSet.getString("Username");
            }
            String sqlInsert = "insert into History(Username, ItemSold,soldPrice) "+ "values ('" + Username + "', '" + itemName + "', '" + itemPrice + "')";
            int countInserted = statement.executeUpdate(sqlInsert);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }


    }


    /**
     * This function is used to delete a single entry from the Test Table
     * @param itemName itemName taken in to locate the item that is to be deleted
     * @param itemPrice itemPrice also used to locate the specific item.
     */
    public void deleteOneItem(String itemName, float itemPrice){
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            this.connection=connection;
            statement = connection.createStatement();
            String sqlDelete = "delete from Test where sellItem = '"+itemName+"' and  sellPrice = '"+itemPrice+"'";
            int countDeleted = statement.executeUpdate(sqlDelete);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This method is used to add an entry to the table in the database, this function is overloaded into
     * different forms to handle different insertion situations. The situation for this particular method is
     * to register a new customer to the platform, only the customer's name and password will be stored in the
     * database.
     * @param idusername The idusername is supposed to be stored in the first column of Test table: Username
     * @param idpassword The idpassword is supposed to be stored in the second column of Test table: password
     */
    public void insertToDatabase(String idusername, String idpassword){

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            this.connection=connection;
            statement = connection.createStatement();

            String sqlInsert = "insert into Test(Username, Password) " // need a space
                    + "values ('"+idusername+"', '"+idpassword+"')";
            int countInserted = statement.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This function is also insertToDatabase, just another form due to overloading, this function is used to handle
     * the situation that the current customer wants to buy or sell anything on the platform, if the customer wants
     * to sell an item, the item information and current user's name will be stored as a new entry in the test table.
     * If the customer wants to buy something, The entry of the merchandise for sale will be deleted from the test table.
     * @param sellOrBuy The String is taken in to check whether the customer wants to buy or sell an item.
     * @param currentUser The user name of the current customer, for marking the buyer of an item.
     * @param itemname The item's name that the customer wants to buy or sell.
     * @param itemprice The price of the item that the customer wants to buy or sell.
     */
    public void insertToDatabase(String sellOrBuy,String currentUser,  String itemname, float itemprice) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            this.connection=connection;
            statement = connection.createStatement();
            if (sellOrBuy == "Sell") {
                String sqlInsert = "insert into Test(Username, sellItem, sellPrice) " // need a space
                        + "values ('"+currentUser+"','" + itemname + "', '" + itemprice + "')";
                System.out.println("The SQL query is: " + sqlInsert);  // Echo for debugging
                int countInserted = statement.executeUpdate(sqlInsert);
            }else{
                String sqlDelete = "delete from Test where sellItem = '"+itemname+"' ";
                int countDeleted = statement.executeUpdate(sqlDelete);
            }
            resultSet = statement.executeQuery("SELECT * FROM Test ");
            printOutUserInfo();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * This boolean function returns true or false, it goes through the whole test table in the database
     * and check that if the username has already been registered. If the username the customer wants to
     * register is already used, this function will return true. The function will return false otherwise
     * @param customerName The user name that needs to be checked.
     * @return true or false regarding the user name whether has already been used.
     */
    public boolean customerExists(String customerName){
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            this.connection=connection;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Test");
            while(resultSet.next()){
                if(resultSet.getString("Username").equals(customerName)){
                    return true;
                }
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return false;
    }

    /**
     * This boolean method checks the combination of user name and password when a user is trying
     * to log in to the platform. If the user name and the password matches, can be found in the Test table.
     * The function returns true, otherwise return false.
     * @param userName The user name that needs to be checked
     * @param password The password the user currently input into the text field, gotta check whether this matches
     *                 the record inside the test table.
     * @return true or false regarding whether the user have input correct user name and password
     */
    public boolean checkLogIn(String userName, String password){
        try (Connection connection = DriverManager.getConnection(url, username, this.password)) {
            this.connection=connection;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Test");
            try{
                while(resultSet.next()){
                    if(resultSet.getString("Username").equals(userName) && resultSet.getString("Password").equals(password)){
                        return true;
                    }
                }
            }catch(Exception e){
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return false;
    }

    /**
     * This method checks that whether the item name matches the item price for a specific item
     * returns true if these two matches, and false if they do not.
     * @param item The item name needs to be checked
     * @param price The item price corresponding to the item.
     * @return true or false regarding whether the name and the price matches.
     */
    public boolean checkItemListing(String item, Float price){
        try (Connection connection = DriverManager.getConnection(url, username, this.password)) {
            this.connection=connection;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Test WHERE sellItem <>'"+null+"'");
            while(resultSet.next()){
                String Username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String sellItem = resultSet.getString("sellItem");
                float sellPrice = resultSet.getFloat("sellPrice");
                String buyItem= resultSet.getString("buyItem");
                float buyPrice = resultSet.getFloat("buyPrice");
                if(sellItem.equalsIgnoreCase(item) && sellPrice== price){
                    return true;
                }
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return false;
    }


    /**
     * This function is used to return the number of entries currently stored inside the test table. It goes through the
     * entire test table, for each entry, the counter plus one. Finally return the counter's value.
     * @return The number of entries currently stored inside the test table.
     */
    public int itemNumber(){
        int itemNumber = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            this.connection=connection;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT sellItem FROM Test");
            while(resultSet.next()){
                itemNumber++;
            }
        }catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return itemNumber;
    }
}
