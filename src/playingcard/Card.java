package playingcard;
/**
 * 
 * @author M_Nukari
 *
 */
public class Card {
	
	// カードの柄
	public static final int SUIT_SPADE = 0;
	public static final int SUIT_HEART = 1;
	public static final int SUIT_DIA = 2;
	public static final int SUIT_CLUB = 3;
	private int suit = -1;
	private int number = -1;
	
	/**
	 * コンストラクタ
	 * @param suit
	 * @param number
	 */
	public Card(int suit, int number) {
		this.suit = suit;
		this.number = number;
	}
	
	/**
	 * 柄を取得
	 * @return
	 */
	public int getSuit() {
		return suit;
	}

	/**
	 * 数を取得
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * 柄(マーク)を取得
	 * @return
	 * @throws Exception
	 */
	public String getSuitAsString(){
		switch(suit) {
		case SUIT_SPADE:
			return "♠";
		case SUIT_CLUB:
			return "♣";
		case SUIT_HEART:
			return "♥";
		case SUIT_DIA:
			return "♦";
		default:
			throw new RuntimeException("使用できない数値です。");
		}
	}
	
	/**
	 * 数を文字列化して取得
	 * @return
	 */
	public String getNumberAsString() {
		if(2 <= number && number <= 10) {
			return String.format("%-2s", number).replace(" ", "_");
		}else{
			switch(number) {
			case 1:
				return "A_";
			case 11:
				return "J_";
			case 12:
				return "Q_";
			case 13:
				return "K_";
			}
		}
		
		throw new RuntimeException("使用できない数値です");
	}
	
	/**
	 * カードを伏せた状態の文字列を取得
	 * @return
	 */
	public static String maskedString() {
	  String str = "";
	  str += "*";
	  str += "__";
	  return str;
	}
	
	/**
	 * カード情報を文字列で表示します。
	 */
	@Override
	public String toString() {
		String str = "";
		str += getSuitAsString();
		str += getNumberAsString();
		return str;
	}
	
	/**
	 * 比較する。
	 * @param c
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
	  if(o instanceof Card == false) {
	    return false;
	  }
	  Card c = (Card)o;
	  return (getSuit() == c.getSuit() && getNumber() == c.getNumber());
	}
}
