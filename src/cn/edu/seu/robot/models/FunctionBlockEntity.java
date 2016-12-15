package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

/**
 * IEC61131-3标准之FUNCTIONBLOCK 
 * inList: 输入变量列表 
 * outList: 输出变量列表 
 * inoutList：输入输出变量列表
 * varList：内部变量列表 
 * tempList：临时变量列表
 * @author yance
 *
 */
public class FunctionBlockEntity extends ITreeEntry {
	List<VarDeclareEntity> inList = new ArrayList<>();
	List<VarDeclareEntity> outList = new ArrayList<>();
	List<VarDeclareEntity> inoutList = new ArrayList<>();
	List<VarDeclareEntity> varList = new ArrayList<>();
	List<VarDeclareEntity> tempList = new ArrayList<>();

	public FunctionBlockEntity(String name) {
		super(name, EntityTypeFactory.FUNCTION_BLOCK_ENTITY);
		this.setImage(PluginImage.FUNCTION);
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

}
