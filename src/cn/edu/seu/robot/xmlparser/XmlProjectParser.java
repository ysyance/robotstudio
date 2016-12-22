package cn.edu.seu.robot.xmlparser;

import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.seu.robot.models.ConfigurationEntity;
import cn.edu.seu.robot.models.DHParam;
import cn.edu.seu.robot.models.DataTypeFolder;
import cn.edu.seu.robot.models.FunctionBlockEntity;
import cn.edu.seu.robot.models.FunctionEntity;
import cn.edu.seu.robot.models.IOFacilityEntity;
import cn.edu.seu.robot.models.POUFolder;
import cn.edu.seu.robot.models.Program;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.models.RobotEntity;
import cn.edu.seu.robot.models.ServoEntity;
import cn.edu.seu.robot.models.ServoFolder;
import cn.edu.seu.robot.models.VarDeclareEntity;

public class XmlProjectParser {
	private File file;
	private ProjectEntity pro;

	public XmlProjectParser(File file) {
		this.file = file;
	}

	public ProjectEntity loadProject() throws DocumentException {
		SAXReader sax = new SAXReader();
		org.dom4j.Document document = sax.read(file);
		Element root = document.getRootElement();
		if (root.getName().equals("projectDescription")) {
			Element node = root.element("name");
			String proName = node.getTextTrim();
			this.pro = new ProjectEntity(proName);

			node = root.element("comment");
			String comment = node.getTextTrim();
			this.pro.setDescription(comment);

			node = root.element("projects");
			List<Element> components = node.elements("component");
			for (Element e : components) {
				Attribute attrType = e.attribute("TYPE");
				String typeVal = attrType.getText();
				Attribute attrFile = e.attribute("FILE");
				String fileVal = attrFile.getText();

				String filePath = this.file.getParent() + File.separator + fileVal;
				if (typeVal.equals("ROBOT")) {
					parseConfig(new File(filePath));
				} else if (typeVal.equals("IEC61131-3")) {
					parseTC6XML(new File(filePath));
				}
			}
		} else {
			return null;
		}
		return this.pro;
	}

	private void parseConfig(File configFile) throws DocumentException {
		SAXReader sax = new SAXReader();
		Document document = sax.read(configFile);
		Element root = document.getRootElement();

		Element node = root.element("header");
		Attribute attr = node.attribute("type");
		this.pro.setHeaderType(attr.getText());
		attr = node.attribute("order");
		this.pro.setHeaderOrder(attr.getText());
		attr = node.attribute("version");
		this.pro.setVersion(attr.getText());
		attr = node.attribute("target");
		this.pro.setTargetMachine(attr.getText());

		IOFacilityEntity io = new IOFacilityEntity("I/O Facility");
		node = root.element("io");
		attr = node.attribute("period");
		io.setPeriod(attr.getText());
		attr = node.attribute("ldi");
		io.setLdi(attr.getText());
		attr = node.attribute("ldo");
		io.setLdo(attr.getText());
		attr = node.attribute("rdi");
		io.setRdi(attr.getText());
		attr = node.attribute("rdo");
		io.setRdo(attr.getText());
		attr = node.attribute("lai");
		io.setLai(attr.getText());
		attr = node.attribute("lao");
		io.setLao(attr.getText());
		attr = node.attribute("rai");
		io.setRai(attr.getText());
		attr = node.attribute("rao");
		io.setRao(attr.getText());
		this.pro.addChild(io);

		ServoFolder servoFolder = new ServoFolder();
		node = root.element("servos");
		attr = node.attribute("period");
		servoFolder.setServoPeriod(attr.getText());
		List<Element> listElem = node.elements();
		for (Element e : listElem) {
			attr = e.attribute("name");
			ServoEntity servo = new ServoEntity(attr.getText());
			attr = e.attribute("id");
			servo.setAxisId(Integer.parseInt(attr.getText()));
			attr = e.attribute("type");
			servo.setAxisType(Integer.parseInt(attr.getText()));
			attr = e.attribute("runMode");
			servo.setRunMode(Integer.parseInt(attr.getText()));
			attr = e.attribute("posLimit");
			servo.setPosLimit(Double.parseDouble(attr.getText()));
			attr = e.attribute("negLimit");
			servo.setNegLimit(Double.parseDouble(attr.getText()));
			attr = e.attribute("maxVec");
			servo.setMaxVec(Double.parseDouble(attr.getText()));
			attr = e.attribute("maxAcc");
			servo.setMaxAcc(Double.parseDouble(attr.getText()));
			attr = e.attribute("maxDec");
			servo.setMaxDec(Double.parseDouble(attr.getText()));
			attr = e.attribute("offset");
			servo.setOffset(Double.parseDouble(attr.getText()));
			attr = e.attribute("ratio");
			servo.setRatio(Double.parseDouble(attr.getText()));
			servoFolder.addChild(servo);
		}
		this.pro.addChild(servoFolder);

		RobotEntity robot = new RobotEntity("Robot");
		node = root.element("robot");
		attr = node.attribute("name");
		robot.setRobotType(attr.getText());
		attr = node.attribute("type");
		robot.setRobotType(attr.getText());
		listElem = node.elements();
		for (Element e : listElem) {
			DHParam dh = new DHParam();
			attr = e.attribute("theta");
			dh.theta = Double.parseDouble(attr.getText());
			attr = e.attribute("d");
			dh.d = Double.parseDouble(attr.getText());
			attr = e.attribute("a");
			dh.a = Double.parseDouble(attr.getText());
			attr = e.attribute("alpha");
			dh.alpha = Double.parseDouble(attr.getText());
			attr = e.attribute("offset");
			dh.offset = Double.parseDouble(attr.getText());
			robot.getDhparams().add(dh);
		}
		this.pro.addChild(robot);

	}

	private void parseTC6XML(File plcfile) throws DocumentException {
		SAXReader sax = new SAXReader();
		org.dom4j.Document document = sax.read(plcfile);
		Element root = document.getRootElement();

		if (root.getName().equals("project")) {
			Element elemInstance = root.element("instances");
			Element elemConfiguration = elemInstance.element("configurations");
			List<Element> configList = elemConfiguration.elements();
			for(Element config : configList) {
				ConfigurationEntity configEntity = new ConfigurationEntity("Configuration");
				this.pro.addChild(configEntity);
			}
			
			
			Element elemTypes = root.element("types");
			DataTypeFolder dataTypes = new DataTypeFolder();
			this.pro.addChild(dataTypes);

			Element elemPous = elemTypes.element("pous");
			POUFolder pouFolder = new POUFolder();
			List<Element> pouList = elemPous.elements();
			for (Element pou : pouList) {
				Attribute attrPouName = pou.attribute("name");
				Attribute attrPouType = pou.attribute("pouType");
				if (attrPouType.getText().equals("function")) {    // the first case
					FunctionEntity funcEntity = new FunctionEntity(attrPouName.getText());
					
					Element elemInterface = pou.element("interface");
					
					List<Element> elemRetVarType = elemInterface.element("returnType").elements();
					VarDeclareEntity retVarEntity = new VarDeclareEntity(attrPouName.getText(), "RET");
					for (Element t : elemRetVarType) {
						retVarEntity.setType(t.getName());
					}
					funcEntity.setRetVar(retVarEntity);
					
					Element localVarGroup = elemInterface.element("localVars");
					if (localVarGroup != null) {
						List<Element> localVarList = localVarGroup.elements();
						for (Element var : localVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "LOCAL");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							funcEntity.getVarList().add(varEntity);
						}
					}
					
					Element elemBody = pou.element("body").element("ST");
					funcEntity.setBody(elemBody.getText());

					Element anno = pou.element("documentation");
					if(anno != null) {
						List<Element> annoList = anno.elements();
						for (Element e : annoList) {
							funcEntity.setAnnotation(e.getText());
						}
					}
					
					pouFolder.addChild(funcEntity);
					
					
				} else if (attrPouType.getText().equals("functionBlock")) { // the second case 
					FunctionBlockEntity fbEntity = new FunctionBlockEntity(attrPouName.getText());

					Element elemInterface = pou.element("interface");

					Element inVarGroup = elemInterface.element("inputVars");
					if (inVarGroup != null) {
						List<Element> inVarList = inVarGroup.elements();
						for (Element var : inVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "INPUT");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							fbEntity.getInList().add(varEntity);
						}
					}

					Element outVarGroup = elemInterface.element("outputVars");
					if (outVarGroup != null) {
						List<Element> outVarList = outVarGroup.elements();
						for (Element var : outVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "OUTPUT");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							fbEntity.getOutList().add(varEntity);
						}
					}

					Element localVarGroup = elemInterface.element("localVars");
					if (localVarGroup != null) {
						List<Element> localVarList = localVarGroup.elements();
						for (Element var : localVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "LOCAL");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							fbEntity.getVarList().add(varEntity);
						}
					}

					Element elemBody = pou.element("body").element("ST");
					fbEntity.setBody(elemBody.getText());

					Element anno = pou.element("documentation");
					if(anno != null) {
						List<Element> annoList = anno.elements();
						for (Element e : annoList) {
							fbEntity.setAnnotation(e.getText());
						}
					}
					
					pouFolder.addChild(fbEntity);
				} else if (attrPouType.getText().equals("program")) {  // the last case
					Program progEntity = new Program(attrPouName.getText());
					
					Element elemInterface = pou.element("interface");

					Element inVarGroup = elemInterface.element("inputVars");
					if (inVarGroup != null) {
						List<Element> inVarList = inVarGroup.elements();
						for (Element var : inVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "INPUT");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							progEntity.getInList().add(varEntity);
						}
					}

					Element outVarGroup = elemInterface.element("outputVars");
					if (outVarGroup != null) {
						List<Element> outVarList = outVarGroup.elements();
						for (Element var : outVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(),  "OUTPUT");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							progEntity.getOutList().add(varEntity);
						}
					}

					Element localVarGroup = elemInterface.element("localVars");
					if (localVarGroup != null) {
						List<Element> localVarList = localVarGroup.elements();
						for (Element var : localVarList) {
							Attribute attrName = var.attribute("name");
							VarDeclareEntity varEntity = new VarDeclareEntity(attrName.getText(), "LOCAL");
							List<Element> elemVarType = var.element("type").elements();
							for (Element t : elemVarType) {
								varEntity.setType(t.getName());
							}
							progEntity.getVarList().add(varEntity);
						}
					}
					
					
					Element elemBody = pou.element("body").element("ST");
					progEntity.setBody(elemBody.getText());

					Element anno = pou.element("documentation");
					if(anno != null) {
						List<Element> annoList = anno.elements();
						for (Element e : annoList) {
							progEntity.setAnnotation(e.getText());
						}
					}
					
					pouFolder.addChild(progEntity);
					
					
					
				}
			}
			
			this.pro.addChild(pouFolder);
			
			
			
			
			

		}
	}

}
