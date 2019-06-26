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
public class CapabilitiesJL {
    /* Needed for the Mozilla-JSS provider */
    static final String initValues = System.getProperty("user.dir").concat("/initValues");

    /**
     * List the the available capabilities for ciphers, key agreement, macs, messages
     * digest, signatures and other objects in the specied provider.
     *
     * This is based on an example given by Julien Nicoulaud
     * @see https://gist.github.com/nicoulaj/531761 
     */
    public static void main(String[] args) {
        String providerName = "Mozilla-JSS";
        Provider provider = Security.getProvider(providerName);
        if (provider == null) {
            System.out.println(providerName + " not found, will try to install it\n");
            try {
                CryptoManager.initialize(initValues);
                CryptoManager cm = CryptoManager.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        try {
          Provider p[] = Security.getProviders();
          for (int i = 0; i < p.length; i++) {
              System.out.println(p[i]);
              for (Enumeration e = p[i].keys(); e.hasMoreElements();)
                  System.out.println("\t" + e.nextElement());
           }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
