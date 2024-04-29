package app.poker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import app.Game;
import lib.Console;
import lib.Input;
import playingcard.Card;
import playingcard.Deck;
import playingcard.Player;

/**
 * ポーカー(ドローポーカー)のゲームクラス
 * @author M_Nukari
 *
 */
public class DrawPokerGame extends Game{
	
  // 山札
	private Deck deck = new Deck();
	
	// 参加者
	private Player[] players = {
	    new HumanPlayer("P1"),
	    new WeekAIPlayer("P2(AI)"),
	    new WeekAIPlayer("P3(AI)")
	};
	
	/**
	 * コンストラクタ
	 */
	public DrawPokerGame(){
	}
	
	/**
	 * ゲーム開始時に1度行う処理
	 */
	public int start() {
	  
	  // 山札
	  this.deck = new Deck();

	  // 参加者
	  this.players = new Player[]{
	      new HumanPlayer("P1"),
	      new WeekAIPlayer("P2(AI)"),
	      new WeekAIPlayer("P3(AI)")
	  };
	  
	  return NORMAL;
	}
	
	/**
	 * ゲームの実行部
	 */
	public int run() {
		// 山札をシャッフル
		this.deck.shuffle();
		
		//　人間が勝利した回数
		int victory = 0;
		
		// 指定回数対戦
		for(int i=0; i<5; i++) {
		  //
		  Console.outH1("Round " + (i + 1) + " 開始", Console.DEFAULT_HEADER, true);

		  // すべての Player がカードを山札から引きます。
		  for(int j=0; j<players.length; j++) {
		    players[j].addHand(deck.draw(5)); // 山札から5枚引いて手札に追加
		    this.showHand(players[j]);
		    //
		    if(j < players.length-1) { 
		      Console.outln("----------");
		    }
		  }

		  // 
		  Console.outH1("ドロー");

		  // Playerがカードを入れ替えます。
		  for(int j=0; j<players.length; j++) {
		    Card[] discard = players[j].discard();
		    this.deck.addDiscard(discard); // 手札を山に捨てます。
		    players[j].addHand(deck.draw(discard.length)); // 捨てた分だけ山札から引く
		  }

		  //
		  Console.outH1("ショーダウン");

		  //
		  for(int j=0; j<players.length; j++) {
		    this.showHand(players[j]);
		    //
		    if(j < players.length-1) {
		      Console.outln("----------");
		    }
		  }

		  //
		  Console.outln("");

		  // 勝負を判定します。
		  Player win = this.judge(players);
		  if(win == null) {
		    Console.outln("ドローゲーム");
		  }else{
		    Console.outln("--- " + win.getName() + " の勝利!! ---");
		    if(win == players[0]) {
		      victory++;
		    }
		  }

		  // 手札を山札に戻し、次の対戦の準備を行います。
		  Console.outln("");
		  Console.outln((i+1) + " 回目終了");
		  
		  //
		  Console.out("");
		  String s_next = Input.getString("次のゲーム: Push Enter, 終了: <Exit>");
		  if(s_next != null && (s_next.equalsIgnoreCase("<exit>") || s_next.equalsIgnoreCase("exit"))) {
		    return CANCEL; //　ゲームを終了する。
		  }
		  Console.outln("");

		  // すべての Player が手札を山に捨てます。
		  for(int j=0; j<players.length; j++) {
		    this.deck.addDiscard(players[j].discardAll()); // 手札を山に捨てます。
		  }
		}
		
		// 結果発表
		Console.outH1("最終結果");
		Console.outln("あなたは " + victory + " 回 AIに勝利しました。");
		if(4 < victory) {
		  Console.outln("さすがのポーカーフェースですね(^^♪");
		}else if(2 < victory) {
		  Console.outln("まずまずの強さですね(´・ω・)");
		}else if(1 < victory) {
		  Console.outln("もうすこし頑張りましょう(-_-)/~~~ピシー!ピシー!");
		}else {
		  Console.outln("ダメダメですね( ˘•ω•˘ )");
		}
		Console.outln("");
		
		return NORMAL;
	}
	
	/**
	 * 役を表示します。
	 * @param p
	 */
	private void showHand(Player p) {
	  // もち札を表示
	  p.showCard();
	  
	  // 役を表示
		Card[] hand = p.getHand();
		Console.out("\t");
		Console.out("[" + DrawPokerHandJudge.getHandName(DrawPokerHandJudge.checkHand(hand)) + "]");
		Console.outln("");
	}
	
	/**
	 * 勝ったプレーヤーを返します。
	 * @param players
	 */
	private Player judge(Player[] players) {
	  // ソートに使う一時的なクラス
	  class Result{
	    int score = 0;
	    Player p = null;
	  }
	  
	  // プレイヤーごとにスコアを計算します。
	  List<Result> list = new ArrayList<Result>();
	  for(int i=0; i<players.length; i++) {
	    Result r = new Result();
	    r.score = DrawPokerHandJudge.getScore(players[i].getHand());
	    r.p = players[i];
	    //
	    list.add(r);
	  }
	  
	  // ソートします。
	  list.sort(new Comparator<Result>(){
      @Override
      public int compare(Result o1, Result o2) {
        // 判定
        if(o1.score > o2.score) {
          return -1;
        }else if(o1.score < o2.score) {
          return 1;
        }
        
        return 0;
      }
	  });
	  
	  // 1番と2番を取り出してスコアを比較します。同点だった場合はこの両者でもう一度カードの強さを比較する必要があります。
	  Result r = list.get(0);
	  if(list.size() >= 2) {
	    Result r2 = list.get(1);
	    if(r.score == r2.score) {
	      r = null;
	    }
	  }
	  
	  //
	  return (r != null ? r.p : null); // 引き分けはNULLを戻します。
	}
	
	/**
	 * テスト用メソッド
	 * @param args
	 */
	public static void main(String[] args) {
	  Card[] cards1 = { 
	      new Card(Card.SUIT_SPADE, 11), 
	      new Card(Card.SUIT_SPADE, 10),
	      new Card(Card.SUIT_DIA, 13),
	      new Card(Card.SUIT_HEART, 12),
	      new Card(Card.SUIT_DIA, 10),
	  };

	  Card[] cards2 = { 
	      new Card(Card.SUIT_DIA, 5), 
	      new Card(Card.SUIT_DIA, 12),
	      new Card(Card.SUIT_SPADE, 12),
	      new Card(Card.SUIT_CLUB, 2),
	      new Card(Card.SUIT_DIA, 7),
	  };
	  
	  Player p1 = new HumanPlayer("P1");
	  Player p2 = new HumanPlayer("P2");
	  
	  p1.addHand(cards1);
	  p2.addHand(cards2);
	  
	  DrawPokerGame game = new DrawPokerGame();
	  Player win = game.judge(new Player[] {p1, p2});
	  System.out.println(win.getName());
	}
}
