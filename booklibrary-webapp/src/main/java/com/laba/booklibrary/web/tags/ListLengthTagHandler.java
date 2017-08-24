package com.laba.booklibrary.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * tag handler to show info about list length and number of entities shown
 */
public class ListLengthTagHandler extends SimpleTagSupport {

    private int listLength;
    private int currentPage;

    public void setListLength(int listLength) {
        this.listLength = listLength;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (currentPage != 0) {
            String totalBooks = LocaleSupport.getLocalizedMessage((PageContext) getJspContext(), "index.bookList.totalBooks");
            String showing = LocaleSupport.getLocalizedMessage((PageContext) getJspContext(), "index.bookList.showing");
            String message;
            if (listLength > 1) {
                message = totalBooks + ": " + listLength + ". " + showing + " " + (currentPage * 5 - 4) + "-" + Math.min(listLength, currentPage * 5);
            } else {
                message = totalBooks + ": " + listLength + ".";
            }
            getJspContext().getOut().println(message);
        } else {
            String totalBooks = LocaleSupport.getLocalizedMessage((PageContext) getJspContext(), "index.bookList.totalBooks");
            String message;
            message = totalBooks + ": " + listLength + ".";
            getJspContext().getOut().println(message);
        }
    }
}
