package ru.terra.lorview.parser.impl;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import ru.terra.lorview.Constants;
import ru.terra.lorview.dto.ForumItemDTO;
import ru.terra.lorview.dto.MessageDTO;
import ru.terra.lorview.parser.Parser;
import ru.terra.lorview.parser.ParserException;

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
    public List<MessageDTO> loadNews() throws ParserException {
        return null;
    }

    @Override
    public List<MessageDTO> loadThread(Integer threadId) throws ParserException {
        return null;
    }

    @Override
    public List<ForumItemDTO> loadForum(String forum) throws ParserException {
        logger.info("Loading forum " + forum);
        List<ForumItemDTO> ret = new ArrayList<>();
        try {
            String url = Constants.LOR_URL + Constants.FORUMS + forum;
            driver.navigate().to(url);

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
            throw new ParserException("Unable to load forum", e);
        }
        return ret;
    }

    @Override
    public List<MessageDTO> loadTracker() throws ParserException {
        return null;
    }
}
