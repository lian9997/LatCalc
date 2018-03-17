package model;
import java.util.*;
public class Driver {
	public static void main(String args[]) {
		Core test = new Core();
		Scanner in = new Scanner(System.in);
		while(true) {
			try {
				System.out.println("begin");
				System.out.printf("%s\n", test.process(in.nextLine()));
			} catch (UnsupportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
