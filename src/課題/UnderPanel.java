package 課題;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * @author avt
 *
 */
public class UnderPanel extends JPanel {
	// 表に表示するデータを定義(最初は空) 
	Object[][] data = new Object[10][1];
	// 列の名前を定義
	String[] columns = { "中身" };

	DefaultTableModel model = new DefaultTableModel(data, columns) {
	    @Override
	    //入力不可にする。
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	};
	
	private List<Object[]> rowList = new ArrayList<>();



	/**
	 * 画面下部パネル
	 */
	public UnderPanel() {
	}

	/**
	 * テーブル
	 */
	public void init() {
		/**
		 * 	テーブル
		 */
		JTable resultTable = new JTable(model);

		//スクロールバー
		JScrollPane sp = new JScrollPane(resultTable);

		// 一列目の列幅を設定
		TableColumn column = resultTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(300);

		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);

	}

	/**
	 * 	テーブルに行を追加するメソッド
	 * @param rowData
	 */
	@SuppressWarnings("javadoc")
	public void addRow(Object[] rowData) {
		model.addRow(rowData);
	}
	
	/**
	 * テーブルをクリアするメソッド
	 */
	public void clearTable() {
		model.setRowCount(0);
	}
}
