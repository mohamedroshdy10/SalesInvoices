/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import Models.HeaderTable;
import Models.LinesTable;
import Views.SalesInvoiceFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Models.LinesTableViewModel;
import Views.HeaderTableDialog;
import Views.LinesTablesDialog;
import java.text.DateFormat;

/**
 *
 * @author compu magic
 */
public class ActionsButtons implements ActionListener, ListSelectionListener 
{
    
    private SalesInvoiceFrame frame;
    
    public ActionsButtons(SalesInvoiceFrame _frame)
    {
        frame=_frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand())
        {
            case "CreateNewInvoice":
            displayNewInvoiceDialog();
            break;
            case "createInvOK":
            createInvOK();
            break;
            case"invHeaderCancel":
                createInvCancel();
                break;
           case"DeleteInv":
            deleteInvoice();
             break;
             
             
             
             
             
            case "SaveLine":
           displayNewLineDialog();
            break;
            
            case "CancelLine":
                deleteLine();
                break;
            
            case "AddNewLine": // in Dialog
                createLineOK();
                break;
            case "RemoveLine": //inDialog
                CancelNewLine();
                break;
            
            
        }
    }
    
    
    private void displayNewInvoiceDialog()
    {
       frame.setHeaderTableDialog(new HeaderTableDialog(frame));
        frame.getHeaderTableDialog().setVisible(true);
        
     
    }
       private void createLineOK() 
       {
        String itemName = frame.getLinesTablesDialog().getItemNameField().getText();
        String itemCountStr = frame.getLinesTablesDialog().getItemCountField().getText();
        String itemPriceStr = frame.getLinesTablesDialog().getItemPriceField().getText();
        frame.getLinesTablesDialog().setVisible(false);
        frame.getLinesTablesDialog().dispose();
        frame.setLinesTablesDialog(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = frame.getTblInvoices().getSelectedRow();
        HeaderTable invoice = frame.getHeaderTableViewModels().getInvoicesList().get(headerIndex);
       // HeaderTable invoice = frame.getInvoicesList().get(headerIndex);
        LinesTable  invoiceLine = new LinesTable(itemName, itemPrice, itemCount, invoice);
        invoice.addInvLine(invoiceLine);
        frame.getLinesTableViewModel().fireTableDataChanged();
        frame.getLinesTableViewModel().fireTableDataChanged();
        frame.getLblInvocieTotal().setText("" + invoice.InvocieTotal());
    }
       
       
       
        private void createInvCancel() 
        {
        frame.getHeaderTableDialog().setVisible(false);
        frame.getHeaderTableDialog().dispose();
        frame.setHeaderTableDialog(null);
    }
        
        private void createInvOK()
        {
        String custName = frame.getHeaderTableDialog().getCustNameField().getText();
        String invDateStr = frame.getHeaderTableDialog().getInvDateField().getText();
        frame.getHeaderTableDialog().setVisible(false);
        frame.getHeaderTableDialog().dispose();
        frame.setHeaderTableDialog(null);
        try {
            
            Date invDate = SalesInvoiceFrame.dateFormat.parse(invDateStr);
            int invNum = getNextInvoiceNum();
            HeaderTable invoiceHeader = new HeaderTable(invNum, invDate,custName);
            frame.getInvoicesList().add(invoiceHeader);
            try {
                  frame.getHeaderTableViewModels().fireTableDataChanged();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } catch (ParseException ex) 
        {
            JOptionPane.showMessageDialog(frame, "plz,Enter A Valid Date Format To Add New Invoices", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
         private int getNextInvoiceNum() 
        {
        int max = 0;
        for (HeaderTable header : frame.getInvoicesList()) {
            if (header.getInvoiceNumer()> max) 
            {
                max = header.getInvoiceNumer();
            }
        }
        return max + 1;
    }
        
        //delete invoice
         private void deleteInvoice() {
        int invIndex = frame.getTblInvoices().getSelectedRow();
        frame.getHeaderTableViewModels().getInvoicesList().remove(invIndex);
        frame.getHeaderTableViewModels().fireTableDataChanged();
        frame.setLinesTableViewModel(new LinesTableViewModel(new ArrayList<LinesTable>()));
        frame.getTblIteams().setModel(frame.getLinesTableViewModel());
        frame.getLinesTableViewModel().fireTableDataChanged();
        frame.getTxtInvoiceCustomerName().setText("");
        frame.getTxtInvocieDate().setText("");
        frame.getLblInvoiceNumber().setText("");
        frame.getLblInvocieTotal().setText("");
    }
        
       
        
   
     private void SelectRowInHeaderTable() {
        int selectedRowIndex = frame.getTblInvoices().getSelectedRow();
        if (selectedRowIndex >= 0) 
        {
            HeaderTable row = frame.getHeaderTableViewModels().getInvoicesList().get(selectedRowIndex);
            frame.getTxtInvoiceCustomerName().setText(row.getCustomerName());
            frame.getTxtInvocieDate().setText(SalesInvoiceFrame.dateFormat.format(row.getData()));
            frame.getLblInvoiceNumber().setText("" + row.getInvoiceNumer());
            frame.getLblInvocieTotal().setText("" + row.InvocieTotal());
            ArrayList<LinesTable> lines = row.getLines();
            frame.setLinesTableViewModel(new LinesTableViewModel(lines));
            frame.getTblIteams().setModel(frame.getLinesTableViewModel());
            frame.getLinesTableViewModel().fireTableDataChanged();
            frame.getBtnSave().setVisible(true);
            frame.getBtnCancel().setVisible(true);
        }
    }
     

     @Override
    public void valueChanged(ListSelectionEvent e)
    {
       SelectRowInHeaderTable();
    }

    private void displayNewLineDialog() 
    {
       frame.setLinesTablesDialog(new LinesTablesDialog(frame));
        frame.getLinesTablesDialog().setVisible(true);
    }

    private void CancelNewLine() {
         frame.getLinesTablesDialog().setVisible(false);
        frame.getLinesTablesDialog().dispose();
        frame.setLinesTablesDialog(null);
    }

     private void deleteLine()
     {
        int lineIndex = frame.getTblIteams().getSelectedRow();
        LinesTable line = frame.getLinesTableViewModel().getInvoiceLines().get(lineIndex);
        frame.getLinesTableViewModel().getInvoiceLines().remove(lineIndex);
        frame.getLinesTableViewModel().fireTableDataChanged();
        frame.getLinesTableViewModel().fireTableDataChanged();
        frame.getLblInvocieTotal().setText("" + line.getInvoiceHeader().getData());
    }
}
