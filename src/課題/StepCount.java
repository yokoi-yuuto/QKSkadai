package 課題;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author avt
 *
 */
public class StepCount {
	private List<String> lines = new ArrayList<>();
	private String extracted;

	/**
	 * コンストラクタ
	 */
	public StepCount() {
	}

	/**
	 * ファイルパスからファイルを読み込み
	 * @param filePath 
	 */
	public void calculateLineCounts(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			// 行がなくなるまで以下をループする----------
			while ((line = reader.readLine()) != null) {
				extracted = "";
				// 空白をすべて無視する
				line = line.trim();

				extractBetweenMarkers(line, "<void property=", ">");
				extractBetweenMarkers(line, "<object class=", ">");
				extractBetweenMarkers(line, "<string>", "</string>");
				extractBetweenMarkers(line, "<boolean>", "</boolean>");

				// 抽出した文字列が空白でない場合のみリストに追加
				if (!extracted.trim().isEmpty()) {
					setLines(getExtracted());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 行がなくなったら終わり----------------------
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(String line) {
		lines.add(line);
	}

	public String getExtracted() {
		return extracted;
	}

	public void setExtracted(String line) {
		extracted += line;
	}

	/**
	 * resetの初期化
	 */
	public void clearResets() {
		lines.clear();
	}

	/**
	 * 開始と終了の間の文字を抽出するメソッド
	 * @param text 文字列
	 * @param startMarker 開始
	 * @param endMarker 終了
	 */
	public void extractBetweenMarkers(String text, String startMarker, String endMarker) {
		//開始と終了の位置を取得。見つからない場合-1が返ってくる
		int startIndex = text.indexOf(startMarker);
		int endIndex = text.indexOf(endMarker);

		//開始と終了の文字列が存在する場合処理を行う
		if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
			//開始と終了のあいだの文字列を抽出
			String x = text.substring(startIndex + startMarker.length(), endIndex).trim();
			//抽出結果が空白じゃない場合処理
			if (!x.isEmpty()) {
				setExtracted(" " + x);
			}
		}
	}
}