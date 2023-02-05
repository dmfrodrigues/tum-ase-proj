package com.asedelivery.common.auth.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

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
        InputStream is = null;

        try {
            // Get the path to the keystore file in the resources folder
            try {
                InputStreamSource iss = new ClassPathResource("classpath:ase_project.keystore");
                is = iss.getInputStream();
            } catch(FileNotFoundException e){
                System.err.println("Failed to load with ClassPathResource; using ResourceUtils.getFile");

                File keystoreFile = ResourceUtils.getFile("classpath:ase_project.keystore");
                is = new FileInputStream(keystoreFile);
            }
            keyStore.load(is, password);
            keyAlias = keyStore.aliases().nextElement();
        } catch (Exception e) {
            System.err.println("Error when loading KeyStore");
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    protected PublicKey getPublicKey() {
        try {
            Certificate cert = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = cert.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Key getPrivateKey() {
        try {
            Key privateKey = (PrivateKey) keyStore.getKey(keyAlias, password);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
