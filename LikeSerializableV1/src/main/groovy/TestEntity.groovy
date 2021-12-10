class TestEntity implements Externalizable {
    private static final long serialVersionUID = 88468757223408586L

    Integer salary
    Long bonus

    Integer setSalary(final Integer salary) {
        assert salary > 0
        this.salary = salary
    }

    void writeExternal(final ObjectOutput out) {
        out.writeInt(salary)
        out.writeLong(bonus)
    }

    void readExternal(final ObjectInput objectInput) {
        salary = objectInput.readInt()
        bonus = objectInput.readLong()
    }
}
