package ru.terra.lorview.parser;

import ru.terra.lorview.dto.ForumItemDTO;
import ru.terra.lorview.dto.MessageDTO;

import java.util.List;

public interface Parser {
    public void start() throws ParserException;
    public void close() throws ParserException;

    public List<MessageDTO> loadNews() throws ParserException;

    public List<MessageDTO> loadThread(Integer threadId) throws ParserException;

    public List<ForumItemDTO> loadForum(String forum) throws ParserException;

    public List<MessageDTO> loadTracker() throws ParserException;
}
