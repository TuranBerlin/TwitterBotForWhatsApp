import com.google.common.base.Converter;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.function.ToIntFunction;

public class MainApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Twitter Bot for WhatsApp\n" +
                "Created by: https://github.com/TuranBerlin\n" +
                "Based from (C#): https://github.com/kaniberkali/Whatsapp-icin-Twitter-botu\n" +
                "----------------------------------------------------------------\n" +
                "Drivers are getting ready...");

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        FirefoxDriver WhatsApp = new FirefoxDriver();
        FirefoxDriver Twitter = new FirefoxDriver(options);
        WhatsApp.navigate().to("https://web.whatsapp.com");
        System.out.println("Drivers are ready!");
        System.out.println("Waiting for WhatsApp login...");
        for (; ; ) {
            try {
                String control = WhatsApp.findElement(By.xpath("//html/body/div[1]/div[1]/div[1]/div[3]/div/header/div[1]/div/img")).getAttribute("src");
                try {
                    String control2 = WhatsApp.findElement(By.xpath("//html/body/div[1]/div[1]/div[1]/div[4]/div[1]/header/div[1]/div/img")).getAttribute("src");
                    System.out.println("Successfully selected!");
                    break;
                } catch (Exception e) {
                    System.out.println("Waiting for select a group or person...");
                }
            } catch (Exception e) {
                System.out.println("Waiting for WhatsApp login...");
            }
        }

        sendMessage(WhatsApp, "The program has been successfully started!");
        sendMessage(WhatsApp, "Type !info for details...");
        for (; ; ) {
            int count = WhatsApp.findElements(By.xpath("//span[@class='i0jNr selectable-text copyable-text']/span")).size();
            String message = WhatsApp.findElements(By.xpath("//span[@class='i0jNr selectable-text copyable-text']/span")).get(count - 1).getText();
            if (message.equals("!info")) {
                sendMessage(WhatsApp, "Welcome to Twitter Bot for WhatsApp\n" +
                        "Created by: https://github.com/TuranBerlin\n" +
                        "Based from (C#): https://github.com/kaniberkali/Whatsapp-icin-Twitter-botu\n" +
                        "----------------------------------------------------------------\n" +
                        "Commands:\n" +
                        "\n" +
                        "!username follows\n" +
                        "!username followers\n" +
                        "!username name\n" +
                        "\n" +
                        "!username tweet\n" +
                        "!username tweet <NUMBER_TO_SIX>\n" +
                        "\n" +
                        "!username comment\n" +
                        "\n" +
                        "!username retweet\n" +
                        "\n" +
                        "!username like\n");
            } else {
                try {

                    String[] data = message.replace("!", "").split(" ");

                    int datacount = 0;
                    if (data.length == 3)
                    {
                        datacount = Integer.parseInt(data[2]);
                    }
                    if (Twitter.getCurrentUrl() != "https://twitter.com/" + data[0])
                        Twitter.navigate().to("https://twitter.com/" + data[0]);
                    Thread.sleep(2000);
                    switch (data[1])
                    {
                        case "follows":
                            String follows = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[1]/div/div[4]/div[1]/a/span[1]/span")).get(datacount).getText();
                            sendMessage(WhatsApp, follows);
                            break;

                        case "followers":
                            String followers = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[1]/div/div[4]/div[2]/a/span[1]/span")).get(datacount).getText();
                            sendMessage(WhatsApp, followers);
                            break;

                        case "name":
                            String name = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div[1]/div/span[1]/span")).get(datacount).getText();
                            sendMessage(WhatsApp, name);
                            break;

                        case "tweet":
                            String twit = Twitter.findElements(By.xpath("//div[@class='css-901oao r-18jsvk2 r-37j5jr r-a023e6 r-16dba41 r-rjixqe r-bcqeeo r-bnwqim r-qvutc0']/span")).get(datacount).getText();
                            sendMessage(WhatsApp, twit);
                            break;

                        case "comment":
                            String comment = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div[1]/div/div/article/div/div/div/div[2]/div[2]/div[2]/div[3]/div")).get(datacount).getAttribute("aria-label");
                            String result = comment.split(",")[0];
                            sendMessage(WhatsApp, result);
                            break;

                        case "retweet":
                            String retweet = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div[1]/div/div/article/div/div/div/div[2]/div[2]/div[2]/div[3]/div")).get(datacount).getAttribute("aria-label");
                            String result2 = retweet.split(",")[1];
                            sendMessage(WhatsApp, result2);
                            break;

                        case "like":
                            String like = Twitter.findElements(By.xpath("//html/body/div/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div[1]/div/div/article/div/div/div/div[2]/div[2]/div[2]/div[3]/div")).get(datacount).getAttribute("aria-label");
                            String result3 = like.split(",")[2];
                            sendMessage(WhatsApp, result3);
                            break;
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    public static void sendMessage(FirefoxDriver WhatsApp, String message) {
        try {
            WhatsApp.findElement(By.xpath("//html/body/div[1]/div[1]/div[1]/div[4]/div[1]/footer/div[1]/div/div/div[2]/div[1]/div/div[2]")).sendKeys(message);
            Thread.sleep(200);
            WhatsApp.findElement(By.xpath("//html/body/div[1]/div[1]/div[1]/div[4]/div[1]/footer/div[1]/div/div/div[2]/div[2]/button")).click();
            System.out.println("----------------------------------------------------------------");
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("Message sending failed!");
        }
    }
}

