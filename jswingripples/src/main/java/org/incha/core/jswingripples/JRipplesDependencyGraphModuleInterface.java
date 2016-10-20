/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

/**
 * Interface of JRipples modules that build a dependency graph and populate JRipples EIG database.
 * @author Maksym Petrenko
 *
 */
public interface JRipplesDependencyGraphModuleInterface extends
		JRipplesModule {
	/**
	 * Called to calculate a dependency graph for all nodes in the JRipple EIG. Typically is called upon EIG initialization.
	 */
	void AnalyzeProject();
}
