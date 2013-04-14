package dk.rootuser.daa.ui.gui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.sorters.SortOrder;
import dk.rootuser.daa.sorters.TitleSorter;

/**
 * GUI Class generated using Netbeans
 * @author martin
 *
 */

public class MainWindow extends javax.swing.JFrame {

	private static final long serialVersionUID = 241530035487560475L;
	private DefaultListModel<String> fileListModel;
	private ArrayList<Resource> resources;
	private DataCiteParser parser;
	private DefaultComboBoxModel<String> sortOrderModel;
	private SortOrder titleSortOrder = SortOrder.NONE;
	
	private HashMap<String, Resource> fileNameResourceMapping;
	
	private MyModel tableModel;
	
	/**
	 * Generates the MainWindow form	
	 */
    public MainWindow() {
    	fileNameResourceMapping = new HashMap<String, Resource>();
    	fileListModel = new DefaultListModel<String>();
    	resources = new ArrayList<Resource>();
    	sortOrderModel = new DefaultComboBoxModel<String>();
    	
    	sortOrderModel.addElement("Ascending");
    	sortOrderModel.addElement("Descending");
    	sortOrderModel.addElement("None");    
    	
    	tableModel = new MyModel();
    	
    	try {
			parser = DataCiteParser.getInstance();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
        initComponents();
    }

    private void initComponents() {
        fileLoadingPanel = new javax.swing.JPanel();
        fileLoadingButton = new javax.swing.JButton();
        sortOrder = new javax.swing.JComboBox<String>(sortOrderModel);
        sortOrder.setSelectedIndex(2);
        sortOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch(sortOrder.getSelectedIndex()) {
				case 0:
					titleSortOrder = SortOrder.ASC;
					break;
					
				case 1:
					titleSortOrder = SortOrder.DESC;
					break;
					
				case 2:
					titleSortOrder = SortOrder.NONE;
					break;
				}
				
				if(titleSortOrder != SortOrder.NONE)
					Collections.sort(resources, new TitleSorter(titleSortOrder));
				
				tableModel.fireTableDataChanged();
			}
		});
        fileListScrollPane = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList<String>(fileListModel);
        fileList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				ListSelectionModel lsm = fileList.getSelectionModel();
				if(!lsm.isSelectionEmpty() && arg0.getValueIsAdjusting()) {
					new DataCiteMetaDataForm(fileNameResourceMapping.get(fileListModel.get(lsm.getMinSelectionIndex()))).setVisible(true);
				}
			}
		});
        dataPanel = new javax.swing.JPanel();
        dataScrollPane = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        dataTable.setModel(tableModel);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DataCite Title and Description previewer");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);

        fileLoadingPanel.setBackground(new java.awt.Color(242, 240, 241));

        fileLoadingButton.setText("Load File");
        fileLoadingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(MainWindow.this, "Choose a DataCite MetaDataFile");
				fd.setFilenameFilter(new FilenameFilter() {
					@Override
					public boolean accept(File f, String name) {
						return name.contains(".xml");
					}
				});
				fd.setVisible(true);
				
				if(fd.getFile() != null) {
					fileListModel.addElement(fd.getFile());
					
					for(File f : fd.getFiles()) {
						try {
							Resource r = parser.parse(f.getAbsolutePath());
							fileNameResourceMapping.put(fd.getFile(), r);
							tableModel.addElement(r);
						} catch (SAXException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(titleSortOrder != SortOrder.NONE)
						Collections.sort(resources, new TitleSorter(titleSortOrder));
				}
			}
		});

        javax.swing.GroupLayout fileLoadingPanelLayout = new javax.swing.GroupLayout(fileLoadingPanel);
        fileLoadingPanel.setLayout(fileLoadingPanelLayout);
        fileLoadingPanelLayout.setHorizontalGroup(
            fileLoadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileLoadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileLoadingButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        fileLoadingPanelLayout.setVerticalGroup(
            fileLoadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileLoadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fileLoadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLoadingButton)
                    .addComponent(sortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileListScrollPane.setViewportView(fileList);
        dataScrollPane.setViewportView(dataTable);

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileLoadingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fileListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fileLoadingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileListScrollPane)))
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    
    private javax.swing.JPanel dataPanel;
    private javax.swing.JScrollPane dataScrollPane;
    private javax.swing.JTable dataTable;
    private javax.swing.JList<String> fileList;
    private javax.swing.JScrollPane fileListScrollPane;
    private javax.swing.JButton fileLoadingButton;
    private javax.swing.JPanel fileLoadingPanel;
    private javax.swing.JComboBox<String> sortOrder;
    
    private class MyModel extends AbstractTableModel {

		private static final long serialVersionUID = -7696425095205294908L;
		private final String[] columnNames = {"Title", "Description"};

        @Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		public void addElement(Resource e) {
        	resources.add(e);
            fireTableRowsInserted(resources.size()-1, resources.size()-1);
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return resources.size();
        }

        @Override
        public String getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex) {
                case 0: return resources.get(rowIndex).getTitles().get(0).getTitle();
                case 1: return resources.get(rowIndex).getDescriptions().get(0).getDescription();
            }
            return null;
        }

    }
}
