package ru.terra.lorview.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.terra.lorview.dto.Comment;
import ru.terra.lorview.dto.CommentsDTO;
import ru.terra.lorview.dto.TrackerItemDTO;
import ru.terra.lorview.parser.Parser;
import ru.terra.lorview.parser.ParserException;
import ru.terra.lorview.parser.ParsersFactory;

import java.util.HashMap;
import java.util.Map;

public class MainWindow {
    private Table trackerTable;
    private Parser parser;
    private TabFolder tabFolder;
    private Map<Integer, TabItem> threadTabs = new HashMap<>();

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        Shell shell = new Shell();
        shell.setSize(966, 670);
        shell.setText("LOR View");
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));

        tabFolder = new TabFolder(shell, SWT.NONE);

        TabItem ti_tracker = new TabItem(tabFolder, SWT.NONE);
        ti_tracker.setText("Tracker");

        Composite composite = new Composite(tabFolder, SWT.NONE);
        ti_tracker.setControl(composite);
        composite.setLayout(new GridLayout(1, false));

        ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);

        ToolItem tiRefresh = new ToolItem(toolBar, SWT.NONE);
        tiRefresh.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                loadTracker();
            }
        });
        tiRefresh.setText("Обновить");

        trackerTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
        trackerTable.setLinesVisible(true);
        trackerTable.setHeaderVisible(true);
        trackerTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        trackerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                if (trackerTable.getSelectionCount() > 0) {
                    TrackerItemDTO trackerItemDTO = (TrackerItemDTO) trackerTable.getSelection()[0].getData();
                    Integer threadId = trackerItemDTO.getThreadId();
                    if (threadTabs.get(trackerItemDTO.getThreadId()) == null)
                        createThreadTab(trackerItemDTO);
                    loadThread(trackerItemDTO.getForum(), trackerItemDTO.isNews(), threadId);
                }
            }
        });

        TableColumn tableColumn = new TableColumn(trackerTable, SWT.NONE);
        tableColumn.setWidth(100);
        tableColumn.setText("Группа");

        TableColumn tableColumn_1 = new TableColumn(trackerTable, SWT.NONE);
        tableColumn_1.setWidth(557);
        tableColumn_1.setText("Заголовок");

        TableColumn tableColumn_2 = new TableColumn(trackerTable, SWT.NONE);
        tableColumn_2.setWidth(184);
        tableColumn_2.setText("Последнее сообщение");

        TableColumn tableColumn_3 = new TableColumn(trackerTable, SWT.NONE);
        tableColumn_3.setWidth(100);
        tableColumn_3.setText("Ответы");

        TabItem ti_forums = new TabItem(tabFolder, SWT.NONE);
        ti_forums.setText("Forums");

        parser = ParsersFactory.getParser();
        try {
            parser.start();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        loadTracker();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private void loadTracker() {
        trackerTable.clearAll();
        trackerTable.removeAll();
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    for (TrackerItemDTO trackerItemDTO : parser.loadTracker()) {
                        TableItem tableItem = new TableItem(trackerTable, SWT.NONE);
                        tableItem.setText(new String[]{trackerItemDTO.getGroup(), trackerItemDTO.getTitle(), trackerItemDTO.getLastMessage(),
                                trackerItemDTO.getReps()});
                        tableItem.setData(trackerItemDTO);
                    }
                } catch (ParserException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void createThreadTab(TrackerItemDTO trackerItemDTO) {
        TabItem tiThread = new TabItem(tabFolder, SWT.NONE);
        tiThread.setText(trackerItemDTO.getTitle());
        threadTabs.put(trackerItemDTO.getThreadId(), tiThread);
    }

    private void loadThread(final String forum, final Boolean isNews, final Integer threadId) {
        try {
            CommentsDTO comments = parser.loadThread(forum, isNews, threadId);
            TabItem tabItem = threadTabs.get(threadId);
            if (tabItem.getControl() != null)
                tabItem.getControl().dispose();

            Composite composite = new Composite(tabFolder, SWT.NONE);
            tabItem.setControl(composite);
            composite.setLayout(new GridLayout(1, false));

            ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);

            ToolItem tiRefresh = new ToolItem(toolBar, SWT.NONE);
            tiRefresh.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    loadThread(forum, isNews, threadId);
                }
            });
            tiRefresh.setText("Обновить");

            Table commentsTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
            commentsTable.setLinesVisible(true);
            commentsTable.setHeaderVisible(true);
            commentsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


            TableColumn tableColumn = new TableColumn(commentsTable, SWT.NONE);
            tableColumn.setWidth(100);
            tableColumn.setText("Автор");

            TableColumn tableColumn_1 = new TableColumn(commentsTable, SWT.NONE);
            tableColumn_1.setWidth(200);
            tableColumn_1.setText("Заголовок");

            TableColumn tableColumn_2 = new TableColumn(commentsTable, SWT.NONE);
            tableColumn_2.setWidth(300);
            tableColumn_2.setText("Сообщение");

            int maxHeight = 0;

            for (Comment comment : comments.getComments()) {
                TableItem tableItem = new TableItem(commentsTable, SWT.NORMAL);
                TableEditor editor = new TableEditor(commentsTable);
                Browser browser = new Browser(commentsTable, SWT.NONE);
                browser.setText(comment.getProcessedMessage());
                editor.horizontalAlignment = SWT.FILL;
                editor.verticalAlignment = SWT.FILL;
                editor.grabHorizontal = true;
                editor.grabVertical = true;
                editor.setEditor(browser, tableItem, 2);
                browser.pack();
                if (browser.getSize().y > maxHeight)
                    maxHeight = browser.getSize().y;
                tableItem.setText(new String[]{comment.getAuthor().getNick(), comment.getTitle()});
            }

            final int finalMaxHeight = maxHeight;
            commentsTable.addListener(SWT.MeasureItem, new Listener() {
                public void handleEvent(Event event) {
                    event.height = finalMaxHeight;
                }
            });

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

}
