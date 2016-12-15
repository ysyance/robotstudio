package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class IOFacilityEntity extends ITreeEntry {

	private String period;
	private String ldi;
	private String ldo;
	private String rdi;
	private String rdo;
	private String lai;
	private String lao;
	private String rai;
	private String rao;

	public IOFacilityEntity(String name) {
		super(name, EntityTypeFactory.IO_FACILITY_ENTITY);
		this.setImage(PluginImage.IO_FACILITY);
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLdi() {
		return ldi;
	}

	public void setLdi(String ldi) {
		this.ldi = ldi;
	}

	public String getLdo() {
		return ldo;
	}

	public void setLdo(String ldo) {
		this.ldo = ldo;
	}

	public String getRdi() {
		return rdi;
	}

	public void setRdi(String rdi) {
		this.rdi = rdi;
	}

	public String getRdo() {
		return rdo;
	}

	public void setRdo(String rdo) {
		this.rdo = rdo;
	}

	public String getLai() {
		return lai;
	}

	public void setLai(String lai) {
		this.lai = lai;
	}

	public String getLao() {
		return lao;
	}

	public void setLao(String lao) {
		this.lao = lao;
	}

	public String getRai() {
		return rai;
	}

	public void setRai(String rai) {
		this.rai = rai;
	}

	public String getRao() {
		return rao;
	}

	public void setRao(String rao) {
		this.rao = rao;
	}

}
