package com.home;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.home.Constants.MESSAGE;


public class KeyReader {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

    // Read keys from file
    KeyFactory kf1 = null;
    KeyFactory kf2 = null;
    PublicKey RSAPublicKey = null;
    PrivateKey RSAPrivateKey = null;

    Path pathPub = Paths.get(Constants.KEY_PATH + Constants.PUBLIC_KEY);
    Path pathPriv = Paths.get(Constants.KEY_PATH + Constants.PRIVATE_KEY);

    byte[] bytesPub = Files.readAllBytes(pathPub);
    byte[] bytesPriv = Files.readAllBytes(pathPriv);

    X509EncodedKeySpec ks1 = new X509EncodedKeySpec(bytesPub);
    PKCS8EncodedKeySpec ks2 = new PKCS8EncodedKeySpec(bytesPriv);

        try {
            kf1 = KeyFactory.getInstance("RSA");
            kf2 = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            RSAPublicKey = kf1.generatePublic(ks1);
            RSAPrivateKey = kf2.generatePrivate(ks2);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


        Base64.Encoder encoder = Base64.getEncoder();
        byte[] pubKeyBytes = RSAPublicKey.getEncoded();
        String pubKeyBase64 = encoder.encodeToString(pubKeyBytes);
        System.out.println(pubKeyBase64);
    //Sign data RSA256
    Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(RSAPrivateKey, new SecureRandom());
    byte[] message = MESSAGE.getBytes();
        signature.update(message);
    byte[] sigBytes = signature.sign();

    //Signature verification
    Signature signature1 = Signature.getInstance("SHA1withRSA");
        signature1.initVerify(RSAPublicKey);
        signature1.update(message);

    boolean result = signature1.verify(sigBytes);



        System.out.println(RSAPublicKey.toString() + " Signature verified = "+result);
}
}
