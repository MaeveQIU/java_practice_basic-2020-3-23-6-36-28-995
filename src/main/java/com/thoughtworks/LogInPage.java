package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public class LogInPage implements Page {

  public final String WRONG_NAME = "用户名错误\n请重新输入用户名和密码：";
  public final String WRONG_PASSWORD = "密码错误\n请重新输入用户名和密码：";
  public final String WRONG_PASSWORD_THREE_TIMES = "您已3次输错密码，账号被锁定";
  public final String INPUT_HINT = "请按正确格式输入用户名和密码：";
  public Homepage homepage = new Homepage();

  @Override
  public void display(Scanner scanner) throws SQLException {
    String input = scanner.next();
    String[] inputArray = input.split(",");
    User user = new User(inputArray[0], inputArray[1]);
    String checkResult = user.checkFormat();

    if (checkResult == null) {
      User checkedUser = UserRepository.getUser(user);
      if (checkedUser == null) {
        System.out.println(WRONG_NAME);
        display(scanner);
      } else if (checkedUser.getName() != null && checkedUser.getPassword() == null) {
        System.out.println(WRONG_PASSWORD);
        display(scanner);
      } else if (checkedUser.getName() == null) {
        System.out.println(WRONG_PASSWORD_THREE_TIMES);
        homepage.display(scanner);
      } else {
        System.out.println(String.format("%s，欢迎回来！\n您的手机号是%s，邮箱是%s", checkedUser.getName(), checkedUser.getPhone(), checkedUser.getEmail()));
        homepage.display(scanner);
      }
    } else {
      System.out.println(checkResult + INPUT_HINT);
      display(scanner);
    }
  }
}
