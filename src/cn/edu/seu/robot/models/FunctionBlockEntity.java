package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

/**
 * IEC61131-3��׼֮FUNCTIONBLOCK 
 * inList: ��������б� 
 * outList: ��������б� 
 * inoutList��������������б�
 * varList���ڲ������б� 
 * tempList����ʱ�����б�
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