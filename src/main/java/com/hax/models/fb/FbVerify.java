package com.hax.models.fb;

/**
 * Created by martin on 6/16/15.
 */
public class FbVerify {
            public String id; //": "10204366521345833",
            public String first_name; //": "Martin",
            public String gender; //": "male",
            public String last_name; //": "Silber",
            public String link; //": "https://www.facebook.com/app_scoped_user_id/10204366521345833/",
            public String locale; //": "es_LA",
            public String name; //": "Martin Silber",
            public String timezone; //": -3,
            public String updated_time; //": "2015-06-05T12:33:08+0000",
            public String email;
            public Boolean verified; //": true

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
