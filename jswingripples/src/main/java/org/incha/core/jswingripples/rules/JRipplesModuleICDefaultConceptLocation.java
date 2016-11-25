/*
 * Created on Dec 4, 2005
 *
 */
package org.incha.core.jswingripples.rules;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.incha.core.jswingripples.JRipplesICModule;
import org.incha.core.jswingripples.JRipplesModuleRunner;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
import org.incha.ui.jripples.EIGStatusMarks;

/**
 * @author Maksym Petrenko
 *
 */
public class JRipplesModuleICDefaultConceptLocation extends JRipplesICModule {
	private final JSwingRipplesEIG eig;

	/**
     * @param eig the eig.
     */
    public JRipplesModuleICDefaultConceptLocation(final JSwingRipplesEIG eig) {
        super();
        this.eig = eig;
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.severe.jripples.modules.interfaces.JRipplesICModule#GetAvailableRulesForMark(java.lang.String)
	 */
	@Override
    public Set<String> GetAvailableRulesForMark(final String mark) {

		if (mark == null) {

			return null;
		} else if (mark.compareTo(EIGStatusMarks.BLANK) == 0) {
			return null;

		} else if (mark.compareTo(EIGStatusMarks.NEXT_VISIT) == 0) {
			final String marks[] = { EIGStatusMarks.LOCATED, EIGStatusMarks.VISITED_CONTINUE, EIGStatusMarks.VISITED };
			return (new LinkedHashSet<>(Arrays.asList(marks)));
		} else if (mark.compareTo(EIGStatusMarks.LOCATED) == 0) {
			final String marks[] = { EIGStatusMarks.LOCATED};
			return (new LinkedHashSet<>(Arrays.asList(marks)));
		} else if (mark.compareTo(EIGStatusMarks.VISITED_CONTINUE) == 0) {
			final String marks[] = { EIGStatusMarks.LOCATED,EIGStatusMarks.VISITED_CONTINUE};
			return (new LinkedHashSet<>(Arrays.asList(marks)));
		} else {
			return null;
		}
	}

    @Override
    public void InitializeStage(JRipplesModuleRunner moduleRunner) {
        final JSwingRipplesEIGNode[] nodes = eig.getAllNodes();
        if (nodes != null) {
            for (int i = 0; i < nodes.length; i++) {
                nodes[i].setMark(EIGStatusMarks.BLANK);
            }

            if (eig.getMainClass() != null) {
                final JSwingRipplesEIGNode mainType = getMainClassNode(nodes);
                if (mainType != null) {
                    mainType.setMark(EIGStatusMarks.NEXT_VISIT);
                }
            }
        }
        moduleRunner.moduleFinished();
        eig.getHistory().clear();
    }


	/*
	 * (non-Javadoc)
	 *
	 * @see org.severe.jripples.modules.interfaces.JRipplesICModule#ApplyRuleAtNode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void ApplyRuleAtNode(final String rule, final JSwingRipplesEIGNode node, final int granularity) {
        if (rule.compareTo(EIGStatusMarks.LOCATED) == 0) {
            CommonEIGRules.assignMarkToNodeAndParents(eig, node,EIGStatusMarks.LOCATED);
        } else if (rule.compareTo(EIGStatusMarks.VISITED_CONTINUE) == 0) {
            CommonEIGRules.applyRuleToNode(eig, node,rule,granularity);

        } else if (rule.compareTo(EIGStatusMarks.VISITED) == 0) {

            CommonEIGRules.applyRuleToNode(eig, node,rule,0);
        }

	}

	@Override
    public void ApplyRuleAtNode(final String rule, final JSwingRipplesEIGNode nodeFrom, final JSwingRipplesEIGNode nodeTo) {
        if (rule.compareTo(EIGStatusMarks.LOCATED) == 0) {
            CommonEIGRules.assignMarkToNodeAndParents(eig, nodeFrom,EIGStatusMarks.LOCATED);

        } else if (rule.compareTo(EIGStatusMarks.VISITED_CONTINUE) == 0) {
            CommonEIGRules.assignMarkToNodeAndNeighbor(eig, nodeFrom, nodeTo, rule,EIGStatusMarks.NEXT_VISIT);

        } else if (rule.compareTo(EIGStatusMarks.VISITED) == 0) {

            CommonEIGRules.applyRuleToNode(eig, nodeFrom,rule,0);
        }
	}

    @Override
    public void runModuleWithinRunner(JRipplesModuleRunner moduleRunner) {
        InitializeStage(moduleRunner);
    }

    private JSwingRipplesEIGNode getMainClassNode(final JSwingRipplesEIGNode[] nodes) {
        for (JSwingRipplesEIGNode node : nodes) {
            final IMember member = node.getNodeIMember();
            if (member instanceof IType && ((IType) member).getFullyQualifiedName().equals(eig.getMainClass())) {
                return node;
            }
        }
        return null;
    }
}
