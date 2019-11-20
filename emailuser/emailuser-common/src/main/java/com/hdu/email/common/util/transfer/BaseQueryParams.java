package com.hdu.email.common.util.transfer;

import java.io.Serializable;

public class BaseQueryParams implements Serializable {

    private int page = 1;
    private int rows = 10;
    private int total;
    private String orderby;
    private String sortBy;
    private String sortDir;

    public BaseQueryParams() {
    }

    public int getTotalPages() {
        int mod = this.total % this.rows;
        int totalPages = this.total / this.rows;
        return mod == 0 ? totalPages : totalPages + 1;
    }

    public int getStart() {
        return (this.page - 1) * this.rows + 1;
    }

    public int getEnd() {
        return this.page * this.rows;
    }

    public int getOffset() {
        return (this.page - 1) * this.rows;
    }

    public int getPage() {
        return this.page;
    }

    public int getRows() {
        return this.rows;
    }

    public int getTotal() {
        return this.total;
    }

    public String getOrderby() {
        return this.orderby;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public String getSortDir() {
        return this.sortDir;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
