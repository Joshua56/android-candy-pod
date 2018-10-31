package com.example.android.candypod.model.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "title", required = false)
    private String mTitle;

    @Element(name = "description", required = false)
    private String mDescription;

    @Element(name = "author", required = false)
    private String mITunesAuthor;

    @Element(name = "language", required = false)
    private String mLanguage;

    @ElementList(inline = true, name = "category", required = false)
    private List<Category> mCategories;

    @ElementList(inline = true, name = "item", required = false)
    private List<Item> mItemList;

    public Channel() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getITunesAuthor() {
        return mITunesAuthor;
    }

    public void setITunesAuthor(String iTunesAuthor) {
        mITunesAuthor = iTunesAuthor;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategory(List<Category> categories) {
        mCategories = categories;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
    }
}
