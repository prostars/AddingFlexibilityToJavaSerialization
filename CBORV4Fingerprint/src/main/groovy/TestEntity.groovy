import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.dataformat.cbor.CBORFactory

class TestEntity implements Externalizable {
    private static final long serialVersionUID = 88468757223408586L
    private static final cborFactory = new CBORFactory()

    Long salary
    Long bonus

    Long setSalary(final Long salary) {
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
        final pis = new PushbackInputStream(objectInput as InputStream)
        final fingerprint = pis.read() as Byte
        if (fingerprint != 0xF0 as Byte)
            pis.unread(fingerprint)

        final parser = cborFactory.createParser(pis as InputStream)
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
