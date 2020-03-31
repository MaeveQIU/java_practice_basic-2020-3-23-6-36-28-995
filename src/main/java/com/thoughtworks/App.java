package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

  public static void main(String[] args) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    Homepage homepage = new Homepage();
    homepage.display(scanner);
  }
}
