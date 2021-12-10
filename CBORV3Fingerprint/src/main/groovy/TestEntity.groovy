import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.dataformat.cbor.CBORFactory

import java.nio.ByteBuffer

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
        out.writeByte(0xF0)
        final generator = cborFactory.createGenerator(out)
        generator.writeStartObject()
        generator.writeNumberField('salary', salary)
        generator.writeNumberField('bonus', bonus)
        generator.close()
    }

    void readExternal(final ObjectInput objectInput) {
        final fingerprint = objectInput.readByte()
        if (fingerprint == 0xF0 as Byte) {
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
        } else {
            def bytes = ByteBuffer.allocate(4)
            bytes.put(0, fingerprint)
            objectInput.read(bytes.array(), 1, 3)
            salary = bytes.getInt()
            bonus = objectInput.readLong()
        }
    }
}
