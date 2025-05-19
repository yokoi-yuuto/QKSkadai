package 課題;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * 警告メッセージを表示するユーティリティクラス
 */
public class WarningDialog {

    /**
     * 指定されたメッセージを警告ダイアログで表示する
     * 
     * @param parent 親コンポーネント
     * @param message 表示する警告メッセージ
     */
    public static void show(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "警告", JOptionPane.WARNING_MESSAGE);
    }
}