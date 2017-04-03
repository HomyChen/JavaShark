package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Homy Chen on 2017-04-02.
 */
public class UsageStatTable {

    private List<UsageStatRow> contents = new ArrayList<>();

    public List<UsageStatRow> getContents() {
        return contents;
    }

    public void setContents(List<UsageStatRow> data) {
        this.contents = data;
    }
}
