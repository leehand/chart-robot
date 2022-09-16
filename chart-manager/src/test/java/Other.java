import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.stream.Collectors;

public class Other {
    private static Logger LOGGER = LoggerFactory.getLogger(Other.class);
    @Test
    public void test01(){
        Set<Long> memberIds = new HashSet<>();
        memberIds.add(1L);
        memberIds.add(2L);
        memberIds.add(3L);
        memberIds.add(4L);
        memberIds.add(5L);

        Set<Long> newMemberIds = new HashSet<>();
        newMemberIds.add(11L);
        newMemberIds.add(22L);
        newMemberIds.add(33L);
        newMemberIds.add(4L);
        newMemberIds.add(5L);

        newMemberIds.retainAll(memberIds);

        LOGGER.info(memberIds.toString());
        LOGGER.info(newMemberIds.toString());
    }

    @Test
    public void test02(){
        List<String> tmp = new ArrayList<>();
        tmp.add("test");
        tmp.add("good");

        String newTmp = StringUtils.join(tmp.toArray(), " and ");

        LOGGER.info(newTmp);
    }

    @Test
    public void test03(){
        HashMap<String, Integer> tmp = new HashMap<>();
        tmp.put("aaa", 444);
        tmp.put("bbb", 222);
        tmp.put("ccc", 333);
        tmp.put("ddd", 555);

        List<Integer> list = tmp.entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());

        LOGGER.info(list.toString());
    }
}
