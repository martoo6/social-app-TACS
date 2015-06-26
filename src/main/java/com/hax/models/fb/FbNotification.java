package com.hax.models.fb;

/**
 * Created by martin on 6/25/15.
 */
public class FbNotification {
    String href;
    String template;

    public FbNotification(String template) {
        this.href = "";
        this.template = template;
    }

    public FbNotification(String href, String template) {
        this.href = href;
        this.template = template;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
