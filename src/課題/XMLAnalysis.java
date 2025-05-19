package 課題;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML解析クラス
 * @author avt
 */
public class XMLAnalysis {
	// 抽出結果を格納するlist
	private List<String> lines = new ArrayList<>();
	// インデントを保持するフィールド
	private int indentLevel = 0;

	/**
	 * コンストラクタ
	 */
	public XMLAnalysis() {
	}

	/**
	 * XMLファイルを解析して情報を抽出するメソッド。
	 * 
	 * @param filePath ファイルパス
	 */
	public void calculateLineCounts(String filePath) {
		try {
			//XMLパーサー
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// XMLファイルを読み込み、DOMオブジェクト（ツリー構造）を生成
			Document document = builder.parse(new File(filePath));

			// ルートタグを取得
			Element root = document.getDocumentElement();
			setLines("ルートタグ: " + root.getNodeName());

			// ルートのすべての子ノードを取得する
			NodeList nodeList = root.getChildNodes();
			
			// 子ノードlistを処理する
			// ルートの下からインデント開始
			indentLevel++;
			processNodes(nodeList);
			// 終了後に戻す
			 indentLevel--;

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 抽出した行listを取得する。
	 * 
	 * @return 抽出された内容を格納したlist
	 */
	public List<String> getLines() {
		return lines;
	}

	/**
	 * 抽出したデータをlistに追加するメソッド。
	 * 
	 * @param line 追加する文字列
	 */
	public void setLines(String line) {
		String indent = new String(new char[indentLevel]).replace("\0", "    "); 
		lines.add(indent + line); // listに新しいデータを追加
	}

	/**
	 * resetの初期化
	 */
	public void clearResets() {
		lines.clear();
	}

	/**
	 * ノードlistを取得しノードを順番に処理していくメソッド。
	 * ノードに子ノードが存在する場合、再帰的に処理
	 * 
	 * @param nodeList ノードlist
	 */
	public void processNodes(NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			//ノードlistからノードを順番に取得
			Node node = nodeList.item(i);

			//ノードの種類で分岐
			switch (node.getNodeType()) {

			// ノードがタグの場合の処理
			case Node.ELEMENT_NODE:
				// タグ名をlistに追加
				setLines("タグ名: " + node.getNodeName());
				// 属性が存在する場合処理
				if (node.hasAttributes()) {
					//ノードから属性listを取得
					NamedNodeMap attributes = node.getAttributes();
					for (int j = 0; j < attributes.getLength(); j++) {
						//ノードから属性を順番に取得し、listに追加する
						Node attr = attributes.item(j);
						setLines(" 属性: " + attr.getNodeName() + " = " + attr.getNodeValue());
					}
				}
				// ノードに子ノードが存在する場合、再帰的に処理を行う
				if (node.hasChildNodes()) {
					indentLevel++;
					processNodes(node.getChildNodes());
					 indentLevel--;
				}
				break;
				
			// textノードの場合の処理
			case Node.TEXT_NODE:
				// タグの中身を取得して出力
				String content = node.getTextContent().trim();
				//抽出結果が空白じゃない場合処理
				if (!content.isEmpty()) {
					//内容をlistに追加
					setLines(" 内容: " + content);
				}
				break;
				
			default:
				//コメントなどは無視
				break;
			}
		}
	}
}