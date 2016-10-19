/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

/**
 * This class is a common interface for all JRipples modules. It
 * provides methods that notify a module when it is loaded or
 * shut down. It also tells a module what was a role of a controller,
 * which triggered activation or deactivation of this module, to process
 * load and shut down events more appropriately.
 * @author Maksym Petrenko
 *
 */
public interface JRipplesModuleInterface {
    /**
     * Runs module.
     */
    void runModule();
}
