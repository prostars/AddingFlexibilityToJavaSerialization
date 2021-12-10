import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.dataformat.cbor.CBORFactory

class TestEntity implements Externalizable {
    private static final long serialVersionUID = 88468757223408586L
    private static final cborFactory = new CBORFactory()

    Double salary
    Long bonus

    Double setSalary(final Double salary) {
        assert salary > 0
        this.salary = salary
    }

    void writeExternal(final ObjectOutput out) {
        final generator = cborFactory.createGenerator(out)
        generator.writeStartObject()
        generator.writeNumberField('salary', salary)
        generator.writeNumberField('bonus', bonus)
        generator.close()
    }

    void readExternal(final ObjectInput objectInput) {
        final parser = cborFactory.createParser(objectInput as InputStream)
        parser.nextToken()
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            final fieldName = parser.getCurrentName()
            parser.nextToken()
            switch (fieldName) {
                case 'salary': salary = parser.getNumberValue(); break
                case 'bonus': bonus = parser.getNumberValue(); break
                default: println("cannot found field name : $fieldName"); break
            }
        }
    }
}
