package app;
import java.util.Arrays;
import java.util.Comparator;

import playingcard.Card;

/**
 * ポーカーの役を判定するための便利クラス（ユーティリティクラスとも言います）。
 * 
 * ※このクラスには基本的にstaticメソッドや定数しか定義されていません。このようなクラスをユーティリティクラスといいます。
 * ※たとえばjava.lang.Mathもユーティリティクラスです。
 * @author M_Nukari
 *
 */
public class DrawPokerHandJudge {
	// 役それぞれに定数を割り当てる
	public static final int ID_STRAIGHT_FLUSH = 10000;
	public static final int ID_FOUR_CARD = 9000;
	public static final int ID_FULLHOUSE = 8000;
	public static final int ID_FLUSH = 7000;
	public static final int ID_STRAIGHT = 6000;
	public static final int ID_THREE_CARD = 5000;
	public static final int ID_TWO_PAIR = 4000;
	public static final int ID_ONE_PAIR = 3000;
	public static final int ID_HIGH_CARD = 2000;
	
	/**
	 * 役の強さをスコアとして取得
	 * @param cards
	 * @return
	 */
	public static int getScore(Card[] cards) {
	  int[] press = pressCardArray(cards, true);
	  
	  int hand = checkHand(cards);
	  int number = 0;
	  
	  if(hand == ID_STRAIGHT_FLUSH) {
	    number = getLargestNumberScore(cards);
    }else if(hand == ID_FOUR_CARD) {
      for(int i=0; i<press.length; i+=2) {
        if(press[i+1] == 4) {
          number = getCardScore(press[i]);
        }
      }
    }else if(hand == ID_FULLHOUSE) {
      number = (getCardScore(press[0]) + getCardScore(press[2])); 
    }else if(hand == ID_FLUSH) {
      number = getLargestNumberScore(cards);
    }else if(hand == ID_STRAIGHT) {
      number = getLargestNumberScore(cards);
    }else if(hand == ID_THREE_CARD) {
      for(int i=0; i<press.length; i+=2) {
        if(press[i+1] == 3) {
          number = getCardScore(press[i]);
        }
      }
    }else if(hand == ID_TWO_PAIR) {
      int _score = 0;
      for(int i=0; i<press.length; i+=2) {
        if(press[i+1] == 2) {
          int val = getCardScore(press[i]);
          if(val > _score) {
            _score = val;
          }
        }
      }
      number = _score;
    }else if(hand == ID_ONE_PAIR) {
      for(int i=0; i<press.length; i+=2) {
        if(press[i+1] == 2) {
          number = getCardScore(press[i]);
        }
      }
    }else if(hand == ID_HIGH_CARD) {
      number = getLargestNumberScore(cards);
    }
	  
	  return (hand + (number * 10));
	}
	
	/**
   * カードの強さを得点として取得
   * @param number
   * @return
   */
  public static int getCardScore(int number) {
    // Aceは別格。
    if(number == 1) {
      return 14; // King(13)よりも強くするため14点とする。
    }

    // それ以外は数字をそのまま点数として扱う。
    return number;
  }
	
	/**
	 * 最も強いカードの強さを点数として取得
	 * @param cards
	 * @return
	 */
	public static int getLargestNumberScore(Card[] cards) {
		Card[] temp = Arrays.copyOf(cards, cards.length);
		
		// カードを数字の順に並び変える
		Arrays.sort(temp, new Comparator<Card>(){
			@Override
			public int compare(Card o1, Card o2) {
			  int score1 = getCardScore(o1.getNumber());
			  int score2 = getCardScore(o2.getNumber());
				return (score1 - score2);
			}
		});
		
		return getCardScore(temp[temp.length-1].getNumber());
	}
	
	/**
	 * ポーカーの役をチェック
	 * @param cards
	 * @return
	 */
	public static int checkHand(Card[] cards) {
		Card[] temp = Arrays.copyOf(cards, cards.length);
		
		if(temp.length != 5) {
			throw new IllegalArgumentException("カードの枚数が不正です。");
		}
		
		// カードを数字の順に並び変える
		Arrays.sort(temp, new Comparator<Card>(){
			@Override
			public int compare(Card o1, Card o2) {
				return (o1.getNumber() - o2.getNumber());
			}
		});
		
		//
		if(isStraightFlush(temp)) {
			return ID_STRAIGHT_FLUSH;
		}else if(isFourCard(temp)) {
			return ID_FOUR_CARD;
		}else if(isFullHouse(temp)) {
			return ID_FULLHOUSE;
		}else if(isFlush(temp)) {
			return ID_FLUSH;
		}else if(isStraight(temp)) {
			return ID_STRAIGHT;
		}else if(isThreeCard(temp)) {
			return ID_THREE_CARD;
		}else if(isTwoPair(temp)) {
			return ID_TWO_PAIR;
		}else if(isOnePair(temp)) {
			return ID_ONE_PAIR;
		}
		
		return ID_HIGH_CARD;
	}
	
	/**
	 * 役の名前を取得
	 * @param id
	 * @return
	 */
	public static String getHandName(int id) {
		switch(id) {
		case ID_STRAIGHT_FLUSH:
			return "ストレートフラッシュ";
		case ID_FOUR_CARD:
			return "フォーカード";
		case ID_FULLHOUSE:
			return "フルハウス";
		case ID_FLUSH:
			return "フラッシュ";
		case ID_STRAIGHT:
			return "ストレート";
		case ID_THREE_CARD:
			return "スリーカード";
		case ID_TWO_PAIR:
			return "ツーペア";
		case ID_ONE_PAIR:
			return "ワンペア";
		default:
			return "ハイカード";
		}
	}

	/**
	 * ワンペア
	 * @param cards
	 * @return
	 */
	private static boolean isOnePair(Card[] cards) {
		int pair = 0;
		int[] press = DrawPokerHandJudge.pressCardArray(cards, false);
		for(int i=0; i<press.length; i+=2) {
			int cnt = press[i+1];
			if(cnt == 2) {
				pair++;
			}
		}

		return (pair == 1);
	}

	/**
	 * ツーペア
	 * @param cards
	 * @return
	 */
	private static boolean isTwoPair(Card[] cards) {
		int pair = 0;
		int[] press = DrawPokerHandJudge.pressCardArray(cards, false);
		for(int i=0; i<press.length; i+=2) {
			int cnt = press[i+1];
			if(cnt == 2) {
				pair++;
			}
		}

		return (pair == 2);
	}

	/**
	 * スリーカード(同じ数のカードが3枚ある)
	 * @param cards
	 * @return
	 */
	private static boolean isThreeCard(Card[] cards) {
		boolean isThree = false;
		boolean isTwo = false;

		int[] press = DrawPokerHandJudge.pressCardArray(cards, false);
		for(int i=0; i<press.length; i+=2) {
			int cnt = press[i+1];
			if(cnt == 3) {
				isThree = true;
			}else if(cnt == 2) {
				isTwo = true;
			}
		}

		// スリーカードはある。ただしペアがあってはいけない。
		return (isThree && !isTwo);
	}

	/**
	 * フラッシュ(すべて同じ柄)
	 * @return
	 */
	private static boolean isFlush(Card[] cards) {
		int suit = cards[0].getSuit();
		for(int i=0; i<cards.length; i++) {
			if(suit != cards[i].getSuit()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ストレート(階段状の数字)
	 * @param cards
	 * @return
	 */
	private static boolean isStraight(Card[] cards) {
		int number = cards[0].getNumber();

		// Jackから始まりAceで終わるケース
		if(number == 1 && cards[4].getNumber() == 13) {
			for(int i=1; i<cards.length-1; i++) {
				if((number+(i+8)) != cards[i].getNumber()) {
					return false;
				}
			}
			return true;
		}

		// 
		for(int i=0; i<cards.length; i++) {
			if((number+i) != cards[i].getNumber()) {
				return false;
			}
		}

		return true;
	}
	

	/**
	 * フルハウス(1ペアとと3カードの組み合わせ)
	 * @param cards
	 * @return
	 */
	private static boolean isFullHouse(Card[] cards) {
		int[] press = DrawPokerHandJudge.pressCardArray(cards, false);
		return (press[1] == 2 && press[3] == 3) || (press[1] == 3 && press[3] == 2); 
	}

	/**
	 * フォーカード(4カード)
	 * @param cards
	 * @return
	 */
	private static boolean isFourCard(Card[] cards) {
		int[] press = DrawPokerHandJudge.pressCardArray(cards, false);
		return (press[1] == 4 || press[3] == 4);
	}

	/**
	 * ストレートフラッシュ
	 * @param cards
	 * @return
	 */
	private static boolean isStraightFlush(Card[] cards) {
		return isFlush(cards) && isStraight(cards);
	}
	
	/**
	 * カードの数ごとに何が何枚あったかをカウントします。
	 * ※このメソッドはオーバーロードされています。
	 * @param cards
	 * @return
	 */
	public static int[] pressCardArray(Card[] cards) {
		return pressCardArray(cards, false);
	}

	/**
	 * カードの数ごとに何が何枚あったかをカウントします。
	 * ※このメソッドはオーバーロードされています。
	 * @param temp
	 * @param sort
	 * @return
	 */
	public static int[] pressCardArray(Card[] cards, boolean sort) {
		// ※この処理は「ランレングス圧縮」と呼ばれる最も基本的な圧縮アルゴリズムとほぼ同じです。
		// 気になる方はGoogleなどで検索してみると面白いと思います。
		
		// 元のデータ順を変えないように、新しく配列を作ります。
		Card[] temp = Arrays.copyOf(cards, cards.length);
		if(sort) {
			// カードを数字の順に並び変える
			Arrays.sort(temp, new Comparator<Card>(){
				@Override
				public int compare(Card o1, Card o2) {
					return (o1.getNumber() - o2.getNumber());
				}
			});
		}

		int[] press = new int[10]; // 奇数番号はカードの数、偶数番号はその枚数です。
		int count = 1, preNumber = -1, j = 0;

		for(int i=0; i<temp.length; i++) {
			int number = temp[i].getNumber();
			if(0 < i) {
				if((number != preNumber)) {
					// 前回までの値を記録
					press[j] = preNumber;
					press[j+1] = count;

					// 次の処理のために初期化
					count = 1;
					j+=2;
				}else{
					count++;
				}
			}
			preNumber = number;
		}

		press[j] = preNumber;
		press[j+1] = count;

		return press;
	}
	
	/**
	 * テスト用のエントリーポイント
	 * @param args
	 */
	public static void main(String[] args) {	  
	  Card[] cards = { 
        new Card(Card.SUIT_DIA, 11), 
        new Card(Card.SUIT_DIA, 9),
        new Card(Card.SUIT_DIA, 2),
        new Card(Card.SUIT_HEART, 7),
        new Card(Card.SUIT_DIA, 7),
    };
		
		// pressCardArray()がどんな働きをしているか確認してみましょう!!
		int[] press = pressCardArray(cards, true);
		for(int val : press) {
			System.out.print(val + ", ");
		}
		System.out.println();

		// 役を正しく判定できるか調べます。
		System.out.println("役  ? " + getHandName(checkHand(cards)) );
		System.out.println("点数? " + getScore(cards));
	}
}
