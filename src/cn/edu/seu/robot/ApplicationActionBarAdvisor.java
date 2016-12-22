package cn.edu.seu.robot;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import cn.edu.seu.robot.actions.CloseProjectAction;
import cn.edu.seu.robot.actions.CreateProjectAction;
import cn.edu.seu.robot.actions.LoadProjectAction;
import cn.edu.seu.robot.actions.OpenEditorAction;
import cn.edu.seu.robot.actions.ShowConsoleAction;
import cn.edu.seu.robot.actions.ShowLibraryExplorerAction;
import cn.edu.seu.robot.actions.ShowProjectExplorerAction;
import cn.edu.seu.robot.actions.ShowPropertySheetAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private Action openFileAction;
	private Action loadProjectAction;
	private Action createProjectAction;
	private Action closeProjectAction;
	
	private IWorkbenchAction saveAction;
	
	private IWorkbenchAction exitAction;
	
	private Action showNavigatorAction;
	private Action showLibraryAcion;
	private Action showConsoleAction;
	private Action showPropertyAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	openFileAction = new OpenEditorAction(window);
    	this.register(openFileAction);
    	loadProjectAction = new LoadProjectAction(window);
    	this.register(loadProjectAction);
    	createProjectAction = new CreateProjectAction(window);
    	this.register(createProjectAction);
    	closeProjectAction = new CloseProjectAction(window);
    	this.register(closeProjectAction);
    	
    	saveAction = ActionFactory.SAVE.create(window);
    	this.register(saveAction);
    	exitAction = ActionFactory.QUIT.create(window);
    	this.register(exitAction);
    	
    	showNavigatorAction = new ShowProjectExplorerAction(window);
    	this.register(showNavigatorAction);
    	showLibraryAcion = new ShowLibraryExplorerAction(window);
    	this.register(showLibraryAcion);
    	showConsoleAction = new ShowConsoleAction(window);
    	this.register(showConsoleAction);
    	showPropertyAction = new ShowPropertySheetAction(window);
    	this.register(showPropertyAction);
    	
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager fileMenu = new MenuManager("&File", "WENJIAN");
    	menuBar.add(fileMenu);
    	
    	fileMenu.add(createProjectAction);
    	fileMenu.add(loadProjectAction);
    	fileMenu.add(closeProjectAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(saveAction);
    	fileMenu.add(new Separator());
    	
    	fileMenu.add(openFileAction);
    	fileMenu.add(new Separator());
    	
    	fileMenu.add(exitAction);
    	
    	MenuManager editMenu = new MenuManager("&Edit", "edit");
    	menuBar.add(editMenu);
    	editMenu.add(showNavigatorAction);
    	
    	MenuManager runMenu = new MenuManager("&Run", "run");
    	menuBar.add(runMenu);
    	runMenu.add(showNavigatorAction);
    	
    	MenuManager windowMenu = new MenuManager("&Window", "Window");
    	menuBar.add(windowMenu);
    	windowMenu.add(showNavigatorAction);
    	windowMenu.add(showLibraryAcion);
    	windowMenu.add(showConsoleAction);
    	windowMenu.add(showPropertyAction);
    }
    
    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {
    	IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
    	toolbar.add(createProjectAction);
    	toolbar.add(loadProjectAction);
    	toolbar.add(closeProjectAction);
    	toolbar.add(new Separator());
    	toolbar.add(openFileAction);
    	toolbar.add(new Separator());   
    	coolBar.add(toolbar);
    	 	
    }
    
}
