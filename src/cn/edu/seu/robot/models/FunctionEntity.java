package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.Iterator;
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
public class FunctionEntity extends ITreeEntry implements IPOUEntity{

	private VarDeclareEntity retVar;

	private List<VarDeclareEntity> varList = new ArrayList<>();
	private String body;
	private String annotation;

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
	
	public String getBody(){
		return this.body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public String getAnnotation(){
		return this.annotation;
	}
	
	public void setAnnotation(String annotation){
		this.annotation = annotation;
	}

	@Override
	public String getPouInfo() {
		String info = this.getName() + "\n";
		info += this.getAnnotation() + "\n";
		info += "(";
		int count = varList.size();
		for(int i = 0; i < count; i ++) {
			info += varList.get(i).getName() + " : " + varList.get(i).getType();
			if(i+1 < count) {
				info += ", ";
			}
		}
		info += ") => (" + retVar.getName() + " : " + retVar.getType() + ")";
		return info;
	}

}
