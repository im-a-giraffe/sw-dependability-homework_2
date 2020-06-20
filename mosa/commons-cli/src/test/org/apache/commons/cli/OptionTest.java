package org.apache.commons.cli;

import org.junit.Test;

import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.*;

public class OptionTest {

  @Test
  public void test01()  throws Throwable  { // fixed test
      Option option0 = new Option("", "");
      assertEquals("arg", option0.getArgName());
      
      option0.setArgName("");
      boolean boolean0 = option0.hasArgName();
      assertFalse(boolean0);
  }

  @Test
  public void test02()  throws Throwable  { // test is correct
      Option option0 = new Option("", "", true, "");
      option0.getKey(); // this check is unnecessary
      assertEquals("arg", option0.getArgName());
      assertEquals(1, option0.getArgs());
  }

  @Test
  public void test03()  throws Throwable  { // test is correct
      Option option0 = new Option("R", "R", true, "R");
      Option option1 = new Option("R", "R", true, "R");
      boolean boolean0 = option1.equals(option0);
      assertEquals("arg", option1.getArgName());
      assertTrue(option1.hasArg());
      assertTrue(boolean0);
  }

  @Test
  public void test04()  throws Throwable  { // fixed test, equals is correct matching the first character of shortopt,
      // but does not make any sense as source says 'this is only set when the Option is a single character' which 'VC' is not
      Option option0 = new Option("VC", "VC");
      int int0 = option0.getId();
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
      assertEquals('V', int0);
      assertEquals("arg", option0.getArgName());
  }

  @Test
  public void test05()  throws Throwable  { // fixed test, hasArgs is default 1 arg
      Option option0 = new Option("P", "P", true, "P");
      boolean boolean0 = option0.hasArgs();
      assertEquals("arg", option0.getArgName());
      assertFalse(boolean0);
      assertEquals(1, option0.getArgs());
  }

  @Test
  public void test06()  throws Throwable  { // test is correct
      Option option0 = new Option("P", true, "P");
      option0.addValueForProcessing("P");
      try {
        option0.getValue(295);
        fail("Expecting exception: IndexOutOfBoundsException");

      } catch(IndexOutOfBoundsException e) {
         //
         // Index: 295, Size: 1
         //
         verifyException("java.util.ArrayList", e);
      }
  }

  @Test
  public void test07()  throws Throwable  { // fixed test, hasArgs is default false
      Option option0 = new Option("", "", true, "");
      Option option1 = new Option("", "");
      boolean boolean0 = option0.equals(option1);
      assertEquals(Option.UNINITIALIZED, option1.getArgs());
      assertFalse(option1.hasLongOpt());
      assertEquals("arg", option1.getArgName());
      assertEquals(1, option0.getArgs());
      assertFalse(boolean0);
  }

  @Test
  public void test08()  throws Throwable  { // test is correct
      Option option0 = new Option("", "", true, "");
      assertEquals("arg", option0.getArgName());
      assertTrue(option0.hasArg());
  }

  @Test
  public void test09()  throws Throwable  { // test is correct
      Option option0 = new Option((String) null, (String) null, true, (String) null);
      assertEquals("arg", option0.getArgName());
      
      option0.setArgName((String) null);
      option0.getArgName();
      assertEquals(1, option0.getArgs());
  }

  @Test
  public void test10()  throws Throwable  { // fixed test, not equal, missing description, also better usage of UNINITIALIZED variable than int value -1
      Option option0 = new Option("SX", "SX");
      Option option1 = new Option("SX", "SX", false, "SX");
      boolean boolean0 = option0.equals(option1);
      assertFalse(boolean0);
      assertEquals(Option.UNINITIALIZED, option1.getArgs());
      assertFalse(option0.hasLongOpt());
      assertEquals("arg", option1.getArgName());
  }
}
