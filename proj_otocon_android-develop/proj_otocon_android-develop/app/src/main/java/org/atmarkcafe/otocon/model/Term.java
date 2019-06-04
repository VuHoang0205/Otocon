package org.atmarkcafe.otocon.model;

/**
 * The Term is object model1  contains the terms of the application
 *
 * @author acv-vuht
 * @version 1.0
 * @since 2018-08-18
 */
public class Term {

    /**
     * save infomation title of term
     */
    private String title;

    /**
     * save infomation content of term
     */
    private String content;

    /**
     * initialize Term
     *
     * @param title   contain infomation title of term
     * @param content contain infomation content of term
     */
    public Term(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
