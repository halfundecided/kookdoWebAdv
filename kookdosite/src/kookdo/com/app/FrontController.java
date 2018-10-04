package kookdo.com.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(urlPatterns = { "*.do" }, initParams = {
		@WebInitParam(name = "config", value = "D:/eclipse/workspace/kookdosite/WebContent//WEB-INF/movePath.properties") })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> cmdMap = new HashMap<String, Object>();

	/**
	 * ���� ����� �� ���� ȣ��Ǵ� �޼ҵ� init() init-param�� ����Ǿ� �ִ� config�� �ش��ϴ�
	 * value��(Command.properties�� ������)�� ������.
	 */
	@Override
	public void init(ServletConfig conf) throws ServletException {
		System.out.println("init()ȣ���...");
		String props = conf.getInitParameter("config");
		System.out.println("props : " + props);
		Properties pr = new Properties();
		// Hashtable�� �ڽ����̺�, Ű���� value���� �����Ͽ� �����ϴ� �ڷᱸ��
		// Command.properties ���Ͽ� ���ε������� Properties �ڷᱸ���� �ű���
		try {

			FileInputStream fis = new FileInputStream(props);
			pr.load(fis);
			// Command.properties���� ������ Properties��ü�� �����Ѵ�.
			if (fis != null)
				fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(pr.getProperty("/index.do"));
		// key���鸸 ����
		Enumeration<Object> en = pr.keys();
		while (en.hasMoreElements()) {
			String cmd = en.nextElement().toString(); // key��
			String className = pr.getProperty(cmd);
			System.out.println("cmd : " + cmd);
			System.out.println("className : " + className);

			if(className!=null){
				className = className.trim();
			}
			
			try {
				Class cmdClass = Class.forName(className);
				Object cmdInstance = cmdClass.newInstance();
				//�ش�Ŭ������ ��üȭ���� �޸𸮿� �ø���.
				
				/////////////////////////////��
				cmdMap.put(cmd, cmdInstance);
				//�� ��ü���� �ؽøʿ� ����
				/////////////////////////////��
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}

		} // while
	}//init

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		requestProcess(req, res);
	}

	private void requestProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("FrontController����");
		//1.Ŭ���̾�Ʈ�� ��ûuri�� �м�
		String uri = req.getRequestURI();
		String myctx = req.getContextPath();
		int len = myctx.length();
		String cmd = uri.substring(len);
		System.out.println("cmd : "+cmd);
		
		Object instance = cmdMap.get(cmd);
		
		AbstractAction action = (AbstractAction)instance;
		if(action==null){
			System.out.println("FrontController : action is null!");
			return;
		}
		//���� ��Ʈ�ѷ�(action)�� execute()�� ȣ��
		try {
			//////////
			action.execute(req, res);
			System.out.println("FrontController : ���� ��Ʈ�ѷ�(action)�� execute()�� ȣ��");
			//////////
			//�̵��� �������� ���
			String viewPage = action.getViewPage();
			if(viewPage==null){
				System.out.println("FrontController : viewPage is null!");
				viewPage = "index.jsp";
				//null�̸� default page�� index�� ����
			}
			
			if(action.isRedirect()){
				//redirect�̵��� ���
				res.sendRedirect(viewPage);
			} else {
				//forward�̵��� ���
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		requestProcess(req, res);
	}

}













