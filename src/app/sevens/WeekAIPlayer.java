package app.sevens;

import java.util.ArrayList;
import java.util.List;

import lib.Console;
import playingcard.Card;

/**
 * AIプレイヤー
 */
public class WeekAIPlayer extends SevensPlayer{
  
  private int nextDiscard = -1;

  /**
   * コンストラクタ
   * @param name
   */
  public WeekAIPlayer(String name) {
    super(name);
  }
  
  /**
   * AIがどのカードを切るか決めるため、現在の場の状況をセットする
   * @param table
   */
  public void setReferenceTable(Card[][] table) {
    List<Card> candidates = new ArrayList<>();
    
    for(int i=0; i<table.length; i++) {
      Card[] set = table[i];
      for(int j=0; j<set.length; j++) {
        if(j < 7 && (set[j] == null && set[j+1] != null)) {
          candidates.add(new Card(i, j+1));
        }else if(7 <= j && (set[j] == null && set[j-1] != null)){
          candidates.add(new Card(i, j+1));
        }
      }
    }
    
    // @debug------------------
    //candidates.forEach(e -> System.out.println(e));
    // @debug------------------
    
    //
    this.nextDiscard = -1;
    
    for(int i=0; i<listHand.size(); i++) {
      Card ch = listHand.get(i);
      int index = candidates.indexOf(ch);
      if(index != -1) {
        this.nextDiscard = i;
        break;
      }
    }
  }

  /**
   * カードを場に出す/捨てる。
   */
  @Override
  public Card[] discard() {
    if(nextDiscard >= 0) {
      Card c = this.listHand.remove(nextDiscard);
      return new Card[] {c};
    }
    
    return new Card[] {};
  }

  /**
   * カードを表示
   * @param info
   * @param card
   */
  public void showCard() {
    Console.out(""); // デフォルトのインデントを取得
    Console.out(String.format("%-10s", getName()) + ": ", 0);
    for(int i=0; i<listHand.size(); i++) {
      //Card card = this.listHand.get(i);
      //Console.out("[" + (i+1) + "]" + card.toString(), 0);
      Console.out(Card.maskedString(), 0);
      if(i < listHand.size()-1) {
        Console.out(", ", 0);
      }
    }
  }
}
