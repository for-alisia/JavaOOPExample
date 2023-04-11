package elenaromanova.datastore;

import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        Optional<String> optMsg = Optional.of("I am an optional string");
        System.out.println(optMsg); // Optional[I am an optional]
        String msg = null;
        Optional<String> optMsg2 = Optional.ofNullable(msg);
        System.out.println(optMsg2.orElse("Hi").toUpperCase());
        if (optMsg2.isPresent()) {
            System.out.println(optMsg2.get());
        }
        optMsg2.orElseThrow(() -> new RuntimeException("Nothing found"));
        optMsg2.orElseGet(() -> "pass here something");
        optMsg2.or(() -> Optional.of("Nothing here"));
        optMsg.filter(s -> s.length() > 3).orElse("Invalid");
    }
}
