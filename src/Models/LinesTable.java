/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
public class LinesTable {
    
   private String iteam;
   private double price;
   private int count;
   private HeaderTable invoiceHeader;

    public LinesTable(String iteam, double price, int count, HeaderTable invoiceHeader) {
        this.iteam = iteam;
        this.price = price;
        this.count = count;
        this.invoiceHeader = invoiceHeader;
    }

    public HeaderTable getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(HeaderTable invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public String getIteamName() {
        return iteam;
    }

    public void setIteamName(String iteam) {
        this.iteam = iteam;
    }

    public double getItemPrice() {
        return price;
    }

    public void setItemPrice(double price) {
        this.price = price;
    }

    public int getItemCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getLinesIteamTotal()
    {
        return price*count;
    }
    public String getDataAsCSV() {
        return "" + getInvoiceHeader().getInvoiceNumer()+ "," + getIteamName()+ "," + getItemPrice() + "," + getItemCount();
    }
}
