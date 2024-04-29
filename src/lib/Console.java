package lib;

import java.awt.Color;

/**
 * コンソール In/Out
 * @author M_Nukari
 */
public class Console {
  
  public static final int DEFAULT_INDENT = 4;
  public static String DEFAULT_HEADER = "■";
  
  /**
   * ヘッダーを出力
   * @param message
   */
  public static void outH1(String message) {
    Console.outH1(message, DEFAULT_HEADER);
  }
  
  /**
   * ヘッダーを出力
   * @param message
   * @param mark
   */
  public static void outH1(String message, String mark) {
    Console.outH1(message, mark, false);
  }
  
  /**
   * ヘッダーを出力
   * @param message
   * @param mark
   * @param color
   */
  public static void outH1(String message, String mark, boolean color) {
    String _mark = mark + (mark.length() > 0 ? " " : "");
    String text = (_mark + " " + message);
    System.out.println();
    System.out.println(color ? withBackColor(text, Color.BLUE) : text);
    System.out.println();
  }
  
  /**
   * 通常出力
   * @param message
   */
  public static void out(String message) {
    Console.out(message, DEFAULT_INDENT);
  }
  
  /**
   * 通常出力
   * @param message
   * @param indent
   */
  public static void out(String message, int indent) {
    System.out.print(getSpace(indent) + message);
  }
  
  /**
   * 通常出力
   * @param message
   */
  public static void outln(String message) {
    Console.out(message + "\n", DEFAULT_INDENT);
  }
  
  /**
   * 通常出力
   * @param message
   * @param indent
   */
  public static void outln(String message, int indent) {
    Console.out(message + "\n", indent);
  }
  
  /**
   * インデントを取得
   * @param size
   * @return
   */
  private static String getSpace(int size) {
    String space = "";
    for(int i=0; i<size; i++) {
      space += " ";
    }
    return space;
  }
  
  /**
   * 背景色を変更
   * @param message
   * @param color
   * @return
   */
  private static String withBackColor(String message, Color color) {
    //System.out.println("\u001b[00;3"+i+"m esc[00;3"+i+" \u001b[00m"); // 文字色
    //System.out.println("\u001b[00;4"+i+"m esc[00;4"+i+" \u001b[00m");  // 背景色
    
    // TODO　今のところ color は効いてません。
    return "\u001b[00;45m" + message + "\u001b[00m";
  }
}
