package stepCounter;

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
				if (file.isFile()) {
					processFile(file);
					//ディレクトリの場合--------
				} else if (file.isDirectory()) {
					processDirectory(file);
				} else {
					//無効な文字列の場合
					System.out.println("無効な文字列です、終了します");
				}
			} else {
				//textフィールドに何もない場合
				System.out.println("textフィールドに文字がありません、終了します");
			}
			//表に複数のデータがあった場合、合計行を出す
			if (numberOfAttempts > 1) {
		        underPanel.addRow(new Object[] {
		                "合計", "",
		                stepCount.getTotalExecution(),
		                stepCount.getTotalBlank(),
		                stepCount.getTotalComment(),
		                stepCount.getTotalLines()
		            });
				numberOfAttempts++;
			}
			//空行を出す(10行-numberOfAttempts)
			for (int i = 1; i <= 10 - numberOfAttempts; i++) {
				underPanel.addRow(new Object[] { "", "", "", "", "", });
			}
			//テキストフィールドの初期化
			headerPanel.clearTextField();
			//Totalの初期化
			stepCount.resetTotals();
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
			//絶対パス
			String absolutePath = file.getAbsolutePath();
			//相対Path
			String path = file.getPath();

			// FileInfoとStepCountを使用してファイル情報を取得
			FileInfo fileInfo = new FileInfo(absolutePath);
			stepCount.calculateLineCounts(absolutePath);

			// デバッグ用
			System.out.println("ファイル名: " + fileInfo.getFileName());
			System.out.println("拡張子: " + fileInfo.getExtension());
			System.out.println("合計行: " + stepCount.getAllLines());
			System.out.println("コメント行: " + stepCount.getCommentLines());
			System.out.println("空行: " + stepCount.getBlankLines());
			System.out.println("実行行: " + stepCount.getActiveLines());

			// テーブルに結果を追加
			underPanel.addRow(new Object[] {
					path,
					fileInfo.getExtension(),
					stepCount.getActiveLines(),
					stepCount.getBlankLines(),
					stepCount.getCommentLines(),
					stepCount.getAllLines()
			});
			numberOfAttempts++;

		} catch (Exception e) {
			System.err.println("ファイルの処理中にエラーが発生しました: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	/**
	 * ・ディレクトリ処理メソッド
	 * ディレクトリ内にあるファイルと子ディレクトリを探す
	 * ファイルの場合processFileへ渡し、
	 * 子ディレクトリの場合processDirectoryへ渡す。
	 * 何もない場合、終了する。
	 * @param directory ディレクトリ
	 */
	protected void processDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					// ファイルの場合
					processFile(file);
				} else if (file.isDirectory()) {
					// 子ディレクトリの場合
					processDirectory(file);
				}
			}
		} else {
			System.out.println("ディレクトリが空です");
		}

	}

	/**
	 * 表をすべて削除するメソッド
	 */
	private void clear() {
		underPanel.clearTable();
		//空行を出す
		for (int i = 1; i <= 10; i++) {
			underPanel.addRow(new Object[] { "", "", "", "", "", });
		}

	}

}
