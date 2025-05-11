package assignments.homework.hw1;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {

    public static void main(String[] args) throws IOException, InterruptedException {

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.touro.edu/");
        urls.add("https://www.touro.edu/about/");
        urls.add("https://www.touro.edu/admissions/");
        urls.add("https://www.touro.edu/directory/");
        urls.add("https://www.touro.edu/news--events/");

        ArrayList<String> emails = new ArrayList<>(), phoneNumbers = new ArrayList<>(), internalUrls = new ArrayList<>(), externalUrls = new ArrayList<>();

        Pattern combinedPattern = Pattern.compile("(?<EMAIL>[\\w._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})" + "|(?<PHONE>\\d?\\d{3}-\\d{3}-\\d{4})" + "|href\\s*=\\s*\"(?<INTERNAL>https?://[^\"\\s]*touro\\.edu[^\"\\s]*)\"" + "|href\\s*=\\s*\"(?<EXTERNAL>https?://(?![^\"\\s]*touro\\.edu)[^\"\\s]*)\"");

        long startTime = 0;
        for (int i = 0; i < urls.size(); i++) {
            if (i > 0) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime < 5000) {
                    Thread.sleep(5000 - elapsedTime);
                }
            }
            URL url = new URL(urls.get(i));
            Scanner scanner = new Scanner(url.openStream());
            String content = scanner.useDelimiter("\\A").next();
            startTime = System.currentTimeMillis();

            Matcher matcher = combinedPattern.matcher(content);
            while (matcher.find()) {
                if (matcher.group("EMAIL") != null) {
                    emails.add(matcher.group("EMAIL"));
                }
                if (matcher.group("PHONE") != null) {
                    phoneNumbers.add(matcher.group("PHONE"));
                }
                if (matcher.group("INTERNAL") != null) {
                    internalUrls.add(matcher.group("INTERNAL"));
                }
                if (matcher.group("EXTERNAL") != null) {
                    externalUrls.add(matcher.group("EXTERNAL"));
                }
            }
        }

        System.out.println("Emails found:");
        for (String email : emails) {
            System.out.println("\n" + email);
        }

        System.out.println("\nPhone Numbers found:");
        for (String phone : phoneNumbers) {
            System.out.println("\n" + phone);
        }

        System.out.println("\nInternal URLs found:");
        for (String internalUrl : internalUrls) {
            System.out.println("\n" + internalUrl);
        }

        System.out.println("\nExternal URLs found:");
        for (String externalUrl : externalUrls) {
            System.out.println("\n" + externalUrl);
        }

    }
}