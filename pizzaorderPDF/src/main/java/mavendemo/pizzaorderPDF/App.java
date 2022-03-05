package mavendemo.pizzaorderPDF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class App 
{
	static String[] columns = {"ID", "OrderName", "QTY", "Price","Total"};
	Scanner sc = new Scanner(System.in);
	 String id,name;
	 int Qty,Rupees,Total;
	

	public pizzaorder addorder() {
		System.out.println("Enter Order no : ");
		id=sc.next();
		System.out.println("Enter Order Name : ");
		name=sc.next();
		System.out.println("Enter Quantity : ");
		Qty=sc.nextInt();
		System.out.println("Enter Price :");
		
		Rupees=sc.nextInt();
		pizzaorder po = new pizzaorder();
		Total=Qty*Rupees;
		po.setOrder_id(id);
		po.setOrder_name(name);
		po.setQty(Qty);
		po.setRupees(Rupees);
		po.setTotal(Total);
//		a1.add(po);
		return po;
	}
	public void XlsUpdate(ArrayList<pizzaorder> a1) throws IOException {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook(); 
        CreationHelper createHelper = workbook.getCreationHelper();
        // Create a Sheet
        Sheet sheet = workbook.createSheet("Pizza");
        // Create a Row
        Row headerRow = sheet.createRow(0);
        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(pizzaorder pi: a1) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0)
                    .setCellValue(pi.getOrder_id());
            row.createCell(1)
                    .setCellValue(pi.getOrder_name());
            row.createCell(2)
            .setCellValue(pi.getQty());
            row.createCell(3)
            .setCellValue(pi.getRupees());
            row.createCell(4)
            .setCellValue(pi.getTotal());
        }
		// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("src/main/resources/file.xls");
        workbook.write(fileOut);
        fileOut.close();
        // Closing the workbook
        workbook.close();
	}
	public void dispayorders(ArrayList<pizzaorder> a1) {
		if (a1.size()==0) {
			System.out.println("No Records Found !!");
		}
		else
		{
			System.out.println();
			System.out.println("Order_ID"+"\t "+"Order_Name"+"\t"+"QTY"+"\t"+"\t"+"Price"+"\t"+"\t"+"Total");
			System.out.println();
			for (pizzaorder pi : a1) {
				System.out.println(pi.getOrder_id()+"\t "+"\t "+pi.getOrder_name()+"\t"+"\t "+pi.getQty()+"\t"+"\t "+pi.getRupees()+"\t"+"\t "+pi.getTotal());
			}
			System.out.println();
		}
	}
	public void searchorder(ArrayList<pizzaorder> a1) {
		// TODO Auto-generated method stub
		int count=0;
		System.out.println("Enter Order Number");
		String in=sc.next();
		for (pizzaorder pi : a1) {
			if (pi.getOrder_id().equals(in)) {
				System.out.println("Order_ID"+"\t "+"Order_Name"+"\t"+"QTY"+"\t"+"\t"+"Price"+"\t"+"\t"+"Total");
				break; //For printing heading
			}
		}
		for (pizzaorder pi : a1) {
			if (pi.getOrder_id().equals(in)) {
				System.out.println(pi.getOrder_id()+"\t "+"\t "+pi.getOrder_name()+"\t"+"\t "+pi.getQty()+"\t"+"\t "+pi.getRupees()+"\t"+"\t "+pi.getTotal());
				count++;
			}
		}
		if (count==0) {
			System.out.println("No order Found !!");
		}
		
	}
	public void deleteorder(ArrayList<pizzaorder> a1) {
		System.out.println("Enter order number you want to delete");
		String in=sc.next();
		int count=0;
		for (pizzaorder po : a1) {
			if (po.getOrder_id().equals(in)) {
				a1.remove(po);
				System.out.println();
				System.out.println("Order Deleted Succesfully !!");
				System.out.println();
				count++;
				break;
			}
		}
		if (count==0) {
			System.out.println("Record Does not Exist !!");
		}
		
		
	}
	public void pdfUpdate(ArrayList<pizzaorder> a1) throws InvalidPasswordException, IOException {
        File file = new File("/home/v2stech/Documents/a.pdf");  
        PDDocument doc = PDDocument.load(file);  
        PDPage page = doc.getPage(0);  
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);    
        //Begin the Content stream   
        contentStream.beginText();   
    
        //Setting the font to the Content stream    
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);  
        //Setting the leading  
        contentStream.setLeading(14.5f);  

        //Setting the position for the line   
        contentStream.newLineAtOffset(25, 700);  

        contentStream.showText("ID"+"  "+"Order_Name"+"  "+"QTY"+"  "+"Price"+"  "+"Total");
        contentStream.newLine(); 
        //Adding text in the form of string
        for (pizzaorder pi : a1) {
        	contentStream.showText(pi.getOrder_id()+"          "+pi.getOrder_name()+"          "+pi.getQty()+"          "+pi.getRupees()+"          "+pi.getTotal());  
            contentStream.newLine(); 
		}
        contentStream.endText();  

        System.out.println("Multiple Text Content is added in the PDF Document.");  

        //Closing the content stream  
        contentStream.close();  
        
        //Saving the document  
        doc.save(new File("/home/v2stech/Documents/a.pdf"));  
        //Closing the document  
        doc.close(); 
		
	}
}
