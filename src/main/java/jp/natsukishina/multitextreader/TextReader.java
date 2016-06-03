package jp.natsukishina.multitextreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テキストファイルを読み込むためのユーティリティクラス<br>
 * juniversalchardetライブラリを利用し、<br>
 * 文字コードを意識することなくテキストファイルの読み込みをすることができます。
 * @author 417.72KI
 *
 */
public class TextReader {

	private static final Logger log = LoggerFactory.getLogger(TextReader.class); 
	
	private TextReader() {
	}

	/**
	 * テキストファイルを読み込みます。<br>
	 * 読み込み失敗時はnullを返します。
	 * @param parent - 読み込むファイルの格納先
	 * @param fileName - 読み込むファイルのファイル名
	 * @return 読み込んだテキスト
	 */
	public static String read(File parent, String fileName) {
		File file = new File(parent, fileName);
		return read(file);
	}

	/**
	 * テキストファイルを読み込みます。<br>
	 * 読み込み失敗時はnullを返します。
	 * @param parent - 読み込むファイルの格納先パス
	 * @param fileName - 読み込むファイルのファイル名
	 * @return 読み込んだテキスト
	 */
	public static String read(String parent, String fileName) {
		File file = new File(parent, fileName);
		return read(file);
	}

	/**
	 * テキストファイルを読み込みます。<br>
	 * 読み込み失敗時はnullを返します。
	 * @param filePath - 読み込むファイルのパス
	 * @return 読み込んだテキスト
	 */
	public static String read(String filePath) {
		File file = new File(filePath);
		return read(file);
	}

	/**
	 * テキストファイルを読み込みます。<br>
	 * 読み込み失敗時はnullを返します。<br>
	 * ※最終行がEOFのみの場合、その行はテキストには含みません。
	 * @param file - 読み込むファイル
	 * @return 読み込んだテキスト
	 */
	public static String read(File file) {
		String charCode = FileCharDetecter.detect(file);
		if (charCode == null) {
			charCode = "UTF-8";
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charCode));) {
			StringBuffer sb = new StringBuffer();
			String str = reader.readLine();
			if(str == null) {
				return "";
			}
			sb.append(str);
			while ((str = reader.readLine()) != null) {
				sb.append("\n");
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			log.error("read failed", e);
			return null;
		}
	}

}
