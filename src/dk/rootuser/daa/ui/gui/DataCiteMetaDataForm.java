package dk.rootuser.daa.ui.gui;

import dk.rootuser.daa.pojos.datacite.Description;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.Title;

public class DataCiteMetaDataForm extends javax.swing.JFrame {

	private static final long serialVersionUID = 1613879505707690983L;
	
	private Resource r;
	
	/**
     * Creates new form DataCiteMetaDataForm
     */
    public DataCiteMetaDataForm(Resource r) {
    	this.r = r;
        initComponents();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        titles = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptions = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(r.getIdentifier().getIdentifier() + " - " + r.getIdentifier().getIdentifierType());
        setPreferredSize(new java.awt.Dimension(800, 600));

        titles.setEditable(false);
        titles.setLineWrap(true);
        titles.setWrapStyleWord(true);
        
        String titleText = "";
        
        for(Title t : r.getTitles()) {
        	titleText += t.getTitle() + (t.getTitleType() != null && t.getTitleType().length() > 0 ? " (" + t.getTitleType() + ")" : "") + "\n\n";
        }
        
        titles.setText(titleText);
        titles.setCaretPosition(0);
        
        jScrollPane1.setViewportView(titles);

        descriptions.setEditable(false);
        descriptions.setLineWrap(true);
        descriptions.setWrapStyleWord(true);
        
        String descriptionText = "";
        
        for(Description d : r.getDescriptions()) {
        	descriptionText += (d.getDescriptionType() != null && d.getDescriptionType().length() > 0 ? d.getDescriptionType() + "\n" : "") + d.getDescription() + "\n\n";
        }
        
        descriptions.setText(descriptionText);
        descriptions.setCaretPosition(0);
        
        jScrollPane2.setViewportView(descriptions);

        jLabel1.setText("Titles");

        jLabel2.setText("Descriptions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)))
        );

        pack();
    }

    private javax.swing.JTextArea descriptions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea titles;
}
