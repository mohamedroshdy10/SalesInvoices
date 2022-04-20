/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.HeaderTable;
import Models.HeaderTableViewModel;
import Models.LinesTable;
import Views.SalesInvoiceFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class ActionsMenu implements ActionListener{
private SalesInvoiceFrame SalFrame;
    

    public ActionsMenu(SalesInvoiceFrame salFrame)
    {
       this.SalFrame=salFrame;
    }
 

    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (e.getActionCommand())
        {
            case "LoadFile":
               LoadFile();
                break;
            case "SaveFile":
                SaveFile();
                break;
                
        }
        
        
    }

     private void LoadFile()   
     {
       JOptionPane.showMessageDialog(SalFrame, "Choose Your Header File,plz!", "Attension", JOptionPane.INFORMATION_MESSAGE);
       JFileChooser fc=new JFileChooser();
           int resulte= fc.showOpenDialog(SalFrame);
           try {
             
               
               
               
               
               if(resulte==JFileChooser.APPROVE_OPTION)
           {
               
              File headerFile=fc.getSelectedFile();
              Path headerPath= Paths.get(headerFile.getAbsolutePath());
              List<String> headersLines = Files.readAllLines(headerPath);
              ArrayList<HeaderTable>invoiceHeaders=new ArrayList<>();
              for(String headerLine:headersLines)
              {
                  String[]invHeader=headerLine.split(",");
                  String invNumber=invHeader[0];
                  String InvDate=invHeader[1];
                  String invCustName=invHeader[2];
                  int code=Integer.parseInt(invNumber);
                  Date inoviceDate= SalesInvoiceFrame.dateFormat.parse(InvDate);
                  HeaderTable tbl=new HeaderTable(code, inoviceDate, invCustName);
                  invoiceHeaders.add(tbl);
              }
            SalFrame.setInvoicesArray(invoiceHeaders);
         JOptionPane.showMessageDialog(SalFrame, "Enter Youe Lines File,plz!", "Attension", JOptionPane.INFORMATION_MESSAGE);
               // JFileChooser fcLines=new JFileChooser();
               // int LinesResulte=fcLines.showOpenDialog(SalFrame);
               resulte=fc.showOpenDialog(SalFrame);
                if(resulte==JFileChooser.APPROVE_OPTION)
                {
                    File LinesFile=fc.getSelectedFile();
                    Path LinesPath=Paths.get(LinesFile.getAbsolutePath());
                    List<String>LinesData=Files.readAllLines(LinesPath);
                    //ArrayList<LinesTable>invLinesTable=new ArrayList<>();
                    for(String LinesInv:LinesData)
                    {
                        String[]invLines=LinesInv.split(",");
                        String invNumStr = invLines[0];
                        String itemName = invLines[1];
                        String itemPriceStr = invLines[2];
                        String itemCountStr = invLines[3];
                        int invNum = Integer.parseInt(invNumStr);
                        double itemPrice = Double.parseDouble(itemPriceStr);
                        int itemCount = Integer.parseInt(itemCountStr);
                        HeaderTable headerObj = SalFrame.getHeaderObjectbyNum(invNum);
                        LinesTable invLine = new LinesTable(itemName, itemPrice, itemCount, headerObj);
                        headerObj.getLines().add(invLine);
                    }
                    
                    HeaderTableViewModel headerTableViewModel=new HeaderTableViewModel(invoiceHeaders);
                    SalFrame.setHeaderTableViewModels(headerTableViewModel);
                    SalFrame.getHeaderTableViewModels();
                    SalFrame.getTblInvoices().setModel(headerTableViewModel);
                    SalFrame.getTblIteams().setVisible(true);
                    

                }
          }
         } catch (IOException e) 
         {
             
             e.printStackTrace();
           System.out.println(e.fillInStackTrace().getMessage());

         }
           catch(ParseException e)
           {
               e.printStackTrace();
               System.out.println(e.fillInStackTrace().getMessage());
           }
    }
      private void SaveFile()
    {
        String headersTables = "";
        String LinesTable = "";
        for (HeaderTable header : SalFrame.getInvoicesList()) {
            headersTables += header.getDataAsCSV();
            headersTables += "\n";
            for (LinesTable line : header.getLines()) {
                LinesTable += line.getDataAsCSV();
                LinesTable += "\n";
            }
        }
        JOptionPane.showMessageDialog(SalFrame, "Save Header To!", "Attension", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(SalFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter hFW = new FileWriter(headerFile);
                hFW.write(headersTables);
                hFW.flush();
                hFW.close();

                JOptionPane.showMessageDialog(SalFrame, "Save Lines TO!", "Attension", JOptionPane.INFORMATION_MESSAGE);
                result = fileChooser.showSaveDialog(SalFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter lFW = new FileWriter(linesFile);
                    lFW.write(LinesTable);
                    lFW.flush();
                    lFW.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(SalFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(SalFrame, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
