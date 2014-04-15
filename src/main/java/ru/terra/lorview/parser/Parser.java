package ru.terra.lorview.parser;

import ru.terra.lorview.dto.CommentsDTO;
import ru.terra.lorview.dto.ForumItemDTO;
import ru.terra.lorview.dto.TrackerItemDTO;

import java.util.List;

public interface Parser {
    public void start() throws ParserException;

    public void close() throws ParserException;

    public CommentsDTO loadNews() throws ParserException;

    public CommentsDTO loadThread(String forum, Boolean isNews, Integer threadId) throws ParserException;

    public List<ForumItemDTO> loadForum(String forum, Boolean isNews) throws ParserException;

    public List<TrackerItemDTO> loadTracker() throws ParserException;
}
