/**
 *
 */
package com.hsjawanda.utilities.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harshdeep S Jawanda <hsjawanda@gmail.com>
 *
 */
class TimingData {

	List<DataLine> lines = new ArrayList<>();

	public void add(String text, String elapsed) {
		this.lines.add(new DataLine(text, elapsed));
	}

	public List<DataLine> lines() {
		return this.lines;
	}

	class DataLine {

		public final String elapsed;

		public final String text;

		public DataLine(String text, String elapsed) {
			this.text = text;
			this.elapsed = elapsed;
		}

	}

	public void clear() {
		this.lines.clear();
	}

}
