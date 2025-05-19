package 課題;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author avt
 *
 */
public class ActionButtonListener implements ActionListener {
	/**
	 * 
	 */
	private HeaderPanel headerPanel;
	/**
	 * 
	 */
	private UnderPanel underPanel;

	/**
	 * 何回行を排出したかをカウントする
	 */
	private int numberOfAttempts;

	private XMLAnalysis stepCount = new XMLAnalysis();

	/**
	 * @param HeaderPanel 
	 * 
	 */
	@SuppressWarnings("javadoc")
	public ActionButtonListener(HeaderPanel headerPanel, UnderPanel underPanel) {
		//headerPanelとunderPanelを受け取る
		this.headerPanel = headerPanel;
		this.underPanel = underPanel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 押されたボタンのテキストを取得
		String command = e.getActionCommand();

		switch (command) {
		case "実行":
			action();
			break;

		case "クリア":
			clear();
			break;
		}
	}

	/**
	 * 実行ボタンを押した際の処理
	 */
	private void action() {
		try {
			//試行回数をリセット
			this.numberOfAttempts = 0;
			//表を削除
			underPanel.clearTable();
			//HeaderPanelクラスのtextフィールドからテキストを取得(取得用のクラスを利用)
			String filePath = headerPanel.getTextFromField();
			//ファイルの便利なやつ
			File file = new File(filePath);

			//取得した文字列が空の時は警告ダイアログを表示
			if (filePath == null || filePath.trim().isEmpty()) {
				WarningDialog.show(headerPanel, "ファイルを指定してください。");
				return;
			}

			//取得した文字列がフォルダの時は警告ダイアログを表示
			if (file.isDirectory()) {
				WarningDialog.show(headerPanel, "フォルダではなく、ファイルを指定してください。");
				return;
			}

			//取得した文字列がXMLかstr以外のの時は警告ダイアログを表示
			String name = file.getName().toLowerCase();
			if (!(name.endsWith(".XML") || name.endsWith(".stl"))) {
				WarningDialog.show(headerPanel, "対応していないファイル形式です（.xml または .stl のみ対応）");
				return;
			}

			//textフィールドに文字列がある場合
			System.out.println("正しい文字列を選出しました、textフィールドの文字列は「" + filePath + "」です");
			//ファイルの場合----------------
			stepCount.calculateLineCounts(filePath);
			processFile(file);

			//空行を出す(10行-numberOfAttempts)
			for (int i = 1; i <= 10 - numberOfAttempts; i++) {
				underPanel.addRow(new Object[] { "" });
			}
			//textフィールドの初期化
			headerPanel.clearTextField();
		} catch (Exception e) {
			// 例外の詳細を出力 
			e.printStackTrace();
		}

	}

	/**
	 * ファイル処理メソッド
	 * @param file ファイルデータ
	 */
	protected void processFile(File file) {
		try {
			// テーブルに結果を追加
			for (String line : stepCount.getLines()) {
				underPanel.addRow(new Object[] { line });
				numberOfAttempts++;
			}

		} catch (Exception e) {
			System.err.println("ファイルの処理中にエラーが発生しました: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	/**
	 * 表をすべて削除するメソッド
	 */
	private void clear() {
		// ★ 確認ダイアログを表示し、ユーザーの選択を取得
		int result = javax.swing.JOptionPane.showConfirmDialog(
				headerPanel,
				"表の内容をクリアしてもよろしいですか？",
				"確認",
				javax.swing.JOptionPane.YES_NO_OPTION,
				javax.swing.JOptionPane.WARNING_MESSAGE);

		//YESを押した場合のみ実行
		if (result == javax.swing.JOptionPane.YES_OPTION) {
			underPanel.clearTable();
			//空行を出す
			for (int i = 1; i <= 10; i++) {
				underPanel.addRow(new Object[] { "" });
			}
		}
	}

}
