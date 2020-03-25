package com.thoughtworks;

public class User {

  private String name;
  private String phone;
  private String email;
  private String password;

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public User(String name, String phone, String email, String password) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.password = password;
  }

  public String checkFormat() {
    if (name.length() < 2 || name.length() > 10) {
      return "用户名不合法\n";
    } else if (password.length() < 8 || password.length() > 16 || !hasDigit(password) || !hasCharacter(password)) {
      return "密码不合法\n";
    }

    if (email != null && phone != null) {
      if (!phone.startsWith("1") || phone.length() != 11) {
        return "手机号不合法\n";
      } else if (!email.contains("@")) {
        return "邮箱不合法\n";
      }
    }
    return null;
  }

  private boolean hasDigit(String string) {
    for (int i = 0; i < string.length(); i++) {
      if (Character.isDigit(string.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  private boolean hasCharacter(String string) {
    for (int i = 0; i < string.length(); i++) {
      if (Character.isLetter(string.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}



