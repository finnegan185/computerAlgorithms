/* File:	BenchmarkReport.java
 * Author:	Zachary Finnegan
 * Date:	4/9/2020
 * Purpose:	This class reads the csv files created by BenchmarkSorts.java
 * 			and finds the averages and variance of coefficients for each 
 * 			array size and displays those values in a table.
 */

package computerAlgorithms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;



public class BenchmarkReport {
	//frame and table fields
	private JFrame frame; 
	private JTable table; 
	// Used to format the table data to only 2 significant figures.
	private static DecimalFormat df = new DecimalFormat("#0.00");
	  
    // Constructor 
    public BenchmarkReport(Object[][] data, String fileName) {
    	// Create frame, the title, close behavior and location
    	frame = new JFrame(); 
    	frame.setTitle("Benchmark Report of " + fileName);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLocationRelativeTo(null);
    	
    	//button creation
    	JButton theButton = new JButton("Choose File");
    	theButton.addActionListener((ActionEvent e) -> {
			if(e.getSource() == theButton) {
				fileChooser();
			}
		});
    	
    	//table creation
    	String[] header = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    	table = new JTable(data, header);
    	table.setFillsViewportHeight(true);
    	table.setFont(new Font("Serif", Font.PLAIN, 18));
    	table.setRowHeight(table.getRowHeight()+8);
    	JScrollPane sp = new JScrollPane(table);
    	sp.setBorder(BorderFactory.createEtchedBorder());

    	//Customizes column width and text alignment
    	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    	rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

    	TableColumn col = null;
    	for(int i = 0; i < 5; i++) {
    		col = table.getColumnModel().getColumn(i);
    		
    		switch(i) {
    			case 0: col.setPreferredWidth(20);
    				break;
    			case 1:col.setPreferredWidth(50);
					col.setCellRenderer(rightRenderer);
					break;
    			case 2:col.setPreferredWidth(30);
					col.setCellRenderer(rightRenderer);
					break;
    			case 3:col.setPreferredWidth(50);
    				col.setCellRenderer(rightRenderer);
    				break;
    			case 4:col.setPreferredWidth(30);
					col.setCellRenderer(rightRenderer);
					break;
    		}
    	}

    	// Adds everything above to panels and the panels to the frame.
    	// Sets Fram size and visibility
    	JPanel thePanel = new JPanel();
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.add(theButton);
    	thePanel.setLayout(new BorderLayout());
    	thePanel.add(sp, BorderLayout.CENTER);
    	thePanel.add(buttonPanel, BorderLayout.SOUTH);
    	frame.add(thePanel);
    	frame.setSize(420,350);
    	frame.setVisible(true);
    }
    
    public static void main(String[] args) { 
    	fileChooser();
    }
    	
    private static void fileChooser() {
    	JFileChooser fc = new JFileChooser(".");
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
			try (Scanner sc = new Scanner(fc.getSelectedFile())){
				Object[][] tableData = new Object[100][5];
				double[] sortCountArr = new double[500];
				double[] timeArr = new double[500];
				int count = 0;
				Scanner lineSc;
				while(sc.hasNext()) {
					String line = sc.nextLine();
					System.out.println(line);
					lineSc = new Scanner(line);
					lineSc.useDelimiter(",");
					tableData[count][0] = lineSc.next();
					int count2 = 0;
					while(lineSc.hasNext()) {
						sortCountArr[count2] = lineSc.nextInt();
						timeArr[count2] = lineSc.nextInt();
						count2++;
					}

					//Gets the mean and variance of the Critical Operation Count and adds 
					//it to the array holding the table data.
					DescriptiveStatistics stat = new DescriptiveStatistics(sortCountArr);
					tableData[count][1] = df.format(stat.getMean());
					tableData[count][2] = df.format((stat.getStandardDeviation() / stat.getMean())*100) + "%";
					
					//Gets the mean and variance of the execution times and add it to the array
					//holding the table data.
					stat = new DescriptiveStatistics(timeArr);
					tableData[count][3] = df.format(stat.getMean());
					tableData[count][4] = df.format((stat.getStandardDeviation() / stat.getMean())*100) + "%";
					
					count++;
					lineSc.close();
				}
				
				new BenchmarkReport(tableData, fc.getSelectedFile().getName());
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "File not found.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
    } 
}
