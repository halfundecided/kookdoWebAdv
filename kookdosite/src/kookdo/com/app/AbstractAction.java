package kookdo.com.app;

//command�������̽��� ��ӹ޾� execute()�� ����� ���´�.
public abstract class AbstractAction implements Command{

	private String viewPage; //������������ ���� ���� 
	private boolean isRedirect; //�̵������ redirect�� true, forward�� false
	
	
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
