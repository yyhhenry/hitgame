import java.util.Scanner;
public class Ghit_main {
	public static Scanner cin=new Scanner(System.in);
    public static void main(String[] args){
    	try {
			new DrawSee("map.hg");
		} catch (Exception e) {
			System.out.print("°´Enter¼üÍË³ö...");
			cin.nextLine();
		}
    }
}