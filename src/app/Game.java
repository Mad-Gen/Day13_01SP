package app;

/**
 * 「ゲーム」の抽象クラス
 */
public abstract class Game {
  
  public static final int NORMAL = 0;
  public static final int CANCEL = 1;

  /**
   * ゲームを実行
   */
  public final void execute() {
    // ゲーム開始時にまとめて行う処理。ロードとか。
    this.start();
    
    if(run() == NORMAL) {
      // 通常の終わり方をした時だけ終了処理を行う。セーブとか。
      this.end();
    }
  }
  
  /**
   * ゲーム開始時に一度実行する処理
   */
  public int start() {
    return NORMAL;
  }
  
  /**
   * 実行部
   */
  public abstract int run();
  
  /**
   * ゲーム終了時に一度実行する処理
   */
  public int end() {
    return NORMAL;
  }
}
