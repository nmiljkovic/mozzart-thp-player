package org.mozzartbet.hackathon.bots;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.TableType;
import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.bots.Bot;
import org.mozzartbet.hackathon.util.PokerConstants;
import org.mozzartbet.hackathon.util.PokerUtils;

import java.util.List;
import java.util.Set;

/**
 * Basic Texas Hold'em poker bot. <br />
 * <br />
 *
 * The current implementation acts purely on the bot's hole cards, based on the
 * Chen formula, combined with a configurable level of tightness (when to play
 * or fold a hand ) and aggression (how much to bet or raise in case of good
 * cards or when bluffing). <br />
 * <br />
 *
 * TODO:
 * <ul>
 * <li>Improve basic bot AI</li>
 * <li>bluffing</li>
 * <li>consider board cards</li>
 * <li>consider current bet</li>
 * <li>consider pot</li>
 * </ul>
 */
public class BasicBot implements Bot {

    /** Tightness (0 = loose, 100 = tight). */
    private final int tightness;

    /** Betting aggression (0 = safe, 100 = aggressive). */
    private final int aggression;

    /** Table type. */
    private TableType tableType;

    /** The hole cards. */
    private Card[] cards;

    /**
     * Constructor.
     *
     * @param tightness
     *            The bot's tightness (0 = loose, 100 = tight).
     * @param aggression
     *            The bot's aggressiveness in betting (0 = careful, 100 =
     *            aggressive).
     */
    public BasicBot(int tightness, int aggression) {
        if (tightness < 0 || tightness > 100) {
            throw new IllegalArgumentException("Invalid tightness setting");
        }
        if (aggression < 0 || aggression > 100) {
            throw new IllegalArgumentException("Invalid aggression setting");
        }
        this.tightness = tightness;
        this.aggression = aggression;
    }

    /** {@inheritDoc} */
    @Override
    public void joinedTable(TableType type, int bigBlind) {
        this.tableType = type;
    }

    /** {@inheritDoc} */
    @Override
    public void messageReceived(String message) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void handStarted(Player dealer) {
        cards = null;
    }

    /** {@inheritDoc} */
    @Override
    public void actorRotated(Player actor) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void boardUpdated(List<Card> cards, int bet, int pot) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public void playerUpdated(Player player) {
        if (player.getCards().length == PokerConstants.NO_OF_HOLE_CARDS) {
            this.cards = player.getCards();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void playerActed(Player player) {
        // Not implemented.
    }

    /** {@inheritDoc} */
    @Override
    public Action act(int minBet, int currentBet, Set<Action> allowedActions, int currentAmount) {
        Action action = null;
        if (allowedActions.size() == 1) {
            // No choice, must check.
            action = Action.CHECK;
        } else {
            double chenScore = PokerUtils.getChenScore(cards);
            double chenScoreToPlay = tightness * 0.2;
            if ((chenScore < chenScoreToPlay)) {
                if (allowedActions.contains(Action.CHECK)) {
                    // Always check for free if possible.
                    action = Action.CHECK;
                } else {
                    // Bad hole cards; play tight.
                    action = Action.FOLD;
                }
            } else {
                // Good enough hole cards, play hand.
                if ((chenScore - chenScoreToPlay) >= ((20.0 - chenScoreToPlay) / 2.0)) {
                    // Very good hole cards; bet or raise!
                    if (aggression == 0) {
                        // Never bet.
                        if (allowedActions.contains(Action.CALL)) {
                            action = Action.CALL;
                        } else {
                            action = Action.CHECK;
                        }
                    } else if (aggression == 100) {
                        // Always go all-in!
                        //FIXME: Check and bet/raise player's remaining cash.
                        int amount = (tableType == TableType.FIXED_LIMIT) ? minBet : 100 * minBet;
                        if(amount > currentAmount){
                            if (allowedActions.contains(Action.CHECK)){
                                action = Action.CHECK;
                            }
                            else if (allowedActions.contains(Action.CALL)){
                                action = Action.CALL;
                            }else{
                                action = Action.FOLD;
                            }

                        }else if (allowedActions.contains(Action.BET)) {
                            action = new BetAction(amount);
                        } else if (allowedActions.contains(Action.RAISE)) {
                            action = new RaiseAction(amount);
                        } else if (allowedActions.contains(Action.CALL)) {
                            action = Action.CALL;
                        } else {
                            action = Action.CHECK;
                        }
                    } else {
                        int amount = minBet;
                        if (tableType == TableType.NO_LIMIT) {
                            int betLevel = aggression / 20;
                            for (int i = 0; i < betLevel; i++) {
                                amount *= 2;
                            }
                        }
                        if (currentBet < amount) {



                            if(amount > currentAmount){
                                if (allowedActions.contains(Action.CHECK)){
                                    action = Action.CHECK;
                                }
                                else if (allowedActions.contains(Action.CALL)){
                                    action = Action.CALL;
                                }else{
                                    action = Action.FOLD;
                                }

                            }else if (allowedActions.contains(Action.BET)) {
                                action = new BetAction(amount);
                            } else if (allowedActions.contains(Action.RAISE)) {
                                action = new RaiseAction(amount);
                            } else if (allowedActions.contains(Action.CALL)) {
                                action = Action.CALL;
                            } else {
                                action = Action.CHECK;
                            }
                        } else {
                            if (allowedActions.contains(Action.CALL)) {
                                action = Action.CALL;
                            } else {
                                action = Action.CHECK;
                            }
                        }
                    }
                } else {
                    // Decent hole cards; check or call.
                    if (allowedActions.contains(Action.CHECK)) {
                        action = Action.CHECK;
                    } else {
                        action = Action.CALL;
                    }
                }
            }
        }
        return action;
    }

    @Override
    public String getName() {
        return "Mozzart " + (int)(200*Math.random()) ;
    }

}
