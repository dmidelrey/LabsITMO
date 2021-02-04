package ru.lab.dbaccess;

import ru.lab.basic.*;
import ru.lab.controller.FlatController;

import java.sql.*;


public class DBController {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "*****";
    private static final String PASSWORD = "****";


    public static void createUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO evstigneev_users (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        }
    }

    public static User getUser(String username) {
        User user = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM evstigneev_users WHERE USERNAME LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        }
        return user;
    }

    public static FlatController getDataByUserId(long userId) {
        FlatController flatController = new FlatController();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM evstigneev_flats WHERE USER_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                Long x = resultSet.getLong("COORDINATE_X");
                int y = resultSet.getInt("COORDINATE_Y");
                java.util.Date creationDate = resultSet.getDate("CREATION_DATE");
                Double area = resultSet.getDouble("AREA");
                long numberOfRooms = resultSet.getLong("NUMBER_OF_ROOMS");
                String furnishString = resultSet.getString("FURNISH");
                Furnish furnish = null;
                if (!furnishString.equals("")) furnish = Furnish.valueOf(furnishString);
                View view = View.valueOf(resultSet.getString("VIEW"));
                Transport transport = Transport.valueOf(resultSet.getString("TRANSPORT"));
                String houseName = resultSet.getString("HOUSE_NAME");
                long houseYear = resultSet.getLong("HOUSE_YEAR");
                Long houseNumberOfFlatsOnTheFloor = resultSet.getLong("HOUSE_NUMBER_OF_FLATS_ON_FLOOR");

                Coordinates coordinates = new Coordinates(x, y);
                House house = new House(houseName, houseYear, houseNumberOfFlatsOnTheFloor);
                Flat flat = new Flat(id, name, coordinates, area, numberOfRooms, furnish, view, transport, house);
                flat.setCreationDate(creationDate);

                flatController.addFlat(flat);
            }
            preparedStatement.close();

            connection.commit();

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        }
        return flatController;
    }

    public static void saveDataByUserId(int userId, FlatController flatController) {
        FlatController oldController = getDataByUserId(userId);
        deleteMissingFlats(userId, oldController, flatController);
        for (Flat flat : flatController.getFlatSet()) {
            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
                if (oldController.getFlatById(flat.getId()) != null) {
                    String sql = "UPDATE evstigneev_flats SET " +
                            "NAME = ?," +
                            "COORDINATE_X = ?," +
                            "COORDINATE_Y = ?," +
                            "CREATION_DATE = ?," +
                            "AREA = ?," +
                            "NUMBER_OF_ROOMS = ?," +
                            "FURNISH = ?," +
                            "VIEW = ?," +
                            "TRANSPORT = ?," +
                            "HOUSE_NAME = ?," +
                            "HOUSE_YEAR = ?," +
                            "HOUSE_NUMBER_OF_FLATS_ON_FLOOR = ?" +
                            "WHERE ID = ?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, flat.getName());
                    preparedStatement.setLong(2, flat.getCoordinates().getX());
                    preparedStatement.setInt(3, flat.getCoordinates().getY());
                    preparedStatement.setDate(4, new java.sql.Date(flat.getCreationDate().getTime()));
                    preparedStatement.setDouble(5, flat.getArea());
                    preparedStatement.setLong(6, flat.getNumberOfRooms());
                    if (flat.getFurnish() == null ) preparedStatement.setString(7, "");
                    else preparedStatement.setString(7, flat.getFurnish().toString());
                    preparedStatement.setString(8, flat.getView().toString());
                    preparedStatement.setString(9, flat.getTransport().toString());
                    preparedStatement.setString(10, flat.getHouse().getName());
                    preparedStatement.setLong(11, flat.getHouse().getYear());
                    preparedStatement.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());
                    preparedStatement.setLong(13, flat.getId());

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.commit();
                } else {
                    String sql = "INSERT INTO evstigneev_flats(ID, NAME, COORDINATE_X, COORDINATE_Y, " +
                            "CREATION_DATE, AREA, NUMBER_OF_ROOMS, FURNISH, VIEW, TRANSPORT, " +
                            "HOUSE_NAME, HOUSE_YEAR, HOUSE_NUMBER_OF_FLATS_ON_FLOOR, USER_ID)" +
                            "VALUES(nextval('evstigneev_flats_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, flat.getName());
                    preparedStatement.setLong(2, flat.getCoordinates().getX());
                    preparedStatement.setInt(3, flat.getCoordinates().getY());
                    preparedStatement.setDate(4, new java.sql.Date(flat.getCreationDate().getTime()));
                    preparedStatement.setDouble(5, flat.getArea());
                    preparedStatement.setLong(6, flat.getNumberOfRooms());
                    if (flat.getFurnish() == null) preparedStatement.setString(7, "");
                    else preparedStatement.setString(7, flat.getFurnish().toString());
                    preparedStatement.setString(8, flat.getView().toString());
                    preparedStatement.setString(9, flat.getTransport().toString());
                    preparedStatement.setString(10, flat.getHouse().getName());
                    preparedStatement.setLong(11, flat.getHouse().getYear());
                    preparedStatement.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());
                    preparedStatement.setInt(13, userId);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.commit();

                }
                connection.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Driver not found");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL exception");
            }
        }
    }

    private static void deleteMissingFlats(int userId, FlatController oldData, FlatController newData) {
        for (Flat flat : oldData.getFlatSet()) {
            if (newData.getFlatById(flat.getId()) == null) {
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    connection.setAutoCommit(false);

                    String sql = "DELETE FROM evstigneev_flats WHERE ID = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, flat.getId());

                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.commit();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Driver not found");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL exception");
                }
            }
        }
    }


    public static void createTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "CREATE SEQUENCE evstigneev_users_sequence INCREMENT BY 1;" +
                    "CREATE SEQUENCE evstigneev_flats_sequence INCREMENT BY 1;" +
                    "CREATE TABLE evstigneev_users(ID int PRIMARY KEY NOT NULL, USERNAME TEXT NOT NULL, PASSWORD TEXT);" +
                    "CREATE TABLE evstigneev_flats" +
                    "(ID int PRIMARY KEY NOT NULL," +
                    "NAME TEXT NOT NULL," +
                    "COORDINATE_X INTEGER NOT NULL," +
                    "COORDINATE_Y int, " +
                    "CREATION_DATE TIMESTAMP," +
                    "AREA FLOAT NOT NULL," +
                    "NUMBER_OF_ROOMS int NOT NULL," +
                    "FURNISH TEXT, " +
                    "VIEW TEXT NOT NULL," +
                    "TRANSPORT TEXT NOT NULL," +
                    "HOUSE_NAME TEXT," +
                    "HOUSE_YEAR int NOT NULL," +
                    "HOUSE_NUMBER_OF_FLATS_ON_FLOOR INTEGER NOT NULL," +
                    "USER_ID int NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            System.out.println("Table created");
            connection.close();
            System.out.println("Connection closed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        }
    }

    public static void clearTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "DROP TABLE evstigneev_users, evstigneev_flats;\n" +
                    "DROP SEQUENCE evstigneev_users_sequence, evstigneev_flats_sequence;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            System.out.println("Data base cleared");
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        }
    }
}
