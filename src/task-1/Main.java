public class Main {
    public static void main(String[] args){
        System.out.print("Argumenty komandnogo ryadka: ");
        if (args.length == 0) {
            System.out.println(" nema argumentov ");
        }else {
            for (String arg : args){
                System.out.print(arg +" ");
            }
            System.out.println(); 
        }
    }
}