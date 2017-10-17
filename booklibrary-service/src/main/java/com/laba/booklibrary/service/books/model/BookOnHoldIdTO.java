package com.laba.booklibrary.service.books.model;

import com.laba.booklibrary.service.users.model.UserTO;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class BookOnHoldIdTO implements Serializable {
    private BookTO bookTO;
    private UserTO userTO;

    @ManyToOne
    public BookTO getBookTO(){
        return bookTO;
    }

    public void setBookTO(BookTO bookTO){
        this.bookTO = bookTO;
    }

    @ManyToOne
    public UserTO getUserTO(){
        return userTO;
    }

    public void setUserTO(UserTO userTO){
        this.userTO = userTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookOnHoldIdTO that = (BookOnHoldIdTO) o;

        if (bookTO != null ? !bookTO.equals(that.bookTO) : that.bookTO != null) return false;
        return userTO != null ? userTO.equals(that.userTO) : that.userTO == null;

    }

    @Override
    public int hashCode() {
        int result = bookTO != null ? bookTO.hashCode() : 0;
        result = 31 * result + (userTO != null ? userTO.hashCode() : 0);
        return result;
    }
}
