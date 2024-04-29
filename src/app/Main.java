package app;

import app.poker.DrawPokerGame;
import lib.Console;
import lib.Input;

/**
 * メインクラス
 * @author M_Nukari
 *
 */
public class Main {

  // クラスを登録する
  private Game[] games = {
    new DrawPokerGame()
  };
  
  /**
   * 実行
   */
  public void loop() {
    Console.outH1("♦♥ CUI　Game Party ♠♣", "", true);
    
    int typeGame = -1;
    while(true) {
      Console.outH1("▶ 何で遊びますか？", "");
      Console.outln("[1] : ドローポーカー(5カードポーカー)");
      Console.outln("[9] : 終了");
      Console.outln("");
      
      // ゲーム選択
      if((typeGame = Input.getInt("選んでください")) != 9){
        try{
          typeGame -= 1;

          // ゲームオブジェクトを取り出して実行
          Game game = games[typeGame];
          game.execute();

        }catch(Exception e){
          Console.outln("ゲームを始められませんでした。");
        }
      }else{
        // 終了
        break;
      }
    }
    
    Console.outH1("終了します。またね(@^^)/~~~", "", true);
  }
  
  
  
	/**
	 * メインクラス
	 * @param args
	 */
	public static void main(String[] args) {
	  
	  // アプリ実行
	  Main main = new Main();
	  main.loop();
	  
	}
}
