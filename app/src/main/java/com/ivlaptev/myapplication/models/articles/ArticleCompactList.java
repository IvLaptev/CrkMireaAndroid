package com.ivlaptev.myapplication.models.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleCompactList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<ArticleCompact> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<ArticleCompact> getResults() {
        return results;
    }

    public void setResults(List<ArticleCompact> results) {
        this.results = results;
    }
}
