package app.sevens;
import lib.Input;
import playingcard.Card;

/**
 * 人が操作するプレイヤークラス
 * @author M_Nukari
 *
 */
public class HumanPlayer extends SevensPlayer{
	
	/**
	 * コンストラクタ
	 * @param name
	 */
	public HumanPlayer(String name) {
		super(name);
	}

	/**
	 * 手札を捨てる
	 * @return
	 */
	public Card[] discard() {
	  String message = "";
	  message += "場に出す手札の番号を指定してください。";
	  message += "\n\tパスの場合はそのまま Enter を押します。";
		
		while(true){
			// コンソールからの入力文字列を取得
			String strInput = Input.getString(message);
			
			// String型をint型に変換して、入れ替える手札の番号を取得。
			try {
				String[] strDiscards = null;
				if(strInput == null || strInput.length() <= 0) {
				  strDiscards = new String[0]; // 長さ0の配列を生成
				}else {
				  strDiscards = new String[1];
				  strDiscards[0] = strInput.replaceAll("\\s*,\\s*", " ").trim();
				}
				
				// 交換対象カードを配列化します。
				Card[] discards = new Card[strDiscards.length];
				for(int i=discards.length-1; i>=0; i--) {
					int swap = Integer.parseInt(strDiscards[i]);
					discards[i] = this.listHand.remove(swap-1); // 対象カードは手札から削除します。 
				}
				
				return discards;
			}catch(NumberFormatException e) {
				message = "もう一度入力してください（数字は半角スペースかカンマで区切ります）"; 
			}catch(IndexOutOfBoundsException e) {
				message = "もう一度入力してください（数字は半角スペースかカンマで区切ります）";
			}catch(NullPointerException e) {
				message = "もう一度入力してください（数字は半角スペースかカンマで区切ります）";
			}
		}
	}
	
	/**
   * カードを表示
   * @param info
   * @param card
   */
  public void showCard() {
    super.showCard();
  }
}
