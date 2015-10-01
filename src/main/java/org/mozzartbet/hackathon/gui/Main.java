package org.mozzartbet.hackathon.gui;

import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.bots.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Glavna klasa, testiranje botova GUI
 */
public class Main {

    public static void main(String args[]) throws Exception {
        if(args.length < 2){
            System.out.println("Morate registrovati bar dva igraca da bi ste igrali");
            return;
        }
        List<Player> players = new ArrayList<Player>();
        for(String botClass : args){
            Bot bot = (Bot)Class.forName(botClass).newInstance();
            players.add(new Player(bot));
        }

        new GameForm(players);




    }
}
