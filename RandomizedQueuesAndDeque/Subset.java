public class Subset {
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);

       RandomizedQueue<String> que = new RandomizedQueue<String>();
       while (!StdIn.isEmpty()) { 
           String s = StdIn.readString();
           que.enqueue(s);
       }

       while (n-- > 0) {
           System.out.println(que.dequeue());
       }
   }
}
