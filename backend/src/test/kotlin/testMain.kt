
import db.InterestList
import db.getUniqueInterests
import main.parseInterests
import main.stringifyInterests
import kotlin.test.*

class SimpleTest {
    @Test fun testGetUniqueInterests() {
        val unique = getUniqueInterests(arrayOf(InterestList(arrayOf("A", "A"))))
        assertEquals(HashSet(elements = mutableListOf("A")), unique)
    }

    @Test fun testParseInterests() {
        // assertEquals(parseInterests(""), emptyList())
        assertEquals(parseInterests("a"), listOf("a"))
        assertEquals(parseInterests("a,b"), listOf("a", "b"))
    }

    @Test fun testStringifyInterests() {
        assertEquals(stringifyInterests(emptyList()), "")
        assertEquals(stringifyInterests(listOf("a")), "a")
        assertEquals(stringifyInterests(listOf("a", "b")), "a,b")
    }
}