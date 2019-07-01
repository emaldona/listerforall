// TODO: turn it into a module
//
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


/**
 * List the available capablities for ciphers, key agreement, macs, message
 * digests, signatures and other objects for the Mozilla-JSS provider.
 *
 * This is based on example 1 from Cryptography for Java by David Hook
 * It incorporates code from org.mozilla.jss.tests.PSSProvider
 */
public class Capabilities {
    /* Might be needed for the Mozilla-JSS provider */
    static final String initValues = System.getProperty("user.dir").concat("/initValues");

    public static void listThisOne(Provider p) throws Exception {
        String pName = p.getName();
        String fName = pName + "_Capabilities.txt";
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
            fw.write(System.lineSeparator()); //new line
        }

        fw.write(String.format("ListerForAll done\n."));
        fw.close();
        File resultsFile = new File(fName);
        assert(resultsFile.exists());
        System.out.println("Capabilities list written to " + fName);
    }

    public static void main(String[] args) throws Exception {
       if (false) {
       // Stolen from jss lister branch
        // Before we initialize the CryptoManager, the JSS Provider shouldn't
        // exist.
        assert(Security.getProvider("Mozilla-JSS") == null);

        CryptoManager.initialize("");
        CryptoManager cm = CryptoManager.getInstance();
        cm.setPasswordCallback(
             new FilePasswordCallback(System.getProperty("user.dir").concat("/password")));

        // Validate that the CryptoManager registers us as the
        // default/first provider.
        Provider p = Security.getProviders()[0];
        assert(p.getName().equals("Mozilla-JSS"));
        assert(p instanceof org.mozilla.jss.JSSProvider);
        }
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
