package com.thoughtworks;

import java.sql.SQLException;
import java.util.Scanner;

public interface Page {

  void display(Scanner scanner) throws SQLException;
}
