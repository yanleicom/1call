package com.yanlei.springboot;

import com.yanlei.springboot.util.TokenUtil;
import com.yanlei.springboot.util.WeekOfDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.NumberFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot1callApplicationTests {
	Logger logger = LoggerFactory.getLogger(SpringBoot1callApplicationTests.class);

	@Test
	public void contextLoads() {
		logger.trace("这是trace");
		logger.debug("这是debug");
		logger.info("这是info");
		logger.warn("这是warn");
		logger.error("这是error");
	}

	@Test
	public void getUuid(){
		String uuid32 = com.yanlei.springboot.util.UUID.getUUID32();
		System.out.println(uuid32);
		String s = "27063e669a60433db44ea9ed043c54aa";
		String s1 = "330506199995086666";
		int length = s1.length();
		System.out.println(length);

	}
	@Test
	public void test1(){
		String str = "a,b,c";
		List<String> result = Arrays.asList(StringUtils.split(str,","));
		System.out.println(result);
	}

	@Test
	public void test2(){
		int diliverNum=3;//举例子的变量
		int queryMailNum=9;//举例子的变量
// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(0);
		String result = numberFormat.format((float)diliverNum/(float)queryMailNum*100);
		System.out.println("diliverNum和queryMailNum的百分比为:" + result + "%");
	}

	@Test
	public void test3() throws Exception {
		String s = TokenUtil.genToken();
		System.out.println(s);
		boolean b = TokenUtil.verificationToken(s);
		System.out.println(b);
	}

	@Test
	public void  test4(){
		Date date = new Date();
		String weekOfDate = WeekOfDateUtil.getWeekOfDate(date);
		System.out.println(weekOfDate);
		Calendar cal=Calendar.getInstance();
		int y=cal.get(Calendar.YEAR);
		int m=cal.get(Calendar.MONTH);
		int d=cal.get(Calendar.DATE);
		int h=cal.get(Calendar.HOUR_OF_DAY);
		int mi=cal.get(Calendar.MINUTE);
		int s=cal.get(Calendar.SECOND);
		System.out.println("现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒");

	}

	@Test
	public void  test5(){
		List<String> A = new ArrayList<String>();
		A.add("330107194711250322");
		A.add("330107194711250324");
		A.add("330107194711250326");
		A.add("330107194711250312");
		A.add("330107194711250313");

		List<String>B = new ArrayList<String>();
		B.add("330107194711250324");
		B.add("330107194711250326");
		B.add("330107194711250312");
		B.add("330107194711250312");

		List<String> C = new ArrayList<String>();
		C.addAll(A);
		C.removeAll(B);
		System.out.println(C);

	}

}
