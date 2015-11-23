package ru.terra.lorview.dto;

public class TrackerItemDTO {
    private String group, title, lastMessage, reps, forum;
    private Integer threadId;
    private Boolean isNews = false;

    public TrackerItemDTO(String group, String forum, String title, String lastMessage, String reps, Integer threadId, Boolean isNews) {
        this.group = group;
        this.forum = forum;
        this.title = title;
        this.lastMessage = lastMessage;
        this.reps = reps;
        this.threadId = threadId;
        this.isNews = isNews;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Override
    public String toString() {
        return "TrackerItemDTO{" +
                "group='" + group + '\'' +
                ", title='" + title + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", reps='" + reps + '\'' +
                ", forum='" + forum + '\'' +
                ", threadId=" + threadId +
                '}';
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public Boolean isNews() {
        return isNews;
    }

    public void setIsNews(Boolean isNews) {
        this.isNews = isNews;
    }
}
