/**
 * MathUtil.java A class for working with various mathematical ideas.
 */

/* This file is part of Math4J.
 * Math4J is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Math4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Math4J; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 * 
 * Copyright 2005 Anthony Magee
 */

package org.magee.util;

// import other packages
import java.math.BigDecimal;
import java.util.ArrayList;

import org.magee.math.Complex;
import org.magee.math.Rational;

// import org.magee.util.*;

/**
 * The <code>MathUtil</code> class is for non-standard math methods. It cannot
 * be instantiated.
 * <p>
 * 
 * It provides static methods for dealing with mathematical ideas and methods
 * not defined by the standard <code>java.lang.Math</code> class. Such methods
 * include statistical approximation, numerical integration, derivitives, matrix
 * operations, etc.
 * 
 * @author Anthony W. Magee &#169 2001
 * @version 1.0, November 28, 2001
 */

/*
 * version changes added errorRound
 */
public class MathUtil {

    /**
     * This method will factor any number <code>num</code> within the
     * <code>long</code> range into its most basic form. This form is defined
     * to be every prime number that is a factor of <code>num</code>.
     * 
     * Note: The method used here is naive division and is simply the best for
     * numbers that <code>long</code> can handle. There is no reason to use
     * any kind of general number sieve or other complex algorithm. It only
     * takes 4ms on my machine to factor the largest product of primes that
     * <code>long</code> can hold, 614889782588491410.
     * 
     * @param num The number to be factored.
     * @return An array of longs whose product is <code>num</code>.
     */
    public static long[] factor(long num) {
        ArrayList<Long> temp = new ArrayList<Long>();
        boolean negativeNum = (num < 0L) ? true : false;
        long factor = 2;
        long power = 0;
        if(negativeNum) {
            temp.add(new Long(-1L));
            num = -num;
        }
        long max = (long) Math.sqrt(num);

        while(factor <= max) {
            while((num % factor) == 0) {
                ++power;
                num /= factor;
            }

            if(power > 0) {
                for(long i = 0; i < power; i++) {
                    temp.add(new Long(factor));
                }
                power = 0;
                max = (long) Math.sqrt(num);
            }

            if(factor == 2)
                factor = 3;
            else
                factor += 2;
        }
        if(num != 1L)
            temp.add(new Long(num));

        long[] reply = new long[temp.size()];
        for(int i = 0; i < reply.length; i++) {
            reply[i] = ((Long) temp.get(i)).longValue();
        }
        return reply;
    }

    /**
     * A quick method to convert <code>num</code> to a binary number. A binary
     * number contains only ones and zeroes.
     * 
     * @param num The number to convert
     */
    public static String longToBinary(long num) {
        /*
         * StringBuffer returnBuf = new StringBuffer(64); // storage for output
         * value
         * 
         * long power = 0; // the highest power of two while(num > (long)
         * Math.pow(2, power)) { // test if 2^power is greater than number
         * power++; // if so increase power by one until it is not }
         * 
         * for(long i = power; i >= 0L; i--) { // loop through all valid powers
         * if((num / (long) Math.pow(2, i)) >= 1) // if 2^power is valid divisor
         * returnBuf.append("1"); // append a bit else returnBuf.append("0"); //
         * else append an empty bit
         * 
         * num %= (long) Math.pow(2, i); // decrease number by its highest
         * disivor of two }
         */

        return Long.toString(num, 2); // return the binary number as a string
                                        // because of length
    }

    /**
     * A quick method to convert <code>bin</code>, a binary representation,
     * to an integer number.
     * 
     * @param bin The number to convert
     */
    public static long binaryToLong(String bin) {
        /*
         * long returnVal = 0L; char[] binChars = bin.toCharArray();
         * 
         * if(binChars.length > 63) throw new IllegalArgumentException("Argument
         * cannot be more than 63 characters in length due to primitive memory
         * limits\r\nArgument length is " + binChars.length);
         * 
         * for(int i = 0; i < binChars.length; i++) { if(binChars[i] != '0' &&
         * binChars[i] != '1') throw new IllegalArgumentException("Argument is
         * not a binary representation. It contains values other than \'0\' and
         * \'1\'"); }
         * 
         * for(int i = 0; i < binChars.length; i++) { if(binChars[i] == '1')
         * returnVal += (long) Math.pow(2, binChars.length - 1 - i); }
         */

        boolean validBinary = true;
        char[] binArray = bin.toCharArray();
        for(int i = 0; validBinary && i < binArray.length; i++) {
            if(binArray[i] != '0' || binArray[i] != '1')
                validBinary = false;
        }
        if(validBinary)
            return Long.parseLong(bin, 2);
        else
            throw new ArithmeticException("Invalid string representation of a binary number: " + bin);
    }

    /**
     * A quick method to convert <code>num</code> to a hexadecimal number. A
     * hexadecimal number contains the digits zero through nine and the first
     * five letters of the english alphabet (A, B, C, D, E, F).
     * 
     * @param num The number to convert
     */
    public static String longToHex(long num) {
        /*
         * StringBuffer returnBuf = new StringBuffer(17);
         * 
         * long power = 0; while(num > (long) Math.pow(16, power)) { power++; }
         * 
         * for(long i = power - 1; i >= 0L; i--) { if((long) Math.floor(num /
         * (long) Math.pow(16, i)) == 10L) returnBuf.append("A"); else if((long)
         * Math.floor(num / (long) Math.pow(16, i)) == 11L)
         * returnBuf.append("B"); else if((long) Math.floor(num / (long)
         * Math.pow(16, i)) == 12L) returnBuf.append("C"); else if((long)
         * Math.floor(num / (long) Math.pow(16, i)) == 13L)
         * returnBuf.append("D"); else if((long) Math.floor(num / (long)
         * Math.pow(16, i)) == 14L) returnBuf.append("E"); else if((long)
         * Math.floor(num / (long) Math.pow(16, i)) == 15L)
         * returnBuf.append("F"); else returnBuf.append(Long.toString((long)
         * Math.floor(num / (long) Math.pow(16, i))));
         * 
         * num %= (long) Math.pow(16, i); }
         */

        return (num < 16L) ? "0" + Long.toString(num) : Long.toString(num, 16);
    }

    /**
     * A quick method to convert <code>hex</code>, a hexadecimal
     * representation, to an integer number.
     * 
     * @param hex The number to convert
     */
    public static long hexToLong(String hex) {
        /*
         * long returnVal = 0L;
         * char[] hexChars = hex.toUpperCase().toCharArray();
         * Vector validElements = new Vector(17);
         * validElements.addElement("0"); validElements.addElement("1"); validElements.addElement("2");
         * validElements.addElement("3"); validElements.addElement("4"); validElements.addElement("5");
         * validElements.addElement("6"); validElements.addElement("7"); validElements.addElement("8");
         * validElements.addElement("9"); validElements.addElement("A"); validElements.addElement("B");
         * validElements.addElement("C"); validElements.addElement("D"); validElements.addElement("E");
         * validElements.addElement("F");
         * 
         * if(hexChars.length > 16)
         *      throw new IllegalArgumentException("Argument cannot be more than 16 characters in length due to primitive memory
         *          limits\nArgument length is " + hexChars.length);
         * 
         * for(int i = 0; i < hexChars.length; i++) {
         *      if(!validElements.contains(new Character(hexChars[i]).toString()))
         *          throw new IllegalArgumentException("Argument is not a hexadecimal representation. It contains values other than
         *              \'0\',\'1\',\'2\',\'3\',\'4\',\'5\',\'6\',\'7\',\'8\',\'9\',\'A\',\'B\',\'C\',\'D\',\'E\' and \'F\'"); }
         * 
         * for(int i = 0; i < hexChars.length; i++) { 
         *      if(hexChars[i] == 'A')
         *          returnVal += 10L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else if(hexChars[i] == 'B')
         *          returnVal += 11L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else if(hexChars[i] == 'C')
         *          returnVal += 12L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else if(hexChars[i] == 'D')
         *          returnVal += 13L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else if(hexChars[i] == 'E')
         *          returnVal += 14L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else if(hexChars[i] == 'F')
         *          returnVal += 15L * (long) Math.pow(16, hexChars.length - 1 - i);
         *      else 
         *          returnVal += Long.parseLong(new Character(hexChars[i]).toString()) * (long) Math.pow(16, hexChars.length - 1 - i);
         * }
         */
        
        hex = hex.toUpperCase();
        boolean validHex = true;
        char[] hexArray = hex.toCharArray();
        for(int i = 0; validHex && i < hexArray.length; i++) {
            if(hexArray[i] != '0' || hexArray[i] != '1' || hexArray[i] != '2' || hexArray[i] != '3' || hexArray[i] != '4' ||
               hexArray[i] != '5' || hexArray[i] != '6' || hexArray[i] != '7' || hexArray[i] != '8' || hexArray[i] != '9' ||
               hexArray[i] != 'A' || hexArray[i] != 'B' || hexArray[i] != 'C' || hexArray[i] != 'D' || hexArray[i] != 'E' ||
               hexArray[i] != 'F')
                validHex = false;
        }
        if(validHex)
            return Long.parseLong(hex, 16);
        else
            throw new ArithmeticException("Invalid string representation of a hexadecimal number: " + hex);
    }

    /**
     * This is the default tolerance value for the <code>rationalize</code>
     * method 10^6.
     */
    protected static final double RATIONAL_TOLERANCE = Math.pow(10, 6);

    /**
     * A method that attempts to represent the double <code>d</code> as a
     * fraction. The methods employs the <code>BigDecimal</code> class to
     * represent a possibly very large numerator and denominator. During the
     * process of rationalization if the fraction is within the specified
     * tolerance <code>toler</code> the method returns that value to save on
     * computation time.
     * 
     * @param d The decimal to be rationalized.
     * @param toler User tolerance value. Must be less than one and greater than
     *            zero.
     * @return A <code>Rational</code> that is within <code>toler</code> of
     *         <code>d</code>.
     * @throws IllegalArgumentException
     */
    public static Rational rationalize(double d, double toler) {
        // if(toler > 0.0 && toler < 1.0) {
        BigDecimal num = new BigDecimal(new Double(d).toString());
        num = num.setScale(16);
        BigDecimal one = new BigDecimal("1");
        one = one.setScale(16);
        BigDecimal zero = new BigDecimal("0");
        zero = zero.setScale(16);

        int maxIter = 10;
        int count = 0;
        Object[] dList = new Object[maxIter];
        BigDecimal floor;

        do {
            floor = num.divide(one, 0, BigDecimal.ROUND_DOWN).setScale(16);
            dList[count] = floor;
            num = num.subtract(floor).setScale(16);
            if(num.compareTo(zero) == 0)
                break;
            BigDecimal temp = one.divide(num, num.scale(), BigDecimal.ROUND_HALF_EVEN);
            if(temp.doubleValue() < toler)
                num = temp;
            else
                break;
            count++;
        } while(count < maxIter);

        dList = stripZeros(dList);
        return fold(new Rational(0L, 1L), dList);
        // } else
        // throw new IllegalArgumentException("com.magee.MathUtil.rationalize()
        // Tolerance value inappropriate.");
    }

    /**
     * Default usage of method <code>rationalize</code> thats uses the default
     * tolerance <code>RATIONAL_TOLERANCE</code>.
     * 
     * @param d The decimal to be rationalized.
     * @return A <code>Rational</code> that is within <code>toler</code> of
     *         <code>d</code>.
     */
    public static Rational rationalize(double d) {
        return rationalize(d, RATIONAL_TOLERANCE);
    }

    /**
     * Provides functionality to the Rationalize function by reconstructing the
     * fraction from a continued fraction.
     */
    private static Rational fold(Rational r, Object[] l) {
        if(l.length > 1)
            return fold((new Rational(((BigDecimal) l[l.length - 1]).longValue(), 1L).add(r)).inverse(), ArrayUtil.removeIndexElement(l, l.length - 1)).reduce();
        else
            return r.add(new Rational(((BigDecimal) l[0]).longValue(), 1L)).reduce();
    }

    /**
     * Strips the nulls off of the continued fraction list.
     */
    private static Object[] stripZeros(Object[] l) {
        while(l[l.length - 1] == null) {
            l = ArrayUtil.removeIndexElement(l, l.length - 1);
        }
        return l;
    }

    /**
     * This is the default tolerance value for the <code>chop</code> method
     * 10^-10.
     */
    protected static final double CHOP_TOLERANCE = Math.pow(10, -10);

    /**
     * This is a simple method to determine if double <code>d</code> is small
     * enough to be considered zero. If <code>d</code> is less than
     * <code>toler</code> zero is returned.
     * 
     * @param d The number to consider.
     * @param toler The smallest number not to evaluate as zero.
     * @return <code>d</code> or zero
     */
    public static double chop(double d, double toler) {
        return (Math.abs(d) < toler) ? 0.0 : d;
    }

    /**
     * Default usage of <code>chop(double)</code> that uses default tolerance
     * <code>CHOP_TOLERANCE</code>.
     * 
     * @param d The number to consider.
     * @return <code>d</code> or zero
     */
    public static double chop(double d) {
        return chop(d, CHOP_TOLERANCE);
    }

    /**
     * This is a convenience overload that takes and returns a
     * <code>Complex</code> object instead of a double. Both the real and
     * imaginary parts are evaluated and returned as a <code>Complex</code>.
     * 
     * @param c The complex number to consider.
     * @param toler The smallest number not to evaluate as zero.
     * @return A <code>Complex</code> whose real and imaginary parts have been
     *         "chopped."
     */
    public static Complex chop(Complex c, double toler) {
        double real = (Math.abs(c.real) < toler) ? 0.0 : c.real;
        double imag = (Math.abs(c.imag) < toler) ? 0.0 : c.imag;
        return new Complex(real, imag);
    }

    /**
     * Default usage of <code>chop(Complex)</code> that uses default tolerance
     * <code>CHOP_TOLERANCE</code> for the real and imaginary parts of the
     * <code>Complex</code>.
     * 
     * @param c The complex number to consider.
     * @return A <code>Complex</code> whose real and imaginary parts have been
     *         "chopped."
     */
    public static Complex chop(Complex c) {
        return chop(c, CHOP_TOLERANCE);
    }

    /**
     * This is the default tolerance value for the <code>chop</code> method
     * 10^-10.
     */
    protected static final double ERROR_TOLERANCE = Math.pow(10, -5);

    /**
     * Default usage of <code>errorRound(double)</code> that uses the default
     * tolerance <code>ROUND_TOLERANCE</code>.
     */
    public static double errorRound(double d) {
        return errorRound(d, ERROR_TOLERANCE);
    }

    /**
     * This method will attempt to round a double that under perfect algebraic
     * conditions should be a obviously rational number. Because of the nature
     * of computer numerics certain numbers such as 1.0 are represented as
     * 0.999999999999. I have tried to fix this error with his method.
     */
    public static double errorRound(double d, double toler) { // added on
                                                                // April 30,
                                                                // 2002
        double reply = d;
        if(toler > Math.abs((Math.floor(1000 * d)) - (1000 * d)))
            reply = Math.floor(1000 * d) / 1000;
        if(toler > Math.abs((Math.ceil(1000 * d)) - (1000 * d)))
            reply = Math.ceil(1000 * d) / 1000;

        return reply;
    }

    /**
     * This method returns the passed double only specified to a certain number
     * of digits.
     * 
     * @param d The number to trim.
     * @param digits The numbers of decimal places to return.
     * @return The number trimed to <code>digits</code> decimal places.
     */
    public static double trim(double d, int digits) {
        double scalar = Math.pow(10, digits);
        return ((int) (d * scalar)) / scalar;
    }

    /**
     * This method returns the passed float only specified to a certain number
     * of digits.
     * 
     * @param f The number to trim.
     * @param digits The numbers of decimal places to return.
     * @return The number trimed to <code>digits</code> decimal places.
     */
    public static float trim(float f, int digits) {
        float scalar = (float) Math.pow(10, digits);
        return ((int) (f * scalar)) / scalar;
    }

    /**
     * Determines if the number given is prime. The number for this algorithm is
     * limited to the bounds of long a 64-bit number. (0 to (2^64)/2-1
     * (9223372036854775807))
     * 
     * @param l The long number to determine primality.
     * @return true if <code>n</code> is prime, false otherwise
     */
    public static boolean isPrime(long l) {
        long[] f = factor(l);
        if(f.length == 1)
            return true;
        else
            return false;
    }
}