package com.thoughtworks;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

  public final static DatabaseUtils utils = new DatabaseUtils();

  public static void addUser(User user) {
    String sql = String.format("INSERT INTO basic_info " +
                    "(name, phone_number, email, password, password_input_count, account_status) " +
                    "VALUES ('%s', '%s', '%s', '%s', 0, 'unlocked');",
            user.getName(), user.getPhone(), user.getEmail(), user.getPassword());

    utils.executeStatement(sql);
  }

  public static User getUser(User user) throws SQLException {
    ResultSet result = isUserExist(user);

    if (result == null) {
      return getUserWithWrongInfo(user);
    } else {
      String lockStatus = result.getString("account_status");
      if (lockStatus.equals("locked")) {
        return new User(null, null);
      } else {
        User checkedUser = new User(result.getString("name"), result.getString("phone_number"),
                result.getString("email"), result.getString("password"));
        resetPasswordInput(checkedUser);
        return checkedUser;
      }
    }
  }

  public static ResultSet isUserExist(User user) throws SQLException {
    String sql = String.format("SELECT name, phone_number, email, password, password_input_count, account_status" +
            " FROM basic_info WHERE name = '%s' AND password = '%s';", user.getName(), user.getPassword());
    ResultSet resultSet = utils.executeSelectStatement(sql);

    if (!resultSet.next()) {
      return null;
    }
    return resultSet;
  }

  public static ResultSet isPasswordCorrect(User user) throws SQLException {
    String sql = String.format("SELECT name, phone_number, email, password, password_input_count, account_status" +
            " FROM basic_info WHERE name = '%s';", user.getName());
    ResultSet resultSet = utils.executeSelectStatement(sql);
    if (!resultSet.next()) {
      return null;
    }
    return resultSet;
  }

  public static User getUserWithWrongInfo(User user) throws SQLException {
    ResultSet resultSet = isPasswordCorrect(user);
    if (resultSet == null) {
      return null;
    } else {
      int count =  updatePasswordInput(user);
      if (count < 3) {
        return new User(user.getName(), null);
      } else {
        lockAccount(user);
        return new User(null, null);
      }
    }
  }

  public static int updatePasswordInput(User user) throws SQLException {
    String selectSql = String.format("SELECT password_input_count FROM basic_info WHERE name = '%s';", user.getName());
    ResultSet result = utils.executeSelectStatement(selectSql);
    result.next();
    int count = result.getInt("password_input_count") + 1;

    String updateSql = String.format("UPDATE basic_info SET password_input_count = '%d' WHERE name = '%s';",
            count, user.getName());
    utils.executeStatement(updateSql);
    return count;
  }

  public static void resetPasswordInput(User user) {
    String sql = String.format("UPDATE basic_info SET password_input_count = 0 WHERE name = '%s';", user.getName());
    utils.executeStatement(sql);
  }

  public static void lockAccount(User user) {
    String sql = String.format("UPDATE basic_info SET account_status = 'locked' WHERE name = '%s';", user.getName());
    utils.executeStatement(sql);
  }
}
