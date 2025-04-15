package 課題;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

/**
 * @author avt
 *
 */
public class HeaderPanel extends JPanel {
	/**テキストフィールドの文字を保持*/
	private JTextField textField;
	private JButton actionBtn;
	private JButton clearBtn;
	/**
	 * 
	 */
	private UnderPanel underPanel;

	/**
	 * 画面上部パネル
	 */
	public HeaderPanel(UnderPanel underPanel) {
		this.underPanel = underPanel;
	}

	/**
	 * Textフィールド、実行ボタン、クリアボタン
	 */
	public void init() {
		//Textフィールド
		textField = new JTextField("");
		textField.setPreferredSize(new Dimension(350, 30));

		// ドラッグ＆ドロップ機能を設定
		textField.setTransferHandler(new TransferHandler() {
			@Override
			public boolean canImport(TransferHandler.TransferSupport support) {
				// ファイルかディレクトリを判断
				return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
			}

			@Override
			public boolean importData(TransferHandler.TransferSupport support) {
				if (!canImport(support)) {
					return false;
				}

				try {
					// ドロップされたファイルリストを取得
					Transferable transferable = support.getTransferable();
					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

					// 最初のファイルのパスをテキストフィールドに設定
					if (files.size() > 0) {
						textField.setText(files.get(0).getAbsolutePath());
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		});

		//実行ボタン
		JButton actionBtn = new JButton("実行");
		//クリアボタン
		JButton clearBtn = new JButton("クリア");

		//パネルに追加
		add(textField);
		add(actionBtn);
		add(clearBtn);

		ActionButtonListener listener = new ActionButtonListener(this, underPanel);
		actionBtn.addActionListener(listener);
		clearBtn.addActionListener(listener);

	}

	/**
	 * テキストテーブルのテキストを受け取る
	 * @return　テキストフィールドのテキスト
	 */
	public String getTextFromField() {

		return textField.getText();

	}

	/**
	 * テキストフィールドの文字列を削除
	 */
	public void clearTextField() {
		textField.setText("");
	}

}
