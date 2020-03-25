package com.thoughtworks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

  public static void addUser(User user) {
    String sql = String.format("INSERT INTO basic_info " +
            "(name, phone_number, email, password, password_input_count, account_status) " +
            "VALUES ('%s', '%s', '%s', '%s', 0, 'unlocked');",
            user.getName(), user.getPhone(), user.getEmail(), user.getPassword());

    DatabaseUtils utils = new DatabaseUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }

  public static User checkUser(User user) throws SQLException {
    String sql = String.format("SELECT name, phone_number, email, password, password_input_count, account_status" +
            " FROM basic_info WHERE name = '%s' AND password = '%s';", user.getName(), user.getPassword());
    DatabaseUtils utils = new DatabaseUtils();
    ResultSet result = utils.executeSelectStatement(sql);

    if (!result.next()) {
      String selectSql = String.format("SELECT name, phone_number, email, password, password_input_count, account_status" +
              " FROM basic_info WHERE name = '%s';", user.getName());
      ResultSet resultSet = utils.executeSelectStatement(selectSql);
      if (!resultSet.next()) {
        utils.closeAll(result);
        return null;
      } else {
        updatePasswordInput(user);
        int inputCount = resultSet.getInt("password_input_count") + 1;
        if (inputCount < 3) {
          utils.closeAll(result);
          return new User(user.getName(), null);
        } else {
          lockAccount(user);
          utils.closeAll(result);
          return new User(null, null);
        }
      }
    } else {
      String status = result.getString("account_status");
      if (status.equals("locked")) {
        utils.closeAll(result);
        return new User(null, null);
      } else {
        User checkedUser = new User(result.getString("name"), result.getString("phone_number"),
                result.getString("email"), result.getString("password"));
        resetPasswordInput(checkedUser);
        utils.closeAll(result);
        return checkedUser;
      }
    }
  }

  public static void updatePasswordInput(User user) throws SQLException {
    DatabaseUtils utils = new DatabaseUtils();

    String selectSql = String.format("SELECT password_input_count FROM basic_info WHERE name = '%s';", user.getName());
    ResultSet result = utils.executeSelectStatement(selectSql);
    result.next();
    int count = result.getInt("password_input_count");

    String updateSql = String.format("UPDATE basic_info SET password_input_count = '%d' WHERE name = '%s';",
            count + 1, user.getName());
    utils.executeStatement(updateSql);
    utils.closeAll();
  }

  public static void resetPasswordInput(User user) {
    String sql = String.format("UPDATE basic_info SET password_input_count = 0 WHERE name = '%s';", user.getName());

    DatabaseUtils utils = new DatabaseUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }

  public static void lockAccount(User user) {
    String sql = String.format("UPDATE basic_info SET account_status = 'locked' WHERE name = '%s';", user.getName());

    DatabaseUtils utils = new DatabaseUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }
}
