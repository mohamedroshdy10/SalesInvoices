/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Views.SalesInvoiceFrame;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author compu magic
 */
public class HeaderTableViewModel extends AbstractTableModel{

    
    //private ArrayList<HeaderTable> ArrayHeader;
        private List<HeaderTable> invoicesList;

    public HeaderTableViewModel(List<HeaderTable> invoicesList) {
        this.invoicesList = invoicesList;
    }
    public List<HeaderTable> getInvoicesList() {
        return invoicesList;
    }

    String[] columns={"Inv No.","Inv Date","Customer Name","Inv Total:"};
    

    public HeaderTableViewModel(ArrayList<HeaderTable> ArrayHeader) {
        this.invoicesList = ArrayHeader;
    }
   
    
    @Override
    public int getRowCount() {
        return invoicesList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        HeaderTable headerTbl=invoicesList.get(rowIndex);
        switch(columnIndex)
        {
            case 0:
                return headerTbl.getInvoiceNumer();
            case 1:
                return SalesInvoiceFrame.dateFormat.format(headerTbl.getData());
            case 2:
                return headerTbl.getCustomerName();
            case 3:
                return headerTbl.InvocieTotal();
        }
        return "Test Obj";
    }
     @Override
    public String getColumnName(int Column)
    {
        return columns[Column];
        /*
        switch(Column)
        {
            case 0:
                return "No.";
            case 1:
                return  "Inv Date";
            case 2:
                return "Customer Name";
        }
        return "";
        */
    }
}
