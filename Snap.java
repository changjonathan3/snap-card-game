/** CS 0445 Fall 17
 * @author Jonathan Chang
 * Assignment 1
 * Khattab
 */

import java.util.*;
public class Snap{
    public static void main (String[] args){
        System.out.println("Welcome to the Game of Snap!\n" + "Now dealing the cards to the players...\n");
        /** Main Deck for all players */
        MultiDS<Card> deck = new MultiDS<Card>(52);
        for (Card.Suits s : Card.Suits.values()) {
            for (Card.Ranks r : Card.Ranks.values()) { //for each unique suit and rank combo, assign to card
                Card c = new Card(s,r);
                deck.addItem(c);
            }
        }
        deck.shuffle();
        /** Create player face down piles, empty face piles, empty snap pile */
        MultiDS<Card> fd1=new MultiDS<Card>(26); //create face down deck piles for each player
        MultiDS<Card> fd2=new MultiDS<Card>(26);
        for(int i=0;i<26;i++){
            fd1.addItem(deck.removeItem());
            fd2.addItem(deck.removeItem());
        }
        fd1.reverse(); fd2.reverse();
        System.out.println("\nHere is Player 1's face-down pile:\n"+fd1);
        System.out.println("\nHere is Player 2's face-down pile:\n"+fd2);
        System.out.println("\nStarting the game!\n");
        MultiDS<Card> fu1=new MultiDS<Card>(26);
        MultiDS<Card> fu2=new MultiDS<Card>(26);
        MultiDS<Card> sp=new MultiDS<Card>(26);

        int rounds=Integer.parseInt(args[0]); //input in cmd prmpt
        boolean one=false,two=false; //check if players ran out of cards
        int count=0; //count rounds
        for(int i=1;i<rounds+1;i++){
            System.out.println("Rnd #"+i+":");
            Card x = fd1.removeItem();
            Card y = fd2.removeItem();
            int result=1;
            if(!fd1.empty()&&!fd2.empty()){
                fu1.addItem(x);
                fu2.addItem(y);
                result = x.compareTo(y);
            }
            int result2=1,result3=1;
            Card z = sp.removeItem();
            if(!fd1.empty()&&!fd2.empty()&&!sp.empty()){
                sp.addItem(z);
                result2= x.compareTo(z);
                result3= y.compareTo(z);
            }
            /** If Match Occurs */
            if (result == 0||result2==0||result3==0){ //checking for matches, probabilities of diff outcomes
                double rnd=Math.random();
                if(rnd<0.4){
                    System.out.println("P1 correct snap");
                    if(result==0)
                        System.out.println(x+" matches "+y+" of P2 ("+fu2.size()+" cards)");
                    if(result2==0)
                        System.out.println(x+" matches "+z+" of snap pile ("+sp.size()+" cards)");
                    while(!fu2.empty())
                        fd1.addItem(fu2.removeItem());
                    while(!sp.empty())
                        fd1.addItem(sp.removeItem());
                    fd1.shuffle();
                }
                else if(rnd<0.8){
                    System.out.println("P2 correct snap");
                    if(result==0)
                        System.out.println(y+" matches "+x+" of P1 ("+fu1.size()+" cards)");
                    if(result3==0)
                        System.out.println(y+" matches "+z+" of snap pile ("+sp.size()+" cards)");
                    while(!fu1.empty())
                        fd2.addItem(fu1.removeItem());
                    while(!sp.empty())
                        fd2.addItem(sp.removeItem());
                    fd2.shuffle();
                }
                else {
                    System.out.println("No snap called");
                }
            }
            /** If No Match, possible incorrect shouting cases*/
            else{                                      //no match outcomes with probabilities
                System.out.println("No match");
                double rnd2=Math.random();
                if(rnd2<0.01){
                    System.out.println("P1 incorrect snap");
                    fu1.shuffle();
                    System.out.println(fu1.size()+" cards moved to snap pile");
                    while(!fu1.empty())
                        sp.addItem(fu1.removeItem());
                }
                else if(rnd2<0.02){
                    System.out.println("P2 incorrect snap");
                    fu2.shuffle();
                    System.out.println(fu2.size()+" cards moved to snap pile");
                    while(!fu2.empty())
                        sp.addItem(fu2.removeItem());
                }
            }
            /** If player can refill empty face down pile */
            if(fd1.empty()&&!fu1.empty()){ //refill player 1 if possible
                while(!fu1.empty())
                    fd1.addItem(fu1.removeItem());
                fd1.shuffle();
                System.out.println("P1 refills empty face-down pile");
            }
            else if(fd2.empty()&&!fu2.empty()){ //refill player 2 if possible
                while(!fu2.empty())
                    fd2.addItem(fu2.removeItem());
                fd2.shuffle();
                System.out.println("P2 refills empty face-down pile");
            }
            /** If player runs out of all cards */
            if(fd1.empty()&&fu1.empty()){  //player 1 is out of cards
                System.out.println("P1 out of cards, P2 wins");
                one=true;
                break;
            }
            else if(fd2.empty()&&fu2.empty()){  //player 2 is out of cards
                System.out.println("P2 out of cards, P1 wins");
                two=true;
                break;
            }
            count=i;
        }
        /** If all rounds go through with no one running out of cards */
        if(!one&&!two){ //if no one ran out of cards
            System.out.println("After "+count+" rounds:");
            int oneCards=fd1.size()+fu1.size();
            int twoCards=fd2.size()+fu2.size();
            if(oneCards>twoCards){
                System.out.println("P1 wins with "+oneCards+" cards vs. "+twoCards+" cards");
            }
            else if (oneCards<twoCards){
                System.out.println("P2 wins with "+twoCards+" cards vs. "+oneCards+" cards");
            }
            else if (oneCards==twoCards){
                System.out.println("Tie game of each P having "+oneCards+" cards");
            }
        }
    }
}