package com.lmk.ms.common.utils.encrypt;

import com.lmk.ms.common.db.Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/08
 */
public class TextTest {

    // @Test
    public void testFloatText(){
        /*Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        Matcher matcher = pattern.matcher("/");
        System.out.println(matcher.matches());*/

        List<Search> searchList = new ArrayList<>();
        searchList.add(Search.eq("name", "Jim"));
        searchList.add(Search.in("age", Arrays.asList("12")));
        searchList.add(Search.like("cs", "ttt"));
        System.out.println(Search.build(searchList));
    }

}
