package com.idugalic.commandside.blog.web;

import java.util.Date;

import com.idugalic.common.blog.model.BlogPostCategory;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * A web request data transfer object for {@link CreateBlogPostCommand}
 * 
 * @author idugalic
 *
 */
public class CreateBlogPostRequest {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "rawContent is mandatory")
    @NotBlank(message = "rawContent is mandatory")
    private String rawContent;
    @NotNull(message = "PublicSlug is mandatory")
    @NotBlank(message = "PublicSlug is mandatory")
    private String publicSlug;
    @NotNull
    private Boolean draft;
    @NotNull
    private Boolean broadcast;
    @Future(message = "'Publish at' date must be in the future")
    @NotNull
    private Date publishAt;
    @NotNull
    private BlogPostCategory category;

    public CreateBlogPostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getPublicSlug() {
        return publicSlug;
    }

    public void setPublicSlug(String publicSlug) {
        this.publicSlug = publicSlug;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Boolean getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Boolean broadcast) {
        this.broadcast = broadcast;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public BlogPostCategory getCategory() {
        return category;
    }

    public void setCategory(BlogPostCategory category) {
        this.category = category;
    }

}
