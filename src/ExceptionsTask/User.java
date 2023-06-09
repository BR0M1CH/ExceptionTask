package ExceptionsTask;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User {
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private long phoneNumber;
    private char gender;

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public char getGender() {
        return gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String lastName, String firstName, String middleName, LocalDate BirthDate, long phoneNumber, char gender) {
        setBirthDate(BirthDate);
        setGender(gender);
        setFirstName(firstName);
        setPhoneNumber(phoneNumber);
        setLastName(lastName);
        setMiddleName(middleName);
    }

    public static User parseUser(String input) throws InvalidFormatException {
        String[] tokens = input.split("\\s+");
        if (tokens.length != 6) {
            throw new InvalidFormatException("Invalid number of parameters");
        }
        String lastName = tokens[0];
        String firstName = tokens[1];
        String middleName = tokens[2];
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(tokens[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid birth date format");
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(tokens[4]);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Invalid phone number format");
        }
        char gender = tokens[5].charAt(0);
        if (gender != 'm' && gender != 'f') {
            throw new InvalidFormatException("Invalid gender format");
        }
        return new User(lastName, firstName, middleName, birthDate, phoneNumber, gender);
    }
    public static void writeUsersToFile(List<User> users) throws IOException {
        Map<String, List<User>> usersByLastName = users.stream()
                .collect(Collectors.groupingBy(User::getLastName));
        for (Map.Entry<String, List<User>> entry : usersByLastName.entrySet()) {
            String filename = entry.getKey() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (User user : entry.getValue()) {
                    writer.println(user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName() + " " +
                            user.getBirthDate() + " " + user.getPhoneNumber() + " " + user.getGender());
                }
            }
        }
    }

}
