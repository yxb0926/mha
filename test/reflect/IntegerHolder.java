package reflect;

import java.lang.reflect.Field;

import org.junit.Test;

public class IntegerHolder {
	public int val;
	
	@Test
	public void intFieldIsSawn() {
		for(Field f : IntegerHolder.class.getDeclaredFields()) {
			if(int.class.isAssignableFrom(f.getType())) {
				return ;
			}
		}
		assert(false);
	}
}
