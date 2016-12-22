package cn.edu.seu.robot.models;

public class VarDeclareEntity {
	
	public static String[] CATEGORY = new String[] {"RET", "INPUT", "OUTPUT", "INOUT", "LOCAL", "GLOBAL", "TEMP"};
	public static String[] OPTIONS = new String[] {"", "CONST", "RETAIN", "NON-RETIAN"};


	private String category;
	private String name;
	private String type;
	private String pos="";
	private String initVal="";
	private String option=""; // constant:常量 retain： 保持变量 non-retain： 非保持变量
	private String annotation=""; // 注释
	
	public VarDeclareEntity() {
	}
	
	public VarDeclareEntity(String name) {
		this.name = name;
	}
	
	public VarDeclareEntity(String name, String category) {
		this.name = name;
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

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
