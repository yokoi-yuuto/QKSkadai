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

	private StepCount stepCount = new StepCount();

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
			//取得した文字列がnullもしくは空文字ではない場合処理を進める
			if (filePath != null && !filePath.isEmpty()) {
				//textフィールドに文字列がある場合
				System.out.println("正しい文字列を選出しました、textフィールドの文字列は「" + filePath + "」です");
				//ファイルの場合----------------
					stepCount.calculateLineCounts(filePath);
					processFile(file);
			} else {
				//textフィールドに何もない場合
				System.out.println("textフィールドに文字がありません、終了します");

			}
			//空行を出す(10行-numberOfAttempts)
			for (int i = 1; i <= 10 - numberOfAttempts; i++) {
				underPanel.addRow(new Object[] { "" });
			}
			//テキストフィールドの初期化
			headerPanel.clearTextField();
			//Totalの初期化
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
		underPanel.clearTable();
		//空行を出す
		for (int i = 1; i <= 10; i++) {
			underPanel.addRow(new Object[] { "" });
		}

	}

}
