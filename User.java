package pack;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;  
public class User extends JFrame {
    static final String DB_URL="jdbc:mysql://localhost/database_name";
    static final String USER="root";
    static final String PASS="PASSWORD";
    static JFrame f=new JFrame("User"); //selection of user or admin
    static JFrame f_i=new JFrame("ONline voting System"); //admin paasword and username      
    static JFrame f1 = new JFrame("Input");//aadhar and epic
    static JFrame f2 = new JFrame("Output");//go and vote
    static JFrame f_i3 = new JFrame("Result");//admin actions
    final JLabel label = new JLabel();  
    private static JRadioButton[] radioButtons;
    public static JButton submitbutton;
    String arr1[]=new String[100];
    int count=0;
public int check1(String Epic){
        try{
        f2.setSize(700, 700);
        f2.setLayout(new BorderLayout());
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        Connection con1 =DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement stmt = con1.prepareStatement("SELECT * FROM voterlist WHERE Epic_no = ?");
        stmt.setString(1, Epic);
        ResultSet rs = stmt.executeQuery();
        String vote_data1="";
        if(rs.next()){
            vote_data1=rs.getString("vote");
        }
        if((vote_data1.equals("notdone")) && (vote_data1!=("Plagarism"))){
        int count1=0;
        Statement stmt_arr =con1.createStatement();
         ResultSet rs1=stmt_arr.executeQuery("Select party_name from winlist");
         PreparedStatement stmt_i=con1.prepareStatement("SELECT COUNT(*) FROM winlist");
         ResultSet rs_i=stmt_i.executeQuery();
         if(rs_i.next()){
         count1=rs_i.getInt(1);
         }
         String arr[]=new String[count1];
         int i=0;
         while(rs1.next()){
             String s=rs1.getString("party_name");
             arr[i]=s;
             i++;
         }
         for(i=0;i<arr.length;i++){
            arr1[i]=arr[i];
         }
         count=count1;
         radioButtons = new JRadioButton[count1];
         ButtonGroup group = new ButtonGroup();
         for (i = 0; i < radioButtons.length; i++) {
             radioButtons[i] = new JRadioButton(arr1[i]);
             group.add(radioButtons[i]);
             radioPanel.add(radioButtons[i]);
             f2.add(radioButtons[i]);
             radioButtons[i].setBounds(300, 50*(i+2), 100, 30);
         }
         f2.add(radioPanel, BorderLayout.CENTER);
         submitbutton = new JButton("Submit");
         submitbutton.setBounds(500,250,100,30);
         f2.add(submitbutton,BorderLayout.SOUTH);
         f2.setVisible(true);
         submitbutton.addActionListener((ActionListener) new ActionListener() {  
         public void actionPerformed(ActionEvent e) {
            String data="";
            if (e.getSource() == submitbutton) {
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        JOptionPane.showMessageDialog(null,"Sucessfully Voted!");
                        data=arr1[i];
                        break;
                    }
                }
        String voter=data;
        int c=0;
        for(int i=0;i<count;i++){
            if(arr[i].toUpperCase().equals(voter.toUpperCase())){
                c=1;
            }
        }
         if(c==1){
            try{
            String vote_data="Done";
           String sql1 = "UPDATE  voterlist set vote = ? where Epic_no= ? ";
           String sql2 = "UPDATE  voterlist set count = ? where Epic_no= ? ";
           PreparedStatement stmt1= con1.prepareStatement(sql1);
           PreparedStatement stmt2= con1.prepareStatement(sql2);
           stmt1.setString(1,vote_data);
           stmt1.setString(2,Epic);
           stmt2.setInt(1,rs.getInt("count")+1);
           stmt2.setString(2,Epic);
           PreparedStatement stmt_for1= con1.prepareStatement("UPDATE voterlist set party = ? WHERE Epic_no = ?");
           stmt_for1.setString(1,voter);
           stmt_for1.setString(2,Epic);
           stmt1.executeUpdate();
           stmt2.executeUpdate();
           stmt_for1.executeUpdate();
           f2.setVisible(false);
           home();
         }
         catch(Exception ex){
            ex.printStackTrace();
         }
        }
        }
   }
});
        }
        }
        catch(Exception ex){
            ex.printStackTrace();
         }
        return 0;
    }
public void check () throws SQLException
    { 
    Connection conn =DriverManager.getConnection(DB_URL,USER,PASS);
    JTextField t1,t2;  
    f.setVisible(false);
    JLabel l=new JLabel("Enter your Aadhar Number:");  
    l.setBounds(650,250, 250,40);      
    f1.add(l);
    t1=new JTextField("");  
    t1.setBounds(650,300, 250,40);  
    JLabel l1=new JLabel("Enter your Epic Number:"); 
    l1.setBounds(650, 350, 250, 40);
    f1.add(l1); 
    t2=new JTextField("");  
    t2.setBounds(650,400, 250,40);  
    f1.add(t1); 
    f1.add(t2);  
    JButton b = new JButton("Submit");  
    b.setBounds(800,500, 100,40); 
    f1.add(b);
    f1.setSize(1800,1800);  
    f1.setLayout(null);  
    f1.setVisible(true);  
    b.addActionListener((ActionListener) new ActionListener() {  
        public void actionPerformed(ActionEvent e) {       
            f1.setVisible(true);
           String aadhar_num = t1.getText();  
           String Epic_num= t2.getText();   
           try{ 
            PreparedStatement stmt_for= conn.prepareStatement("SELECT * FROM voterlist WHERE Epic_no = ?");
            stmt_for.setString(1, Epic_num);
            ResultSet rs_for= stmt_for.executeQuery();
            String checkString="";
            String vote="";
            if(rs_for.next()){
             vote=rs_for.getString("vote");
             checkString=rs_for.getString("Aadhar_Number");
         }
         if((t2.getText()).isEmpty() || (t1.getText()).isEmpty() ){
            JOptionPane.showMessageDialog(f2,"Please fill the details");  
         }
         else{
         if(checkString.equals(aadhar_num)){
            if((vote.equals("Done")) || (vote.equals("Plagarism"))){
                JOptionPane.showMessageDialog(f,"Plagarism");
                String vote_data="Plagarism";
                String sql_i = "UPDATE  voterlist set vote = ? where Epic_no= ? ";
                String sql_i1 = "UPDATE  voterlist set count = ? where Epic_no= ? ";
                PreparedStatement stmt_i= conn.prepareStatement(sql_i);
                PreparedStatement stmt_i1= conn.prepareStatement(sql_i1);
                stmt_i.setString(1,vote_data);
                stmt_i.setString(2,Epic_num);
                stmt_i1.setInt(1,rs_for.getInt("count")+1);
                stmt_i1.setString(2,Epic_num);
                stmt_i.executeUpdate();
                stmt_i1.executeUpdate(); 
                home();
            }
            else{
            JOptionPane.showMessageDialog(f2,"Sucessfully Registered");
            f1.setVisible(false);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM voterlist WHERE Epic_no = ?");
            stmt.setString(1, Epic_num);
            ResultSet rs = stmt.executeQuery();
            JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13;  
            if(rs.next()){
            l1=new JLabel("\t\t\t Welcome "+rs.getString("First_name")+" "+rs.getString("Last_name"));  
            l1.setBounds(1050,80, 250,30);  
            l2=new JLabel("Epic Number: \t"+rs.getString("Epic_No"));  
            l2.setBounds(1050,130, 250,30); 
            l3=new JLabel("First Name: \t"+rs.getString("First_name"));  
            l3.setBounds(1050,180, 250,30); 
            l4=new JLabel("Last Name: \t"+rs.getString("Last_name"));  
            l4.setBounds(1050,230, 250,30); 
            l5=new JLabel("DATE OF BIRTH: \t"+rs.getString("Date_Of_Birth"));  
            l5.setBounds(1050,280, 250,30); 
            l6=new JLabel("GENDER: \t"+rs.getString("Gender"));  
            l6.setBounds(1050,330, 250,30); 
            l7=new JLabel("RELIGION: \t"+rs.getString("RELIGION_NAME"));  
            l7.setBounds(1050,380, 250,30); 
            l8=new JLabel("AADHAR NUMBER: \t"+rs.getString("Aadhar_Number"));  
            l8.setBounds(1050,430, 250,30); 
            l9=new JLabel("FATHER NAME: \t"+rs.getString("Father_Name"));  
            l9.setBounds(1050,480, 250,30);  
            l10=new JLabel("NATIVEPLACE: \t"+rs.getString("NATIVEPLACE"));  
            l10.setBounds(1050,530, 250,30); 
            l11=new JLabel("ADRESS: \t"+rs.getString("Adress"));  
            l11.setBounds(1050,580, 350,30); 
            l12=new JLabel("NATIONALITY: \t"+rs.getString("NATIONALITY"));  
            l12.setBounds(1050,630, 250,30); 
            l13=new JLabel("STATE: \t"+rs.getString("state"));  
            l13.setBounds(1050,680, 250,30); 
            f2.add(l1);
            f2.add(l2);
            f2.add(l3);
            f2.add(l4);
            f2.add(l5);
            f2.add(l6);
            f2.add(l7);
            f2.add(l8);
            f2.add(l9);
            f2.add(l10);
            f2.add(l11);
            f2.add(l12);
            f2.add(l13);
            User obj= new User();
            obj.check1(Epic_num);
            }
            setSize(1800, 1800);
            setLayout(new BorderLayout());
            f2.setLayout(null);
            f2.setVisible(true);
          }
    }
    else{
        JOptionPane.showMessageDialog(f2,"Invalid details");  
      try{  check();
      } 
    catch(Exception ex){
        ex.printStackTrace();
     }
    }
}  
}
catch(Exception ex){
    ex.printStackTrace();
}
}
});
}
public static void home1(){
    Admin obj=new Admin();
    JPanel panel = new JPanel(new GridLayout(3,3));
    JButton but1=new JButton("Plagarismlist");
    JButton but2=new JButton("Voting Percentage");
    JButton but3=new JButton("Start");
    JButton but4=new JButton("Insert");
    JButton but5=new JButton("Change");
    JButton but6=new JButton("Total no:of votes polled for a party");
    JButton but7=new JButton("Delete");
    JButton but8=new JButton("Stop");
    JButton but9=new JButton("Home");
    JButton but10=new JButton("Results");
    but1.setBounds(200,150,100, 30);
    but2.setBounds(250,200,100, 30);
    but3.setBounds(300,250,100, 30);
    but4.setBounds(350,300,100, 30);
    but5.setBounds(400,350,100, 30);
    but6.setBounds(450,400,100, 30);
    but7.setBounds(500,450,100, 30);
    but8.setBounds(550,500,100, 30);
    but8.setBounds(600,550,100, 30);
    f_i.setVisible(false);
    but1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               obj.palagarismlist();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               obj.caluculate_percentage();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               obj.start();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               Admin.insertparty();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but5.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               obj.change();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but6.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
               Admin.countpartyvotes();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but7.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
           try {
               Admin.delete();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but8.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
           try {
               obj.Stop();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but10.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
           try {
           Admin.result();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     but9.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           try {
            f_i3.setVisible(false);
            home();
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
        }
     });
     panel.add(but1);
     panel.add(but2);
     panel.add(but3);
     panel.add(but4);
     panel.add(but5);
     panel.add(but6);
     panel.add(but7);
     panel.add(but8);
     panel.add(but9);
     panel.add(but10);
     f_i3.add(panel);
     f_i3.setSize(1800, 1800);
     f_i3.setVisible(true);
   }
public void user1(){
    final JPasswordField value = new JPasswordField();   
    final JTextField text = new JTextField(); 
     JLabel l1=new JLabel("Enter Username:");    
     l1.setBounds(650,250, 100,30);     
        text.setBounds(750,250, 250,30);      
        JLabel l2=new JLabel("Enter Password:");    
        l2.setBounds(650,350, 100,40);      
        value.setBounds(750,350, 250,40);      
        JButton b = new JButton("Login");  
        b.setBounds(900,450, 80,30);    
        f_i.add(value); 
        f_i.add(l1);
        f_i.add(label);
        f_i.add(l2);
        f_i.add(b);
        f_i.add(text);  
        f_i.setSize(1800,1800);    
        f_i.setLayout(null);    
        f_i.setVisible(true);     
        f.setVisible(false);
        b.addActionListener((ActionListener) new ActionListener() {  
        public void actionPerformed(ActionEvent e) {       
           String data = text.getText();  
           String data1 = new String(value.getPassword());   
           if(data.equals("Admin") && data1.equals("12345")){
            JOptionPane.showMessageDialog(null,"Sucessfully Logged in!");  
                home1();
           }
           else{
            JOptionPane.showMessageDialog(f,"Invalid details");  
            user1();
           }
        }  
     });   
    }
public static void home() throws SQLException{
    User obj=new User();
    JPanel panel=new JPanel();
    JButton userButton = new JButton("User");
    userButton.setBounds(100,550,200,200);
    JButton adminButton = new JButton("Admin");
    adminButton.setBounds(900,600,120,40);
    JButton exit = new JButton("Exit");
    exit.setBounds(100,30,120,40);
    panel.add(adminButton);
    panel.add(userButton);
    panel.add(exit);
    panel.setBounds(100,100,500,500);
    f.add(panel);
    f.setSize(1500,1500);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
  PreparedStatement stmt1=con1.prepareStatement("select stop_fun from func_list");
  ResultSet rs_i1=stmt1.executeQuery();
  int data=0;
  if(rs_i1.next()){
      data=rs_i1.getInt("stop_fun");
  }
  if(data==1){
      JOptionPane.showMessageDialog(null,"Voting time has been done no one is allowed to vote");  
  }
  else{
    userButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
          try {
              obj.check();
          } catch (SQLException e1) {
              e1.printStackTrace();
          }
       }
    });
}
adminButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
          obj.user1();
       }
    });
exit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            f.setVisible(false);
        }
    });
}
public static void main(String[] args) throws SQLException {
    home();
}
}