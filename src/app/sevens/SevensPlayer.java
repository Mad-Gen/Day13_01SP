package app.sevens;

import java.util.ArrayList;
import java.util.List;

import playingcard.Card;
import playingcard.Player;

/**
 * 7並べをするプレイヤークラス
 */
public abstract class SevensPlayer extends Player{

  /**
   * コンストラクタ
   * @param name
   */
  public SevensPlayer(String name) {
    super(name);
  }

  /**
   * 7のカードを取り出す
   * @return
   */
  public Card[] getNumber7Cards() {
    List<Card> list = new ArrayList<>();
    for(int i=listHand.size()-1; i>=0; i--) {
      Card c = this.listHand.get(i);
      if(c.getNumber() == 7) {
        this.listHand.remove(i);
        list.add(c);
      }
    }
    return (list.toArray(new Card[0]));
  }
}
