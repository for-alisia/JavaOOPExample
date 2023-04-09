package elenaromanova;

public class ExceptionsExample {
    public static void main(String[] args) {
        String[] arr = {"one", "two", "three"};
        int num = 0;
        try {
            System.out.println(arr.length / num);
            System.out.println(arr[3]);
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException  e) {
            System.out.println("Our catch worked! " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Some generic exception");
        }
        // This line will be executed as exception is wrapped with try/catch
        System.out.println("You may it to the end!");
    }
}
