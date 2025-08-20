package vn.kieudon.workwave.domain.dto;

public class ResultPagination {
    private Meta meta;
    private Object result;

    public ResultPagination() {

    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
