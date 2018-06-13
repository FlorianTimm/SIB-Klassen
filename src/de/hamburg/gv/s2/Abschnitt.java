package de.hamburg.gv.s2;

public class Abschnitt implements Comparable<Abschnitt> {
	private Netzknoten vnk, nnk;
	private int len = -1;

	public Abschnitt(String vnk, String nnk, int len) {
		this(new Netzknoten(vnk), new Netzknoten(nnk), len);
	}

	public Abschnitt(Netzknoten vnk, Netzknoten nnk, int len) {
		setVNK(vnk);
		setNNK(nnk);
		setLEN(len);
	}

	public Abschnitt(String vnk, String nnk) {
		this(new Netzknoten(vnk), new Netzknoten(nnk));
	}

	public Abschnitt(Netzknoten vnk, Netzknoten nnk) {
		this(vnk, nnk, -1);
	}

	public Abschnitt(Abschnitt copy) {
		this(copy.getVNK(), copy.getNNK(), copy.getLEN());
	}

	public Abschnitt() {

	}

	public boolean setVNK(String vnk) {
		Netzknoten vnkK = new Netzknoten();
		if (vnkK.setNK(vnk)) {
			this.setVNK(vnkK);
			return true;
		}
		return false;
	}

	public boolean setVNK(Netzknoten vnk) {
		this.vnk = vnk;
		return true;
	}

	public boolean setNNK(String nnk) {
		Netzknoten nnkK = new Netzknoten();
		if (nnkK.setNK(nnk)) {
			this.setNNK(nnkK);
			return true;
		}
		return false;
	}

	public boolean setNNK(Netzknoten nnk) {
		this.nnk = nnk;
		return true;
	}

	public boolean setLEN(int len) {
		if (len >= 0) {
			this.len = len;
			return true;
		} else {
			return false;
		}
	}

	public Netzknoten getVNK() {
		return vnk;
	}

	public Netzknoten getNNK() {
		return nnk;
	}

	public int getLEN() {
		return len;
	}

	public String toString() {
		return this.toString(" ");
	}

	public String toString(String delimiter) {
		return getVNK() + delimiter + getNNK();
	}

	@Override
	public int compareTo(Abschnitt other) {
		// TODO Auto-generated method stub
		int r = 0;
		if ((this.getVNK() == null || this.getNNK() == null) && (other.getVNK() == null || other.getNNK() == null)) {
			return 0;
		} else if (this.getVNK() == null || this.getNNK() == null) {
			return -1;
		} else if (other.getVNK() == null || other.getNNK() == null) {
			return 1;
		} else if ((r = this.getVNK().compareTo(other.getVNK())) != 0) {
			return r;
		} else if ((r = this.getNNK().compareTo(other.getNNK())) != 0) {
			return r;
		} else if ((r = Integer.compare(this.getLEN(), other.getLEN())) != 0) {
			return r;
		}
		return 0;
	}

	public Abschnitt clone() {
		return new Abschnitt(getVNK().clone(), getNNK().clone(), getLEN());
	}

	public void drehen() {
		Netzknoten vnk = getVNK();
		setVNK(getNNK());
		setNNK(vnk);
	}
}
