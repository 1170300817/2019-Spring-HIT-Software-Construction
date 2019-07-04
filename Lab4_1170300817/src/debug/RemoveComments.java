///*
//
//This program is used for removing all the comments in a program code.
//
//Example 1:
//Input: 
//source = ["/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"]
//
//The line by line code is visualized as below:
//
///*Test program */
//int main()
//{ 
//  // variable declaration 
//int a, b, c;
///* This is a test
//   multiline  
//   comment for 
//   testing */
//a = b + c;
//}
//
//Output: ["int main()","{ ","  ","int a, b, c;","a = b + c;","}"]
//
//The line by line code is visualized as below:
//
//int main()
//{ 
//
//int a, b, c;
//a = b + c;
//}
//
//Explanation: 
//The string /* denotes a block comment, including line 1 and lines 6-9. The string // denotes line 4 as comments.
//
//Example 2:
//
//Input: 
//source = ["a/*comment", "line", "more_comment*/b"]
//
//Output: ["ab"]
//
//Explanation: 
//The original source string is "a/*comment\nline\nmore_comment*/b", where we have bolded the newline characters.  
//After deletion, the implicit newline characters are deleted, leaving the string "ab", which when delimited by newline characters becomes ["ab"].
//
//
//Note:
//
//The length of source is in the range [1, 100].
//The length of source[i] is in the range [0, 80].
//Every open block comment is eventually closed.
//There are no single-quote, double-quote, or control characters in the source code.
//
//*/

package debug;

import java.util.ArrayList;
import java.util.List;

class RemoveComments {
	public static List<String> removeComments(String[] source) {
		boolean inBlock = false;
		StringBuilder newline = new StringBuilder();
//        List<String> ans = new List(); 改为
		List<String> ans = new ArrayList<String>();
		for (String line : source) {
			int i = 0;
			char[] chars = line.toCharArray();
			if (!inBlock)
				newline = new StringBuilder();
			while (i < line.length()) {
				if (!inBlock && i + 1 <= line.length() && chars[i] == '/' && chars[i + 1] == '*') {
					inBlock = true;
					i++;   //增加指针i
				} else if (inBlock && i + 1 <= line.length() && chars[i] == '*' && chars[i + 1] == '/') {
					inBlock = false;
					i++;   //增加指针i
				} else if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '/') {
					i++;  
					break; // 处理单行注释的情况
				} else if (!inBlock) {
					newline.append(chars[i]);
				}
				i++;
			}
//			if (inBlock && newline.length() > 0) {
			if (!inBlock && newline.length() > 0) {
				ans.add(new String(newline));
			}
		}
		return ans;
	}
}