package playingcard;
import java.util.ArrayList;
import java.util.List;

import lib.Console;

/**
 * 遊ぶ人を表す「プレイヤー」クラスです。
 * @author M_Nukari
 *
 */
public abstract class Player {
	// プレイヤー名
	private String name;

	// 手札
	protected List<Card> listHand = new ArrayList<>();

	/**
	 * コンストラクタ
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * プレイヤー名を取得
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 手札を追加
	 * @param cards
	 */
	public void addHand(Card[] cards) {
		for(Card c : cards) {
			this.listHand.add(c);
		}
	}

	/**
	 * 手札を参照
	 * @return
	 */
	public Card[] getHand() {
		return listHand.toArray(new Card[0]);
	}

	/**
	 * 手札を捨てる
	 * abstractがついているのでサブクラス(継承したクラス)でメソッドの処理を実際にコーディングする必要があります。
	 * @return
	 */
	public abstract Card[] discard();

	/**
	 * 手札をすべて破棄
	 */
	public Card[] discardAll() {
		// リストを配列に変換するコードです。
		Card[] discard = this.getHand();

		// 手札からカードを削除します。
		this.listHand.clear();

		return discard;
	}

	/**
	 * カードを表示
	 * @param info
	 * @param card
	 */
	public void showCard() {
	  Console.out(""); // デフォルトのインデントを取得
	  Console.out(String.format("%-10s", name) + ": ", 0);
		for(int i=0; i<listHand.size(); i++) {
			Card card = this.listHand.get(i);
			Console.out("[" + (i+1) + "]" + card.toString(), 0);
			if(i < listHand.size()-1) {
			  Console.out(", ", 0);
			}
		}
	}
}
