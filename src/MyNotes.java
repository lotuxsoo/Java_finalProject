import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

class Item implements Serializable{
	String name; //제목
	int year; //년도
	int star; //별점
	String intro; //줄거리,책소개
	String comment; //감상평
	String filepath;
	 //ImageIcon image; //이미지
	public Item(String name,int year,int star,String intro,String comment,String filepath) {
		this.name=name;
		this.year=year;
		this.star=star;
		this.intro=intro;
		this.comment=comment;
		this.filepath=filepath;
	}
 }
class Movie extends Item implements Serializable{
	String director; //감독
	String actor; //배우
	String genre; //장르 
	String level; //등급
	
	public Movie(String name,String director,String actor,String genre,String level,int year,String filepath,int star,String intro,String comment) {
		super(name,year,star,intro,comment,filepath);
		this.director=director;
		this.actor=actor;
		this.genre=genre;
		this.level=level;
	}
}
class Book extends Item implements Serializable{
	String author; //저자 
	String publisher; //출판사

	public Book(String name,String author,String publisher,int year,String filepath,int star,String intro,String comment) {
		super(name,year,star,intro,comment,filepath);
		this.author=author;
		this.publisher=publisher;
	}	
}
class ItemCollections{
	
	Vector<Item> vect = new Vector<Item>(); //아이템 객체들을 담는 item벡터 정의 

	//객체 전체의 이름을 저장하는  vallname 리턴
	public Vector<String>vall(){
		Vector<String> vallname = new Vector<String>(); 
		for(int i=0;i<vect.size();i++) {
			vallname.add(vect.get(i).name);
		}
		return vallname;
	}
	//영화 객체만의 이름을 저장하는 vmoviename 리턴
	public Vector<String>vmovie(){
		Vector<String>vmoviename = new Vector<String>();
		for(int i=0;i<vect.size();i++) {
			if(vect.get(i) instanceof Movie)
				vmoviename.add(vect.get(i).name);
		}
		return vmoviename;
	}
	//책 객체만의 이름을 저장하는 vbookname 리턴
	public Vector<String>vbook(){
		Vector<String>vbookname = new Vector<String>();
		for(int i=0;i<vect.size();i++) {
			if(vect.get(i) instanceof Book)
				vbookname.add(vect.get(i).name);
		}
		return vbookname;
	}
	//영화객체의 값이 변경되었을 때 변경된 값 적용하는 함수
	public void setMovie(String name,String director,String actor,String genre,String level,int year,String filepath,int star,String intro,String comment,int index) {
	     Movie mv=(Movie)vect.get(index);
	      mv.name=name;
	      mv.director=director;
	      mv.actor=actor;
	      mv.genre=genre;
	      mv.level=level;
	      mv.year=year;
	      mv.filepath=filepath;
	      mv.star=star;
	      mv.intro=intro;
	      mv.comment=comment;
	   }
	//책객체의 값이 변경되었을 때 변경된 값 적용하는 함수
	public void setBook(String name,String author,String publisher,int year,String filepath,int star,String intro,String comment,int index) {
		Book bk =(Book)vect.get(index);
		bk.name=name;
		bk.author=author;
		bk.publisher=publisher;
		bk.year=year;
		bk.filepath=filepath;
		bk.star=star;
		bk.intro=intro;
		bk.comment=comment;
	}
	//삭제 함수 
	public void del(int index) {
		vect.remove(index);
	}
}

public class MyNotes extends JFrame{
	
	ItemCollections items = new ItemCollections(); //아이템컬렉션 객체 생성
	//다이알로그를 2개 생성해서 라디오버튼쌍도 2개씩 생성
	JRadioButton r1 = new JRadioButton("Movie"); 
	JRadioButton r2 = new JRadioButton("Book");
	JRadioButton r3 = new JRadioButton("Movie");
	JRadioButton r4 = new JRadioButton("Book");
	
	//2개의 다이알로그에 영화,책 정보를 담는 패널 각각 생성
	JPanel JMovie = new JPanel();
	JPanel JBook = new JPanel();
	JPanel JMovie2 = new JPanel();
	JPanel JBook2 = new JPanel();
	MyDialog dialog = new MyDialog(this,"입력"); //MyDialog객체 생성
	cdialog cd = new cdialog(this,"입력"); //cdialog객체 생성
	
	JList<String>allname = new JList<String>(items.vall()); //객체 전체의 이름을 갖는 리스트
	JList<String>moviename = new JList<String>(items.vmovie()); //영화 이름을 갖는 리스트
	JList<String>bookname = new JList<String>(items.vbook()); //책 이름을 갖는 리스트 
	
	Vector<String>vsearchname = new Vector<String>();
	JList<String>searchname = new JList<String>(vsearchname); //사용자가 찾는 이름을 담는 리스트 
	
	String name; //제목
	int year; //년도
	int star; //별점
	String intro; //줄거리,책소개
	String comment; //감상평
	String director; //감독
	String actor; //배우
	String genre; //장르 
	String level; //등급
	String author; //작가
	String publisher; //출판사
	String filepath; //이미지 파일 경로 
	ImageIcon icon = new ImageIcon("images/gm_noimage.png"); //기본 이미지
	Image img = icon.getImage(); //이미지 객체 생성 
	
	//리스트 선택마다 바뀌는 출력 정보 
	JLabel Jiname;JLabel Jidirector;JLabel Jiactor;JLabel Jigenre;JLabel Jilevel;
	JLabel Jiyear;JLabel Jistar;
	JLabel Jbname;JLabel Jbauthor;JLabel Jbpublisher;
	JLabel Jbyear;JLabel Jbstar;
	JTextArea ta;JTextArea tb;
	//출력 정보 중 정보이름 필드 
	JLabel iname;JLabel idirector;JLabel iactor;JLabel igenre;
	JLabel ilevel;JLabel iyear;JLabel istar;
	JLabel bname;JLabel bauthor;JLabel bpublisher;
	JLabel byear;JLabel bstar;
	
	int index=999; //리스트의 인덱스를 담는 index선언 

	//다이알로그에 사용자가 추가하는 내용들 멤버변수 선언 
	JTextField Jtitle;JTextField Jdirector;JTextField Jactor;
	JComboBox<String> Jgenre;JComboBox<String> Jlevel;JComboBox<Integer> Jyear;
	JTextField tp;JSlider Jeval;JTextArea Jintro;JTextArea Jcomment;
	JTextField Jtitle2;JTextField Jauthor;JTextField Jpublisher;
	JComboBox<Integer> Jyear2;JTextField tp2;JSlider Jeval2;
	JTextArea Bintro; JTextArea Bcomment;
	
	JTextField Jtitle3;JTextField Jdirector3;JTextField Jactor3;
	JComboBox<String> Jgenre3;JComboBox<String> Jlevel3;JComboBox<Integer> Jyear3;
	JTextField tp3;JSlider Jeval3;
	JTextArea Jintro3;JTextArea Jcomment3;
	
	JTextField Jtitle4;JTextField Jauthor4;JTextField Jpublisher4;
	JComboBox<Integer> Jyear4;
	JTextField tp4;JSlider Jeval4;
	JTextArea Bintro4; JTextArea Bcomment4;
	
	JPanel movieinfo; JPanel bookinfo;
	
	public MyNotes() {
		setTitle("JAVA 005분반 1916955 최수연");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		createMenu(); //메뉴 생성 함수 
	
		//상단에 제목과 현재 시간 출력하는 패널 생성
		JPanel p1 = new JPanel();
		p1.setBounds(0,8,883,32);
		p1.setLayout(new BorderLayout());
		JLabel title = new JLabel("                   My Notes");
		title.setOpaque(true);
		title.setForeground(Color.BLUE);
		title.setFont(new Font("고딕",Font.ITALIC|Font.BOLD,30));
		p1.add(title);
		JLabel timerLabel = new JLabel();
		timerLabel.setFont(new Font("고딕",Font.ITALIC,20));
		TimerRunnable runnable = new TimerRunnable(timerLabel);
		Thread th = new Thread(runnable);
		p1.add(timerLabel,BorderLayout.EAST);
		c.add(p1);
		
		//Jlist에 addListSelectionListener추가 
		allname.addListSelectionListener(new listselection());
		moviename.addListSelectionListener(new listselection());
		bookname.addListSelectionListener(new listselection());
		searchname.addListSelectionListener(new listselection());
		
		//탭팬에 추가할 패널에 각각의 Jlist추가 
		JPanel allPanel = new JPanel();
		allPanel.setLayout(new BorderLayout());
		allPanel.setSize(200,500);
		allPanel.add(new JScrollPane(allname),BorderLayout.CENTER);
		
		JPanel moviePanel = new JPanel();
		moviePanel.setLayout(new BorderLayout());
		moviePanel.setSize(200,500);
		moviePanel.add(new JScrollPane(moviename),BorderLayout.CENTER);
		
		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new BorderLayout());
		bookPanel.setSize(200,500);
		bookPanel.add(new JScrollPane(bookname),BorderLayout.CENTER);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setSize(200,500);
		searchPanel.add(new sp(),BorderLayout.CENTER);
		
		//탭팬과 추가버튼을 출력하는 패널 생성
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBounds(0,50,250,540);
		
		JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
		pane.addTab("전체",allPanel);
		pane.addTab("영화",moviePanel);
		pane.addTab("도서",bookPanel);
		pane.addTab("검색",searchPanel);
		p2.add(pane,BorderLayout.CENTER);
		c.add(p2);
		
		//추가버튼 생성, 추가버튼 클릭시 dialog객체 추가 
		JButton jadd = new JButton("추가");
		jadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});
		jadd.setBounds(84,600,65,30);
		c.add(jadd);
		JBook.setVisible(false);
		dialog.setSize(500,900);
		
		//이미지,줄거리,감상평 출력하는 패널 생성
		JPanel pp = new JPanel();
		pp.setBounds(252,50,632,580);
		pp.setLayout(new BorderLayout());
		
		JPanel p3 = new JPanel();
		p3.setLayout(null);
		Border border0 = BorderFactory.createTitledBorder("상세 보기");
		p3.setBorder(border0);
		
		drawPanel dp = new drawPanel(); //이미지를 출력하는 패널
		dp.setBounds(10,15,190,218);
		p3.add(dp); //이미지패널 추가 
		
		//영화정보 추가후 영화 리스트 선택시 보이는 패널 
		movieinfo = new JPanel();
		movieinfo.setLayout(null);
		movieinfo.setBounds(195,15,300,220);
		iname = new JLabel("제목");
		iname.setBounds(10,10,40,20);
		idirector = new JLabel("감독");
		idirector.setBounds(10,40,40,20);
		iactor = new JLabel("배우");
		iactor.setBounds(10,70,40,20);
		igenre = new JLabel("장르");
		igenre.setBounds(10,100,40,20);
		ilevel = new JLabel("등급");
		ilevel.setBounds(10,130,40,20);
		iyear = new JLabel("개봉년도");
		iyear.setBounds(10,160,60,20);
		istar = new JLabel("별점");
		istar.setBounds(10,190,40,20);
		movieinfo.add(iname);movieinfo.add(idirector);movieinfo.add(iactor);movieinfo.add(igenre);
		movieinfo.add(ilevel);movieinfo.add(iyear);movieinfo.add(istar);
		//리스트 선택 시 출력되는 정보
		Jiname = new JLabel("");
		Jiname.setBounds(80,10,100,20);
		Jidirector = new JLabel("");
		Jidirector.setBounds(80,40,100,20);
		Jiactor = new JLabel("");
		Jiactor.setBounds(80,70,270,20);
		Jigenre = new JLabel("");
		Jigenre.setBounds(80,100,100,20);
		Jilevel = new JLabel("");
		Jilevel.setBounds(80,130,100,20);
		Jiyear = new JLabel("");
		Jiyear.setBounds(80,160,100,20);
		Jistar = new JLabel("");
		Jistar.setBounds(80,190,100,20);
		movieinfo.add(Jiname);movieinfo.add(Jidirector);movieinfo.add(Jiactor);movieinfo.add(Jigenre);
		movieinfo.add(Jilevel);movieinfo.add(Jiyear);movieinfo.add(Jistar);
		movieinfo.setVisible(false);
		p3.add(movieinfo);
		
		//책정보 추가후 책 리스트 선택시 보이는 패널 
		bookinfo = new JPanel();
		bookinfo.setLayout(null);
		bookinfo.setBounds(195,15,300,220);
		bname = new JLabel("제목");
		bname.setBounds(10,10,40,20);
		bauthor = new JLabel("저자");
		bauthor.setBounds(10,55,40,20);
		bpublisher = new JLabel("출판사");
		bpublisher.setBounds(10,100,40,20);
		byear = new JLabel("출판년도");
		byear.setBounds(10,145,60,20);
		bstar = new JLabel("별점");
		bstar.setBounds(10,190,40,20);
		bookinfo.add(bname);bookinfo.add(bauthor);bookinfo.add(bpublisher);bookinfo.add(byear);bookinfo.add(bstar);
		Jbname= new JLabel("");
		Jbname.setBounds(80,10,100,20);
		Jbauthor= new JLabel("");
		Jbauthor.setBounds(80,55,270,20);
		Jbpublisher= new JLabel("");
		Jbpublisher.setBounds(80,100,100,20);
		Jbyear= new JLabel("");
		Jbyear.setBounds(80,145,100,20);
		Jbstar= new JLabel("");
		Jbstar.setBounds(80,190,100,20);
		bookinfo.add(Jbname);bookinfo.add(Jbauthor);bookinfo.add(Jbpublisher);
		bookinfo.add(Jbyear);bookinfo.add(Jbstar);
		bookinfo.setVisible(false);
		p3.add(bookinfo);
	
		JPanel psum = new JPanel(); //줄거리 출력 패널
		psum.setLayout(new BorderLayout());
		ta = new JTextArea(7,55);
		Border border1 = BorderFactory.createTitledBorder("줄거리");
		psum.setBorder(border1);
		psum.add(new JScrollPane(ta),BorderLayout.CENTER);
		psum.setBounds(5,237,621,148);
		p3.add(psum); 
	
		JPanel pcom = new JPanel(); //감상평 출력 패널 
		pcom.setLayout(new BorderLayout());
		tb = new JTextArea(7,55);
		Border border2 = BorderFactory.createTitledBorder("감상평");
		pcom.setBorder(border2);
		pcom.add(new JScrollPane(tb),BorderLayout.CENTER);
		pcom.setBounds(5,385,621,148);
		p3.add(pcom);
		pp.add(p3,BorderLayout.CENTER);
		
		pp.add(new pbutton(),BorderLayout.SOUTH);
		c.add(pp);
		setSize(900,700);
		setVisible(true);
		th.start();
	}
	
	//수정과 삭제버튼을 담는 pbutton패널 생성 
	class pbutton extends JPanel{
		public pbutton() {
	
		cd.setSize(500,900); //수정 버튼 시 눌리는 다이알로그 setSize
	
		JButton jchange = new JButton("수정");
		jchange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(index != 999) { //리스트 값이 선택되었을 때 
					if(items.vect.get(index) instanceof Movie) {
						//선택된 객체가 Movie타입일 때, 다이알로그에 객체의 값들로 setText하기 
						r3.setSelected(true);
						JBook2.setVisible(false);
						JMovie2.setVisible(true);
						Jtitle3.setText(((Movie)items.vect.get(index)).name);	
						Jdirector3.setText(((Movie)items.vect.get(index)).director);
						Jactor3.setText(((Movie)items.vect.get(index)).actor);	
						Jgenre3.setSelectedItem(((Movie)items.vect.get(index)).genre);
						Jlevel3.setSelectedItem(((Movie)items.vect.get(index)).level);
						Jyear3.setSelectedItem(((Movie)items.vect.get(index)).year);	
						tp3.setText(((Movie)items.vect.get(index)).filepath);
						Jeval3.setValue((((Movie)items.vect.get(index)).star));
						Jintro3.setText(((Movie)items.vect.get(index)).intro);
						Jcomment3.setText(((Movie)items.vect.get(index)).comment);
					}
					else if(items.vect.get(index) instanceof Book) {
						//선택된 객체가 Book타입일 때, 다이알로그에 객체의 값들로 setText하기 
						r4.setSelected(true);
						JBook2.setVisible(true);
						JMovie2.setVisible(false);
						Jtitle4.setText(((Book)items.vect.get(index)).name);
						Jauthor4.setText(((Book)items.vect.get(index)).author);
						Jpublisher4.setText(((Book)items.vect.get(index)).publisher);
						Jyear4.setSelectedItem((((Book)items.vect.get(index)).year));
						tp4.setText(((Book)items.vect.get(index)).filepath);
						Jeval4.setValue((((Book)items.vect.get(index)).star));
						Bintro4.setText(((Book)items.vect.get(index)).intro); 
						Bcomment4.setText(((Book)items.vect.get(index)).comment);			
						}
					
						cd.setVisible(true); //다이알로그 보여주기 
						}
					}
			});
		
			JButton jdelete = new JButton("삭제");
			jdelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int res = JOptionPane.showConfirmDialog(null,"정말 삭제하시겠습니까?","삭제 확인",JOptionPane.YES_NO_OPTION);
					if(res == JOptionPane.YES_OPTION) {
						if(index != 999) {
							if(items.vect.get(index) instanceof Movie) {
								//선택된 객체를 삭제, 리스트의 값들 재설정 
								items.del(index);
								allname.setListData(items.vall());
								moviename.setListData(items.vbook());
								//삭제를 위해 선택된 객체의 패널 정보들 초기화 
								Jiname.setText("");
								Jidirector.setText("");
								Jiactor.setText("");
								Jigenre.setText("");
								Jilevel.setText("");
								Jiyear.setText("");
								Jistar.setText("");
								ta.setText("");
								tb.setText("");
								icon =  new ImageIcon("images/gm_noimage.png");
								img = icon.getImage();
								
								movieinfo.setVisible(false);
							}
							else if(items.vect.get(index) instanceof Book) {
								//선택된 객체를 삭제, 리스트의 값들 재설정 
								items.del(index);
								allname.setListData(items.vall());
								bookname.setListData(items.vbook());
								//삭제를 위해 선택된 객체의 패널 정보들 초기화 
								Jbname.setText("");
								Jbauthor.setText("");
								Jbpublisher.setText("");
								Jbyear.setText("");
								Jbstar.setText("");
								ta.setText("");
								tb.setText("");
								icon =  new ImageIcon("images/gm_noimage.png");
								img = icon.getImage();
								
								bookinfo.setVisible(false);
							}
						}
					}
				}
			});
			add(jchange); 
			add(jdelete);
		}
	}
	
	//'추가'버튼 누를 시의 입력 다이얼로그
	class MyDialog extends JDialog{
		public MyDialog(JFrame frame,String title) {
			super(frame,title);
			setLayout(null);
			
			//라디오 버튼 Movie,Book 추가 
			JPanel North = new JPanel();
			r1.setSelected(true); //디폴트상태는 Movie버튼 선택상태
			ButtonGroup choice = new ButtonGroup(); // 버튼 그룹 생성
			choice.add(r1); choice.add(r2);
			North.add(r1); North.add(r2);  // JPanel에 추가
			North.setBounds(125,10,200,30);
			add(North);
			MyItemListener listener = new MyItemListener();
			r1.addItemListener (listener);
			r2.addItemListener (listener);
			
			//다이알로그의 영화 정보를 담는 JMovie 추가 
			JMovie.setBounds(0,50,485,800);
			add(JMovie);
			Border border3 = BorderFactory.createTitledBorder("영화 정보");
			JMovie.setBorder(border3);
			JMovie.setLayout(null);
			
			JLabel aa= new JLabel ("제목");
			aa.setBounds(30,50,40,20);
			JLabel bb=new JLabel ("감독");
			bb.setBounds(30,110,40,20);
			JLabel cc=new JLabel ("배우");
			cc.setBounds(30,170,40,20);
			JLabel dd=new JLabel ("장르");
			dd.setBounds(30,230,40,20);
			JLabel ee=new JLabel ("등급");
			ee.setBounds(30,290,40,20);
			JLabel ff=new JLabel ("개봉년도");
			ff.setBounds(30,350,80,20);
			JLabel gg=new JLabel ("포스터");
			gg.setBounds(30,410,40,20);
			JLabel hh=new JLabel ("별점");
			hh.setBounds(30,465,40,20);
			JLabel ii=new JLabel ("줄거리");
			ii.setBounds(30,545,40,20);
			JLabel jj=new JLabel ("감상평");
			jj.setBounds(30,630,40,20);
			JMovie.add(aa);JMovie.add(bb);JMovie.add(cc);JMovie.add(dd);JMovie.add(ee);
			JMovie.add(ff);JMovie.add(gg);JMovie.add(hh);JMovie.add(ii);JMovie.add(jj);
			
			//사용자로부터 입력받는 필드들 (영화)
			Jtitle = new JTextField(20);
			Jtitle.setBounds(100,40,350,40);
			JMovie.add(Jtitle);
			Jdirector = new JTextField(20);
			Jdirector.setBounds(100,100,350,40);
			JMovie.add(Jdirector);
			Jactor = new JTextField(20);
			Jactor.setBounds(100,160,350,40);
			JMovie.add(Jactor);
			
			String[] Sgenre = {"드라마","미스터리,스릴러","범죄","애니메이션","액션"};
			String[] Slevel = {"전체","12세 이상","15세 이상","청소년 관람 불가"};
			Integer[] Syear = {2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010};			
			Jgenre = new JComboBox<String>(Sgenre);
			Jgenre.setBounds(100,220,350,40);
			JMovie.add(Jgenre);
			Jlevel = new JComboBox<String>(Slevel);
			Jlevel.setBounds(100,280,350,40);
			JMovie.add(Jlevel);
			Jyear = new JComboBox<Integer>(Syear);
			Jyear.setBounds(100,340,350,40);
			JMovie.add(Jyear);
			
			//포스터 이미지 설정 
			tp = new JTextField(10);
			JButton jp = new JButton("불러오기");
			jp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();//파일 다이얼로그 생성
					int ret = chooser.showOpenDialog(null);
					filepath = chooser.getSelectedFile().getPath();//사용자가 선택한 파일경로 저장
					tp.setText(filepath);
					icon =  new ImageIcon(filepath);
				}
			});
			tp.setBounds(100,405,250,28);
			jp.setBounds(360,403,100,30);
			JMovie.add(tp); JMovie.add(jp);
			
			//별점 슬라이더 설정 
			Jeval = new JSlider(JSlider.HORIZONTAL,1,10,5);
			Jeval.setPaintLabels(true);
			Jeval.setPaintTicks(true);
			Jeval.setPaintTrack(true);
			Jeval.setMajorTickSpacing(1);
			Jeval.setBounds(100,460,350,40);
			JMovie.add(Jeval);
			
			Jintro = new JTextArea(4,30);
			Jcomment = new JTextArea(4,30);
			Jintro.setBounds(80,510,370,85);
			JMovie.add(Jintro);
			Jcomment.setBounds(80,605,370,85);
			JMovie.add(Jcomment);
			
			//OK버튼 누를 시, 입력된 객체 정보 저장 
			JButton Jok = new JButton("OK");
			Jok.setBounds(220,700,65,30);
			JMovie.add(Jok);
			Jok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					name= Jtitle.getText();
					director = Jdirector.getText();
					actor = Jactor.getText();
					genre = Jgenre.getSelectedItem().toString();
					level = Jlevel.getSelectedItem().toString();
					year =  (int)Jyear.getSelectedItem();
					star = Jeval.getValue();
					intro = Jintro.getText();
					comment = Jcomment.getText();
					//Movie객체를 생성해서 모든 객체를 담는 vect에 저장하고, 리스트값 재설정 
					Movie m = new Movie(name,director,actor,genre,level,year,filepath,star,intro,comment);
					items.vect.add(m);
					allname.setListData(items.vall());
					moviename.setListData(items.vmovie());
					
					dialog.setVisible(false);
					Jtitle.setText("");
					Jdirector.setText("");
					Jactor.setText("");
					Jgenre= new JComboBox<String>(Sgenre);
					Jlevel= new JComboBox<String>(Slevel);
					Jyear= new JComboBox<Integer>(Syear);
					Jeval= new JSlider(JSlider.HORIZONTAL,1,10,5);
					tp.setText("");
					Jintro.setText("");
					Jcomment.setText("");
				}
			});
			
			JBook.setBounds(0,50,485,800);
			add(JBook);
			JBook.setLayout(null);
			Border border4 = BorderFactory.createTitledBorder("도서 정보");
			JBook.setBorder(border4);
			JLabel a= new JLabel ("제목");
			a.setBounds(30,60,40,20);
			JLabel b= new JLabel ("저자");
			b.setBounds(30,130,40,20);
			JLabel c= new JLabel ("출판사");
			c.setBounds(30,200,40,20);
			JLabel d= new JLabel ("출판년도");
			d.setBounds(30,267,80,20);
			JLabel e= new JLabel ("책 이미지");
			e.setBounds(30,340,90,20);
			JLabel f= new JLabel ("별점");
			f.setBounds(30,410,40,20);
			JLabel g= new JLabel ("책 소개");
			g.setBounds(30,510,40,20);
			JLabel h= new JLabel ("감상평");
			h.setBounds(30,600,40,20);
			JBook.add(a);JBook.add(b);JBook.add(c);JBook.add(d);JBook.add(e);
			JBook.add(f);JBook.add(g);JBook.add(h);
			
			//사용자로부터 입력받는 필드들 (책)
			Jtitle2 = new JTextField(20);
			Jauthor = new JTextField(20);
			Jpublisher = new JTextField(20);
			Jtitle2.setBounds(100,50,350,40);
			Jauthor.setBounds(100,120,350,40);
			Jpublisher.setBounds(100,190,350,40);
			JBook.add(Jtitle2);JBook.add(Jauthor);JBook.add(Jpublisher);
			
			Integer[] Syear2 = {2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010};			
			Jyear2 = new JComboBox<Integer>(Syear2);
			Jyear2.setBounds(100,260,350,40);
			JBook.add(Jyear2);
			
			//포스터 이미지 설정 
			tp2 = new JTextField(20);
			JButton jp2 = new JButton("불러오기");
			jp2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();//파일 다이얼로그 생성
					int ret = chooser.showOpenDialog(null);
					filepath = chooser.getSelectedFile().getPath();
					tp2.setText(filepath);
					icon =  new ImageIcon(filepath);
				}
			});
			tp2.setBounds(100,335,250,28);
			jp2.setBounds(360,333,100,30);
			JBook.add(tp2); JBook.add(jp2);
			
			//별점 슬라이더 설정 
			Jeval2 = new JSlider(JSlider.HORIZONTAL,1,10,5);
			Jeval2.setPaintLabels(true);
			Jeval2.setPaintTicks(true);
			Jeval2.setPaintTrack(true);
			Jeval2.setMajorTickSpacing(1);
			Jeval2.setBounds(100,413,350,40);
			JBook.add(Jeval2);
			
			Bintro = new JTextArea(4,30);
			Bcomment = new JTextArea(4,30);
			Bintro.setBounds(92,475,375,85);
			Bcomment.setBounds(92,572,375,85);
			JBook.add(Bintro); JBook.add(Bcomment);
			
			//OK버튼 누를 시, 입력된 객체 정보 저장 
			JButton Jok2 = new JButton("OK");
			Jok2.setBounds(220,700,65,30);
			JBook.add(Jok2);
			Jok2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					name= Jtitle2.getText();
					author = Jauthor.getText();
					publisher = Jpublisher.getText();
					year = (int)Jyear2.getSelectedItem();
					star = Jeval2.getValue(); //별점 내용 저장 
					intro = Bintro.getText();
					comment=Bcomment.getText();
					//Book객체를 생성해서 모든 객체를 담는 vect에 저장하고, 리스트값 재설정 
					Book b = new Book(name,author,publisher,year,filepath,star,intro,comment);
					items.vect.add(b);
					allname.setListData(items.vall());
					bookname.setListData(items.vbook());
					
					dialog.setVisible(false);
					Jtitle2.setText("");
					Jauthor.setText("");
					Jpublisher.setText("");
					Jyear2=new JComboBox<Integer>(Syear2);
					Jeval2= new JSlider(JSlider.HORIZONTAL,1,10,5);
					tp2.setText("");
					Bintro.setText("");
					Bcomment.setText("");
					r1.setSelected(true);
				}
			});
		}	
	}
	
	//'수정'버튼 누를 시의 입력 다이얼로그
	class cdialog extends JDialog{
		public cdialog(JFrame frame,String title) {
			super(frame,title);	
			setLayout(null);	
			
			//라디오 버튼 Movie,Book 추가 
			JPanel North2 = new JPanel();
			r3.setSelected(true); //디폴트상태는 Movie버튼 선택상태
			ButtonGroup choice2 = new ButtonGroup(); // 버튼 그룹 생성
			choice2.add(r3); choice2.add(r4);
			North2.add(r3); North2.add(r4);  // JPanel에 추가
			North2.setBounds(125,10,200,30);
			add(North2);
			
			JBook2.setVisible(false); //디폴트상태는 Movie이므로 책정보 가리기 
			JMovie2.setBounds(0,50,485,800);
			add(JMovie2);
			Border border3 = BorderFactory.createTitledBorder("영화 정보");
			JMovie2.setBorder(border3);
			JMovie2.setLayout(null);
			
			JLabel aa= new JLabel ("제목");
			aa.setBounds(30,50,40,20);
			JLabel bb=new JLabel ("감독");
			bb.setBounds(30,110,40,20);
			JLabel cc=new JLabel ("배우");
			cc.setBounds(30,170,40,20);
			JLabel dd=new JLabel ("장르");
			dd.setBounds(30,230,40,20);
			JLabel ee=new JLabel ("등급");
			ee.setBounds(30,290,40,20);
			JLabel ff=new JLabel ("개봉년도");
			ff.setBounds(30,350,80,20);
			JLabel gg=new JLabel ("포스터");
			gg.setBounds(30,410,40,20);
			JLabel hh=new JLabel ("별점");
			hh.setBounds(30,465,40,20);
			JLabel ii=new JLabel ("줄거리");
			ii.setBounds(30,545,40,20);
			JLabel jj=new JLabel ("감상평");
			jj.setBounds(30,630,40,20);
			JMovie2.add(aa);JMovie2.add(bb);JMovie2.add(cc);JMovie2.add(dd);JMovie2.add(ee);
			JMovie2.add(ff);JMovie2.add(gg);JMovie2.add(hh);JMovie2.add(ii);JMovie2.add(jj);
			
			//사용자로부터 입력받는 필드들 (영화)
			Jtitle3 = new JTextField(20);
			Jtitle3.setBounds(100,40,350,40);
			JMovie2.add(Jtitle3);
			Jdirector3 = new JTextField(20);
			Jdirector3.setBounds(100,100,350,40);
			JMovie2.add(Jdirector3);
			Jactor3 = new JTextField(20);
			Jactor3.setBounds(100,160,350,40);
			JMovie2.add(Jactor3);
			
			String[] Sgenre = {"드라마","미스터리,스릴러","범죄","애니메이션","액션"};
			String[] Slevel = {"전체","12세 이상","15세 이상","청소년 관람 불가"};
			Integer[] Syear = {2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010};			
			Jgenre3 = new JComboBox<String>(Sgenre);
			Jgenre3.setBounds(100,220,350,40);
			JMovie2.add(Jgenre3);
			Jlevel3 = new JComboBox<String>(Slevel);
			Jlevel3.setBounds(100,280,350,40);
			JMovie2.add(Jlevel3);
			Jyear3 = new JComboBox<Integer>(Syear);
			Jyear3.setBounds(100,340,350,40);
			JMovie2.add(Jyear3);
			
			//포스터 이미지 설정 
			tp3 = new JTextField(10);
			JButton jp3 = new JButton("불러오기");
			jp3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();//파일 다이얼로그 생성
					int ret = chooser.showOpenDialog(null);
					filepath = chooser.getSelectedFile().getPath();
					tp3.setText(filepath);
					icon =  new ImageIcon(filepath);
				}
			});
			tp3.setBounds(100,405,250,28);
			jp3.setBounds(360,403,100,30);
			JMovie2.add(tp3); JMovie2.add(jp3);
			
			//별점 슬라이더 설정 
			Jeval3 = new JSlider(JSlider.HORIZONTAL,1,10,5);
			Jeval3.setPaintLabels(true);
			Jeval3.setPaintTicks(true);
			Jeval3.setPaintTrack(true);
			Jeval3.setMajorTickSpacing(1);
			Jeval3.setBounds(100,460,350,40);
			JMovie.add(Jeval3);
			
			Jintro3 = new JTextArea(4,30);
			Jcomment3 = new JTextArea(4,30);
			Jintro3.setBounds(80,510,370,85);
			JMovie2.add(Jintro3);
			Jcomment3.setBounds(80,605,370,85);
			JMovie2.add(Jcomment3);
			
			//OK버튼 누를 시 수정된 값을 setMovie함수로 보내고 기본패널에 바뀐 값 적용 
			JButton Jok3 = new JButton("OK");
			Jok3.setBounds(220,700,65,30);
			JMovie2.add(Jok3);
			Jok3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					items.setMovie(Jtitle3.getText(),Jdirector3.getText(),Jactor3.getText(),(String)Jgenre3.getSelectedItem(),(String)Jlevel3.getSelectedItem(),(int)Jyear3.getSelectedItem(),tp3.getText(),Jeval3.getValue(),Jintro3.getText(),Jcomment3.getText(),index);		
					Jiname.setText(((Movie)items.vect.get(index)).name);
					Jidirector.setText(((Movie)items.vect.get(index)).director);
					Jiactor.setText(((Movie)items.vect.get(index)).actor);
					Jigenre.setText(((Movie)items.vect.get(index)).genre);
					Jilevel.setText(((Movie)items.vect.get(index)).level);
					Jiyear.setText(Integer.toString(((Movie)items.vect.get(index)).year));
					Jistar.setText(Integer.toString(((Movie)items.vect.get(index)).star));
					ta.setText(((Movie)items.vect.get(index)).intro);
					tb.setText(((Movie)items.vect.get(index)).comment);
					icon =  new ImageIcon(((Movie)items.vect.get(index)).filepath);
					img = icon.getImage();
					cd.setVisible(false);
				}
			});
			
			JBook2.setBounds(0,50,485,800);
			add(JBook2);
			JBook2.setLayout(null);
			Border border4 = BorderFactory.createTitledBorder("도서 정보");
			JBook2.setBorder(border4);
			JLabel a= new JLabel ("제목");
			a.setBounds(30,60,40,20);
			JLabel b= new JLabel ("저자");
			b.setBounds(30,130,40,20);
			JLabel c= new JLabel ("출판사");
			c.setBounds(30,200,40,20);
			JLabel d= new JLabel ("출판년도");
			d.setBounds(30,267,80,20);
			JLabel e= new JLabel ("책 이미지");
			e.setBounds(30,340,90,20);
			JLabel f= new JLabel ("별점");
			f.setBounds(30,410,40,20);
			JLabel g= new JLabel ("책 소개");
			g.setBounds(30,510,40,20);
			JLabel h= new JLabel ("감상평");
			h.setBounds(30,600,40,20);
			JBook2.add(a);JBook2.add(b);JBook2.add(c);JBook2.add(d);JBook2.add(e);
			JBook2.add(f);JBook2.add(g);JBook2.add(h);
			
			//사용자로부터 입력받는 필드들 (책)
			Jtitle4 = new JTextField(20);
			Jauthor4 = new JTextField(20);
			Jpublisher4 = new JTextField(20);
			Jtitle4.setBounds(100,50,350,40);
			Jauthor4.setBounds(100,120,350,40);
			Jpublisher4.setBounds(100,190,350,40);
			JBook2.add(Jtitle4);JBook2.add(Jauthor4);JBook2.add(Jpublisher4);
			
			Integer[] Syear2 = {2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010};			
			Jyear4 = new JComboBox<Integer>(Syear2);
			Jyear4.setBounds(100,260,350,40);
			JBook2.add(Jyear4);
			
			//포스터 이미지 설정 
			tp4 = new JTextField(20);
			JButton jp4 = new JButton("불러오기");
			jp4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();//파일 다이얼로그 생성
					int ret = chooser.showOpenDialog(null);
					filepath = chooser.getSelectedFile().getPath();
					tp4.setText(filepath);
					icon =  new ImageIcon(filepath);
				}
			});
			tp4.setBounds(100,335,250,28);
			jp4.setBounds(360,333,100,30);
			JBook2.add(tp4); JBook2.add(jp4);
			
			//별점 슬라이더 설정 
			Jeval4 = new JSlider(JSlider.HORIZONTAL,1,10,5);
			Jeval4.setPaintLabels(true);
			Jeval4.setPaintTicks(true);
			Jeval4.setPaintTrack(true);
			Jeval4.setMajorTickSpacing(1);
			Jeval4.setBounds(100,413,350,40);
			JBook2.add(Jeval4);
			Bintro4 = new JTextArea(4,30);
			Bcomment4 = new JTextArea(4,30);
			Bintro4.setBounds(92,475,375,85);
			Bcomment4.setBounds(92,572,375,85);
			JBook2.add(Bintro4); JBook2.add(Bcomment4);
			
			//OK버튼 누를 시 수정된 값을 setBook함수로 보내고 기본패널에 바뀐 값 적용 
			JButton Jok4 = new JButton("OK");
			Jok4.setBounds(220,700,65,30);
			JBook2.add(Jok4);
			Jok4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					items.setBook(Jtitle4.getText(),Jauthor4.getText(),Jpublisher4.getText(),(int)Jyear4.getSelectedItem(),tp4.getText(),Jeval4.getValue(),Bintro4.getText(),Bcomment4.getText(),index);
					Jbname.setText(((Book)items.vect.get(index)).name);
					Jbauthor.setText(((Book)items.vect.get(index)).author);
					Jbpublisher.setText(((Book)items.vect.get(index)).publisher);
					Jbyear.setText(Integer.toString(((Book)items.vect.get(index)).year));
					Jbstar.setText(Integer.toString(((Book)items.vect.get(index)).star));
					ta.setText(((Book)items.vect.get(index)).intro);
					tb.setText(((Book)items.vect.get(index)).comment);
					icon =  new ImageIcon(((Book)items.vect.get(index)).filepath);
					img = icon.getImage();
					cd.setVisible(false);
				}
			});
		}	
	}
	
	//화면에 알맞은 이미지를 출력하는 패널
	class drawPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this); //패널에 꽉차게 이미지 출력
			repaint();
		}
	}
	
	//리스트 선택 시 불리는 리스트셀렉션리스너
	class listselection implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			Object o = e.getSource();
			
			//선택된 리스트가 각각 allname,moviename,bookname,searchname일 때 구분
			if(!e.getValueIsAdjusting()) {
				if(o.equals(allname)) {
					Object s= (Object)allname.getSelectedValue();			
					for(int i=0;i<items.vall().size();i++){	
						if(items.vall().get(i).equals(s)){
							index=i;
							if(items.vect.get(i) instanceof Movie) {
								Jiname.setText(((Movie)items.vect.get(i)).name);
								Jidirector.setText(((Movie)items.vect.get(i)).director);
								Jiactor.setText(((Movie)items.vect.get(i)).actor);
								Jigenre.setText(((Movie)items.vect.get(i)).genre);
								Jilevel.setText(((Movie)items.vect.get(i)).level);
								Jiyear.setText(Integer.toString(((Movie)items.vect.get(i)).year));
								Jistar.setText(Integer.toString(((Movie)items.vect.get(i)).star));
								ta.setText(((Movie)items.vect.get(i)).intro);
								tb.setText(((Movie)items.vect.get(i)).comment);
								icon =  new ImageIcon(((Movie)items.vect.get(i)).filepath);
								img = icon.getImage();
								bookinfo.setVisible(false);
								movieinfo.setVisible(true);
								}
							else if(items.vect.get(i) instanceof Book) {
								Jbname.setText(((Book)items.vect.get(i)).name);
								Jbauthor.setText(((Book)items.vect.get(i)).author);
								Jbpublisher.setText(((Book)items.vect.get(i)).publisher);
								Jbyear.setText(Integer.toString(((Book)items.vect.get(i)).year));
								Jbstar.setText(Integer.toString(((Book)items.vect.get(i)).star));
								ta.setText(((Book)items.vect.get(i)).intro);
								tb.setText(((Book)items.vect.get(i)).comment);
								icon =  new ImageIcon(((Book)items.vect.get(i)).filepath);
								img = icon.getImage();
								movieinfo.setVisible(false);
								bookinfo.setVisible(true);
								}
							}
						}	
					}
				else if(o.equals(moviename)) {
					String s= (String)moviename.getSelectedValue();
					for(int i=0;i<items.vall().size();i++){	
						if(items.vall().get(i).equals(s)){
							index=i;
							if(items.vect.get(i) instanceof Movie) {
								Jiname.setText(items.vect.get(i).name);
								Jidirector.setText(((Movie)items.vect.get(i)).director);
								Jiactor.setText(((Movie)items.vect.get(i)).actor);
								Jigenre.setText(((Movie)items.vect.get(i)).genre);
								Jilevel.setText(((Movie)items.vect.get(i)).level);
								Jiyear.setText(Integer.toString(((Movie)items.vect.get(i)).year));
								Jistar.setText(Integer.toString(((Movie)items.vect.get(i)).star));
								ta.setText(((Movie)items.vect.get(i)).intro);
								tb.setText(((Movie)items.vect.get(i)).comment);
								icon =  new ImageIcon(((Movie)items.vect.get(i)).filepath);
								img = icon.getImage();
								bookinfo.setVisible(false);
								movieinfo.setVisible(true);
							}			
						}
					}
				}
				else if(o.equals(bookname)) {
					String s= (String)bookname.getSelectedValue();
					for(int i=0;i<items.vall().size();i++){	
						if(items.vall().get(i).equals(s)){
							index=i;
							if(items.vect.get(i) instanceof Book) {
								Jbname.setText(((Book)items.vect.get(i)).name);
								Jbauthor.setText(((Book)items.vect.get(i)).author);
								Jbpublisher.setText(((Book)items.vect.get(i)).publisher);
								Jbyear.setText(Integer.toString(((Book)items.vect.get(i)).year));
								Jbstar.setText(Integer.toString(((Book)items.vect.get(i)).star));
								ta.setText(((Book)items.vect.get(i)).intro);
								tb.setText(((Book)items.vect.get(i)).comment);
								icon =  new ImageIcon(((Book)items.vect.get(i)).filepath);
								img = icon.getImage();
								movieinfo.setVisible(false);
								bookinfo.setVisible(true);
							}
						}
					}
				}
				else if(o.equals(searchname)) {		
					String s= (String)searchname.getSelectedValue();
						for(int i=0;i<items.vall().size();i++){	
							if(items.vall().get(i).equals(s)){
								index=i;
								if(items.vect.get(i) instanceof Movie) {
									Jiname.setText(((Movie)items.vect.get(i)).name);
									Jidirector.setText(((Movie)items.vect.get(i)).director);
									Jiactor.setText(((Movie)items.vect.get(i)).actor);
									Jigenre.setText(((Movie)items.vect.get(i)).genre);
									Jilevel.setText(((Movie)items.vect.get(i)).level);
									Jiyear.setText(Integer.toString(((Movie)items.vect.get(i)).year));
									Jistar.setText(Integer.toString(((Movie)items.vect.get(i)).star));
									ta.setText(((Movie)items.vect.get(i)).intro);
									tb.setText(((Movie)items.vect.get(i)).comment);
									icon =  new ImageIcon(((Movie)items.vect.get(i)).filepath);
									img = icon.getImage();
									bookinfo.setVisible(false);
									movieinfo.setVisible(true);
									}
								else if(items.vect.get(i) instanceof Book) {
									Jbname.setText(((Book)items.vect.get(i)).name);
									Jbauthor.setText(((Book)items.vect.get(i)).author);
									Jbpublisher.setText(((Book)items.vect.get(i)).publisher);
									Jbyear.setText(Integer.toString(((Book)items.vect.get(i)).year));
									Jbstar.setText(Integer.toString(((Book)items.vect.get(i)).star));
									ta.setText(((Book)items.vect.get(i)).intro);
									tb.setText(((Book)items.vect.get(i)).comment);
									icon =  new ImageIcon(((Book)items.vect.get(i)).filepath);
									img = icon.getImage();
									movieinfo.setVisible(false);
									bookinfo.setVisible(true);
									}
								}
							}	
						}		
					}
				}
			}

	//검색 패널 생성
	class sp extends JPanel{
		public sp() {	
			setLayout(new BorderLayout());
			JPanel jp = new JPanel();
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("제목");
			combo.addItem("별점");
			jp.add(combo);
			JTextField jt = new JTextField(11);
			jp.add(jt);
			
			//검색버튼 누를 시 사용자가 입력한 값을 한글자씩 문자열에 저장함
			JButton jb = new JButton("검색");
			jb.addActionListener ( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String scombo = combo.getSelectedItem().toString();
					String sjt = jt.getText();
					char[] ssjt = new char[sjt.length()];
					for(int b=0;b<sjt.length();b++)
						ssjt[b] = sjt.charAt(b);
					
					vsearchname.clear(); //매번 검색할 때마다 vsearchname 초기화
					
					//'별점'선택 시,입력된 별점보다 크거나 같은 객체를 vsearchname에 추가 
					if(scombo.equals("별점")) {
						int flag1=0;
						int sstar= Integer.parseInt(sjt);
						for(int i=0;i<items.vall().size();i++){	
							if(items.vect.get(i).star>=sstar){
								flag1=1;
								vsearchname.add(items.vect.get(i).name);
								searchname.setListData(vsearchname);
							}
						}
						if(flag1==0)
							JOptionPane.showMessageDialog(null,"["+sstar+"] 검색 결과가 없습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
					}
					//'제목'선택 시,사용자가 입력한 문자열과 객체의 이름을 담은 문자열 하나씩 비교
					else if(scombo.equals("제목")) {
						int flag2=0;
						int iindex=0;
						for(int i=0;i<items.vall().size();i++){	
							char[] name = new char[(items.vect.get(i).name).length()];
							for(int a=0;a<(items.vect.get(i).name).length();a++) {
								name[a] = (items.vect.get(i).name).charAt(a);
							}
							for(int t=0;t<=name.length-ssjt.length;t++) {
									int j=0;
									while(j<ssjt.length && name[t+j]==ssjt[j]) {
										j++;
										if(j==ssjt.length) {
											flag2=1;
											iindex=i;
										}			
								}
							}
							if(flag2==1) { //선택된 객체의 이름 vsearchname에 추가 
								vsearchname.add(items.vect.get(iindex).name);
								searchname.setListData(vsearchname);
								flag2=2;
							}
						}
						
				
						if(flag2==0)
							JOptionPane.showMessageDialog(null,"["+sjt+"] 검색 결과가 없습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
					}
					//vsearchname.clear(); //매번 검색할 때마다 vsearchname 초기화
				}
			});
		
			jp.add(jb);	
			add(jp,BorderLayout.NORTH);
			add(searchname,BorderLayout.CENTER);
		}
	}	
	
	//라디오버튼 Movie,Book 선택 시 
	class MyItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return;
			if(r1.isSelected()) {
				JBook.setVisible(false);
				JMovie.setVisible(true);
			}
			else if(r2.isSelected()) { 
				JMovie.setVisible(false);
				JBook.setVisible(true);	
			}
		}
	}
	
	// 현재시간 출력 타이머 스레드 생성
	class TimerRunnable implements Runnable{
		private JLabel timerLabel;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
		
		public TimerRunnable(JLabel timerLabel) {
			this.timerLabel=timerLabel;
		}
		public void run() {
			while(true) {
				Date time = new Date();
				timerLabel.setText(format1.format(time));
			try {
					Thread.sleep(1000); //1초동안 잠자기
				}
			catch(InterruptedException e) {
				return;
				}
			}
		}
	}
	//메뉴 생성 함수
	private void createMenu() {
		JMenuBar mb= new JMenuBar();
		JMenu mfile = new JMenu("파일");
		JMenuItem [] filemenu = new JMenuItem[4];
		String[] fileitem = {"불러오기","저장하기"," ","종료"};
		MenuActionListener ml = new MenuActionListener();
		//파일메뉴에 항목 등록
		for(int i=0;i<filemenu.length;i++) {
			if (fileitem[i].equals(" ")) {
				mfile.addSeparator(); //저장과 종료메뉴 사이에 분리선 삽입
				continue;
			}
			filemenu[i] = new JMenuItem(fileitem[i]);
			filemenu[i].addActionListener(ml);
			mfile.add(filemenu[i]);
		}
		mb.add(mfile);
		//도움말메뉴에 항목 등록
		JMenu mhelper = new JMenu("도움말");
		JMenuItem helpermenu = new JMenuItem("시스템 정보");
		helpermenu.addActionListener(ml); //"버전 정보"에 ActionListener 등록
		mhelper.add(helpermenu); //버전정보 삽입
		mb.add(mhelper); //메뉴바에 help항목추가 
		setJMenuBar(mb);
	}
	//메뉴 클릭시 불리는 ActionListener 생성 
	class MenuActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			String cmd = e.getActionCommand(); //사용자가 선택한 메뉴아이템 문자열 리턴
		
			switch(cmd) {	
			case "불러오기":
				JFileChooser getter = new JFileChooser();
				int get = getter.showOpenDialog(null); // 파일 다이알로그 출력
				if(get == JFileChooser.APPROVE_OPTION) {
					String getfilepath = getter.getSelectedFile().getPath();
					ObjectInputStream ois = null;
					try {
						ois = new ObjectInputStream(new FileInputStream(getfilepath));
						} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						} catch (IOException e1) {
						e1.printStackTrace();
						}
					try {
						items.vect =(Vector<Item>) ois.readObject(); //아이템 객체에 불러오기 
						allname.setListData(items.vall());
						bookname.setListData(items.vbook());
						moviename.setListData(items.vmovie());
						}
					 	catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					 	} catch (IOException e1) {
						e1.printStackTrace();
						}
					try {
						ois.close();
						} 
						catch (IOException e1) {
						e1.printStackTrace();
						}
					}	
					break;
				
			case "저장하기":
				JFileChooser setter  = new JFileChooser();
				int set = setter.showSaveDialog(null); // 파일 저장 다이알로그 
				if(set == JFileChooser.APPROVE_OPTION) {
					String getfilepath2 = setter.getSelectedFile().getPath();
		        	ObjectOutputStream oos = null;
					try {
						oos = new ObjectOutputStream(new FileOutputStream(getfilepath2));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						oos.writeObject(items.vect); //아이템 객체에 쓰기 
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						oos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}		
				break;
			case "시스템 정보": 
				JOptionPane.showMessageDialog(null,"MyNotes 시스템 v 1.0 입니다.","Message",JOptionPane.INFORMATION_MESSAGE); // 메시지 다이얼로그 출력
				break;
			case "종료": 
				int result = JOptionPane.showConfirmDialog(null,"종료하시겠습니까?","종료 확인",JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) System.exit(0);
				break;
			}
		}
	}

	public static void main(String[] args) {
		new MyNotes();//객체 생성
	}
}
