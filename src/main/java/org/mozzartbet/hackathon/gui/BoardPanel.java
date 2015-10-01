package org.mozzartbet.hackathon.gui;

import org.mozzartbet.hackathon.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Board panel with the community cards and general information.
 */
public class BoardPanel extends JPanel {
    
    /** The serial version UID. */
    private static final long serialVersionUID = 8530615901667282755L;

    /** The maximum number of community cards. */
    private static final int NO_OF_CARDS = 5;
    
    /** Label with the bet. */
    private final JLabel betLabel;

    /** Label with the pot. */
    private final JLabel potLabel;

    /** Labels with the community cards. */
    private final JLabel[] cardLabels;
    
    /** Label with a custom message. */
    private final JLabel messageLabel;
    
    /**
     * Constructor.
     */
    public BoardPanel() {

        
        setBorder(UIConstants.PANEL_BORDER);
        
        setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        JLabel label = new JLabel("Bet");
        label.setForeground(Color.GREEN);
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        label = new JLabel("Pot");
        label.setForeground(Color.GREEN);
        gc.gridx = 3;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(0, 5, 0, 5);
        add(label, gc);
        
        betLabel = new JLabel(" ");
        betLabel.setBorder(UIConstants.LABEL_BORDER);
        betLabel.setForeground(Color.GREEN);
        betLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(5, 5, 5, 5);
        add(betLabel, gc);

        potLabel = new JLabel(" ");
        potLabel.setBorder(UIConstants.LABEL_BORDER);
        potLabel.setForeground(Color.GREEN);
        potLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 3;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        gc.insets = new Insets(5, 5, 5, 5);
        add(potLabel, gc);

        // The five card positions.
        cardLabels = new JLabel[NO_OF_CARDS];
        for (int i = 0; i < 5; i++) {
            cardLabels[i] = new JLabel(ResourceManager.getIcon("/images/card_placeholder.png"));
            gc.gridx = i;
            gc.gridy = 2;
            gc.gridwidth = 1;
            gc.gridheight = 1;
            gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.NONE;
            gc.weightx = 0.0;
            gc.weighty = 0.0;
            gc.insets = new Insets(5, 1, 5, 1);
            add(cardLabels[i], gc);
        }
        
        // Message label.
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 5;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.insets = new Insets(0, 0, 0, 0);
        add(messageLabel, gc);
        
        // Control panel.
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 5;
        gc.gridheight = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        setPreferredSize(new Dimension(600, 370));
        
        update(null, 0, 0);
    }
    
    /**
     * Updates the current hand status.
     * 
     * @param bet
     *            The bet.
     * @param pot
     *            The pot.
     */
    public void update(List<Card> cards, int bet, int pot) {
        if (bet == 0) {
            betLabel.setText(" ");
        } else {
            betLabel.setText("RSD " + bet);
        }
        if (pot == 0) {
            potLabel.setText(" ");
        } else {
            potLabel.setText("RSD " + pot);
        }
        int noOfCards = (cards == null) ? 0 : cards.size();
        for (int i = 0; i < NO_OF_CARDS; i++) {
            if (i < noOfCards) {
                cardLabels[i].setIcon(ResourceManager.getCardImage(cards.get(i)));
            } else {
                cardLabels[i].setIcon(ResourceManager.getIcon("/images/card_placeholder.png"));
            }
        }
    }
    
    /**
     * Sets a custom message.
     * 
     * @param message
     *            The message.
     */
    public void setMessage(String message) {
        if (message.length() == 0) {
            messageLabel.setText(" ");
        } else {
            messageLabel.setText(message);
        }
    }
    

}
