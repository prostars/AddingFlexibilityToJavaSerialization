class TestEntity implements Externalizable {
    private static final long serialVersionUID = 88468757223408586L

    Long salary
    Long bonus

    Long setSalary(final Long salary) {
        assert salary > 0
        this.salary = salary
    }

    void writeExternal(final ObjectOutput out) {
        out.writeLong(salary)
        out.writeLong(bonus)
    }

    void readExternal(final ObjectInput objectInput) {
        salary = objectInput.readLong()
        bonus = objectInput.readLong()
    }
}
