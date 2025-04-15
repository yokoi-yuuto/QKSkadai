package 課題;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class XMLReaderFrame extends JFrame{
	private HeaderPanel headerPanel;
	private UnderPanel underPanel;

	/**
	 * コンストラクタ
	 */
	public XMLReaderFrame() {
		setupFrame();
		initComponents();
	}

	/**
	 * フレーム
	 */
	private void setupFrame() {
		// Frameのステータスを設定
		setVisible(true);
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * フレームに追加するコンポーネント
	 */
	private void initComponents() {
		//パネル
		JPanel mainPanel = new JPanel();

		//レイアウト
		mainPanel.setLayout(new BorderLayout());

		UnderPanel underPanel = new UnderPanel();
		underPanel.init();

		//他クラスのパネルを追加
		HeaderPanel headerPanel = new HeaderPanel(underPanel);
		headerPanel.init();

		// mainPanel にパネルを追加
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(underPanel, BorderLayout.SOUTH);

		//フレームに追加
		add(mainPanel);
	}

}
