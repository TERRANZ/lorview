package ru.terra.lorview.parser.impl;

import flexjson.JSONDeserializer;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import ru.terra.lorview.Constants;
import ru.terra.lorview.dto.CommentsDTO;
import ru.terra.lorview.dto.ForumItemDTO;
import ru.terra.lorview.dto.TrackerItemDTO;
import ru.terra.lorview.parser.Parser;
import ru.terra.lorview.parser.ParserException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HtmlUnitParserImpl implements Parser {
    private HtmlUnitDriver driver;
    private Logger logger = Logger.getLogger(this.getClass());

    public HtmlUnitParserImpl() {
    }

    @Override
    public void start() throws ParserException {
        logger.info("Starting...");
        driver = new HtmlUnitDriver();
    }

    @Override
    public void close() throws ParserException {
        logger.info("Close");
        driver.close();
    }

    @Override
    public CommentsDTO loadNews() throws ParserException {
        return null;
    }

    @Override
    public CommentsDTO loadThread(String forum, Boolean isNews, Integer threadId) throws ParserException {
        try {
            StringBuilder url = new StringBuilder();
            url.append(Constants.LOR_URL);

            if (isNews)
                url.append(Constants.NEWS);
            else
                url.append(Constants.FORUMS);

            url.append(forum);
            url.append("/");
            url.append(threadId.toString());
            url.append("/comments");

            URL google = new URL(url.toString());
            return new JSONDeserializer<CommentsDTO>().deserialize(new InputStreamReader(google.openStream(), Charset.forName("UTF-8")), CommentsDTO.class);
        } catch (IOException ex) {
            logger.error("Unable to load thread", ex);
            throw new ParserException("Unable to load thread", ex);
        }
    }

    @Override
    public List<ForumItemDTO> loadForum(String forum, Boolean isNews) throws ParserException {
        logger.info("Loading forum " + forum);
        List<ForumItemDTO> ret = new ArrayList<>();
        try {
            StringBuilder url = new StringBuilder();
            url.append(Constants.LOR_URL);

            if (isNews)
                url.append(Constants.NEWS);
            else
                url.append(Constants.FORUMS);

            url.append(forum);

            driver.navigate().to(url.toString());

            for (WebElement webElement : driver.findElementByXPath("//*[@id=\"bd\"]/div[2]/table/tbody").findElements(By.cssSelector("tr"))) {
                String title = webElement.findElement(By.cssSelector("a")).getText();
                String link = webElement.findElement(By.cssSelector("a")).getAttribute("href");
                Integer threadId = Integer.parseInt(link.substring(link.lastIndexOf("/") + 1, link.indexOf("?") < 0 ? link.length() : link.indexOf("?")));
                String lastMessageDate = webElement.findElement(By.className("dateinterval")).getText();
                String responses = webElement.findElement(By.className("numbers")).getText();
                ForumItemDTO item = new ForumItemDTO(title, lastMessageDate, responses, threadId);
                ret.add(item);
            }

        } catch (Exception e) {
            logger.error("Unable to load forum", e);
            throw new ParserException("Unable to load forum", e);
        }
        return ret;
    }

    @Override
    public List<TrackerItemDTO> loadTracker() throws ParserException {
        logger.info("Loading tracker");
        List<TrackerItemDTO> ret = new ArrayList<>();
        try {
            String url = Constants.LOR_URL + Constants.TRACKER;
            driver.navigate().to(url);

            for (WebElement webElement : driver.findElementByXPath("//*[@id=\"bd\"]/div[2]/table/tbody").findElements(By.cssSelector("tr"))) {
                String group = webElement.findElement(By.className("hideon-tablet")).getText();
                String title = webElement.findElements(By.cssSelector("td")).get(1).findElement(By.cssSelector("a")).getText();
                String link = webElement.findElements(By.cssSelector("td")).get(1).findElement(By.cssSelector("a")).getAttribute("href");
                link = link.substring(0, link.lastIndexOf("?"));
                Integer threadId = 0;
                try {
                    threadId = Integer.parseInt(link.substring(link.lastIndexOf("/") + 1, link.length()));
                } catch (NumberFormatException e) {
                    link = link.substring(0, link.lastIndexOf("/"));
                    threadId = Integer.parseInt(link.substring(link.lastIndexOf("/") + 1, link.length()));
                }
                String forum = link.substring(link.lastIndexOf("org.ru/") + 7, link.lastIndexOf("/"));
                Boolean isNews = forum.contains("news");
                forum = forum.substring(forum.indexOf("/") + 1, forum.length());
                String lastMessage = webElement.findElement(By.className("dateinterval")).getText();
                String resps = webElement.findElement(By.className("numbers")).getText();
                TrackerItemDTO item = new TrackerItemDTO(group, forum, title, lastMessage, resps, threadId, isNews);
                ret.add(item);
            }

        } catch (Exception e) {
            logger.error("Unable to load tracker", e);
            throw new ParserException("Unable to load tracker", e);
        }
        return ret;
    }
}
