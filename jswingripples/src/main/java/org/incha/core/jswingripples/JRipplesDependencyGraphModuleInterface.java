/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

import java.util.Set;

import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

/**
 * Interface of JRipples modules that build a dependency graph and populate JRipples EIG database.
 * @author Maksym Petrenko
 *
 */
public interface JRipplesDependencyGraphModuleInterface extends
		JRipplesModuleInterface {
	/**
	 * Called to calculate a dependency graph for all nodes in the JRipple EIG. Typically is called upon EIG initialization.
	 */
	void AnalyzeProject();
}
