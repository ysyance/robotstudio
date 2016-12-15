package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

/**
 * IEC61131-3标准POU之FUNCTION实体
 * retVar： FUNCTION返回值
 * varList：参数列表
 * @author yance
 *
 */
public class FunctionEntity extends ITreeEntry {

	private VarDeclareEntity retVar;

	private List<VarDeclareEntity> varList = new ArrayList<>();

	public FunctionEntity(String name) {
		super(name, EntityTypeFactory.FUNCTION_ENTITY);
		this.setImage(PluginImage.FUNCTION);
	}

	public List<VarDeclareEntity> getVarList() {
		return varList;
	}

	public void setVarList(List<VarDeclareEntity> varList) {
		this.varList = varList;
	}

	public VarDeclareEntity getRetVar() {
		return retVar;
	}

	public void setRetVar(VarDeclareEntity retVar) {
		this.retVar = retVar;
	}

}
