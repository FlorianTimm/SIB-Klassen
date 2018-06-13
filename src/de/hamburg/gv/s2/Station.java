package de.hamburg.gv.s2;

public class Station implements Cloneable, Comparable<Station> {
	private Abschnitt abs;
	private int vst = -1;
	private int bst = -1;
	private boolean gedreht = false;

	/**
	 * Erzeugt eine Station
	 * 
	 * @param abs
	 *            Abschnitt
	 * @param vst
	 *            von Station
	 * @param bst
	 *            bis Station
	 */
	public Station(Abschnitt abs, int vst, int bst) {
		this.abs = abs;
		setVST(vst);
		setBST(bst);
	}

	public Station(Abschnitt abs) {
		this.abs = abs;
		this.vst = 0;
		this.bst = pruefeST(abs.getLEN());
	}

	public static Station fromString(String vnk, String nnk, String vst, String bst) {
		if (vnk.equals("") && nnk.equals("")) {
			return null;
		}
		return Station.fromString(new Abschnitt(vnk, nnk), vst, bst);
	}

	public static Station fromString(Abschnitt abschnitt, String vst, String bst) {
		Station station = new Station(abschnitt);
		int newVst = Integer.parseInt(vst);
		station.setVST(newVst);

		int newBst = Integer.parseInt(bst);
		station.setBST(newBst);
		return station;
	}

	public void setVST(int vst) {
		this.vst = pruefeST(vst);
		if (vst > bst) {
			setBST(vst);
		}
	}

	public void setBST(int bst) {
		bst = pruefeST(bst);
		if (bst < this.vst) {
			this.bst = this.vst;
		} else {
			this.bst = bst;
		}
	}

	public void setDrehung(boolean gedreht) {
		this.gedreht = gedreht;
	}

	public Abschnitt getABS() {
		return abs;
	}

	public int pruefeST(int st) {
		if (st < 0) {
			return 0;
		} else if (abs.getLEN() == -1 || st <= abs.getLEN()) {
			return st;
		} else {
			return abs.getLEN();
		}
	}

	public int getVST() {
		return pruefeST(vst);
	}

	public int getBST() {
		return pruefeST(bst);
	}

	public void setABS(Abschnitt abs) {
		this.abs = abs;
	}

	public boolean getDrehung() {
		return gedreht;
	}

	public String toString() {
		return this.toString(" ");
	}

	public String toString(String delimiter) {
		return this.abs.toString(delimiter) + delimiter + this.getVST() + delimiter + this.getBST();
	}

	public Station clone() {
		return new Station(getABS().clone(), getVST(), getBST());
	}

	@Override
	public int compareTo(Station other) {
		// TODO Auto-generated method stub
		int r = 0;
		if ((r = this.getABS().compareTo(other.getABS())) != 0) {
			return r;
		} else if ((r = Integer.compare(this.getVST(), other.getVST())) != 0) {
			return r;
		} else if ((r = Integer.compare(this.getBST(), other.getBST())) != 0) {
			return r;
		}
		return r;
	}

}
