//package org.mozilla.jss.tests;

import java.util.*;
import java.security.*;
import javax.crypto.*;

import org.mozilla.jss.*;
import org.mozilla.jss.pkcs11.*;

import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.Properties;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.jss.util.Password;
import org.mozilla.jss.util.PasswordCallback;
import org.mozilla.jss.util.PasswordCallbackInfo;
import org.mozilla.jss.util.NullPasswordCallback;
import org.mozilla.jss.crypto.AlreadyInitializedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * List the available capabilities for ciphers, key agreement, macs, message
 * digests, signatures and other objects for the Mozilla-JSS provider.
 *
 * This is based on example 1 from Cryptography for Java by David Hook
 * A second briefer enumeration method on main is from example given at
 * http://www.java2s.com/Code/Java/Security/ListAllProviderAndItsAlgorithms.html
 * It incorporates code from org.mozilla.jss.tests.JSSProvider
 */
public class ListerForAll {

    public static Logger logger = LoggerFactory.getLogger(ListerForAll.class);
    public static String logFile = "ProvidersCapabilities.txt";

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


    public static void listCapabilities(FileWriter fw, Provider p) throws Exception {
        System.out.println(p);
        String pName = p.getName();
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
    }

    public static void addJssProvider(String[] args) throws Exception {

        try {
            String dbArg = System.getProperty("user.dir").concat("/nssdb");
            String pwArg = System.getProperty("user.dir").concat("/passwords");
            SetupDB dbSetter = new SetupDB();
            dbSetter.setupTheDatabase(dbArg, pwArg);
        } catch (AlreadyInitializedException aie) {
            logger.info("AlreadyInitializedException caught " +
                        "CryptoManager.initialize(initializationValues): " + aie.getMessage(), aie);
            aie.printStackTrace();
            System.out.println("Already Initialized: keep going");
        }
        // Validate that the CryptoManager registers us as the
        // default/first provider.
        Provider p = Security.getProviders()[0];
        assert(p.getName().equals("Mozilla-JSS"));
        assert(p instanceof org.mozilla.jss.JSSProvider);
        System.out.println("Mozilla-JSS is registered as first provider");
        System.out.println("First provider is:");
        System.out.println(p.getName());
    }

    public static void main(String[] args) throws Exception {
        try {

          File dir = new File(System.getProperty("user.dir").concat("/nssdb"));
          dir.mkdir();

            addJssProvider(args);
        } catch (Exception e) {
            logger.info("Exception caught " +
                        "in main: " + e.getMessage(), e);
            System.out.println(e);
            return;
        }

        FileWriter fw = new FileWriter(new File(logFile));

        Provider ps[] = Security.getProviders();
        /* List them using the verbose listing method which
         * adds results for each provider listed to the log file
         */
        for (int i = 0; i < ps.length; i++) {
            listCapabilities(fw, ps[i]);
        }

        fw.write(String.format("ListerForAll done\n."));
        fw.close();
        File resultsFile = new File(logFile);
        assert(resultsFile.exists());
        System.out.println("Wrote " + logFile);

        /* List them using the brief listing method
         * which just writes to standard output
         */
        for (int i = 0; i < ps.length; i++) {
            System.out.println(ps[i]);
            for (Enumeration e = ps[i].keys(); e.hasMoreElements();)
                System.out.println("\t" + e.nextElement());
        }
    }
}
