package kookdo.com.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//�������̽� : �߻�޼ҵ�� ����� ����� ���´�.
public interface Command {
	//�������̽��� �޼ҵ�� �ڵ����� public abstract�� �ٴ´�.
	void execute(HttpServletRequest req, HttpServletResponse res)throws Exception;
	
	
}
