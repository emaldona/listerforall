// TODO: turn it into a module
//
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.net.ssl.*;

import org.mozilla.jss.*;
import org.mozilla.jss.pkcs11.*;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

/**
 * List the available capablities for ciphers, key agreement, macs, message
 * digests, signatures and other objects for the Mozilla-JSS provider.
 */
public class Capabilities {
    /* Needed for the Mozilla-JSS provider */
    static final String nssconf = System.getProperty("user.dir").concat("/nss.cfg");

    //final Logger logger = LoggerFactory.getLogger(Capabilities.class);
    static final String[] providers = {
        /*
        -- NullPointerException on provider.keySet on jdk 8
        "JdkLDAP",
        "JdkSASL", 
        */
        "SunPKCS11",
        "SUN",
        "SunRsaSign",
        "SunEC",
        "SunJSSE",
        "SunJCE",
        "SunJGSS",
        "SunSASL",
        "XMLDSig",
        "SunPCSC",
        "Mozilla-JSS",
        ""
    };

    /**
     * List the the available capabilities for ciphers, key agreement, macs, messages
     * digest, signatures and other objects in the specied provider.
     *
     * This is based on example 1 from Cryptography for Java by David Hook
     *
     * @param providerName name of the provider
     * @throws Exception if the missing provider can't be installed
     */
    public static void listThisOne(String providerName) throws Exception {

        Provider provider = Security.getProvider(providerName);
        if (provider == null) {
            System.out.println(providerName + " not found, will try to install it\n");
            try {
                CryptoManager.initialize(nssconf);
                CryptoManager cm = CryptoManager.getInstance();
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
        System.out.println( "Capabilities of " + providerName);
        Set<Object> keySet = provider.keySet();
        assert(keySet != null);
        Iterator it = keySet.iterator();
        assert(it != null);
        provider = Security.getProvider(providerName);
        it = provider.keySet().iterator();
        while (it.hasNext()) {
            String entry = (String)it.next();
            if (entry.startsWith("Alg.alias.")) {
                entry = entry.substring("Alg.Alias.".length());
            }
            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length()+1);
            assert(name != null);
            System.out.println("\t" + factoryClass + ": " + name);
        }
    }

    public static void main(String[] args) {
        try {
            int i = 0;
            while (providers[i].length() > 0) {
                String providerName = providers[i];
                System.out.println("----" + providerName + "----");
                listThisOne(providers[i]);
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
