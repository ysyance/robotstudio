package cn.edu.seu.robot.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.EditorPart;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.models.FunctionBlockEntity;
import cn.edu.seu.robot.models.FunctionEntity;
import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.models.Program;
import cn.edu.seu.robot.models.VarDeclareEntity;
import cn.edu.seu.robot.utils.PluginImage;

public class CodeEditor extends EditorPart {
	public static final String ID = "cn.edu.seu.robot.codeeditor";

	private boolean dirty;
	private CodeEditorInput input;
	private SourceViewer codeViewer;
	private TableViewer tabViewer;

	private int curLine;
	private Color curLineColor;
	private Color textBackgroundColor;

	private IUndoManager undoManager;

	public CodeEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		dirty = false;
		// TODO
		firePropertyChange(ISaveablePart2.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
		this.input = (CodeEditorInput) input;

		this.curLine = 1;
		this.curLineColor = new Color(Display.getCurrent(), new RGB(224, 251, 252));
		this.textBackgroundColor = new Color(Display.getCurrent(), new RGB(255, 255, 255));

	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);

		createDeclareSection(sashForm);
		createCodeSection(sashForm);

		sashForm.setWeights(new int[] { 1, 2 });

	}

	public void createDeclareSection(Composite parent) {
		tabViewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		Table table = tabViewer.getTable();
		TableColumn tc1 = new TableColumn(table, SWT.NONE);
		tc1.setWidth(100);
		tc1.setText("Category");
		
		TableColumn tc2 = new TableColumn(table, SWT.NONE);
		tc2.setWidth(100);
		tc2.setText("Name");
		
		TableColumn tc3 = new TableColumn(table, SWT.NONE);
		tc3.setWidth(100);
		tc3.setText("Type");
		
		TableColumn tc4 = new TableColumn(table, SWT.NONE);
		tc4.setWidth(100);
		tc4.setText("Position");
		
		TableColumn tc5 = new TableColumn(table, SWT.NONE);
		tc5.setWidth(100);
		tc5.setText("Value");
		
		TableColumn tc6 = new TableColumn(table, SWT.NONE);
		tc6.setWidth(100);
		tc6.setText("Option");
		
		TableColumn tc7 = new TableColumn(table, SWT.NONE);
		tc7.setWidth(200);
		tc7.setText("Annotation");
		
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		ITreeEntry entry = input.getEntry();
		
		tabViewer.setContentProvider(new TableTreeViewContentProvider());
		tabViewer.setLabelProvider(new TableViewerLabelProvider());
		tabViewer.setSorter(new TableViewerSorter());
		
		tabViewer.setInput(entry);
		

		
//		Color color = Display.getDefault().getSystemColor(SWT.COLOR_RED);  
//		table.getItems()[table.getItemCount()-1].setBackground(color);
		
		tabViewer.setColumnProperties(new String[]{"category", "name", "type", "position", "value", "option", "description"});
		CellEditor[] cellEditor = new CellEditor[7];
		cellEditor[0] = new ComboBoxCellEditor(tabViewer.getTable(), VarDeclareEntity.CATEGORY, SWT.READ_ONLY);
		cellEditor[1] = new TextCellEditor(tabViewer.getTable());
		cellEditor[2] = new TextCellEditor(tabViewer.getTable());
		cellEditor[3] = new TextCellEditor(tabViewer.getTable());
		cellEditor[4] = new TextCellEditor(tabViewer.getTable());
		cellEditor[5] = new ComboBoxCellEditor(tabViewer.getTable(), VarDeclareEntity.OPTIONS,	SWT.READ_ONLY);
		cellEditor[6] = new TextCellEditor(tabViewer.getTable());
	
		tabViewer.setCellEditors(cellEditor);
		tabViewer.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				boolean modifyFlag = false;
				
				TableItem item = (TableItem)element;
				VarDeclareEntity var = (VarDeclareEntity)item.getData();
				
				if(property.equals("category")) {
					int comboxIndex = (Integer)value;
					String cur = VarDeclareEntity.CATEGORY[comboxIndex];
					if(!cur.equals(var.getCategory()) && var.getCategory() != "RET") {
						var.setCategory(cur);
						modifyFlag = true;
					}
				} else if(property.equals("name")) {
					String cur = (String)value;
					if(!cur.equals(var.getName())) {
						var.setName(cur);
						modifyFlag = true;
					}
				} else if(property.equals("type")) {
					String cur = (String)value;
					if(!cur.equals(var.getType())) {
						var.setType(cur);
						modifyFlag = true;
					}
				} else if(property.equals("value")) {
					String cur = (String)value;
					if(!cur.equals(var.getInitVal())) {
						var.setInitVal(cur);
						modifyFlag = true;
					}
				} else if(property.equals("position")) {
					String cur = (String)value;
					if(!cur.equals(var.getPos())) {
						var.setPos(cur);
						modifyFlag = true;
					}
				} else if(property.equals("option")) {
					int comboxIndex = (Integer)value;
					String cur = VarDeclareEntity.OPTIONS[comboxIndex];
					if(!cur.equals(var.getOption())) {
						var.setOption(cur);
						modifyFlag = true;
					}
					
				} else if(property.equals("description")) {
					String cur = (String)value;
					if(!cur.equals(var.getAnnotation())) {
						var.setAnnotation(cur);
						modifyFlag = true;
					}
					
				}
				tabViewer.update(var, null);
				if(modifyFlag == true) {
					dirty = true;
					firePropertyChange(ISaveablePart2.PROP_DIRTY);
				}
 			}
			
			@Override
			public Object getValue(Object element, String property) {
				VarDeclareEntity var = (VarDeclareEntity)element;
				if(property.equals("category")) {
					return getCategoryIndex(var.getCategory());
				} else if(property.equals("name")) {
					return var.getName();
				} else if(property.equals("type")) {
					return var.getType();
				} else if(property.equals("value")) {
					return var.getInitVal();
				} else if(property.equals("position")) {
					return var.getPos();
				} else if(property.equals("option")) {
					return getOptionIndex(var.getOption());
				} else if(property.equals("description")) {
					return var.getAnnotation();
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}
			
			private int getCategoryIndex(String key) {
				for(int i = 0; i < VarDeclareEntity.CATEGORY.length; i ++) {
					if(VarDeclareEntity.CATEGORY[i].equals(key)) {
						return i;
					}
				}
				return -1;
			}
			
			private int getOptionIndex(String key) {
				for(int i = 0; i < VarDeclareEntity.OPTIONS.length; i ++) {
					if(VarDeclareEntity.OPTIONS[i].equals(key)) {
						return i;
					}
				}
				return 0;
			}
		});
		
		TableViewerActionGroup actionGroup = new TableViewerActionGroup(tabViewer);
		actionGroup.fillContextMenu(new MenuManager());
		
	}

	class TableTreeViewContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object element) {
			if (element instanceof FunctionBlockEntity) {
				FunctionBlockEntity entity = (FunctionBlockEntity) element;
				List<VarDeclareEntity> all = new ArrayList<>();
				all.addAll(entity.getInList());
				all.addAll(entity.getInoutList());
				all.addAll(entity.getOutList());
				all.addAll(entity.getVarList());
				all.addAll(entity.getTempList());
				all.addAll(entity.getGlobalList());
				return all.toArray();

			} else if (element instanceof FunctionEntity) {
				FunctionEntity entity = (FunctionEntity) element;
				List<VarDeclareEntity> all = new ArrayList<>();
				all.add(entity.getRetVar());
				all.addAll(entity.getInList());
				all.addAll(entity.getVarList());
				return all.toArray();

			} else if (element instanceof Program) {
				Program entity = (Program) element;

				List<VarDeclareEntity> all = new ArrayList<>();
				all.addAll(entity.getInList());
				all.addAll(entity.getInoutList());
				all.addAll(entity.getOutList());
				all.addAll(entity.getVarList());
				all.addAll(entity.getTempList());
				all.addAll(entity.getGlobalList());
				return all.toArray();
			}
			return null;
		}

	}

	class TableViewerLabelProvider implements ITableLabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getColumnImage(Object element, int col) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int col) {
			VarDeclareEntity var = (VarDeclareEntity) element;
			if (col == 0) {
				return var.getCategory();
			} else if (col == 1) {
				return var.getName();
			} else if (col == 2) {
				return var.getType();
			} else if (col == 3) {
				return var.getInitVal();
			} else if (col == 4) {
				return var.getPos();
			} else if (col == 5) {
				return var.getOption();
			} else if (col == 6) {
				return var.getAnnotation();
			}
			return null;
		}

	}

	class TableViewerSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			VarDeclareEntity var1 = (VarDeclareEntity) e1;
			VarDeclareEntity var2 = (VarDeclareEntity) e2;

			return var1.getCategory().compareTo(var2.getCategory());
		}

	}

	class TableViewerActionGroup extends ActionGroup {
		private TableViewer tv;
		public TableViewerActionGroup(TableViewer tv) {
			this.tv = tv;
		}
		
		@Override
		public void fillContextMenu(IMenuManager mgr) {
			MenuManager menuManager = (MenuManager)mgr;
			
			menuManager.add(new AddInputAction());
			menuManager.add(new AddLocalAction());
			ITreeEntry entry = input.getEntry();
			if(entry instanceof FunctionBlockEntity || entry instanceof Program) {
				menuManager.add(new AddInoutAction());
				menuManager.add(new AddOutputAction());
				
				menuManager.add(new AddTempAction());
				menuManager.add(new AddGlobalAction());
			} 	
			
			menuManager.add(new DelAction());
			
			Table table = tv.getTable();
			Menu menu = menuManager.createContextMenu(table);
			table.setMenu(menu);
		}
		
		private class AddInputAction extends Action {
			public AddInputAction() {
				this.setText("Add INPUT");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "INPUT");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionEntity) {
					((FunctionEntity)entry).getInList().add(var);
				} else if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getInList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getInList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		private class AddInoutAction extends Action {
			public AddInoutAction() {
				this.setText("Add INOUT");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "INOUT");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getInoutList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getInoutList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		private class AddOutputAction extends Action {
			public AddOutputAction() {
				this.setText("Add OUTPUT");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "OUTPUT");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getOutList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getOutList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		private class AddTempAction extends Action {
			public AddTempAction() {
				this.setText("Add TEMP");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "TEMP");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getTempList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getTempList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		private class AddLocalAction extends Action {
			public AddLocalAction() {
				this.setText("Add LOCAL");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "LOCAL");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionEntity) {
					((FunctionEntity)entry).getVarList().add(var);
				} else if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getVarList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getVarList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		private class AddGlobalAction extends Action {
			public AddGlobalAction() {
				this.setText("Add GLOBAL");
				this.setImageDescriptor(PluginImage.ADD_VARIABLE_DESC);
			}
			public void run() {
				VarDeclareEntity var = new VarDeclareEntity("Unnamed", "GLOBAL");
				ITreeEntry entry = input.getEntry();
				if(entry instanceof FunctionBlockEntity) {
					((FunctionBlockEntity)entry).getGlobalList().add(var);
				} else if(entry instanceof Program) {
					((Program)entry).getGlobalList().add(var);
				}
				tabViewer.refresh();
			}
		}
		
		
		
		private class DelAction extends Action {
			public DelAction() {
				this.setText("Del Variable");
				this.setImageDescriptor(PluginImage.DEL_VARIABLE_DESC);
			}
			
			public void run() {
				IStructuredSelection selection = (IStructuredSelection)tv.getSelection();
				VarDeclareEntity var = (VarDeclareEntity)selection.getFirstElement();
				if(var == null) return;
				String category = var.getCategory();
				ITreeEntry entry = input.getEntry();
				switch(category) {
				case "INPUT":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getInList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getInList().remove(var);
					} else if(entry instanceof FunctionEntity) {
						((FunctionEntity)entry).getInList().remove(var);
					}
					break;
				case "INOUT":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getInoutList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getInoutList().remove(var);
					}
					break;
				case "OUTPUT":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getOutList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getOutList().remove(var);
					}
					break;
				case "LOCAL":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getVarList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getVarList().remove(var);
					} else if(entry instanceof FunctionEntity) {
						((FunctionEntity)entry).getVarList().remove(var);
					}
					break;
				case "TEMP":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getTempList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getTempList().remove(var);
					}
					break;
				case "GLOBAL":
					if(entry instanceof FunctionBlockEntity) {
						((FunctionBlockEntity)entry).getGlobalList().remove(var);
					} else if(entry instanceof Program) {
						((Program)entry).getGlobalList().remove(var);
					}
					break;
				}
				
				tabViewer.refresh();
			}
			
		}
		
	}
	
	public void createCodeSection(Composite parent) {
		CompositeRuler ruler = new CompositeRuler();

		AnnotationRulerColumn annoCol = new AnnotationRulerColumn(12);
		LineNumberRulerColumn lineCol = new LineNumberRulerColumn();
		AnnotationRulerColumn placeHolder = new AnnotationRulerColumn(8);
		lineCol.setBackground(new Color(Display.getCurrent(), new RGB(240, 240, 240)));

		ruler.addDecorator(0, annoCol);
		ruler.addDecorator(1, lineCol);
		ruler.addDecorator(2, placeHolder);

		codeViewer = new SourceViewer(parent, ruler, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		codeViewer.configure(new CodeConfiguration());

		Font font = new Font(Display.getCurrent(), StringConverter
				.asFontData(Activator.getDefault().getPreferenceStore().getString(Activator.PREF_FONT_CODE)));
		codeViewer.getTextWidget().setFont(font);

		Document document = new Document();
		IDocumentPartitioner partitioner = new FastPartitioner(new CodePartitionScanner(),
				new String[] { CodePartitionScanner.CODE_COMMENT, CodePartitionScanner.CODE_STRING });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);

		codeViewer.setDocument(document);
		codeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		undoManager = new TextViewerUndoManager(100); // 初始化撤销管理器对象，默认可撤销100次
		undoManager.connect(codeViewer); // 将该撤销管理器应用于文档
		codeViewer.setUndoManager(undoManager);

		StyledText styledText = codeViewer.getTextWidget();
		styledText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (isUndoKeyPress(e)) {
					codeViewer.doOperation(ITextOperationTarget.UNDO);
				} else if (isRedoKeyPress(e)) {
					codeViewer.doOperation(ITextOperationTarget.REDO);
				}
			}

			private boolean isUndoKeyPress(KeyEvent e) {
				// CTRL + z
				return ((e.stateMask & SWT.CONTROL) > 0) && ((e.keyCode == 'z') || (e.keyCode == 'Z'));
			}

			private boolean isRedoKeyPress(KeyEvent e) {
				// CTRL + y
				return ((e.stateMask & SWT.CONTROL) > 0) && ((e.keyCode == 'y') || (e.keyCode == 'Y'));
			}

			public void keyReleased(KeyEvent e) {
				// do nothing
			}
		});

		StyledText text = codeViewer.getTextWidget();
		text.setIndent(4);

		String body = null;
		ITreeEntry entry = input.getEntry();
		if (entry instanceof FunctionBlockEntity) {
			FunctionBlockEntity entity = (FunctionBlockEntity) input.getEntry();
			body = entity.getBody();
		} else if (entry instanceof FunctionEntity) {
			FunctionEntity entity = (FunctionEntity) input.getEntry();
			body = entity.getBody();
		} else if (entry instanceof Program) {
			Program entity = (Program) input.getEntry();
			body = entity.getBody();
		}
		if (body != null) {
			text.setText(body);
		} else {
			text.setText("");
		}

		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				firePropertyChange(ISaveablePart2.PROP_DIRTY);
			}
		});

		text.addCaretListener(new CaretListener() {

			@Override
			public void caretMoved(CaretEvent event) {
				text.setLineBackground(curLine, 1, textBackgroundColor);
				curLine = text.getLineAtOffset(text.getCaretOffset());
				text.setLineBackground(curLine, 1, curLineColor);
				// IStatusLineManager statusline =
				// getEditorSite().getActionBars().getStatusLineManager();
				// statusline.setMessage("Positon " + curLine + " : " +
				// text.getCaret().getLocation().x);

			}
		});
	}

	public class CodeConfiguration extends SourceViewerConfiguration {

		public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
			return new String[] { IDocument.DEFAULT_CONTENT_TYPE, CodePartitionScanner.CODE_COMMENT,
					CodePartitionScanner.CODE_STRING };
		}

		public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {

			ContentAssistant assistant = new ContentAssistant();

			// IContentAssistProcessor tagContentAssistProcessor = new
			// TagContentAssistProcessor(getXMLTagScanner());
			// assistant.setContentAssistProcessor(tagContentAssistProcessor,
			// XMLPartitionScanner.XML_START_TAG);

			assistant.enableAutoActivation(true);
			assistant.setAutoActivationDelay(500);
			assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
			assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);

			return assistant;

		}

		private RuleBasedScanner getCommentScanner() {
			RuleBasedScanner scanner = new RuleBasedScanner();
			// EditorColorProvider colorProvider =
			// Activator.getDefault().getEditorColorProvider();
			// scanner.setDefaultReturnToken(colorProvider.getToken(Activator.PREF_COLOR_COMMENT));
			scanner.setDefaultReturnToken(
					new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(0, 255, 0)))));
			return scanner;
		}

		private RuleBasedScanner getStringScanner() {
			RuleBasedScanner scanner = new RuleBasedScanner();
			// EditorColorProvider colorProvider =
			// Activator.getDefault().getEditorColorProvider();
			// scanner.setDefaultReturnToken(colorProvider.getToken(Activator.PREF_COLOR_STRING));
			scanner.setDefaultReturnToken(
					new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(0, 0, 255)))));
			return scanner;
		}

		private RuleBasedScanner getDefaultScanner() {
			return new CodeKeywordPartitionScanner();
		}

		public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

			PresentationReconciler reconciler = new PresentationReconciler();

			DefaultDamagerRepairer commentDR = new DefaultDamagerRepairer(getCommentScanner());
			reconciler.setDamager(commentDR, CodePartitionScanner.CODE_COMMENT);
			reconciler.setRepairer(commentDR, CodePartitionScanner.CODE_COMMENT);

			DefaultDamagerRepairer stringDR = new DefaultDamagerRepairer(getStringScanner());
			reconciler.setDamager(stringDR, CodePartitionScanner.CODE_STRING);
			reconciler.setRepairer(stringDR, CodePartitionScanner.CODE_STRING);

			DefaultDamagerRepairer keywordDR = new DefaultDamagerRepairer(getDefaultScanner());
			reconciler.setDamager(keywordDR, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(keywordDR, IDocument.DEFAULT_CONTENT_TYPE);

			return reconciler;
		}
	}

	/**
	 * 关键词分词
	 * 
	 * @author Administrator
	 *
	 */
	public static class CodeKeywordPartitionScanner extends RuleBasedScanner {

		private static String[] KEYWORDS = { "IF", "THEN", "ELSIF", "ELSE", "END_IF", "CASE", "OF", "END_CASE", "FOR",
				"TO", "DO", "END_FOR", "WHILE", "END_WHILE", "REPEAT", "UNTIL", "END_REPEAT", "EXIT", "RETURN",
				"CONFIGURATION", "END_CONFIGURATION", "TASK", "RESOURCE", "ON", "END_RESOURCE", "VAR_CONFIG",
				"VAR_ACCESS", "END_VAR", "WITH", "PROGRAM", "RETAIN", "NON_RETAIN", "FUNTION", "END_FUNCTION",
				"FUN_BLOCK", "END_FUNTION_BLOCK", "END_PROGRAM", "VAR", "END_VAR", "CONSTANT", "VAR_TEMP", "VAR_INPUT",
				"VAR_OUTPUT", "VAR_IN_OUT", "VAR_GLOGAL", "AT", "VAR_EXTERNAL" };

		public CodeKeywordPartitionScanner() {
			IToken keyword = Activator.getDefault().getEditorColorProvider().getToken(Activator.PREF_COLOR_KEYWORD);
			IToken other = Activator.getDefault().getEditorColorProvider().getToken(Activator.PREF_COLOR_DEFAULT);

			WordRule wordRule = new WordRule(new IWordDetector() {
				public boolean isWordPart(char c) {
					return Character.isJavaIdentifierPart(c);
				}

				public boolean isWordStart(char c) {
					return Character.isJavaIdentifierStart(c);
				}
			}, other);
			for (int i = 0; i < KEYWORDS.length; i++) {
				wordRule.addWord(KEYWORDS[i], keyword);
				wordRule.addWord(KEYWORDS[i].toLowerCase(), keyword);
			}
			IRule[] rules = new IRule[2];
			rules[0] = wordRule;
			rules[1] = new WhitespaceRule(new IWhitespaceDetector() {
				public boolean isWhitespace(char character) {
					return Character.isWhitespace(character);
				}
			});

			this.setRules(rules);
		}

	}

	/**
	 * 用于Code编辑分区，区分字符串或者注释
	 * 
	 * @author Administrator
	 *
	 */
	public class CodePartitionScanner extends RuleBasedPartitionScanner {

		public static final String CODE_COMMENT = "__sql_comment";
		public static final String CODE_STRING = "__sql_string";

		public CodePartitionScanner() {
			IPredicateRule[] rules = new IPredicateRule[3];

			IToken comment = new Token(CODE_COMMENT);
			rules[0] = new MultiLineRule("(*", "*)", comment, (char) 0, true);
			// rules[1] = new EndOfLineRule("--", comment);

			IToken string = new Token(CODE_STRING);
			rules[1] = new SingleLineRule("\"", "\"", string, '\\');
			rules[2] = new SingleLineRule("\'", "\'", string, '\\');

			this.setPredicateRules(rules);
		}

	}

	@Override
	public void setFocus() {
		codeViewer.getControl().setFocus();
	}

}
