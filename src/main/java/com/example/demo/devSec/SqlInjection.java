package com.example.demo.devSec;

public class SqlInjection {
  public static void preventSqlToCheck(String str) throw RuntimeException {
    // do check
    if (isRisky) {  
      // '' @ ^ * ^ % & etc blocked
    }else{
      throw new RuntimeException("is so risky");
    }

    return;
  }

  public static void preventSqlToCheckCollection(Collection Collection) throw RuntimeException {
    Iterator iter= Collection.Iterator();
    
    for (var element : iter) {
      if (condition.classType("String")) {
        // Do check 
      }eles{
        throw new RuntimeException("is so risky");
      }
    }

    return;
  }
}
