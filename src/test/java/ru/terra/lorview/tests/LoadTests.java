package ru.terra.lorview.tests;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;
import ru.terra.lorview.dto.ForumItemDTO;
import ru.terra.lorview.dto.TrackerItemDTO;
import ru.terra.lorview.parser.Parser;
import ru.terra.lorview.parser.ParserException;
import ru.terra.lorview.parser.ParsersFactory;

import java.text.ParseException;

/**
 * Date: 11.04.14
 * Time: 13:32
 */

public class LoadTests extends TestCase {

    private Logger logger = Logger.getLogger(this.getClass());

    public LoadTests() {
        super("Load tests");
        BasicConfigurator.configure();
    }

    @Test
    public void test1() throws ParserException, ParseException {
        Parser parser = ParsersFactory.getParser();
        parser.start();
        for (ForumItemDTO forumItemDTO : parser.loadForum("general", false)) {
            logger.info("Loaded " + forumItemDTO);
        }
        for (TrackerItemDTO trackerItemDTO : parser.loadTracker()) {
            logger.info("Loaded " + trackerItemDTO);
        }
        parser.close();
    }
}
