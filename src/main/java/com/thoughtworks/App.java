package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

  public static void main(String[] args) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    displayApp(scanner);
  }

  public static void displayApp(Scanner scanner) throws SQLException {
    System.out.println("1. 注册\n2. 登录\n3. 退出\n 请输入你的选择(1~3)：");
    String number = scanner.next();
    switch (number) {
      case "1":
        System.out.println("请输入注册信息(格式：用户名,手机号,邮箱,密码)：");
        signUp(scanner);
        displayApp(scanner);
        break;

      case "2":
        System.out.println("请输入用户名和密码(格式：用户名,密码)：");
        logIn(scanner);
        displayApp(scanner);
        break;

      case "3":
        System.out.println("那拜拜吧");
        scanner.close();
        break;
    }
  }

  public static void signUp(Scanner scanner) {
    String input = scanner.next();
    String[] inputArray = input.split(",");
    User user = new User(inputArray[0], inputArray[1], inputArray[2], inputArray[3]);
    String checkResult = user.checkFormat();
    if (checkResult == null) {
      UserRepository.addUser(user);
      System.out.println(String.format("%s，恭喜你注册成功！", user.getName()));
    } else {
      System.out.println(checkResult + "请按正确格式输入注册信息：");
      signUp(scanner);
    }
  }

  public static void logIn(Scanner scanner) throws SQLException {
    String input = scanner.next();
    String[] inputArray = input.split(",");
    User user = new User(inputArray[0], inputArray[1]);
    String checkResult = user.checkFormat();

    if (checkResult == null) {
      User checkedUser = UserRepository.checkUser(user);
      if (checkedUser == null) {
        System.out.println("用户名错误\n请重新输入用户名和密码：");
        logIn(scanner);
      } else if (checkedUser.getName() != null && checkedUser.getPassword() == null) {
        System.out.println("密码错误\n请重新输入用户名和密码：");
        logIn(scanner);
      } else if (checkedUser.getName() == null) {
        System.out.println("您已3次输错密码，账号被锁定");
        displayApp(scanner);
      } else {
        System.out.println(String.format("%s，欢迎回来！\n您的手机号是%s，邮箱是%s", checkedUser.getName(), checkedUser.getPhone(), checkedUser.getEmail()));
      }
    } else {
      System.out.println(checkResult + "请按正确格式输入用户名和密码：");
      logIn(scanner);
    }
  }
}
