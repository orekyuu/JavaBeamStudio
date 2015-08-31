package net.orekyuu.javatter.core.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VersionComparatorTest {

    @Test
    public void testCompare() throws Exception {
        VersionComparator comparator = new VersionComparator();
        assertTrue(comparator.compare("1.0.0", "1.0.0") == 0);

        assertTrue(comparator.compare("1.1.2", "1.1.2.0") == 0);
        assertTrue(comparator.compare("1.1.2.0", "1.1.2") == 0);

        assertTrue(comparator.compare("1.1.0", "1.0.0") > 0);
        assertTrue(comparator.compare("1.0.0", "1.1.0") < 0);

        assertTrue(comparator.compare("1.0.0", "1.0.0.0.1") < 0);
        assertTrue(comparator.compare("1.0.0.0.1", "1.0.0") > 0);
    }
}
