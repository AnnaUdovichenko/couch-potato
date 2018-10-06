
import db.InterestList
import db.getUniqueInterests
import kotlin.test.*

class SimpleTest {

    @Test fun testFoo() {
        assertEquals(10, main.foo())
    }
    @Test fun testGetUniqueInterests() {
        val unique = getUniqueInterests(arrayOf(InterestList(arrayOf("A", "A"))))
        assertEquals(HashSet(elements = mutableListOf("A")), unique)
    }
}