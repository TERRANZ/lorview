package ru.terra.lorview.dto;

/**
 * Date: 11.04.14
 * Time: 13:36
 */
public class ForumItemDTO {
    private String title, lastMessageDate, responses;
    private Integer threadId;

    public ForumItemDTO(String title, String lastMessageDate, String responses, Integer threadId) {
        this.title = title;
        this.lastMessageDate = lastMessageDate;
        this.responses = responses;
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Override
    public String toString() {
        return "ForumItemDTO{" +
                "title='" + title + '\'' +
                ", lastMessageDate='" + lastMessageDate + '\'' +
                ", responses='" + responses + '\'' +
                ", threadId=" + threadId +
                '}';
    }
}
