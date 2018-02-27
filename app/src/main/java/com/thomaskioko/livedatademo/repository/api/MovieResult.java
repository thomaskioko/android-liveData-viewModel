package com.thomaskioko.livedatademo.repository.api;

import com.google.gson.annotations.SerializedName;
import com.thomaskioko.livedatademo.db.entity.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieResult {
    private Integer page;
    private List<Movie> results = new ArrayList<>();
    @SerializedName(value = "total_results")
    private Integer totalResults;
    @SerializedName(value = "total_pages")
    private Integer totalPages;

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The results
     */
    public List<Movie> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /**
     * @return The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}

