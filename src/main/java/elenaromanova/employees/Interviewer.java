package elenaromanova.employees;

public interface Interviewer {
    // We can put fields on interfaces, but they are final
    String language = "java";
    // Default key allows method in interface has a real implementation
    // Available from Java 8
    // We can use abstract (normal) and default methods in interface
    default void setInterview(String candidateName) {
        System.out.println("Interview was set up");
    }

    default int getAmountOfInterviews() {
        return 10;
    }

    // We can create getter for the field, but not setter
    default String getLanguage() {
        return language;
    }
}
