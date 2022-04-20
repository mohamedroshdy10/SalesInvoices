/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.LinesTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class LinesTableViewModel extends AbstractTableModel {

    private List<LinesTable> invoiceLines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    
    public LinesTableViewModel(List<LinesTable> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public List<LinesTable> getInvoiceLines() {
        return invoiceLines;
    }
    @Override
    public int getRowCount() {
        return invoiceLines.size();
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Item Name";
            case 1:
                return "Item Price";
            case 2:
                return "Count";
            case 3:
                return "Line Total";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LinesTable row = invoiceLines.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return row.getIteamName();
            case 1:
                return row.getItemPrice();
            case 2:
                return row.getItemCount();
            case 3:
                return row.getLinesIteamTotal();
            default:
                return "";
        }
        
    }
    
}
