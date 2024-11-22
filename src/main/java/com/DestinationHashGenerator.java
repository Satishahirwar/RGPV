package com.bajaj;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DestinationHashGenerator {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar DestinationHashGenerator.jar <roll_number> <file_path>");
            return;
        }

        String rollNumber = args[0].toLowerCase().replaceAll("\\s", "");
        String filePath = args[1];

        try {
            String destinationValue = JsonParserUtil.getDestinationValue(filePath);
            String randomString = RandomStringGenerator.generateRandomString(8);
            String concatenatedValue = rollNumber + destinationValue + randomString;
            String hashValue = generateMD5Hash(concatenatedValue);

            System.out.println(hashValue + ";" + randomString);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
