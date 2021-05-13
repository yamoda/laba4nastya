package view.outputview;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class OutputPanel extends JPanel {
    private final JLabel calculationOutput;
    private final JTree calculationTree;

    private final JButton foldButton;
    private final JButton expandButton;

    public OutputPanel() {
        setLayout(new BorderLayout());

        calculationOutput = new JLabel(" ");
        calculationOutput.setFont(new Font("Times Roman", Font.PLAIN, 50));

        calculationTree = new JTree();
        calculationTree.setEditable(true);
        calculationTree.setModel(null);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        foldButton = new JButton("Fold");
        expandButton = new JButton("Expand");
        buttonsPanel.add(foldButton);
        buttonsPanel.add(expandButton);

        add(new JScrollPane(calculationOutput), BorderLayout.NORTH);
        add(calculationTree, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public JLabel getCalculationOutput() { return calculationOutput; }

    public JTree getCalculationTree() { return calculationTree; }

    public JButton getFoldButton() { return foldButton; }

    public JButton getExpandButton() { return expandButton; }
}
