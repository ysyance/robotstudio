package cn.edu.seu.robot.models;

public class VarDeclareEntity {
	private String name;
	private String type;
	private String pos;
	private String initVal;
	private String option; // constant:���� retain�� ���ֱ��� non-retain�� �Ǳ��ֱ���
	private String annotation; // ע��

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getInitVal() {
		return initVal;
	}

	public void setInitVal(String initVal) {
		this.initVal = initVal;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

}
