/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 *
 * @author Leon
 */
public class RSA {
    static BigInteger p,q,n,phi,e,d;
    SecureRandom r;
    
    public RSA(){
        r = new SecureRandom();
        p = BigInteger.probablePrime(31, r);
        q = BigInteger.probablePrime(31, r);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(15, r);
        while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }
    
    public static String encode(byte [] text){
        String s = "";
        for(byte b : text){
            s += Byte.toString(b);
        }
        return s;
    }
    
    public static byte [] encrypted(byte [] text){
        return new BigInteger(text).modPow(e, n).toByteArray();
    }
    
    public static byte [] decrypted(byte [] text){
        return new BigInteger(text).modPow(d, n).toByteArray();
    }
    
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        RSA rsa = new RSA();
        System.out.println("Time taken to generate keys is " + (float)(System.currentTimeMillis() - start)/1000 + " seconds");
        System.out.println("Enter input string");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        start = System.currentTimeMillis();
        byte [] encrypted = rsa.encrypted(input.getBytes());
        System.out.println("Encrypted String " + new String(encrypted));
        System.out.println("Time taken to encrypt is " + (float)(System.currentTimeMillis() - start)/1000 + " seconds");
        start = System.currentTimeMillis();
        byte [] decrypted = rsa.decrypted(encrypted);
        System.out.println("Before decryption code" + encode(decrypted));
        System.out.println("After complete decryption " + new String(decrypted));
        System.out.println("Time taken to decrypt is " + (float)(System.currentTimeMillis() - start)/1000 + " seconds");
        
    }
    
}

/*
Test Case 1 : 
Time taken to generate keys is 0.153 seconds
Enter input string
poiu
Encrypted String W�(-�Ox
Time taken to encrypt is 0.001 seconds
Before decryption code112111105117
After complete decryption poiu
Time taken to decrypt is 0.001 seconds

Test Case 2 :
Time taken to generate keys is 1.551 seconds
Enter input string
werty
Encrypted String ��(�Yf�
Time taken to encrypt is 0.0 seconds
Before decryption code119101114116121
After complete decryption werty
Time taken to decrypt is 0.001 seconds

Test Case 3 : 
Time taken to generate keys is 1.973 seconds
Enter input string
2345
Encrypted String 7 �H ��
Time taken to encrypt is 0.001 seconds
Before decryption code50515253
After complete decryption 2345
Time taken to decrypt is 0.001 seconds
*/
