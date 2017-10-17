package com.laba.booklibrary.service.books.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Blueprint for a book object. Contain all fields to work with a book.
 */
@Entity
@Table(name = "books")
public class BookTO implements Serializable {
    private long id;
    private String title;
    private String author;
    private int publishYear;
    private String description;
    private int count;
    private Set<BookOnHoldTO> bookOnHoldTOs = new HashSet<BookOnHoldTO>(0);

    @Override
    public String toString() {
        return "BookTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishYear=" + publishYear +
                '}';
    }

    public BookTO(){}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fk_bookTO", cascade = CascadeType.ALL)
    public Set<BookOnHoldTO> getBookOnHoldTOs(){
        return this.bookOnHoldTOs;
    }

    public void setBookOnHoldTOs(Set<BookOnHoldTO> bookOnHoldTOs) {
        this.bookOnHoldTOs = bookOnHoldTOs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "publish_year")
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
