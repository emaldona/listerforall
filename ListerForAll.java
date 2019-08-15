//package org.mozilla.jss.samples;

import java.security.Security;
import java.security.Provider;
import org.slf4j.Logger;

/**
 * List the available capabilities for ciphers, key agreement, macs, message
 * digests, signatures and other objects for the Mozilla-JSS provider.
 *
 */
public class ListerForAll {

    public static void main(String[] args) {
        try {
            Capabilities lister = new Capabilities();
            if (!lister.createOutputDirs()) return;
            lister.addJssProvider();
            Provider ps[] = Security.getProviders();
            lister.listBrief(ps);
            lister.listVerbose(ps);
        } catch (Exception e) {
            Capabilities.logger.info("Exception caught in main: " + e.getMessage(), e);
        }
    }
}
