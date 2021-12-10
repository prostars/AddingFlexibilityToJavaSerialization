import net.spy.memcached.AddrUtil
import net.spy.memcached.MemcachedClient

class LikeSerializableV2 {
    static main(args) {
        def mc = new MemcachedClient(AddrUtil.getAddresses(["localhost:11211"]))
        def entityForWrite = new TestEntity().tap {
            salary = Integer.MAX_VALUE
            bonus = Integer.MAX_VALUE * 2L
        }

        def entityForRead = mc.get('TestKey') as TestEntity
        mc.shutdown()

        println entityForRead.salary
        println entityForRead.bonus

        assert entityForWrite.salary == entityForRead.salary
        assert entityForWrite.bonus == entityForRead.bonus
    }
}
