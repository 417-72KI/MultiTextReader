package jp.natsukishina.multitextreader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextReaderTest {

	private enum FileName {
		EUCJP("eucjptest.txt"), JIS("jistest.txt"), SJIS("sjistest.txt"), UTF8("utf8test.txt");
		public final String name;

		private FileName(String fileName) {
			this.name = fileName;
		}
	}

	private static File parent;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parent = new File("src/test/resources");
	}

	@Test
	public void test_euc_jp() {
		String actual = "EUC-JP\nてすと\nほげ\nhoge";
		String expect = TextReader.read(parent, FileName.EUCJP.name);
		assertThat(expect, is(actual));
	}

	@Test
	public void test_jis() {
		String actual = "JIS\nてすと\nほげ\nhoge";
		String expect = TextReader.read(parent, FileName.JIS.name);
		assertThat(expect, is(actual));
	}

	@Test
	public void test_s_jis() {
		String actual = "Shift-JIS\nてすと\nほげ\nhoge";
		String expect = TextReader.read(parent, FileName.SJIS.name);
		assertThat(expect, is(actual));
	}

	@Test
	public void test_utf8() {
		String actual = "UTF-8\nてすと\n\nほげ\nhoge";
		String expect = TextReader.read(parent, FileName.UTF8.name);
		assertThat(expect, is(actual));
	}

	@Test()
	public void test_not_exists() {
		assertThat(TextReader.read(parent, "hoge.txt"), nullValue());
	}
}
