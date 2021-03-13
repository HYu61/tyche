package pers.hyu.tyche.utils;

import java.util.List;

/**
 * This class is used to encapsulated the data from the pageHelper.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class PagedResult {
    private int page;			// the current page
    private int total;			// the total page
    private long records;		// the total num of all the records
    private List<?> rows;		// the result of one page

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
