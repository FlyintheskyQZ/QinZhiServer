package seu.qz.qzserver.entity;

public class AlreadyExistCustomer {

    private boolean isExisted;

    private String reason;

    private AppCustomer customer;

    public AlreadyExistCustomer() {
    }

    public AlreadyExistCustomer(boolean isExisted, String reason) {
        this.isExisted = isExisted;
        this.reason = reason;
        this.customer = null;
    }

    public boolean isExisted() {
        return isExisted;
    }

    public void setExisted(boolean existed) {
        isExisted = existed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AppCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(AppCustomer customer) {
        this.customer = customer;
    }
}
