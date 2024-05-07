package app.sevens;

import java.util.ArrayList;
import java.util.List;

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
  
  private SevensPlayer[] players = {
      new HumanPlayer("You"),
      new WeekAIPlayer("Nobita"),
      new WeekAIPlayer("Doraemon"),
      new WeekAIPlayer("Gian"),
      new WeekAIPlayer("Suneo"),
      new WeekAIPlayer("Shizuka")
  };
  
  private List<SevensPlayer> winner = new ArrayList<>();
  
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
    int div = deck.getSize() / players.length;
    for(SevensPlayer p : players) {
      p.addHand(deck.draw(div));
    }
    if(deck.getSize() > 0) {
      // 余りが出たら最後の人に追加。
      players[players.length-1].addHand(deck.draw(deck.getSize()));
    }
    
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
    
    // まだ配置されていないスペースがある限り続ける。
    while(getEmptySize(table) > 0) {
      
      Console.outH1("場に出すカードを選んでください。");
      for(SevensPlayer p : players) {
        if(p.getHand().length > 0) {
          
          // 手札の選択
          while(true) {
            this.showHand(p);

            // AIが現在のテーブルを参照してどのカードを切るか決められるようにする。
            if(p instanceof WeekAIPlayer) {
              ((WeekAIPlayer)p).setReferenceTable(table);
            }

            // 場に出すカードを選択
            Card[] discard = p.discard();
            if(discard.length <= 0){
              Console.outln("パスしました。");
              break;
            }else{
              if(setToTable(discard) == false){
                Console.outln("そのカードは出せません。");
                p.addHand(discard); // カードを切るのに失敗したら手札に戻す。
              }else{
                break;
              }
            }
          }

          // テーブルを表示
          Console.outH1("現在のテーブル");
          this.showTable(table);
          Console.outln("");
        }
        
        // もち札が無くなった順番にプレイヤーを記録します。
        if(p.getHand().length <= 0 && winner.contains(p) == false) {
          this.winner.add(p);
        }
      }
      
      //
      Console.outln("");
      String s_next = Input.getString("続ける: Push Enter, 終了: <Exit>");
      if(s_next != null && (s_next.equalsIgnoreCase("<exit>") || s_next.equalsIgnoreCase("exit"))) {
        return CANCEL; //　ゲームを終了する。
      }
      Console.outln("");
    }
    
    //
    Console.outH1("勝敗");
    Console.outln("勝者は " + winner.get(0).getName() + " でした!");
    
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
   * テーブルの空きスペース
   * @param table
   * @return
   */
  private int getEmptySize(Card[][] table) {
    int count = 0;
    for(int i=0; i<table.length; i++) {
      Card[] set = table[i];
      for(int j=0; j<set.length; j++) {
        if(set[j] == null) {
          count++;
        }
      }
    }
    
    return count;
  }
  
  /**
   * 手札を表示
   * @param p
   */
  private void showHand(SevensPlayer p) {
    p.showCard();
    Console.outln("");
    Console.outln("");
  }
}
