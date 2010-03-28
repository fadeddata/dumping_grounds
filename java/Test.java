/**
 * @return    A HashSet containing the CommPortIdentifier for all serial ports that are not currently being used.
 */

import java.io.*;
import java.util.*;
import gnu.io.*;

public class Test {

    public static void main(String [ ] args) {
	int i;
	for (i = 0; i <= 10; i++){
	    System.out.println(String.valueOf(1 + i * 2));
	}
    }
}