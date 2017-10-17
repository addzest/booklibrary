package com.laba.booklibrary.service.books.model;

import com.laba.booklibrary.service.users.model.UserTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books_onhold")
@AssociationOverrides({
        @AssociationOverride(name = "fk_bookTO",
            joinColumns = @JoinColumn(name = "book_id")),
        @AssociationOverride(name = "fk_userTO",
            joinColumns = @JoinColumn(name = "user_id"))})
public class BookOnHoldTO implements Serializable{
    private BookOnHoldIdTO fk = new BookOnHoldIdTO();
    private long operationId;
    private String holdType;
    private boolean approved;

    public BookOnHoldTO(){}

    @EmbeddedId
    public BookOnHoldIdTO getFk(){
        return fk;
    }

    public void setFk(BookOnHoldIdTO fk){
        this.fk = fk;
    }

    @Transient
    public BookTO getBookTO(){
        return getFk().getBookTO();
    }

    public void setBookTO(BookTO bookTO){
        getFk().setBookTO(bookTO);
    }

    @Transient
    public UserTO getUserTO(){
        return getFk().getUserTO();
    }

    public void setUserTO(UserTO userTO){
        getFk().setUserTO(userTO);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id", nullable = false)
    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    @Column(name = "hold_type", nullable = false)
    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
