
package pack;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Timer;
import java.util.TimerTask;
public class Admin {
    static final String DB_URL="jdbc:mysql://localhost/database_name";
    static final String USER="root";
    static final String PASS="PASSWORD";
    private JTable voterListTable;
    private static JPanel panel;
    private static JLabel votesPolledLabel;
    private JLabel totalVotesLabel;
    private JLabel votingPercentageLabel;
    private JLabel oldNameLabel, newNameLabel;
    private JTextField oldNameField, newNameField;
    JFrame frame3=new JFrame();
    JFrame frame5=new JFrame();
    JFrame frame2=new JFrame();
    static JFrame frame_del=new JFrame();
    private JButton changeButton;
    static JFrame frame6=new JFrame();
    static int data1=0;
    static int countdownSeconds = 10;
public void palagarismlist() throws SQLException{
    JFrame frame=new JFrame();
    String[] columnNames = {"Epic Number", "First Name", "Last Name", "Date of Birth", "Gender", "Religion",
    "Aadhar Number", "Father Name", "Native Place", "Address", "Total Attempts"};
Object[][] data = {};
DefaultTableModel model = new DefaultTableModel(data, columnNames);
voterListTable = new JTable(model);
Connection con1 = DriverManager.getConnection(DB_URL, USER, PASS);
PreparedStatement stmt = con1.prepareStatement("Select * from voterlist where vote=? ");
stmt.setString(1, "Plagarism");
ResultSet rs = stmt.executeQuery();
if(rs.next()!=false){
try {
while (rs.next()) {
String epicNumber = rs.getString("Epic_No");
String firstName = rs.getString("First_name");
String lastName = rs.getString("Last_name");
String dob = rs.getString("Date_Of_Birth");
String gender = rs.getString("Gender");
String religion = rs.getString("RELIGION_NAME");
String aadharNumber = rs.getString("Aadhar_Number");
String fatherName = rs.getString("Father_Name");
String nativePlace = rs.getString("NATIVEPLACE");
String address = rs.getString("Adress");
int totalAttempts = rs.getInt("count");
Object[] row = {epicNumber, firstName, lastName, dob, gender, religion, aadharNumber,
    fatherName, nativePlace, address, totalAttempts};
model.addRow(row);
}
} catch (SQLException e) {
e.printStackTrace();
}
JButton previous = new JButton("Exit");
frame.add(previous);
previous.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            User.home();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
});
JScrollPane scrollPane = new JScrollPane(voterListTable);
frame.add(scrollPane);
frame.setTitle("Plagiarism List");
frame.setSize(800, 400);
frame.setLocationRelativeTo(null);
frame.setVisible(true);
    }
    else{
        JOptionPane.showMessageDialog(null,"No plagarism list found.");  
    }
}
public void caluculate_percentage() throws SQLException{
    JFrame Frame1 = new JFrame("Calculate Votes");
    panel = new JPanel(new GridLayout(4, 2, 5, 5));
    votesPolledLabel = new JLabel("Votes Polled:");
    totalVotesLabel = new JLabel("Total Votes:");
    votingPercentageLabel = new JLabel("Voting Percentage:");
    try {
        int votespolled=0,totalvotes=0;
    Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
    PreparedStatement stmt=con1.prepareStatement("SELECT COUNT(*) FROM voterlist WHERE vote IN (?, ?)");
    stmt.setString(1,"Done");
    stmt.setString(2,"Plagarism");
    ResultSet rs=stmt.executeQuery();
    PreparedStatement stmt1=con1.prepareStatement("SELECT COUNT(*) FROM voterlist");
    ResultSet rs1=stmt1.executeQuery();
    if (rs.next()) {
        votespolled = rs.getInt(1);
    }
    if(rs1.next()){
    totalvotes=rs1.getInt(1);
    } 
        int votingPercentage = (votespolled * 100) / totalvotes;
        votesPolledLabel.setText("The votes polled:\t"+votespolled);
        totalVotesLabel.setText("Total Votes: " + totalvotes);
        votingPercentageLabel.setText("Voting Percentage: " + votingPercentage + "%");
    } 
    catch (SQLException e) {
        e.printStackTrace();
    }
    JButton previous = new JButton("Exit");
Frame1.add(previous);
previous.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            User.home();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
});
    panel.add(votesPolledLabel);
    panel.add(totalVotesLabel);
    panel.add(votingPercentageLabel);
    Frame1.add(panel);
    Frame1.setSize(300, 150);
    Frame1.setLocationRelativeTo(null);
    Frame1.setVisible(true);
}
public void start() throws SQLException{
    Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
    PreparedStatement stmt1=con1.prepareStatement("select stop_fun from func_list where stop_fun=?");
stmt1.setInt(1, 1);
ResultSet rs=stmt1.executeQuery();
int data=0;
if(rs.next()){
data=rs.getInt("stop_fun");
}
if(data==1){
    Statement stmt = con1.createStatement();
        String sql1 = "ALTER TABLE voterlist drop column vote";
        String sql2 = "ALTER TABLE voterlist ADD vote varchar(20) DEFAULT 'notdone'";
        String sql3 = "ALTER TABLE voterlist drop column party";
        String sql4 = "ALTER TABLE voterlist ADD party varchar(20) DEFAULT 'None'";   
        String sql5 = "ALTER TABLE voterlist drop column count";
        String sql6 = "ALTER TABLE voterlist ADD count int(1) DEFAULT 0";
        String sql7="Alter table winlist drop column count";
        String sql8="Alter table winlist ADD count int(1) DEFAULT 0";
        String sql9="drop table func_list";
        String sql10="create table func_list(stop_fun int(1) DEFAULT 0)";
        stmt.executeUpdate(sql1);
        stmt.executeUpdate(sql2);
        stmt.executeUpdate(sql3);
        stmt.executeUpdate(sql4);
        stmt.executeUpdate(sql5);
        stmt.executeUpdate(sql6);
        stmt.executeUpdate(sql7);
        stmt.executeUpdate(sql8);
        stmt.executeUpdate(sql9);
        stmt.executeUpdate(sql10);
    JOptionPane.showMessageDialog(null,"Voting has been Started!");  
}
else{
        
        JOptionPane.showMessageDialog(null,"Already Voting is in progress!");  
    }
}
public static void insertparty()throws SQLException{
Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
PreparedStatement stmt1=con1.prepareStatement("select stop_fun from func_list where stop_fun=?");
stmt1.setInt(1, 1);
ResultSet rs=stmt1.executeQuery();
int data=0;
if(rs.next()){
data=rs.getInt("stop_fun");
}
if(data==1){
    JFrame frame2=new JFrame();
    JTextField t1;  
    panel = new JPanel(new GridLayout(4, 2, 5, 5));
    votesPolledLabel = new JLabel("Enter new Party Name:");
    votesPolledLabel.setBounds(50,50, 250,20);      
    frame2.add(votesPolledLabel);
    t1=new JTextField("");  
    t1.setBounds(50,100, 250,40);  
    frame2.add(t1);
    JButton pre=new JButton("Back");
    pre.setBounds(150,250, 80,30);
    frame2.add(pre);
    JButton b = new JButton("submit");  
    b.setBounds(250,250, 80,30); 
    frame2.add(b);
    frame2.setSize(400,400);  
    frame2.setLayout(null);  
    frame2.setVisible(true);  
    pre.addActionListener((ActionListener) new ActionListener() {  
        public void actionPerformed(ActionEvent e) {     
            frame2.setVisible(false);
            User.home1();  
        }
    });
        b.addActionListener((ActionListener) new ActionListener() {  
        public void actionPerformed(ActionEvent e) {       
        String name=t1.getText();
        int count1=0;
        try{
        Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt_arr =con1.createStatement();
        ResultSet rs1=stmt_arr.executeQuery("Select party_name from winlist");
        PreparedStatement stmt_i=con1.prepareStatement("SELECT COUNT(*) FROM winlist");
        ResultSet rs_i=stmt_i.executeQuery();
        if(rs_i.next()){
        count1=rs_i.getInt(1);
        }
        String arr[]=new String[count1];
        int i=0;
        int c=0;
        while(rs1.next()){
            String s=rs1.getString("party_name");
            arr[i]=s;
            i++;
        }
        for( i=0;i<count1;i++){
            if(arr[i].toUpperCase().equals(name.toUpperCase())){
                c=1;
            }
        }
        if(c==0){
        PreparedStatement stmt=con1.prepareStatement("Insert into winlist values(?,?)");
        stmt.setString(1, name);
        stmt.setInt(2,0);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null,"Party name added sucessfully");  
        frame2.setVisible(false);
        User.home1();
        }
        else{
            frame2.setVisible(false);  
            JOptionPane.showMessageDialog(null,"Party name already exists!");  
            insertparty();
        }
    }
    catch(Exception ex){
        ex.printStackTrace();
}
}
 });
}
else{
    JOptionPane.showMessageDialog(null,"Already Voting is in progress You Cant Insert a new Party!");  
}
}
public void change()throws SQLException{
    Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
PreparedStatement stmt1=con1.prepareStatement("select stop_fun from func_list where stop_fun=?");
stmt1.setInt(1, 1);
ResultSet rs=stmt1.executeQuery();
int data=0;
if(rs.next()){
data=rs.getInt("stop_fun");
}
if(data==1){
    Connection conn=DriverManager.getConnection(DB_URL,USER,PASS);
    oldNameLabel = new JLabel("Enter Old name:");
    oldNameLabel.setBounds(250, 150, 100, 30);
    frame3.add(oldNameLabel);
    newNameLabel = new JLabel("Enter New name:");
    newNameLabel.setBounds(250, 550, 100, 30);
    frame3.add(newNameLabel);
    oldNameField = new JTextField(20);
    oldNameField.setBounds(350,150, 100,30);
    frame3.add(oldNameField);
    newNameField = new JTextField(20);
    newNameField.setBounds(350, 250, 100,30);
    changeButton = new JButton("changename");
    JButton previous = new JButton("Back");
    frame3.add(previous);
previous.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            User.home();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
});
    changeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                int count1=0;
                Statement stmt_arr =conn.createStatement();
        ResultSet rs1=stmt_arr.executeQuery("Select party_name from winlist");
        PreparedStatement stmt_i=conn.prepareStatement("SELECT COUNT(*) FROM winlist");
        ResultSet rs_i=stmt_i.executeQuery();
        String oldName = oldNameField.getText();
        String newName = newNameField.getText();
        if(rs_i.next()){
        count1=rs_i.getInt(1);
        }
        String arr[]=new String[count1];
        int i=0;
        int c=0;
        int d=1;
        int e1=0;
        while(rs1.next()){
            String s=rs1.getString("party_name");
            arr[i]=s;
            i++;
        }
        for( i=0;i<count1;i++){
            if(arr[i].toUpperCase().equals(oldName.toUpperCase()) ){
                c=1;
            }
        }
        for( i=0;i<count1;i++){
            if(arr[i].toUpperCase().equals(newName.toUpperCase()) ){
                d=0;
            }
        }
        if(oldName.toUpperCase().equals(newName.toUpperCase())){
            e1=1;
        }
    if (oldName.isEmpty() || newName.isEmpty()) {
        JOptionPane.showMessageDialog(frame3, "Please fill the details");
        return;
    }
    if(e1!=1){
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM winlist WHERE party_name = ?");
        stmt.setString(1, newName.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        if(c==1){
        if(d==1){
        stmt = conn.prepareStatement("SELECT count FROM winlist WHERE party_name = ?");
        stmt.setString(1, oldName.toUpperCase());
        rs = stmt.executeQuery();
        int voteCount = 0;
        if (rs.next()) {
            voteCount = rs.getInt("count");
        }
        stmt = conn.prepareStatement("DELETE FROM winlist WHERE party_name = ?");
        stmt.setString(1, oldName.toUpperCase());
        stmt.executeUpdate();
        stmt = conn.prepareStatement("INSERT INTO winlist (party_name, count) VALUES (?, ?)");
        stmt.setString(1, newName.toUpperCase());
        stmt.setInt(2, voteCount);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(frame3, "Party name changed successfully!");
    }
    else{
        JOptionPane.showMessageDialog(frame3, "new party already exists!");
    }
}
else{
    JOptionPane.showMessageDialog(frame3, "Old party not exists!");
}
        }
        else{
            JOptionPane.showMessageDialog(frame3, "old party name and new party name is same! ");
        }
    }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame3, "Error changing party name: " + ex.getMessage());
        }
    }
});
    JPanel panel=new JPanel();
    panel.setLayout(new GridLayout(3, 2));
    panel.add(oldNameLabel);
    panel.add(oldNameField);
    panel.add(newNameLabel);
    panel.add(newNameField);
    panel.add(new JLabel()); 
    panel.add(changeButton);
    frame3.add(panel);
    frame3.setTitle("Party Name Change");
    frame3.setSize(400, 150);
    frame3.setVisible(true);
    frame3.setLocationRelativeTo(null); 
}
else{
    JOptionPane.showMessageDialog(null,"Already Voting is in progress You Cant change the Party name");  

}
}
public static void countpartyvotes() throws SQLException{
    HashMap<String, Integer> hashMap = new HashMap<>();
    int count1 = 0;
    Connection con1 = DriverManager.getConnection(DB_URL, USER, PASS);
    PreparedStatement stmt_i = con1.prepareStatement("SELECT COUNT(*) FROM winlist");
    ResultSet rs_i = stmt_i.executeQuery();
    if (rs_i.next()) {
        count1 = rs_i.getInt(1);
    }
    Statement stmt = con1.createStatement();
    ResultSet rs1 = stmt.executeQuery("SELECT party_name FROM winlist");
    String arr[] = new String[count1];
    int i = 0;
    int count[] = new int[count1];
    while (rs1.next()) {
        String s = rs1.getString("party_name");
        arr[i] = s;
        i++;
    }
    PreparedStatement stmt1 = con1.prepareStatement("SELECT COUNT(*) FROM voterlist WHERE party=?");
    for (int j = 0; j < arr.length; j++) {
        stmt1.setString(1, arr[j]);
        ResultSet rs = stmt1.executeQuery();
        if (rs.next()) {
            count[j] = rs.getInt(1);
        }
    }
    for (int k = 0; k < arr.length; k++) {
        hashMap.put(arr[k], count[k]);
    }
    List<Map.Entry<String, Integer>> list = new ArrayList<>(hashMap.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    });
    LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : list) {
        sortedHashMap.put(entry.getKey(), entry.getValue());
    }
    JFrame fr_1 = new JFrame();
        JPanel panel = new JPanel();
                DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Party Name", "Vote Count"}
        );
        JTable table = new JTable(tableModel);
                for (Map.Entry<String, Integer> entry : sortedHashMap.entrySet()) {
            tableModel.addRow(new Object[] {entry.getKey(), entry.getValue()});
        }
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        fr_1.getContentPane().add(panel);
        fr_1.setTitle("Party votes");
        fr_1.pack();
        fr_1.setVisible(true);
}
public void Stop() throws SQLException{
Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
PreparedStatement stmt=conn.prepareStatement("select stop_fun from func_list where stop_fun=?");
stmt.setInt(1, 1);
ResultSet rs=stmt.executeQuery();
int data1=0;
if(rs.next()){
data1=rs.getInt("stop_fun");
}
if(data1==1){
    JOptionPane.showMessageDialog(null,"Already stopped!");  
}
else{
    int data=1;
    Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
    PreparedStatement stmt1=con1.prepareStatement("Insert into func_list values (?)");
    stmt1.setInt(1,data);
    stmt1.executeUpdate();
    JOptionPane.showMessageDialog(null,"Elections has been stopeed No one can vote");  
}
}
public static void delete() throws SQLException{
    Connection con1=DriverManager.getConnection(DB_URL,USER,PASS);
PreparedStatement stmt1=con1.prepareStatement("select stop_fun from func_list where stop_fun=?");
stmt1.setInt(1, 1);
ResultSet rs=stmt1.executeQuery();
int data=0;
if(rs.next()){
data=rs.getInt("stop_fun");
}
if(data==1){
    JTextField t1;  
    panel = new JPanel(new GridLayout(4, 2, 5, 5));
    votesPolledLabel = new JLabel("Enter party name to delete: ");
    votesPolledLabel.setBounds(50,50, 250,20);      
    frame_del.add(votesPolledLabel);
    t1=new JTextField("");  
    t1.setBounds(50,100, 200,30);  
    frame_del.add(t1);
    JButton pre=new JButton("Back");
    pre.setBounds(150,250,80,30);
    frame_del.add(pre);    
    JButton b = new JButton("submit");  
    b.setBounds(250,250, 80,30); 
    frame_del.add(b);
    frame_del.setSize(400,400);  
    frame_del.setLayout(null);  
    b.addActionListener((ActionListener) new ActionListener() {  
        public void actionPerformed(ActionEvent e) {       
        String name=t1.getText();
        int count1=0;
        pre.addActionListener((ActionListener) new ActionListener() {  
            public void actionPerformed(ActionEvent e) {     
                frame_del.setVisible(false);
                User.home1();  
            }
        });
        try (Connection con1 = DriverManager.getConnection(DB_URL,USER,PASS)) {
            Statement stmt_arr =con1.createStatement();
            ResultSet rs1=stmt_arr.executeQuery("Select party_name from winlist");
            PreparedStatement stmt_i=con1.prepareStatement("SELECT COUNT(*) FROM winlist");
            ResultSet rs_i=stmt_i.executeQuery();
            if(rs_i.next()){
            count1=rs_i.getInt(1);
            }
            String arr[]=new String[count1];
            int i=0;
            int c=0;
            while(rs1.next()){
                String s=rs1.getString("party_name");
                arr[i]=s;
                i++;
            }
            for( i=0;i<count1;i++){
                if(arr[i].toUpperCase().equals(name.toUpperCase())){
                    c=1;
                }
            }
            if(c==1){
            PreparedStatement stmt=con1.prepareStatement("delete from winlist where party_name=?");
            stmt.setString(1, name);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame_del,"Party deleted sucessfully");  
            JButton previous = new JButton("previous");
            frame_del.add(previous);
            previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    User.home();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
            }
            else{
                JOptionPane.showMessageDialog(frame_del,"Party does not exists!");  
                frame_del.setVisible(false);
                delete();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
}
    });
    frame_del.setVisible(true);  
}
else{
    JOptionPane.showMessageDialog(null,"Already Voting is in progress You Cant Delete a existing Party!");  
}
}
public static void result() throws SQLException{
    JLabel label=new JLabel();
    Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
    PreparedStatement stmt_i1=conn.prepareStatement("select stop_fun from func_list where stop_fun=?");
    stmt_i1.setInt(1, 1);
    ResultSet rs=stmt_i1.executeQuery();
    HashMap<String, Integer> hashMap = new HashMap<>();
    int count1 = 0;
    Connection con1 = DriverManager.getConnection(DB_URL, USER, PASS);
    PreparedStatement stmt_i = con1.prepareStatement("SELECT COUNT(*) FROM winlist");
    ResultSet rs_i = stmt_i.executeQuery();
    if (rs_i.next()) {
        count1 = rs_i.getInt(1);
    }
    Statement stmt = con1.createStatement();
    ResultSet rs1 = stmt.executeQuery("SELECT party_name FROM winlist");
    String arr[] = new String[count1];
    int i = 0;
    int count[] = new int[count1];
    while (rs1.next()) {
        String s = rs1.getString("party_name");
        arr[i] = s;
        i++;
    }
    PreparedStatement stmt1 = con1.prepareStatement("SELECT COUNT(*) FROM voterlist WHERE party=?");
    for (int j = 0; j < arr.length; j++) {
        stmt1.setString(1, arr[j]);
        ResultSet rs_i1 = stmt1.executeQuery();
        if (rs_i1.next()) {
            count[j] = rs_i1.getInt(1);
        }
    }
    for (int k = 0; k < arr.length; k++) {
        hashMap.put(arr[k], count[k]);
    }
    List<Map.Entry<String, Integer>> list = new ArrayList<>(hashMap.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String,Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    });
    LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : list) {
        sortedHashMap.put(entry.getKey(), entry.getValue());
    }
    JLabel timerLabel = new JLabel("Timer: 0 seconds");
    frame6.getContentPane().add(timerLabel);
    if(rs.next()){
    data1=rs.getInt("stop_fun");
    }
    Timer timer = new Timer();
    JLabel countdownLabel = new JLabel("The winner will be declared in " + countdownSeconds + " seconds");
    countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
    countdownLabel.setBounds(200, 70, 500, 30);
    frame6.add(countdownLabel);
    Map.Entry<String, Integer> firstEntry = sortedHashMap.entrySet().iterator().next();
    if(data1==0){
        JOptionPane.showMessageDialog(null,"Please stop the voting and come back to see winning party");  
    }
    else{
    timer.schedule(new TimerTask() {
        public void run() {
            countdownSeconds--; 
            if (countdownSeconds == 0) {
                if(data1==1){
                    label.setText("The winner is " + firstEntry.getKey()+" and won by the majority of  " +firstEntry.getValue()+" votes");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBounds(200,100, 500,30);  
                    frame6.add(label);
                }
                frame6.remove(countdownLabel);
                frame6.setSize(400,400);
                frame6.setVisible(true);
                cancel(); 
            } else {
                countdownLabel.setText("The winner will be declared in " + countdownSeconds + " seconds");
            }
        }
    }, 0, 1000);
        frame6.setSize(400, 400);
    frame6.setVisible(true);   
}
}
}
