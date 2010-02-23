package enums;

import org.junit.Test;

public class ValuesRespectOrdinate {
	
	public static enum vals {
		val0,
		val1,
		val2,
		val3,
		val4,
		val5,
		val6,
		val7,
		val8,
		val9;
	}
	
	
	@Test
	public void makeTest() {
		for(vals v : vals.values()) {
			assert(v.name().equals("val"+v.ordinal()));
		}
	}

}
