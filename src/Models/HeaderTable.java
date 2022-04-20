/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author compu magic
 */
public class HeaderTable {
    
    private int invoiceNumer;
    private Date data; 
   private String CustomerName;
   private ArrayList<LinesTable> invoicesItems;
  
    public HeaderTable(int invoiceNumer, Date data, String CustomerName) {
        this.invoiceNumer = invoiceNumer;
        this.data = data;
        this.CustomerName = CustomerName;
    }

    public int getInvoiceNumer() {
        return invoiceNumer;
    }

    public void setInvoiceNumer(int invoiceNumer) {
        this.invoiceNumer = invoiceNumer;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }
    /*
   public double InvocieTotal()
    {
      double inviceTotal=0;
      for(int i=0;i<invoicesItems.size();i++)
      {
          inviceTotal+=invoicesItems.get(i).getLinesIteamTotal();
      }
      return inviceTotal;
    }
*/

    
     public double InvocieTotal() {
        double total = 0.0;
        for (LinesTable line : getLines()) {
            total += line.getLinesIteamTotal();
        }
        return total;
    }

    public void setInvoicesItems(ArrayList<LinesTable> invoicesItems) {
        this.invoicesItems = invoicesItems;
    }
    public void setLines(ArrayList<LinesTable> lines) {
        this.invoicesItems = lines;
    }
     public ArrayList<LinesTable> getLines() {
        if (invoicesItems == null)
            invoicesItems = new ArrayList<>();  // lazy creation
        return invoicesItems;
    }
      public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoiceNumer()+ "," + df.format(getData()) + "," + getCustomerName();
    }
     public void addInvLine(LinesTable line) {
        getLines().add(line);
    }
     
}
