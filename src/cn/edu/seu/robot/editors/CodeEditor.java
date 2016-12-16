package cn.edu.seu.robot.editors;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cn.edu.seu.robot.Activator;

public class CodeEditor extends EditorPart {
	public static final String ID = "cn.edu.seu.robot.codeeditor";

	private boolean dirty;
	private CodeEditorInput input;
	private SourceViewer codeViewer;

	private IUndoManager undoManager;

	public CodeEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
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
		CompositeRuler ruler = new CompositeRuler();

		AnnotationRulerColumn annoCol = new AnnotationRulerColumn(15);
		LineNumberRulerColumn lineCol = new LineNumberRulerColumn();

		ruler.addDecorator(0, annoCol);
		ruler.addDecorator(1, lineCol);

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

		undoManager = new TextViewerUndoManager(100); 	// 初始化撤销管理器对象，默认可撤销100次
		undoManager.connect(codeViewer); 				// 将该撤销管理器应用于文档
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
		text.setText("FUNTION func END_FUNCTION");

		
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				firePropertyChange(ISaveablePart2.PROP_DIRTY);
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
