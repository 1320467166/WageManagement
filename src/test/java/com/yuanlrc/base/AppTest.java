package com.yuanlrc.base;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yuanlrc.base.bean.GradeEnum;
import com.yuanlrc.base.entity.admin.AssessTarget;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.service.admin.AssessTargetService;
import com.yuanlrc.base.service.admin.SalaryService;
import com.yuanlrc.base.service.admin.StaffService;
import com.yuanlrc.base.util.DateUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest 
{

    @Autowired
    private SalaryService salaryService;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testString() {
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> JsontoMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }

    @Test
    public  void testYear(){
        List<Object> objects = salaryService.countPayRollDepartmentByYear(2021);
        List<String> months = new ArrayList<>();
        List<BigDecimal> countPayroll = new ArrayList<>();
        for(Object object:objects){
           Object[] o= (Object[]) object;
           if(!months.contains(o[0].toString())){
               months.add(o[0].toString());
           }
            if(!countPayroll.contains(o[1].toString())){

            }
            System.out.println(o[0]);
            System.out.println(o[1]);
            System.out.println(o[2]);
        }
        System.out.println("测试");
    }

}
