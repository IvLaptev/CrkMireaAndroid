package com.ivlaptev.myapplication.models.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleCompact {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("preview_text")
    @Expose
    private String previewText;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("last_updated_date")
    @Expose
    private String lastUpdatedDate;
    @SerializedName("content")
    @Expose
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
