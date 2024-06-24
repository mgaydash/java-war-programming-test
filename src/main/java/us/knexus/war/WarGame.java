/*
 *----------------------------------------------------------------
 * @author: Justin
 * Project Name: Programming Test
 *----------------------------------------------------------------
 * Description:(Class overview)
 *  A programming test designed to use a Deck API to build a functioning
 * version of the card game War.
 *  
 *----------------------------------------------------------------
 * Copyright Notice: Knexus Research Corporation, ${date?date?string("yyyy")}
 * All rights Reserved

 *----------------------------------------------------------------
 * Disclaimer:
 * THIS SOFTWARE IS PROVIDED BY KNEXUS RESEARCH CORP., "AS IS" 
 * AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, WARRANTIES OF INFRINGEMENT AND THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL KNEXUS RESEARCH CORP.
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE DISTRIBUTION OR USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package us.knexus.war;

import us.knexus.deck.Card;
import us.knexus.deck.CardFactory;
import us.knexus.deck.Deck;

import java.util.Stack;

/**
 * A simple version of the card game WarGame
 
 * @author Justin
 */
public class WarGame {

    /**
     * This function when called will play a single round of Game of War.
     *
     * @param deck1 Deck of cards representing player 1
     * @param deck2 Deck of cards representing player 2
     */
    public static void playRound(Deck deck1, Deck deck2) {
        Card card1 = safeDraw(deck1);
        Card card2 = safeDraw(deck2);

        if (card1.getOrdinal() > card2.getOrdinal()) {
            // Player 1 wins
            deck1.addToDiscard(card1);
            deck1.addToDiscard(card2);
        } else if (card1.getOrdinal() < card2.getOrdinal()) {
            // Player 2 wins
            deck2.addToDiscard(card1);
            deck2.addToDiscard(card2);
        } else {
            // Players go to war
            Deck winner = goToWar(deck1, deck2);
            winner.addToDiscard(card1);
            winner.addToDiscard(card2);
        }
    }


    private static Deck goToWar(Deck deck1, Deck deck2) {
        // TODO: Unhandled case if decks each have a single card of same value
        // TODO: Unhandled case if empty deck goes to war

        Stack<Card> stack1 = new Stack<>();
        Stack<Card> stack2 = new Stack<>();

        do {
            if (deck1.getNumCards() != 0) {
                stack1.push(safeDraw(deck1));
            }
            if (deck1.getNumCards() != 0) {
                stack1.push(safeDraw(deck1));
            }

            if (deck2.getNumCards() != 0) {
                stack2.push(safeDraw(deck2));
            }
            if (deck2.getNumCards() != 0) {
                stack2.push(safeDraw(deck2));
            }
        } while (stack1.peek().getOrdinal() == stack2.peek().getOrdinal());

        if (stack1.peek().getOrdinal() > stack2.peek().getOrdinal()) {
            // Player 1 wins war
            for (Card card : stack1) {
                deck1.addToDiscard(card);
            }
            for (Card card : stack2) {
                deck1.addToDiscard(card);
            }

            return deck1;
        } else {
            // Player 2 wins war
            for (Card card : stack1) {
                deck2.addToDiscard(card);
            }
            for (Card card : stack2) {
                deck2.addToDiscard(card);
            }

            return deck2;
        }
    }

    private static Card safeDraw(Deck deck) {
        if (deck.getDrawPileSize() == 0) {
            deck.shuffle();
        }

        return deck.draw();
    }
    
    /**
     * This function when called will play an entire game of War.
     * playRound will get called until either play is completely out of cards.
     * 
     * @param player1 Deck of cards representing player 1
     * @param player2 Deck of cards representing player 2
     */
    public static void playGame(Deck player1, Deck player2) {
        while( player1.getNumCards() > 0 && player2.getNumCards() > 0 )
            playRound(player1, player2);
        
        System.out.println("Player1: " + player1.getNumCards());
        System.out.println("Player2: " + player2.getNumCards());
        if( player1.getNumCards() > 0 )
            System.out.println("Player 1 Wins!");
        else if( player2.getNumCards() > 0 )
            System.out.println("Player 2 Wins!");
    }
    
    public static void main(String[] args) {
        // Get a new CardFactory instance
        CardFactory factory = new CardFactory();
        
        // Get a full deck, set as player 1
        Deck player1 = factory.createFullDeck();        
        // Make a new empty deck for player 2
        Deck player2 = new Deck();
        // Shuffle the player 1 deck
        player1.shuffle();

        // deal half of player 1's shuffled deck to player 2
        int deckSize = player1.getDrawPileSize() / 2;
        for(int i=0; i<deckSize; ++i)
            player2.addToDiscard(player1.draw());

        // shuffle both decks
        player1.shuffle();
        player2.shuffle();

        // play a single round of War
        playRound(player1, player2);
        
        // play an entire game of War
//        playGame(player1, player2);
    }
}
