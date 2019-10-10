package com.pnctraining.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TagCollection")
@TypeAlias("TagCollection")
public class TagEntity {

    @Id
    private String tagId;
    @Indexed(unique = true)
    private String tagName;

    public TagEntity(String tagName) {
        this.tagName = tagName.toLowerCase();
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.toLowerCase();
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
