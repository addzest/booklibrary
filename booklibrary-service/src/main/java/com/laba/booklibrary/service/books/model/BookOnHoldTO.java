package com.laba.booklibrary.service.books.model;

import com.laba.booklibrary.service.users.model.UserTO;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books_onhold")
@AssociationOverrides({
        @AssociationOverride(name = "pk.bookTO",
            joinColumns = @JoinColumn(name = "book_id")),
        @AssociationOverride(name = "pk.userTO",
            joinColumns = @JoinColumn(name = "user_id"))})
public class BookOnHoldTO implements Serializable{
    private BookOnHoldIdTO pk = new BookOnHoldIdTO();
    private String holdType;
    private boolean approved;

    public BookOnHoldTO(){}

    @EmbeddedId
    public BookOnHoldIdTO getPk() {
        return pk;
    }

    public void setPk(BookOnHoldIdTO pk) {
        this.pk = pk;
    }

    @Transient
    public BookTO getBookTO(){
        return getPk().getBookTO();
    }

    public void setBookTO(BookTO bookTO){
        getPk().setBookTO(bookTO);
    }

    @Transient
    public UserTO getUserTO(){
        return getPk().getUserTO();
    }

    public void setUserTO(UserTO userTO){
        getPk().setUserTO(userTO);
    }

    @Column(name = "hold_type", nullable = false)
    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    @Column(name = "approved", columnDefinition = "TINYINT", length = 1)
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
