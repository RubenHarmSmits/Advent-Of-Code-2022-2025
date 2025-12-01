public class UserDataProcessor {

    public void processUsers(String[] users) {
        for (String user : users) {
            processUser(user);
        }
    }

    private void processUser(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Null value encountered in arguments");
        }
        if (isEmail(input)) {
            System.out.println("Email: " + input);
        } else {
            handleNonEmailInput(input);
        }
    }

    private boolean isEmail(String input) {
        return input.contains("@");
    }

    private void handleNonEmailInput(String input) {
        try {
            int number = Integer.parseInt(input);
            System.out.println("Number: " + (number * 2));
        } catch (NumberFormatException e) {
            System.out.println("Other: " + input);
        }
    }
}
