package net.timeboxing.spring.adapter.testimpl;

public class DefaultUser implements User {

    private final Integer id;

    public DefaultUser(Integer id) {
        this.id = id;
    }

    @Override
    public Integer id() {
        return id;
    }
}
