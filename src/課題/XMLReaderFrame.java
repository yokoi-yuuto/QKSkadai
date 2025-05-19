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
		setVisible(true);
	}

	/**
	 * フレーム
	 */
	private void setupFrame() {
		// Frameのステータスを設定
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * フレームに追加するコンポーネント
	 */
	private void initComponents() {
		//パネル
		JPanel mainPanel = new JPanel(new BorderLayout());

		//レイアウト
		mainPanel.setLayout(new BorderLayout());

		this.underPanel = new UnderPanel();
		this.underPanel.init();

		//他クラスのパネルを追加
		this.headerPanel = new HeaderPanel(this.underPanel);
		this.headerPanel.init();

		// mainPanel にパネルを追加
		mainPanel.add(this.headerPanel, BorderLayout.NORTH);
		mainPanel.add(this.underPanel, BorderLayout.CENTER);

		//フレームに追加
		add(mainPanel);
	}

}
