package home.ahmad.model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Ahmad Alrefai
 */
@Cacheable
@Entity
@Table(name = "country")
public class Country extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7995777625699126629L;

	@NotNull
	@Size(min = 2, max = 2, message = "must be exactly 2 letters")
	@Column(name = "iso_code")
	private String isoCode;

	@NotNull
	@Size(min = 2, max = 80, message = "must be 2-80 letters and spaces")
	private String name;

	@NotNull
	@Size(min = 2, max = 80, message = "must be 2-80 letters and spaces")
	@Column(name = "printable_name")
	private String printableName;

	@NotNull
	@Size(min = 3, max = 3, message = "must be exactly 3 letters")
	private String iso3;

	@NotNull
	@Size(min = 3, max = 3, message = "must be exactly 3 letters")
	private String numcode;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getPrintableName() {
		return printableName;
	}

	public void setPrintableName(String printableName) {
		this.printableName = printableName;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getNumcode() {
		return numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	@Override
	public String toString() {
		return name + ", " + isoCode;
	}
}
