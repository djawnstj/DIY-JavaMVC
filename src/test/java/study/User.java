package study;

class User {
    private long id;
    private Pay pay;

    User() {

    }

    User(long id, Pay pay) {
        this.id = id;
        this.pay = pay;
    }

    public long getId() {
        return id;
    }

    public Pay getPay() {
        return pay;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

}