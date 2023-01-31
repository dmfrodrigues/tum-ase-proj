package com.asedelivery.backend.Auth.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;

@Component
public class KeyStoreManager {
    private KeyStore keyStore;
    private String keyAlias;
    private char[] password = "ase2223-t15".toCharArray();

    public KeyStoreManager() throws KeyStoreException, IOException {
        loadKeyStore();
    }

    public void loadKeyStore() throws KeyStoreException, IOException {
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fis = null;

        try {
            // Get the path to the keystore file in the resources folder
            File keystoreFile = ResourceUtils.getFile("classpath:ase_project.keystore");
            fis = new FileInputStream(keystoreFile);
            keyStore.load(fis, password);
            keyAlias = keyStore.aliases().nextElement();
        } catch (Exception e) {
            System.err.println("Error when loading KeyStore");
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    protected PublicKey getPublicKey() {
        try {
            // Get public key from keyStore and return it
            Certificate cert = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = cert.getPublicKey();
            return publicKey;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected Key getPrivateKey() {
        try {
            // Get private key from keyStore and return it
            Key privateKey = (PrivateKey) keyStore.getKey(keyAlias, password);
            return privateKey;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
