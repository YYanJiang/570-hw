package lab;

public class BuzzFizz {
	public static void main(String args[]){
		buzzFizz();
	}
	
	public static void buzzFizz(){
        for (int i = 74; i <= 291; i++){
		    if ( i % 5 == 0 && i % 3 == 0) {
			    System.out.print("BuzzFizz");
			} else {
			    if (i % 3 == 0){
					System.out.print("Buzz");
				} else if ( i % 5 == 0 ) {
					System.out.print("Fizz");
				} else {
					System.out.print(i);
				}
			}
			System.out.println();
		}
    }
}
