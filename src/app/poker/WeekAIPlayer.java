package app.poker;

import playingcard.Card;
import playingcard.Player;

/**
 * AIプレイヤー
 * 自動的に交換する手札を選択します。
 * @author M_Nukari
 *
 */
public class WeekAIPlayer extends Player {

	/**
	 * コンストラクタ
	 * @param name
	 */
	public WeekAIPlayer(String name) {
		super(name);
	}

	/**
	 * 手札を交換する
	 * @return
	 */
	public Card[] discard() {
		Card[] discards = new Card[0];
		Card[] handCards = this.getHand();
		
		// まず自分の役を確認
		int hand = DrawPokerHandJudge.checkHand(getHand());
		if(hand == DrawPokerHandJudge.ID_THREE_CARD) {
			// スリーカードの場合は残りの2枚を交換する。フルハウスを狙う。
			
			// 3カードを構成している数を調査
			int number = this.getNumberOfCardSpecifiedCount(3);
			
			// 3カード以外のカードを交換
			if(number != -1) {
				discards = new Card[2];
				for(int i=0, j=0; i<handCards.length; i++) {
					Card c = handCards[i];
					if(c.getNumber() != number) {
						discards[j++] = c;
					}
				}
			}
		}else if(hand == DrawPokerHandJudge.ID_TWO_PAIR) {
			//　ツーペアの場合は残りの1枚を交換する。フルハウスを狙う。
			
			// ペアになっていないカードを調査
			int number = this.getNumberOfCardSpecifiedCount(1);
			
			// ペアになっていないカードを交換
			if(number != -1) {
				discards = new Card[1];
				for(int i=0; i<handCards.length; i++) {
					Card c = handCards[i];
					if(c.getNumber() == number) {
						discards[0] = c;
					}
				}
			}
		}else if(hand == DrawPokerHandJudge.ID_ONE_PAIR) {
			// ワンペアの場合は残りの3枚を交換する。ワンペア以上を狙う。
			
			// ペア以外のカードをすべて交換する
			int number = this.getNumberOfCardSpecifiedCount(2); 
			
			// ペアになっていないカードを交換
			if(number != -1) {
				discards = new Card[3];
				for(int i=0, j=0; i<handCards.length; i++) {
					Card c = handCards[i];
					if(c.getNumber() != number) {
						discards[j++] = c;
					}
				}
			}
		}else if(hand == DrawPokerHandJudge.ID_HIGH_CARD) {
			// この時の戦略が一番難しいのです。
			// きちんと調べればストレート狙いがよいとわかる場合もあるかもしれませんが、検索が難しいため基本的にフラッシュ狙いにします。
			// ポーカーは5枚の手札で競いますので、必ず2枚以上の同じマークのカードが含まれているはずです。
			// 残りの3枚を捨てれば少なくともワンペア、運が良ければフラシュを狙えます。
			
			// 柄ごとに分類してカードの枚数を数えます。
			int[] mapSuit = new int[4];
			for(int i=0; i<handCards.length; i++) {
				Card c = handCards[i];
				mapSuit[c.getSuit()] = mapSuit[c.getSuit()] + 1;
			}
			
			// 一番多い柄以外のカードを交換します。
			int suit = this.getIndexOfMax(mapSuit);
			int count = mapSuit[suit];
			// 
			discards = new Card[5-count];
			for(int i=0, j=0; i<handCards.length; i++) {
				Card c = handCards[i];
				if(c.getSuit() != suit) {
					discards[j++] = c;
				}
			}
		}
		
		// 手札のリストから削除
		for(Card c : discards) {
			this.listHand.remove(c);
		}
		
		return discards;
	}
	
	/**
	 * 手札の中から指定された枚数存在するカードの数を取得します。
	 * ※例えば3が指定された場合3カードを構成しているカードの数を返します。
	 * @param count
	 * @return
	 */
	private int getNumberOfCardSpecifiedCount(int count) {
		int number = -1;
		int[] press = DrawPokerHandJudge.pressCardArray(getHand());
		for(int i=0; i<press.length; i+=2) {
			if(press[i+1] == count) {
				number = press[i];
			}
		}
		return number;
	}
	
	/**
	 * 配列の中から最大値を持つIndexを求めます。
	 * @param array
	 * @return
	 */
	private int getIndexOfMax(int[] array) {
		int index = -1;
		int max = Integer.MIN_VALUE; // Int型の最小値で変数を初期化します。
		
		for(int i=0; i<array.length; i++) {
			if(max < array[i]) {
				index = i;
				max = array[i];
			}
		}
		
		return index;
	}
}
