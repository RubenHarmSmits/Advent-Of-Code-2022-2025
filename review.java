public class UserDataProcessor {
    public void process(String[] args) {

        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if(s == null) {
                throw new IllegalArgumentException("Null value at index " + i);
            }
            if (s.contains("@")) {
                System.out.println("Email: " + s);
            } else {
                try {
                    int n = Integer.parseInt(s);
                    System.out.println("Number: " + (n * 2));
                } catch (Exception e) {
                    System.out.println("Other: " + s);
                }
            }

        }
    }
}