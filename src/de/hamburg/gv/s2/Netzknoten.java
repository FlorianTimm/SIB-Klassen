package de.hamburg.gv.s2;
import java.util.regex.Pattern;

public class Netzknoten implements Comparable<Netzknoten> {
	private String nk = null;
	private int kartenblatt, lfdNr;
	private String zusatz = "";

	public Netzknoten() {
	}

	public Netzknoten(String nk) {
		setNK(nk);
	}

	public Netzknoten(int kartenblatt, int lfdNr, char zusatz) {
		this.setKartenblatt(kartenblatt);
		this.setLfdNr(lfdNr);
		this.setZusatz(zusatz);
	}

	public Netzknoten(String kartenblatt, String lfdNr, String zusatz) {
		this(toInt(kartenblatt), toInt(lfdNr), zusatz.charAt(0));
	}

	public static int toInt(String zahlAlsText) {
		try {
			int zahl = Integer.parseInt(zahlAlsText);
			return zahl;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public boolean setNK(String nk) {
		if (pruefeNK(nk.trim())) {
			this.nk = nk;
			return true;
		}
		return false;
	}

	public boolean setKartenblatt(int kartenblatt) {
		if (kartenblatt >= 1000 && kartenblatt <= 9999) {
			this.kartenblatt = kartenblatt;
			this.nk = null;
			return true;
		}
		return false;
	}

	public boolean setLfdNr(int lfdNr) {
		if (lfdNr >= 1 && lfdNr <= 99999) {
			this.lfdNr = lfdNr;
			this.nk = null;
			return true;
		}
		return false;
	}

	public void setZusatz(char zusatz) {
		this.zusatz = String.valueOf(zusatz);;
		this.nk = null;
	}

	public boolean pruefeNK(String nk) {
		return Pattern.matches("^\\d{9}([A-Z]?|\\s?)$", nk);
	}

	public String toString() {
		if (nk == null) {
			int vnkInt = this.kartenblatt * 100000 + this.lfdNr;
			if (zusatz.compareTo("O") == 0)
				zusatz = "";				
			nk = new StringBuilder().append(vnkInt).append(this.zusatz).toString();
		}
		return nk;
	}
	
	public int compareTo(Netzknoten nk) {
		return this.toString().compareTo(nk.toString());
	}
	
	public Netzknoten clone() {
		return new Netzknoten (this.toString());
	}
	
	public boolean equals(Netzknoten nk) {
		return this.toString().equals(nk.toString());
	}
}
