package hu.bme.mhorvath.newsapp.model;

import com.orm.SugarRecord;

public class Source extends SugarRecord {

    private String source;

    public Source() {
    }

    public Source(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
