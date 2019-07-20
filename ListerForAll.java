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
    public static String verboseFile = "ProvidersCapabilitiesVerbose.txt";

    /* Inner class to avoid having to create the NSS databases
     * and instead use the existing one in the system
     */
    public static class UseSystemDB {
        /* NSS_DB_LOCATION should probably be setup via build.sh and find a way to
         * query the value from here, need to learn how to do that.
         */
        public static String NSS_DB_LOCATION = "/etc/pki/nssdb";
        private UseSystemDB() {}
        /* Only a static method */

       /* This method is adapted from one used in the candlepin projects
        * https://github.com/candlepin/candlepin/pull/2370/files#diff-170cc2e1af322c9796d4d8fe20e32bb0R98
        * an approach that was suggested by Alexander Scheel
        */
        public static void addJSSProviderListerWay() throws Exception {
            logger.debug("Starting call to JSSProviderLoader.addProvider()...");
            InitializationValues ivs = new InitializationValues(NSS_DB_LOCATION);
            ivs.noCertDB = true;
            ivs.installJSSProvider = true;
            ivs.initializeJavaOnly = false;
            CryptoManager.initialize(ivs);
            CryptoManager cm = CryptoManager.getInstance();
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
            UseSystemDB.addJSSProviderListerWay();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Alternative method failed: keep going");
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
            addJssProvider(args);
        } catch (Exception e) {
            logger.info("Exception caught " +
                        "in main: " + e.getMessage(), e);
            System.out.println(e);
            return;
        }

        FileWriter fw = new FileWriter(new File(logFile));

        Provider ps[] = Security.getProviders();


        try {
            fw.write(String.format("ListerForAll: brief list starts\n"));
            for (int i = 0; i < ps.length; i++) {
                String pName = ps[i].getName();
                fw.write(System.lineSeparator());
                fw.write(String.format("Capabilities of %s written out\n", pName));
                for (Enumeration e = ps[i].keys(); e.hasMoreElements();) {
                    fw.write(String.format("\t %s", e.nextElement()));
                    fw.write(System.lineSeparator());
                }
            }
            fw.write(String.format("ListerForAll: brief list done\n"));
            fw.close();
            File resultsFile = new File(logFile);
            assert(resultsFile.exists());
            System.out.println("Capabilities list written to " + logFile);
        } catch (Exception e) {
            System.out.println(e);
        }

        /* Verbose list to a separate file */
        FileWriter vw = new FileWriter(new File(verboseFile));
        /* List them using the verbose listing method which
         * adds results for each provider listed to the log file
         */
        for (int i = 0; i < ps.length; i++) {
            listCapabilities(vw, ps[i]);
        }

        vw.write(String.format("Verbose done\n"));
        vw.close();
        File vresultsFile = new File(verboseFile);
        assert(vresultsFile.exists());
        System.out.println("Wrote " + verboseFile);
    }
}
