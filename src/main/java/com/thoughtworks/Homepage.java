package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public class Homepage implements Page {
  public final String CHOOSE_MESSAGE = "1. 注册\n2. 登录\n3. 退出\n 请输入你的选择(1~3)：";
  public final String SIGN_UP_MESSAGE = "请输入注册信息(格式：用户名,手机号,邮箱,密码)：";
  public final String LOG_IN_MESSAGE = "请输入用户名和密码(格式：用户名,密码)：";
  public final String GOODBYE_MESSAGE = "那拜拜吧";
  public final String DEFAULT_MESSAGE = "请输入1、2、3范围内数字";

  @Override
  public void display(Scanner scanner) throws SQLException {
    System.out.println(CHOOSE_MESSAGE);

    String number = scanner.next();
    switch (number) {
      case "1":
        System.out.println(SIGN_UP_MESSAGE);
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.display(scanner);
        break;

      case "2":
        System.out.println(LOG_IN_MESSAGE);
        LogInPage logInPage = new LogInPage();
        logInPage.display(scanner);
        break;

      case "3":
        System.out.println(GOODBYE_MESSAGE);
        scanner.close();
        break;

      default:
        System.out.println(DEFAULT_MESSAGE);
        display(scanner);
    }
  }
}
