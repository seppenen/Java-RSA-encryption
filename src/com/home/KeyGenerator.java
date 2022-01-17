package com.home;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class KeyGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        // Create keypairs
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstanceStrong();
        keyGen.initialize(2048, random);

        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        // Save keys to file
        byte[] encPriv = priv.getEncoded();
        FileOutputStream privfos = new FileOutputStream(Constants.PRIVATE_KEY);
        privfos.write(encPriv);
        privfos.close();

        byte[] encPub = pub.getEncoded();
        FileOutputStream pubfos = new FileOutputStream(Constants.PUBLIC_KEY);
        pubfos.write(encPub);
        pubfos.close();


    }
    }

