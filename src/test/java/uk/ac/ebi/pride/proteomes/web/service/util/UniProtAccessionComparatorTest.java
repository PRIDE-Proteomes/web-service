package uk.ac.ebi.pride.proteomes.web.service.util;

import junit.framework.Assert;
import org.junit.Test;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.UniprotAccessionComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Florian Reisinger
 *         Date: 28/01/14
 * @since 1.0.0
 */
public class UniProtAccessionComparatorTest {



    @Test
    public void testCompare() {
        String zero = "P12345";
        String one = "P12345-0";
        String two = "P12345-01";
        String three = "P12345-2";
        String four = "P12345-19";
        String five = "P12345-21";
        String six = "P12345-211";
        String seven = "P31234";
        String eight = "Q3Z76E";

        List<String> list = new ArrayList<String>();
        list.add(five);
        list.add(three);
        list.add(one);
        list.add(two);
        list.add(six);
        list.add(eight);
        list.add(zero);
        list.add(seven);
        list.add(four);

        Collections.sort(list, new UniprotAccessionComparator());

        Assert.assertEquals(list.get(0), zero);
        Assert.assertEquals(list.get(1), one);
        Assert.assertEquals(list.get(2), two);
        Assert.assertEquals(list.get(3), three);
        Assert.assertEquals(list.get(4), four);
        Assert.assertEquals(list.get(5), five);
        Assert.assertEquals(list.get(6), six);
        Assert.assertEquals(list.get(7), seven);
        Assert.assertEquals(list.get(8), eight);


    }

}
