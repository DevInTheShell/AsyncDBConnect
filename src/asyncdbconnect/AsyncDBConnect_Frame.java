package asyncdbconnect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class AsyncDBConnect_Frame extends javax.swing.JFrame
{
    private Timer timer;
    private Thread1 thread1;
    
    public class Thread1 implements Runnable
    {
        private final Thread worker;
        private boolean isRunning;
        
        public Thread1()
        {
            worker = new Thread(this);
        }
        
        public void start()
        {
            worker.start();
            isRunning = true;
        }
        
        public void interrupt()
        {
            worker.interrupt();
            isRunning = false;
        }
        
        @Override
        public void run()
        {
            DefaultTableModel model = (DefaultTableModel) users_Table.getModel();

            try
            {
                try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "DEVELOPER", "P@ssw0rd"))
                {
                    try (Statement statement = connection.createStatement())
                    {
                        try (ResultSet resultSet = statement.executeQuery("select * from users"))
                        {
                            while(resultSet.next())
                            {
                                if(!isRunning)
                                {
                                    break;
                                }
                                
                                model.addRow(new String[]
                                {
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4)
                                });
                            }
                        }
                    }
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex);
            }
            
            timer.stop();
            start_Button.setEnabled(true);
            stop_Button.setEnabled(false);
            clear_Button.setEnabled(true);
        }
    }
    
    public AsyncDBConnect_Frame()
    {
        initComponents();
        
        DefaultTableModel model = (DefaultTableModel) users_Table.getModel();
        if(model.getRowCount() > 0)
        {
            model.removeRow(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents()//GEN-BEGIN:initComponents
    {

        start_Button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        users_Table = new javax.swing.JTable();
        time_label = new javax.swing.JLabel();
        stop_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AsyncDBConnect");
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 600));

        start_Button.setText("Start");
        start_Button.setName("Start_Button"); // NOI18N
        start_Button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                start_Button_MouseClicked(evt);
            }
        });

        users_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null}
            },
            new String []
            {
                "ID", "First name", "Last name", "Date created"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        users_Table.setName(""); // NOI18N
        jScrollPane1.setViewportView(users_Table);
        users_Table.getAccessibleContext().setAccessibleName("");

        time_label.setText("0s");

        stop_Button.setText("Stop");
        stop_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        stop_Button.setEnabled(false);
        stop_Button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                stop_ButtonMouseClicked(evt);
            }
        });

        clear_Button.setText("Clear");
        clear_Button.setEnabled(false);
        clear_Button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                clear_Button_MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(start_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stop_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(time_label)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start_Button)
                    .addComponent(time_label)
                    .addComponent(stop_Button)
                    .addComponent(clear_Button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addContainerGap())
        );

        start_Button.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleName("");

        pack();
    }//GEN-END:initComponents

    private void start_Button_MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_start_Button_MouseClicked
    {//GEN-HEADEREND:event_start_Button_MouseClicked
        start_Button.setEnabled(false);
        stop_Button.setEnabled(true);
        time_label.setText(String.format("0s"));
        
        ActionListener taskPerformer = new ActionListener()
        {
            int time = 0;
            
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                time += 1;
                time_label.setText(String.format("%ds", time));
            }
        };
        timer = new Timer(1000, taskPerformer);
        timer.start();
        
        thread1 = new Thread1();
        thread1.start();
    }//GEN-LAST:event_start_Button_MouseClicked

    private void stop_ButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_stop_ButtonMouseClicked
    {//GEN-HEADEREND:event_stop_ButtonMouseClicked
        thread1.interrupt();
        timer.stop();
        start_Button.setEnabled(true);
        stop_Button.setEnabled(false);
        clear_Button.setEnabled(true);
    }//GEN-LAST:event_stop_ButtonMouseClicked

    private void clear_Button_MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clear_Button_MouseClicked
    {//GEN-HEADEREND:event_clear_Button_MouseClicked
        time_label.setText(String.format("0s"));
        
        DefaultTableModel model = (DefaultTableModel) users_Table.getModel();
        while(model.getRowCount() > 0)
        {
            model.removeRow(0);
        }
        
        clear_Button.setEnabled(false);
    }//GEN-LAST:event_clear_Button_MouseClicked

    public static void main(String args[])
    {
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AsyncDBConnect_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() ->
        {
            new AsyncDBConnect_Frame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clear_Button;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton start_Button;
    private javax.swing.JButton stop_Button;
    private javax.swing.JLabel time_label;
    private javax.swing.JTable users_Table;
    // End of variables declaration//GEN-END:variables
}
