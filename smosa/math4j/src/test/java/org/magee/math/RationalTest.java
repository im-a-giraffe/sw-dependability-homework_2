package org.magee.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RationalTest {

  @Test
  public void test01()  throws Throwable  { // fixed
      Rational rational0 = new Rational(762L, 762L);
      assertEquals(763L, rational0.add(762L).longValue());
  }

  @Test
  public void test02()  throws Throwable  { // test is correct
      Rational rational0 = new Rational((-1L), (-661L));
      long long0 = rational0.longValue();
      assertEquals(0L, long0);
      assertEquals(0.0015128593040847202, rational0.doubleValue(), 0.01);
  }

  @Test
  public void test03()  throws Throwable  { // text fixed
      Rational rational0 = new Rational((-1431L), (-1431L));
      Rational rational1 = rational0.multiply(rational0);
      assertEquals(1L, rational0.longValue());
      assertEquals(1L, rational1.floatValue(), 0.0F);
  }

  @Test
  public void test04()  throws Throwable  { // test is correct
      Rational rational0 = new Rational(1L, 1L);
      Rational rational1 = rational0.multiply(rational0);
      assertEquals(1, rational0.intValue());
      assertEquals((byte)1, rational1.byteValue());
  }

  @Test
  public void test05()  throws Throwable  { // test is correct
      Rational rational0 = new Rational(6L, (-2L));
      Rational rational1 = rational0.pow(5);
      assertEquals(7776L, rational1.numerator);
      assertEquals((byte)13, rational1.byteValue());
      assertEquals(-243, rational1.intValue());
  }

  @Test
  public void test06()  throws Throwable  { // test fixed
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.subtract((-1L));
      assertEquals(2, rational1.doubleValue(), 0.0);
      assertEquals(1L, rational1.denominator);
      assertEquals((-1L), rational0.denominator);
  }

  @Test
  public void test07()  throws Throwable  { // test fixed
      Rational rational0 = new Rational(0L, (-1802L));
      Rational rational1 = rational0.abs();
      assertEquals(0, rational1.doubleValue(), 0.0);
      assertEquals((1802L), rational1.denominator);
  }

  @Test
  public void test08()  throws Throwable  { // test correct
      Rational rational0 = new Rational(887L, 887L);
      Rational rational1 = rational0.negate();
      assertEquals((byte) (-1), rational1.byteValue());
      assertEquals(1, rational0.intValue());
  }

  @Test
  public void test09()  throws Throwable  { // correct, but partially fixed (no range needed in delta since zero value)
      Rational rational0 = new Rational(0L, 1090L);
      double double0 = rational0.doubleValue();
      assertEquals(1090L, rational0.denominator);
      assertEquals(0.0, double0, 0.0);
  }

  @Test
  public void test10()  throws Throwable  { // test is correct
      Rational rational0 = new Rational(1184L, 73L);
      Rational rational1 = rational0.abs();
      assertEquals(1184L, rational0.numerator);
      assertEquals(16.21917808219178, rational1.doubleValue(), 0.01);
  }
}
