package org.incha.ui.classview;

import java.awt.Color;

import javax.swing.JLabel;

import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
import org.incha.core.search.Highlight;
import org.incha.core.search.Searcher;
import org.incha.ui.jripples.EIGStatusMarks;

public class ClassTreeRenderer extends AbstractMemberRenderer {
    private static final long serialVersionUID = 5646472411943179112L;

    /**
     * Default constructor.
     */
    public ClassTreeRenderer() { super(); }

    /**
     * @param label the table cell being rendered.
     * @param node the associated eig node.
     * @param column the table column for the given label.
     */
    @Override
    protected void renderOtherColumn(final JLabel label, final JSwingRipplesEIGNode node,
            final int column) {
        final String mark = node.getMark();
        switch (column) {
            case 1:
                if (mark != null && !mark.isEmpty()) {
                    final Color color = EIGStatusMarks.getColorForMark(mark);
                    label.setBackground(color);
                    label.setText(mark);
                } else {
                    label.setText("");
                }
                label.setIcon(null);
            break;
            case 2:
                label.setIcon(null);
                label.setText(node.getProbability());
            break;
            case 3:
                label.setIcon(null);
                label.setText(getFullName(node));
            break;
            case 4:
                label.setBackground(Highlight.getColor(node.getShortName()));
                label.setText(searchResults(node));
            default:
            break;
        }
    }

    /**
     * Creates a string containing the number of appearances of the last searched term in the
     * given node.
     * @param node the EIG node.
     * @return string containing the total search hits. If there are no search hits, the empty
     * string is returned.
     */
    private String searchResults(JSwingRipplesEIGNode node) {
        int hits = Searcher.getInstance().totalHits(node.getShortName());
        return (hits == 0 ? "" : (" (" + hits + ")"));
    }
}
