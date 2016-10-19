/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

import java.util.Set;

import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

/**
 * Interface of JRipples modules that provide various estimations for JRipplesEIG nodes.
 * @author Maksym Petrenko
 * @see JRipplesEIG
 * @see JRipplesEIGNode
 *
 */
public interface JRipplesAnalysisModuleInterface extends
		JRipplesModuleInterface {

	/**
	 * Called to calculate estimations for all nodes in the JRipple EIG. Typically is called upon EIG initialization.
	 */
	void AnalyzeProjectWithinModuleRunner(JRipplesModuleRunner moduleRunner);
}

