package com.chengh.db;

import com.alibaba.fastjson.JSON;
import com.chengh.db.entity.User;
import com.chengh.db.mapper.UserMapper;
import com.chengh.db.util.EventDemo;
import com.chengh.db.util.IdGenerator;
import com.chengh.db.util.threadPool.TestThreadTask;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestAplication.class)
@WebAppConfiguration
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Resource
    IdGenerator idGenerator;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    public void save() {
        for (Integer i = 0; i < 100; i++) {
            User user = new User();
            //user.setId(i.longValue());
            user.setName("chengh" + i);
            user.setSex(i % 2);
            user.setPhone("12345678910");
            user.setEmail("123@qq.com");
            user.setPassword("123456");
            userMapper.save(user);
        }
    }

    @Test
    public void getByIds() {
        System.out.println(JSON.toJSONString(
                userMapper.getById(123L)));
    }

    @Test
    public void getCreateDate() throws IOException {
        File xlsFile = new File("/Users/chengh/Downloads/机会ID.xlsx");
        /**
         * 这里根据不同的excel类型
         * 可以选取不同的处理类：
         *          1.XSSFWorkbook
         *          2.HSSFWorkbook
         */
        // 获得工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));

        // 获得工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        System.out.println(sheet.getPhysicalNumberOfRows() + "-----" + sheet.getLastRowNum());

        XSSFCell cell = sheet.getRow(0).getCell(0);
        System.out.println(Double.valueOf(cell.getNumericCellValue()).intValue());


//        List<String> mobiles = new ArrayList<>();
//        for (int i = 1; i < rows; i++) {
//            XSSFRow sheetRow = sheet.getRow(i);
//            if(sheetRow == null ||sheetRow.getCell(0) == null){
//                continue;
//            }
//            mobiles.add(sheetRow.getCell(0).getStringCellValue());
//        }
//        System.out.println("==========" + mobiles.size());
//        int start = 0;
//        int end = 2000;
//        List<crmchance> crmchances = new ArrayList<>();
//        while (true) {
//            crmchances.addAll(userMapper.getCreateDate(mobiles.subList(start, end)));
//            start = end;
//            end = start + 2000 > mobiles.size() ? mobiles.size() : start + 2000;
//            System.out.println("==========end:" + end);
//            if (start == mobiles.size()) {
//                break;
//            }
//        }
//        Map<String, List<crmchance>> chances = crmchances.stream().collect(Collectors.groupingBy(crmchance::getMobile));
//        crmchances.clear();
//        for (int i = 1; i < rows; i++) {
//            XSSFRow sheetRow = sheet.getRow(i);
//            if(sheetRow == null || sheetRow.getCell(0) == null){
//                continue;
//            }
//            XSSFCell cell = sheetRow.createCell(1);
//            XSSFCell cell2 = sheetRow.getCell(0);
//            cell.setCellValue(chances.get(cell2.getStringCellValue()).get(0).getCreateDate());
//            if(i%1000 ==0) {
//                System.out.println("==========i:" + i);
//            }
//        }
//        chances.clear();
//        FileOutputStream excelFileOutPutStream = new FileOutputStream("/Users/chengh/Downloads/未添加数据.xlsx");
//
//        workbook.write(excelFileOutPutStream);
//
//        excelFileOutPutStream.flush();
//
//        excelFileOutPutStream.close();
    }

    @Test
    public void publish() {
        EventDemo demo = new EventDemo(this, "1234555");
        applicationEventPublisher.publishEvent(demo);
    }

    @Test
    public void threadTest() {
        List<Future<String>> testThreadTasks = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            TestThreadTask task = new TestThreadTask();
            testThreadTasks.add(threadPoolExecutor.submit(task));
        }
        testThreadTasks.forEach(testThreadTask -> {
            try {
                System.out.println(testThreadTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            ;
        });
    }

    public static void main(String[] args) {
        List<String> aaa = new ArrayList<>();
        aaa.add("面授班");
        aaa.add("一元订单");
        aaa.add("发布会");
        aaa.add("广告点击");
        if (aaa.indexOf("发布会") != -1) {
            aaa.add(aaa.indexOf("发布会") +1 , "渠道推广激活" );
        }
        System.out.println(aaa);
    }
}
