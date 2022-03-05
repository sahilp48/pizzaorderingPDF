package mavendemo.pizzaorderPDF;

import java.util.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class orderingsystem {
	
	public static void main(String[] args) throws IOException {
		int a = 1, choice = 0;
		Scanner sc = new Scanner(System.in);
		App app = new App();
		ArrayList<pizzaorder> a1=new ArrayList<pizzaorder>();
		while (a == 1) {
			System.out.println();
			System.out.print(
					"Enter Your Choice : \n 1.Add Order \n 2.View Order\n 3.Search Order\n 4.Delete Order\n 5.Exit\n ");
			choice = sc.nextInt();
			if (choice == 1) {
				pizzaorder data = app.addorder();
				a1.add(data);
				app.XlsUpdate(a1);	
				app.pdfUpdate(a1);
		    }
			if (choice == 2) {
				app.dispayorders(a1);			
			}
			if (choice==3) {
				app.searchorder(a1);
			}
			if (choice==4) {
				app.deleteorder(a1);
				app.XlsUpdate(a1);
				app.pdfUpdate(a1);
			}
			if (choice==5) {
				break;
			}
		}
	}
}
