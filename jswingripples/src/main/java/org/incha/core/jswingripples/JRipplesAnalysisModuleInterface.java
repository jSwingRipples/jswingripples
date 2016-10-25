/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

/**
 * Interface of JRipples modules that provide various estimations for JRipplesEIG nodes.
 * @author Maksym Petrenko

 */
public interface JRipplesAnalysisModuleInterface extends JRipplesModule {

	/**
	 * Called to calculate estimations for all nodes in the JRipple EIG. Typically is called upon EIG initialization.
	 */
	void AnalyzeProjectWithinModuleRunner(JRipplesModuleRunner moduleRunner);
}

