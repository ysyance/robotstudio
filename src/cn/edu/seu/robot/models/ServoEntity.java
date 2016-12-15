package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class ServoEntity extends ITreeEntry {
	private int axisId;
	private int axisType;

	private int runMode;
	private double posLimit;
	private double negLimit;
	private double maxVec;
	private double maxAcc;
	private double maxDec;
	private double offset; // ÁãµãÆ«²î
	private double ratio; // ¼õËÙ±È
	
	public ServoEntity(String name) {
		super(name, EntityTypeFactory.SERVO_ENTITY);
		this.setImage(PluginImage.SERVO);
	}

	public int getAxisId() {
		return axisId;
	}

	public void setAxisId(int axisId) {
		this.axisId = axisId;
	}

	public int getAxisType() {
		return axisType;
	}

	public void setAxisType(int axisType) {
		this.axisType = axisType;
	}

	public int getRunMode() {
		return runMode;
	}

	public void setRunMode(int runMode) {
		this.runMode = runMode;
	}

	public double getPosLimit() {
		return posLimit;
	}

	public void setPosLimit(double posLimit) {
		this.posLimit = posLimit;
	}

	public double getNegLimit() {
		return negLimit;
	}

	public void setNegLimit(double negLimit) {
		this.negLimit = negLimit;
	}

	public double getMaxVec() {
		return maxVec;
	}

	public void setMaxVec(double maxVec) {
		this.maxVec = maxVec;
	}

	public double getMaxAcc() {
		return maxAcc;
	}

	public void setMaxAcc(double maxAcc) {
		this.maxAcc = maxAcc;
	}

	public double getMaxDec() {
		return maxDec;
	}

	public void setMaxDec(double maxDec) {
		this.maxDec = maxDec;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
