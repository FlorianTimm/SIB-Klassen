package de.hamburg.gv.s2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangeSetDB {
	private List<ChangeSet> changes;
	ChangeSetDBListener listener = null;

	public ChangeSetDB() {
		changes = new ArrayList<ChangeSet>();
	}

	public ChangeSetDB(ChangeSetDBListener listener) {
		this();
		setListener(listener);
	}

	public void dataChanged() {
		if (listener != null) {
			listener.changeSetsChanged();
		}
	}

	public void addSimple(ChangeSet neu) {
		changes.add(neu);
		dataChanged();
	}

	public void add(ChangeSet[] neue) {
		List<ChangeSet> neu = new ArrayList<ChangeSet>();
		for (int i = 0; i < neue.length; i++) {
			if (neue[i].getAlt() == null) {
				changes.add(neue[i]);
			} else {
				neu.add(neue[i]);
			}
		}

		Abschnitt[] abs = new Abschnitt[neu.size()];
		boolean[] verarbeitet = new boolean[neu.size()];
		ChangeSet[] cs_template = new ChangeSet[neu.size()];

		for (int i = 0; i < neu.size(); i++) {
			abs[i] = neu.get(i).getAlt().getABS();
			verarbeitet[i] = false;
			cs_template[i] = null;
		}

		int anzahl = changes.size();
		for (int i = 0; i < anzahl; ++i) {
			ChangeSet alt = changes.get(i);
			ChangeSet[] cs = cs_template.clone();

			for (int j = 0; j < neu.size(); j++) {
				if (alt.getNeu() != null && alt.getNeu().getABS().compareTo(abs[j]) == 0) {
					// System.out.println("gleich:");
					// System.out.println(alt.toTabString());
					// System.out.println(neu1.toTabString());
					cs[j] = zusammenfassen(alt, neu.get(j));

					verarbeitet[j] = true;
				}
			}

			boolean firstChanged = false;

			for (int j = 0; j < neu.size(); j++) {
				if (cs[j] != null) {
					if (firstChanged) {
						changes.add(cs[j]);
					} else {
						firstChanged = true;
						changes.set(i, cs[j]);
					}
				}
			}

		}
		for (int j = 0; j < neu.size(); j++) {
			if (!verarbeitet[j]) {
				changes.add(neu.get(j));
			}
		}
		dataChanged();
	}

	private ChangeSet zusammenfassen(ChangeSet alt, ChangeSet neu) {
		ChangeSet cs = new ChangeSet();

		Station csAlt = null;
		if (alt.getAlt() != null) {
			csAlt = alt.getAlt().clone();
		}
		Station csNeu = null;
		if (neu.getNeu() != null) {
			csNeu = neu.getNeu().clone();
		}
		if (alt.isGedreht() ^ neu.isGedreht()) {
			cs.setGedreht(true);
		}
		int vst1 = alt.getNeu().getVST();
		int bst1 = alt.getNeu().getBST();
		// int len1 = bst1 - vst1;

		int vst2 = neu.getAlt().getVST();
		int bst2 = neu.getAlt().getBST();

		int vstZ = max(vst1, vst2);
		int bstZ = min(bst1, bst2);
		int lenZ = bstZ - vstZ;

		if (lenZ <= 0) {
			return null;
		}

		if (csAlt != null) {
			int vstA = vstZ - alt.getNeu().getVST() + alt.getAlt().getVST();
			int bstA = vstA + lenZ;
			csAlt.setVST(vstA);
			csAlt.setBST(bstA);
		}

		if (csNeu != null) {
			int vstE = vstZ - neu.getAlt().getVST() + neu.getNeu().getVST();
			int bstE = vstE + lenZ;
			csNeu.setVST(vstE);
			csNeu.setBST(bstE);
		}

		cs.setAlt(csAlt);
		cs.setNeu(csNeu);

		if (alt.getAlt() == null) {
			System.out.println("gibts");
			System.out.println(cs.toString());
		}

		return cs;
	}

	private int min(int m, int n) {
		if (m > n)
			return n;
		return m;
	}

	private int max(int m, int n) {
		if (m > n)
			return m;
		return n;
	}

	public List<ChangeSet> getAll() {
		return changes;
	}

	public void sort() {
		Collections.sort(changes);
		dataChanged();
	}

	public void clearData() {
		changes.clear();
	}

	public int size() {
		return changes.size();
	}

	public ChangeSet get(int reihe) {
		return changes.get(reihe);
	}

	public void set(int row, ChangeSet cs) {
		changes.set(row, cs);
		dataChanged();
	}

	public void remove(int row) {
		changes.remove(row);
		dataChanged();
	}

	public void saveToFile(File exportFile) {
		String text = "";
		for (ChangeSet cs : this.changes) {
			text += cs.toString("\t") + "\n";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(exportFile));
			writer.write(text);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}

	public void importFromFile(File importFile) {

		try {
			BufferedReader in = new BufferedReader(new FileReader(importFile));

			String line = "";
			while ((line = in.readLine()) != null) {
				System.out.println("Buchstaben: " + line.length());
				String[] input = line.split("\t");
				if (input.length < 4) {
					continue;
				}
				ChangeSet cs = new ChangeSet();
				System.out.println("Teile: " + input.length);
				// for (String s : input)
				// System.out.print(s);
				if (input.length >= 4) {
					cs.setAlt(Station.fromString(input[0], input[1], input[2], input[3]));
				}
				if (input.length >= 8) {
					cs.setNeu(Station.fromString(input[4], input[5], input[6], input[7]));
				}
				if (input.length == 9) {
					cs.setGedreht(Boolean.valueOf(input[8]));
				}
				this.addSimple(cs);
				// System.out.println(this.size());
			}
			in.close();
		} catch (IOException e) {
			System.err.println("cat: Fehler beim Verarbeiten");
		}
		dataChanged();
	}

	public void setListener(ChangeSetDBListener listener) {
		this.listener = listener;
	}

}
