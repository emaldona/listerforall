//package org.mozilla.jss.tests;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.net.ssl.*;

import org.mozilla.jss.*;
import org.mozilla.jss.pkcs11.*;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.mozilla.jss.util.Password;
import org.mozilla.jss.util.PasswordCallback;
import org.mozilla.jss.util.PasswordCallbackInfo;
import org.mozilla.jss.util.NullPasswordCallback;

/**
 * List the available capabilities for ciphers, key agreement, macs, message
 * digests, signatures and other objects for the Mozilla-JSS provider.
 *
 * This is based on example 1 from Cryptography for Java by David Hook
 * It incorporates code from org.mozilla.jss.tests.PSSProvider
 */
public class ListerForAll {

    /* Inner class FilePasswordCallback needed by inner class SetupDB
     */
    public static class FilePasswordCallback implements PasswordCallback {

        private Properties passwords;

        public FilePasswordCallback(String filename) throws IOException {
            passwords = new Properties();
            FileInputStream in = new FileInputStream(filename);
            passwords.load(in);
        }

        public Password getPasswordFirstAttempt(PasswordCallbackInfo info)
            throws PasswordCallback.GiveUpException
        {
            String pw = passwords.getProperty(info.getName());
            if ( pw == null ) {
                throw new PasswordCallback.GiveUpException();
            } else {
                System.out.println("***FilePasswordCallback returns " + pw);
                return new Password(pw.toCharArray());
            }
        }

        public Password getPasswordAgain(PasswordCallbackInfo info)
            throws PasswordCallback.GiveUpException
        {
            throw new PasswordCallback.GiveUpException();
        }
    }

    /* Inner class SetupDB to create the NSS databases
     */
    public static class SetupDB {

        SetupDB() {}

        public void setupTheDatabase(String dbdir, String passwords) throws Exception {

            CryptoManager.initialize(dbdir);
            CryptoManager cm = CryptoManager.getInstance();

            (cm.getInternalKeyStorageToken()).initPassword(
                new NullPasswordCallback(),
                new FilePasswordCallback(passwords))
            ;
        }
    }


    public static void listThisOne(Provider p) throws Exception {
        String pName = p.getName();
        String fName = "CapabilitiesOf" + pName + ".txt";
        FileWriter fw = new FileWriter(new File(fName));
        fw.write(String.format("Capabilities of %s\n.", pName));
        Set<Object> keySet = p.keySet();
        assert(keySet != null);
        Iterator it = keySet.iterator();
        assert(it != null);
        it = p.keySet().iterator();
        while (it.hasNext()) {
            String entry = (String)it.next();
            if (entry.startsWith("Alg.alias.")) {
                entry = entry.substring("Alg.Alias.".length());
            }
            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length()+1);
            assert(name != null);
            fw.write(String.format("\t %s : %s", factoryClass, name));
            fw.write(System.lineSeparator());
        }

        fw.write(String.format("ListerForAll done\n."));
        fw.close();
        File resultsFile = new File(fName);
        assert(resultsFile.exists());
        System.out.println("Capabilities list written to " + fName);
    }

    public static void main(String[] args) throws Exception {
       /*
       String dbArg = System.getProperty("user.dir").concat("/nssdb");
       String pwArg = System.getProperty("user.dir").concat("/passwords");
       SetupDB dbSetter = new SetupDB();
       dbSetter.setupTheDatabase(dbArg, pwArg);
       */

        // Validate that the CryptoManager registers us as the
        // default/first provider.
        Provider p = Security.getProviders()[0];
        assert(p.getName().equals("Mozilla-JSS"));
        assert(p instanceof org.mozilla.jss.JSSProvider);
        System.out.println("Mozilla-JSS is registered as first provider");

        try {
            Provider ps[] = Security.getProviders();
            for (int i = 0; i < ps.length; i++) {
                listThisOne(ps[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
