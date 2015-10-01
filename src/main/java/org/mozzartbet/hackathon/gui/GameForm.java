package org.mozzartbet.hackathon.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.Table;
import org.mozzartbet.hackathon.TableType;
import org.mozzartbet.hackathon.observers.IActObserver;

/**
 * The game's main frame.
 * 
 * This is the core class of the Swing UI client application.
 */
public class GameForm extends JFrame implements IActObserver {
    
    /** Serial version UID. */
    private static final long serialVersionUID = -5414633931666096443L;
    
    /** Table type (betting structure). */
    private static final TableType TABLE_TYPE = TableType.NO_LIMIT;

    /** The size of the big blind. */
    private static final int BIG_BLIND = 10;
    
    /** The table. */
    private final Table table;
    
    /** The players at the table. */
    private final Map<String, Player> players;
    
    /** The GridBagConstraints. */
    private final GridBagConstraints gc;
    
    /** The board panel. */
    private final BoardPanel boardPanel;
    
    /** The player panels. */
    private final Map<String, PlayerPanel> playerPanels;

    /** The current dealer's name. */
    private String dealerName;

    /** The current actor's name. */
    private String actorName;


    /**
     * Constructor.
     */
    public GameForm(List<Player> gamePlayers) {
        super("Mozzart Texas Holdem Poker AI Tournament");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(UIConstants.TABLE_COLOR);
        setLayout(new GridBagLayout());

        gc = new GridBagConstraints();
        
        boardPanel = new BoardPanel();
        addComponent(boardPanel, 1, 1, 1, 1);
        
        this.players = new HashMap<String, Player>();
        for(Player player : gamePlayers){
            this.players.put(player.getName(), player);
        }

        table = new Table(TABLE_TYPE, BIG_BLIND, this);
        for (Player player : players.values()) {
            table.addPlayer(player);
        }
        
        playerPanels = new HashMap<String, PlayerPanel>();
        int i = 0;
        for (Player player : players.values()) {
            PlayerPanel panel = new PlayerPanel();
            playerPanels.put(player.getName(), panel);
            switch (i++) {
                case 0:
                    // North position.
                    addComponent(panel, 1, 0, 1, 1);
                    break;
                case 1:
                    // East position.
                    addComponent(panel, 2, 1, 1, 1);
                    break;
                case 2:
                    // South position.
                    addComponent(panel, 1, 2, 1, 1);
                    break;
                case 3:
                    // West position.
                    addComponent(panel, 0, 1, 1, 1);
                    break;
                default:
                    // Do nothing.
            }
        }
        
        // Show the frame.
        pack();
        //setResizable(false);
        //setLocationRelativeTo(null);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize, ySize);
        setVisible(true);

        // Start the game.
        table.run();
    }
    
    @Override
    public void joinedTable(TableType type, int bigBlind, List<Player> players) {
        for (Player player : players) {
            PlayerPanel playerPanel = playerPanels.get(player.getName());
            if (playerPanel != null) {
                playerPanel.update(player);
            }
        }
    }

    @Override
    public void messageReceived(String message) {
        boardPanel.setMessage(message);
    }

    @Override
    public void handStarted(Player dealer) {
        setDealer(false);
        dealerName = dealer.getName();
        setDealer(true);
    }

    @Override
    public void actorRotated(Player actor) {
        setActorInTurn(false);
        actorName = actor.getName();
        setActorInTurn(true);
    }

    @Override
    public void boardUpdated(List<Card> cards, int bet, int pot) {
        boardPanel.update(cards, bet, pot);
    }

    @Override
    public void playerUpdated(Player player) {
        PlayerPanel playerPanel = playerPanels.get(player.getName());
        if (playerPanel != null) {
            playerPanel.update(player);
        }
    }

    /**
     * Adds an UI component.
     * 
     * @param component
     *            The component.
     * @param x
     *            The column.
     * @param y
     *            The row.
     * @param width
     *            The number of columns to span.
     * @param height
     *            The number of rows to span.
     */
    private void addComponent(Component component, int x, int y, int width, int height) {
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0.0;
        gc.weighty = 0.0;
        getContentPane().add(component, gc);
    }

    /**
     * Sets whether the actor  is in turn.
     * 
     * @param isInTurn
     *            Whether the actor is in turn.
     */
    private void setActorInTurn(boolean isInTurn) {
        if (actorName != null) {
            PlayerPanel playerPanel = playerPanels.get(actorName);
            if (playerPanel != null) {
                playerPanel.setInTurn(isInTurn);
            }
        }
    }

    /**
     * Sets the dealer.
     * 
     * @param isDealer
     *            Whether the player is the dealer.
     */
    private void setDealer(boolean isDealer) {
        if (dealerName != null) {
            PlayerPanel playerPanel = playerPanels.get(dealerName);
            if (playerPanel != null) {
                playerPanel.setDealer(isDealer);
            }
        }
    }

    @Override
    public void playerStartThinking(Player player) {

    }

    @Override
    public void playerStopThinking(Player player) {

    }
}
