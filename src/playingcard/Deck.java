package playingcard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * カードの山札
 */
public class Deck {
	
	// 山札を格納するリストです。
	private List<Card> listCards = new ArrayList<>();
	
	// 捨て札を格納するリストです。
	private List<Card> listDiscards = new ArrayList<>();

	/**
	 * コンストラクタ
	 */
	public Deck() {
		this.init();
	}
	
	/**
	 * 山札にカードを準備する。
	 */
	public void init() {
		// 4つのマークを配列に格納し、順番に処理します。
		final int[] SUITS = {Card.SUIT_SPADE, Card.SUIT_CLUB, Card.SUIT_HEART, Card.SUIT_DIA};
		for(int i=0; i<SUITS.length; i++) {
			int suit = SUITS[i];
			for(int j=1; j<=13; j++) {
				this.listCards.add(new Card(suit, j));
			}
		}
	}
	
	/**
	 * 捨て札を山札に戻す
	 */
	public void rebuild() {
		this.listCards.addAll(listDiscards);
		this.listDiscards.clear();
		
		//
		this.shuffle();
		//Console.outln("<<山札をシャッフルして戻す>>", 0);
	}
	
	/**
	 * 山札をシャッフルする
	 */
	public void shuffle() {
		// カードをランダムに並び替える。
		Collections.shuffle(listCards);
	}
	
	/**
	 * 指定された枚数カードを引く
	 * @param count
	 * @return
	 */
	public Card[] draw(int count) {
		Card[] box = new Card[count];
		
		if(listCards.size() < count) {
			this.rebuild();
			this.shuffle();
		}
		
		// カードを箱に移す
		for(int i=0; i<count; i++) {
			box[i] = this.listCards.remove(listCards.size()-1);
		}
		
		return box;
	}
	
	/**
	 * カードを山に戻す
	 * @param card
	 */
	public void addDiscard(Card[] card) {
		for(Card c : card) {
			this.listDiscards.add(c);
		}
	}
	
}
