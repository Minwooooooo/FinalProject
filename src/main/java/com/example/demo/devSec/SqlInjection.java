package com.example.demo.devSec;


import java.util.Collections;

public class SqlInjection {
  public static void preventSqlToCheck(String str) throws RuntimeException {
    // '' @ ^ * | < > & etc blocked

    // Ex
    if (str.contain(`)) { 
      throw new RuntimeException("'");
    }else if (str.contain("|")) {
      throw new RuntimeException("|"); 
    }else if{
      // .....
    }
  }


  public static void preventSqlToCheckCollection(Collections collection) throws RuntimeException {
    Iterable iterable= (Iterable) collection;
    for (var element : iterable) {
      if (element.getClass().getTypeName().equals("java.lang.String")) {
        // Do check
        preventSqlToCheck(element);
      }
    }
  }
}

