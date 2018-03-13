package model;
import java.util.*;
public class Driver {
	public static void main(String args[]) {
		Core test = new Core();
		Scanner in = new Scanner(System.in);
		//while(true) {
			try {
				System.out.printf("%f\n", test.multiSimpleBlock("(4 +5)*(5+5)*5-6*2"));
			} catch (UnsupportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}
}
