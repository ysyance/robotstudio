package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

/**
 * IEC61131-3标准之FUNCTIONBLOCK inList: 输入变量列表 outList: 输出变量列表 inoutList：输入输出变量列表
 * varList：内部变量列表 tempList：临时变量列表
 * 
 * @author yance
 *
 */
public class FunctionBlockEntity extends ITreeEntry implements IPOUEntity {


	private List<VarDeclareEntity> inList = new ArrayList<>();
	private List<VarDeclareEntity> outList = new ArrayList<>();
	private List<VarDeclareEntity> inoutList = new ArrayList<>();
	private List<VarDeclareEntity> varList = new ArrayList<>();
	private List<VarDeclareEntity> tempList = new ArrayList<>();
	private List<VarDeclareEntity> globalList = new ArrayList<>();

	private String body;
	private String annotation;

	public FunctionBlockEntity(String name) {
		super(name, EntityTypeFactory.FUNCTION_BLOCK_ENTITY);
		this.setImage(PluginImage.FUNCTIONBLOCK);
	}

	public List<VarDeclareEntity> getInList() {
		return inList;
	}

	public void setInList(List<VarDeclareEntity> inList) {
		this.inList = inList;
	}

	public List<VarDeclareEntity> getOutList() {
		return outList;
	}

	public void setOutList(List<VarDeclareEntity> outList) {
		this.outList = outList;
	}

	public List<VarDeclareEntity> getInoutList() {
		return inoutList;
	}

	public void setInoutList(List<VarDeclareEntity> inoutList) {
		this.inoutList = inoutList;
	}

	public List<VarDeclareEntity> getVarList() {
		return varList;
	}

	public void setVarList(List<VarDeclareEntity> varList) {
		this.varList = varList;
	}

	public List<VarDeclareEntity> getTempList() {
		return tempList;
	}

	public void setTempList(List<VarDeclareEntity> tempList) {
		this.tempList = tempList;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public List<VarDeclareEntity> getGlobalList() {
		return globalList;
	}

	public void setGlobalList(List<VarDeclareEntity> globalList) {
		this.globalList = globalList;
	}
	
	@Override
	public String getPouInfo() {
		String info = "NAME: " + this.getName() + "\n";
		info += "DESC: " + this.getAnnotation() + "\n";
		
		info += "\n|> IEC Interface <|\n";
		info += "(";
		int count = inList.size();
		for(int i = 0; i < count; i ++) {
			info += inList.get(i).getName() + " : " + inList.get(i).getType();
			if(i+1 < count) {
				info += ", ";
			}
		}
		info += ") => (" ;
		count = outList.size();
		for(int i = 0; i < count; i ++) {
			info += outList.get(i).getName() + " : " + outList.get(i).getType();
			if(i+1 < count) {
				info += ", ";
			}
		}
		info += ")" + "\n";
		
		info += "\n|> IEC Body <|\n" + this.body;
		
		return info;
	}

}
