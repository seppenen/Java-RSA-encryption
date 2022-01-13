package com.home;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {

        String messageString = "text";

        // Create keypairs
        /*
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyGen.initialize(2048, random);

        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        // Save keys to file
        byte[] encPriv = priv.getEncoded();
        FileOutputStream privfos = new FileOutputStream("RSAPrivateKey.key");
        privfos.write(encPriv);
        privfos.close();

        byte[] encPub = pub.getEncoded();
        FileOutputStream pubfos = new FileOutputStream("RSAPublicKey.key");
        pubfos.write(encPub);
        pubfos.close();
        */

        // Read keys from file
        Path pathPub = Paths.get("/Users/alexandrseppenen/Development/Java/JWT RSA Encryption/RSAPublicKey.key");
        Path pathPriv = Paths.get("/Users/alexandrseppenen/Development/Java/JWT RSA Encryption/RSAPrivateKey.key");

        byte[] bytesPub = Files.readAllBytes(pathPub);
        byte[] bytesPriv = Files.readAllBytes(pathPriv);

        X509EncodedKeySpec ks1 = new X509EncodedKeySpec(bytesPub);
        KeyFactory kf1 = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec ks2 = new PKCS8EncodedKeySpec(bytesPriv);
        KeyFactory kf2 = KeyFactory.getInstance("RSA");

        PublicKey RSAPublicKey = kf1.generatePublic(ks1);
        PrivateKey RSAPrivateKey = kf2.generatePrivate(ks2);

        //Sign data RSA256
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(RSAPrivateKey, new SecureRandom());
        byte[] message = messageString.getBytes();
        signature.update(message);
        byte[] sigBytes = signature.sign();

        //Validating signature
        Signature signature1 = Signature.getInstance("SHA1withRSA");
        signature1.initVerify(RSAPublicKey);
        signature1.update(message);

        boolean result = signature1.verify(sigBytes);
        System.out.println("result = "+result);

    }
    }

