package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public class SignUpPage implements Page {
  public final String INPUT_HINT = "请按正确格式输入注册信息：";
  public Homepage homepage = new Homepage();

  @Override
  public void display(Scanner scanner) throws SQLException {
    String input = scanner.next();
    String[] inputArray = input.split(",");
    User user = new User(inputArray[0], inputArray[1], inputArray[2], inputArray[3]);
    String checkResult = user.checkFormat();
    if (checkResult == null) {
      UserRepository.addUser(user);
      System.out.println(String.format("%s，恭喜你注册成功！", user.getName()));
      homepage.display(scanner);
    } else {
      System.out.println(checkResult + INPUT_HINT);
      display(scanner);
    }
  }
}
