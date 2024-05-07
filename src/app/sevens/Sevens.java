package app.sevens;

import app.Game;
import lib.Console;
import lib.Input;
import playingcard.Card;
import playingcard.Deck;

/**
 * 7並べ
 * @author M_Nukari
 */
public class Sevens extends Game{

  private Deck deck = new Deck();
  private Card[][] table = new Card[4][13];
  
  private HumanPlayer[] players = {
      new HumanPlayer("suneo")
  };
  
  /**
   * スタート時に一度だけ実行される
   */
  public int start() {
    // シャッフル
    this.deck.shuffle();
   
    return NORMAL;
  }
  
  /**
   * 実行部
   */
  @Override
  public int run() {
    
    Console.outH1("テーブルをセッティングしています...");
    
    // カードを引く
    players[0].addHand(deck.draw(deck.getSize()));
    
    // テーブルのセッティング
    for(int i=0; i<players.length; i++) {
      Card[] hand = players[i].getNumber7Cards();
      for(int j=0; j<hand.length; j++) {
        Card c = hand[j];
        if(c.getNumber() == 7) {
          table[c.getSuit()][6] = c; 
        }
      }
    }
    // テーブルを表示
    this.showTable(table);
    
    //
    while(true) {
      Console.outH1("場に出すカードを選んでください。");

      // 手札の選択
      while(true) {
        this.showHand(players[0]);

        // 場に出すカードを選択
        Console.outln("");
        //
        Card[] discard = players[0].discard();
        if(discard.length <= 0){
          Console.outln("パスしました。");
          break;
        }else{
          if(setToTable(discard) == false){
            Console.outln("そのカードは出せません。");
            players[0].addHand(discard); // カードを切るのに失敗したら手札に戻す。
          }else{
            break;
          }
        }
      }

      // テーブルを表示
      Console.outH1("現在のテーブル");
      this.showTable(table);
      
      //
      Console.outln("");
      String s_next = Input.getString("続ける: Push Enter, 終了: <Exit>");
      if(s_next != null && (s_next.equalsIgnoreCase("<exit>") || s_next.equalsIgnoreCase("exit"))) {
        //return CANCEL; //　ゲームを終了する。
        break;
      }
      Console.outln("");
    }
    
    return NORMAL;
  }
  
  /**
   * 場にカードをセット
   * @param cards
   */
  private boolean setToTable(Card[] cards) {
    Card c = cards[0];
    
    int shift = 0;
    if(c.getNumber() < 7) {
      shift = 1;
    }else if(7 < c.getNumber()){
      shift = -1;
    }
    
    Card bc = table[c.getSuit()][(c.getNumber()-1)+shift];
    if(bc != null) {
      table[c.getSuit()][(c.getNumber()-1)] = c;
      return true;
    }
    
    return false;
  }

  /**
   * テーブルの表示
   * @param cards
   */
  private void showTable(Card[][] cards) {
    for(int i=0; i<cards.length; i++) {
      Card[] set = cards[i];
      
      Console.out("");
      for(int j=0; j<set.length; j++) {
        Card card = set[j];
        if(card == null){
          Console.out((Card.maskedString() + " "), 0);
        }else{
          Console.out((card.toString() + " "), 0);
        }
      }
      
      Console.outln("", 0);
    }
  }
  
  /**
   * 手札を表示
   * @param p
   */
  private void showHand(HumanPlayer p) {
    p.showCard();
    Console.outln("");
  }
}
