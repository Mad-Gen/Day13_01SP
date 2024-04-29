package app;
import java.util.Arrays;

import lib.Input;
import playingcard.Card;
import playingcard.Player;

/**
 * 人が操作するプレイヤークラス
 * @author M_Nukari
 *
 */
public class HumanPlayer extends Player{
	
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
	  message += "交換する手札の番号を指定してください。";
	  message += "\n\tスペースかカンマ区切りで複数指定できます。";
	  message += "\n\t交換しない場合はそのまま Enter を押します。";
		
		while(true){
			// コンソールからの入力文字列を取得
			String strSwap = Input.getString(message);
			
			// String型をint型に変換して、入れ替える手札の番号を取得。
			try {
				// スペース、またはカンマ区切りで分割します。
				// このコードはまず最初に「カンマ(前後に半角スペースも可)」を「半角スペース」に置き換え、次に「半角スペース(連続可)」で分割しています。
				String[] strsSwap = null;
				if(strSwap == null || strSwap.length() <= 0) {
				  strsSwap = new String[0]; // 長さ0の配列を生成
				}else {
				  strsSwap = strSwap.replaceAll("\\s*,\\s*", " ").split("\\s+");
				}
				
				// 文字列を数値型に変換します。
				int[] idxSwap = new int[strsSwap.length];
				for(int i=0; i<strsSwap.length; i++) {
					String val = strsSwap[i].trim(); // 文字列の前後に空白がある場合は必要な部分だけ切り取る。
					idxSwap[i] = Integer.parseInt(val);
				}
				
				// 入力された値を小さい順に並び変える（ArraysはCollectionsの類似機能を提供します。処理対象は配列です）。
				Arrays.sort(idxSwap);
				
				// 交換対象カードを配列化します。
				Card[] discards = new Card[idxSwap.length];
				for(int i=idxSwap.length-1; i>=0; i--) {
					int swap = idxSwap[i] - 1;
					discards[i] = this.listHand.remove(swap); // 対象カードは手札から削除します。 
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
}
