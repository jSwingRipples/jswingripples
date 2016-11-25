/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

import java.util.Set;

import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

/**
 * Interface of JRipples modules that provide and execute Incremental Change (IC) propagation rules for JRipplesEIG nodes.
 * @author Maksym Petrenko
 *
 */
public abstract class JRipplesICModule extends JRipplesModule {

	public abstract void InitializeStage(JRipplesModuleRunner moduleRunner);
	/**
	 * Returns a set of marks (names of propagation rules), available for a node with the supplied current mark. This is called to determine which propagation rules can still be applied to a particular node and display this rules in GUI.
	 * @param mark
	 *  current mark of a node
	 * @return
	 * 	a set of marks (of type String)
	 */
	public abstract Set<String> GetAvailableRulesForMark(String mark);
	/**
	 * Applies the selected propagation rule at the selected node.
	 * @param rule
	 *  rule to apply
	 * @param node
	 *  node to apply the rule at
	 * @param granularity
	 * granularity at which the rule is applied
	 */
	public abstract void ApplyRuleAtNode(String rule, JSwingRipplesEIGNode node, int granularity);
	/**
	 * Applies the selected propagation rule at the selected node using the particular dependency instead of the whole dependency graph.
	 * @param rule
	 * rule to apply
	 * @param nodeFrom
	 * node to apply the rule at
	 * @param nodeTo
	 * node, to which the rule propagates

	 */
	public abstract void ApplyRuleAtNode(String rule, JSwingRipplesEIGNode nodeFrom, JSwingRipplesEIGNode nodeTo);
}
