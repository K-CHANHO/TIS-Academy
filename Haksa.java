import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Haksa extends JFrame{
	
	JLabel lblID = null;
	JTextField tfID = null;
	JLabel lblName = null;
	JTextField tfName = null;
	JLabel lblDept = null;
	JTextField tfDept = null;
	JLabel lblAddress = null;
	JTextField tfAddress = null;
	//JTextArea taList = null; 
	DefaultTableModel model = null;
	JTable table = null;
	
	JButton btnInsert = null;
	JButton btnSelect = null;
	JButton btnUpdate = null;
	JButton btnDelete = null;
	JButton btnSearch=null;
	
	public Haksa() {
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new FlowLayout());
		Container c = this.getContentPane();
		
		lblID = new JLabel("학번");
		c.add(lblID);
		tfID = new JTextField(14);
		c.add(tfID);
		btnSearch = new JButton("검색");
		c.add(btnSearch);
		
		lblName = new JLabel("이름");
		c.add(lblName);
		tfName = new JTextField(20);
		c.add(tfName);
		
		lblDept = new JLabel("학과");
		c.add(lblDept);
		tfDept= new JTextField(20);
		c.add(tfDept);
		
		lblAddress = new JLabel("주소");
		c.add(lblAddress);
		tfAddress = new JTextField(20);
		c.add(tfAddress);
		
//		taList = new JTextArea(10,25);
//		c.add(new JScrollPane(taList));
		String[] colName = {"학번", "이름", "학과", "주소"};
		model = new DefaultTableModel(colName,0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(270,270));
		c.add(new JScrollPane(table));
		
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table=(JTable)e.getComponent();//클릭한 테이블 구하기

			    model=(DefaultTableModel)table.getModel();//테이블의 모델 구하기

			    String id=(String)model.getValueAt(table.getSelectedRow(), 0);
			    tfID.setText(id);

			    String name=(String)model.getValueAt(table.getSelectedRow(), 1);
			    tfName.setText(name);

			    String dept=(String)model.getValueAt(table.getSelectedRow(), 2);
			    tfDept.setText(dept);
			    
			    String address=(String)model.getValueAt(table.getSelectedRow(), 3);
			    tfAddress.setText(address);
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		
		btnInsert = new JButton("등록");
		c.add(btnInsert);
		btnSelect = new JButton("목록");
		c.add(btnSelect);
		btnUpdate = new JButton("수정");
		c.add(btnUpdate);
		btnDelete = new JButton("삭제");
		c.add(btnDelete);
		
		
		// 이벤트처리
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hkd","hong");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement(); // Statement 객체생성
					
					ResultSet rs = stmt.executeQuery("select * from student"); // select문 실행
					//taList.setText("");
					model.setRowCount(0);
					while(rs.next()) {
						tfID.setText("");
						tfName.setText("");
						tfDept.setText("");
						tfAddress.setText("");						
						//taList.append(rs.getString("id")+"\t"+ rs.getString("name")+"\t"+ rs.getString("dept")+"\n");
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");

						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hkd","hong");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement(); // Statement 객체생성
					//insert
					stmt.executeUpdate("insert into student values('"+tfID.getText()+"','"+tfName.getText()+"','"+tfDept.getText()+"','"+tfAddress.getText()+"')");
					// 한 행씩 읽어옴
					ResultSet rs = stmt.executeQuery("select * from student"); // select문 실행
					//taList.setText("");
					model.setRowCount(0);
					while(rs.next()) {
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
						model.addRow(row);
						
						tfID.setText("");
						tfName.setText("");
						tfDept.setText("");
						tfAddress.setText("");
//						taList.append(rs.getString("id")+"\t"+ rs.getString("name")+"\t"+ rs.getString("dept")+"\n");
						
					}
					rs.close();
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hkd","hong");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement(); // Statement 객체생성
					// 한 행씩 읽어옴
					ResultSet rs = stmt.executeQuery("select * from student where id = '"+tfID.getText()+"'"); // select문 실행
//					taList.setText("");
					model.setRowCount(0);
					while(rs.next()) {
//						tfID.setText(rs.getString("id"));
//						tfName.setText(rs.getString("name"));
//						tfDept.setText(rs.getString("dept"));
//						taList.append(rs.getString("id")+"\t"+ rs.getString("name")+"\t"+ rs.getString("dept")+"\n");
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
						
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hkd","hong");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement(); // Statement 객체생성
					
					//update
					stmt.executeUpdate("update student set name='"+tfName.getText()+"', dept = '"+tfDept.getText()+"', address = '"+tfAddress.getText()+"' where id='"+tfID.getText()+"'");
					
					// 한 행씩 읽어옴
					ResultSet rs = stmt.executeQuery("select * from student"); // select문 실행
					
//					taList.setText("");
					model.setRowCount(0);
					while(rs.next()) {
						tfID.setText("");
						tfName.setText("");
						tfDept.setText("");
						tfAddress.setText("");
//						taList.append(rs.getString("id")+"\t"+ rs.getString("name")+"\t"+ rs.getString("dept")+"\n");
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
						
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});

		btnDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(null,"정말 삭제하시겠습니까?","데이터 삭제",JOptionPane.YES_NO_OPTION);
				if(choose==JOptionPane.YES_OPTION){
				try {
					//oracle jdbc드라이버 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hkd","hong");// 연결
					System.out.println("연결완료");
					
					Statement stmt = conn.createStatement(); // Statement 객체생성
					
					//delete
					stmt.executeUpdate("delete from student where id='"+tfID.getText()+"'");
					
					// 한 행씩 읽어옴
					ResultSet rs = stmt.executeQuery("select * from student"); // select문 실행
					
//					taList.setText("");
					model.setRowCount(0);
					while(rs.next()) {
						tfID.setText("");
						tfName.setText("");
						tfDept.setText("");
						tfAddress.setText("");
//						taList.append(rs.getString("id")+"\t"+ rs.getString("name")+"\t"+ rs.getString("dept")+"\n");
						String[] row=new String[4];//컬럼의 갯수가 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
							
						model.addRow(row);
					}
					rs.close();
					stmt.close();
					conn.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
			else if(choose==JOptionPane.NO_OPTION){
				JOptionPane.showMessageDialog(null,"취소되었습니다.");
			}
				
			}
			
		});
		
		
		
		
		this.setSize(300,500);
		this.setVisible(true);
		
	}


	public static void main(String[] args) {
		new Haksa();
		
	}

}
