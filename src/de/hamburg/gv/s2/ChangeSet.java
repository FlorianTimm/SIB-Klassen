package de.hamburg.gv.s2;

public class ChangeSet implements Comparable<ChangeSet> {
	private Station alt = null, neu = null;
	private boolean gedreht = false;

	/**
	 * @return the alt
	 */
	public Station getAlt() {
		return alt;
	}

	public void setAlt(Station alt) {
		this.alt = alt;
	}

	public Station getNeu() {
		return neu;
	}

	public void setNeu(Station neu) {
		this.neu = neu;
	}

	public boolean isGedreht() {
		return gedreht;
	}

	public void setGedreht(boolean gedreht) {
		this.gedreht = gedreht;
	}

	public ChangeSet(Station alt, Station neu, boolean gedreht) {
		this.alt = alt;
		this.neu = neu;
		this.gedreht = gedreht;
	}

	public ChangeSet(Station alt) {
		this.alt = alt;
		this.neu = alt;
		this.gedreht = false;
	}

	public ChangeSet(Station alt, boolean gedreht) {
		this.alt = alt;
		this.neu = alt;
		this.gedreht = gedreht;
	}

	public ChangeSet() {
	}

	public String toString() {
		return toString(" ");
	}

	public String toString(String delimiter) {
		String r = "";
		
		if (alt != null) {
			r += alt.toString(delimiter);
		} else {
			r += delimiter + delimiter + delimiter;
		}
		r += delimiter;
		if (neu != null) {
			r += neu.toString(delimiter);
			r += delimiter;
			r += gedreht;
		} else {
			r += delimiter + delimiter + delimiter + delimiter;
		}
		return r;
	}

	@Override
	public int compareTo(ChangeSet other) {
		if (this.getAlt() == null && other.getAlt() == null) {
			if (this.getNeu() == null && other.getNeu() == null) {
				return 0;
			} else if (this.getNeu() == null) {
				return 1;
			} else if (other.getNeu() == null) {
				return -1;
			} else {
				return this.getNeu().compareTo(other.getNeu());
			}
		} else if (this.getAlt() == null) {
			return 1;
		} else if (other.getAlt() == null) {
			return -1;
		} else {
			return this.getAlt().compareTo(other.getAlt());
		}
	}

}
