package org.mozzartbet.hackathon.gui.loader;

import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.bots.BasicBot;

import java.util.ArrayList;
import java.util.List;

/**
 * PLayers Loader
 * TODO should be done via reflection or config file
 */
public class PlayerLoader {

    public static List<Player> getPlayerList(){
        List<Player> players = new ArrayList<Player>();
        players.add(new Player(new BasicBot(75, 75)));
        players.add(new Player(new BasicBot(0, 75)));
        players.add(new Player(new BasicBot(25, 50)));
        players.add(new Player(new BasicBot(50, 25)));
        return players;
    }
}
