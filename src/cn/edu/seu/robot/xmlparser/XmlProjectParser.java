package cn.edu.seu.robot.xmlparser;

import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.seu.robot.models.DHParam;
import cn.edu.seu.robot.models.IOFacilityEntity;
import cn.edu.seu.robot.models.LibraryEntity;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.models.RobotEntity;
import cn.edu.seu.robot.models.ServoEntity;
import cn.edu.seu.robot.models.ServoFolder;

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
			for(Element e : components) {
				Attribute attrType = e.attribute("TYPE");
				String typeVal = attrType.getText();
				Attribute attrFile = e.attribute("FILE");
				String fileVal = attrFile.getText();
				
				String filePath = this.file.getParent() + File.separator + fileVal;
				if(typeVal.equals("ROBOT")) {
					parseConfig(new File(filePath));
				} else if(typeVal.equals("IEC61131-3")) {
					parseTC6XML(new File(filePath));
				}
			}
		} else {
			return null;
		}
		return this.pro;
	}
	
	private void parseConfig(File configFile) throws DocumentException{
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
		for(Element e : listElem) {
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
		robot.setType(attr.getText());
		listElem = node.elements();
		for(Element e : listElem) {
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
	
	private void parseTC6XML(File plcfile) {
	}
	
	
	
}


















