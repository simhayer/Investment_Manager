import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Main GUI class
 * 
 */
public class Window extends JFrame {
    
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;

    private JPanel basePanel;
    private JPanel addPanel;
    private JPanel sellPanel;
    private JPanel updatePanel;
    private JPanel gainPanel;
    private JPanel searchPanel;

    String[] typeCombo = {"Stock", "MutualFund"};


    ArrayList<Investment> investList = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> hMap = new HashMap<String, ArrayList<Integer>>();     //Standard hashmap storing all hashmaps

    String delimiters = "[ ]+";
    float deletedGain = 0.0f;

    int count = 0;
    int index = 0;

    float investGain = 0.0f;

    /**
     * Main constructor
     */
    public Window(){
        super();
        setSize(WIDTH,HEIGHT);
        setTitle("ePortfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        basePanel = new JPanel();
        basePanel.setVisible(false);
        add(basePanel);
        basePanel();

        addPanel = new JPanel();
        addPanel.setVisible(false);
        add(addPanel);

        sellPanel = new JPanel();
        sellPanel.setVisible(false);
        add(sellPanel);

        updatePanel = new JPanel();
        updatePanel.setVisible(false);
        add(updatePanel);

        gainPanel = new JPanel();
        gainPanel.setVisible(false);
        add(gainPanel);

        searchPanel = new JPanel();
        searchPanel.setVisible(false);
        add(searchPanel);

        JMenu commands = new JMenu("Commands");

        JMenuItem add = new JMenuItem("Add");
        JMenuItem sell = new JMenuItem("Sell");
        JMenuItem update = new JMenuItem("Update");
        JMenuItem gain = new JMenuItem("Gain");
        JMenuItem search = new JMenuItem("Search");
        commands.add(add);
        add.addActionListener(new AddPanel());
        commands.add(sell);
        sell.addActionListener(new SellPanel());
        commands.add(update);
        update.addActionListener(new UpdatePanel());
        commands.add(gain);
        gain.addActionListener(new GainPanel());
        commands.add(search);
        search.addActionListener(new SearchPanel());
        JMenuBar bar = new JMenuBar();
        bar.add(commands);
    
        setJMenuBar(bar);


    }

    /**
     * Buy panel listener
     */
    private class AddPanel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            addPanel.removeAll();
            addPanel();
            basePanel.setVisible(false);
            addPanel.setVisible(true);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);
            
        }
    }

    /**
     * Sell panel listener
     */
    private class SellPanel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            sellPanel.removeAll();
            sellPanel();
            basePanel.setVisible(false);
            addPanel.setVisible(false);
            sellPanel.setVisible(true);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);
            
        }
    }

    /**
     * Update panel listener
     */
    private class UpdatePanel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            updatePanel.removeAll();
            updatePanel();
            basePanel.setVisible(false);
            addPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(true);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);

        }
    }

    /**
     * Gain panel listener
     */
    private class GainPanel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gainPanel.removeAll();
            gainPanel();
            basePanel.setVisible(false);
            addPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(true);
            searchPanel.setVisible(false);

            gainInvest();
            
        }
    }

    /**
     * Search panel listener
     */
    private class SearchPanel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            searchPanel.removeAll();
            searchPanel();
            basePanel.setVisible(false);
            addPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(true);
            
        }
    }


    /**
     * Base panel
     */
    public void basePanel(){
        
        basePanel.setLayout(new BoxLayout(basePanel,BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to ePortfolio");
        welcome.setAlignmentX(25);
        basePanel.add(welcome);

        JTextArea welcome1 = new JTextArea("Choose a command from \"Commands\" menu to buy or sell \nan investment, update prices for all investments, get gain for the \nprotfolio, search for relevant investments, or quit the program");
        welcome1.setEditable(false);
        basePanel.add(welcome1);
        
        basePanel.setVisible(true);
    }

    int typeInd = 0;
    String symbolStr = " ";
    String nameStr = " ";
    String quantityStr = "0";
    String priceStr = "0.0";
    String lPriceStr = "0.0";
    String hPriceStr = "0.0";


    JComboBox<String> typeList;
    JTextField symbolIn;
    JTextField nameIn;
    JTextField quantityIn;
    JTextField priceIn;
    JTextField lPriceIn;
    JTextField hPriceIn;
    JTextField gainIn;


    JTextArea messageArea;
    JScrollPane messageScroll;


    /**
     * Buy panel
     */
    public void addPanel(){

        JPanel top = new JPanel();
        JPanel low = new JPanel();

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        JPanel leftTop = new JPanel();

        addPanel.add(top);
        addPanel.add(low);

        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.add(rightTop);
        top.add(leftTop);

        JLabel buying = new JLabel("Buying an investment");
        rightTop.add(buying);

        JPanel firstLine = new JPanel();
        rightTop.add(firstLine);
        firstLine.setLayout(new BoxLayout(firstLine,BoxLayout.X_AXIS));
        JLabel type = new JLabel("Type");
        firstLine.add(type);
        
        typeList = new JComboBox<>(typeCombo);
        firstLine.add(typeList);
        typeList.addActionListener(new typeListener());



        JPanel secondLine = new JPanel();
        rightTop.add(secondLine);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
        JLabel symbol = new JLabel("Symbol");
        secondLine.add(symbol);
        
        symbolIn = new JTextField(30);
        secondLine.add(symbolIn);
        symbolIn.addActionListener(new symbolListener());



        JPanel thirdLine = new JPanel();
        rightTop.add(thirdLine);
        thirdLine.setLayout(new BoxLayout(thirdLine,BoxLayout.X_AXIS));
        JLabel name = new JLabel("Name");
        thirdLine.add(name);
        
        nameIn = new JTextField(30);
        thirdLine.add(nameIn);
        nameIn.addActionListener(new nameListener());


        JPanel fourthLine = new JPanel();
        rightTop.add(fourthLine);
        fourthLine.setLayout(new BoxLayout(fourthLine,BoxLayout.X_AXIS));
        JLabel quantity = new JLabel("Quantity");
        fourthLine.add(quantity);
        
        quantityIn = new JTextField(30);
        fourthLine.add(quantityIn);
        quantityIn.addActionListener(new quantityListener());



        JPanel fifthLine = new JPanel();
        rightTop.add(fifthLine);
        fifthLine.setLayout(new BoxLayout(fifthLine,BoxLayout.X_AXIS));
        JLabel price = new JLabel("Price");
        fifthLine.add(price);
        
        priceIn = new JTextField(30);
        fifthLine.add(priceIn);
        priceIn.addActionListener(new priceListener());

        nullCheck();


        ///LEFT
        JButton buy = new JButton("Buy");
        buy.addActionListener(new BuyListener());
        leftTop.add(buy);

        JButton reset = new JButton("Reset");
        reset.addActionListener(new ResetListener());
        leftTop.add(reset);


        ///Bottom
        low.setLayout(new BoxLayout(low,BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages");
        low.add(messages);

        messageArea = new JTextArea(10,30);
        messageArea.setEditable(false);;
        //messageArea.setSize(500, 500);
        messageScroll = new JScrollPane(messageArea);

        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        low.add(messageScroll);
    

    }

    /**
     * Checking null values
     */
    private void nullCheck(){
      if(nameStr == null){
        nameStr = " ";
      }
      if(quantityStr == null){
        quantityStr = "0";
      }
      if(priceStr == null){
        priceStr = "0.0";
      }
      if(lPriceStr == null){
        lPriceStr = " ";
      }
      if(hPriceStr == null){
        hPriceStr = " ";
      }

    }

    /**
     * Investment type listener
     */
    private class typeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            typeInd = typeList.getSelectedIndex();
        }

    }

    /**
     * Symbol listener
     */
    private class symbolListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            symbolStr = symbolIn.getText();
        }

    }

    /**
     * name listener
     */
    private class nameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            nameStr = nameIn.getText();
        }

    }

    /**
     * Quantity listener
     */
    private class quantityListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            quantityStr = quantityIn.getText();
        }

    }

    /**
     * Price listener
     */
    private class priceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            priceStr = priceIn.getText();
        }

    }

    /**
     * Buy listener
     */
    private class BuyListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

          typeInd = typeList.getSelectedIndex();
          symbolStr = symbolIn.getText();
          nameStr =   nameIn.getText();
          quantityStr = quantityIn.getText();
          priceStr = priceIn.getText();
          nullCheck();

          try{
            Investment.validate(nameStr, symbolStr, Float.parseFloat(priceStr), Integer.parseInt(quantityStr));
          }
          catch(NullPointerException e1){
            messageArea.setText("Error value validation..try again");
            return;
          }
          catch(NumberFormatException e2){
            messageArea.setText("Error value validation..try again");
            return;
          }
          catch(Exception e3){
            messageArea.setText("Error value validation..try again");
            return;
          }
          nullCheck();
          if(Investment.validate(nameStr, symbolStr, Float.parseFloat(priceStr), Integer.parseInt(quantityStr))){
            addInvestment();
          }
          else{
            messageArea.setText("Error value validation..try again");
          }
            
        }

    }

    /**
     * Sell listener
     */
    private class SellListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {

          symbolStr = symbolIn.getText();
          quantityStr = quantityIn.getText();
          priceStr = priceIn.getText();
          nullCheck();
          

          try{
            Investment.validate(symbolStr, Float.parseFloat(priceStr), Integer.parseInt(quantityStr));
          }
          catch(NullPointerException e1){
            messageArea.setText("Error value validation..try again");
            return;
          }
          catch(NumberFormatException e2){
            messageArea.setText("Error value validation..try again");
            return;
          }
          catch(Exception e3){
            messageArea.setText("Error value validation..try again");
            return;
          }
          nullCheck();
          if(Investment.validate(symbolStr, Float.parseFloat(priceStr), Integer.parseInt(quantityStr))){
            sellInvestment();
          }
          else{
            messageArea.setText("Error value validation..try again");
          }
        }

    }

    /**
     * Reset listener
     */
    private class ResetListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            typeList.setSelectedIndex(0);
            symbolIn.setText("");
            nameIn.setText("");
            quantityIn.setText("");
            priceIn.setText("");
            
        }

    }

    /**
     * Sell panel
     */
    public void sellPanel(){
        JPanel top = new JPanel();
        JPanel low = new JPanel();

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        JPanel leftTop = new JPanel();

        sellPanel.add(top);
        sellPanel.add(low);

        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.add(rightTop);
        top.add(leftTop);

        JLabel buying = new JLabel("Selling an investment");
        rightTop.add(buying);


        JPanel secondLine = new JPanel();
        rightTop.add(secondLine);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
        JLabel symbol = new JLabel("Symbol");
        secondLine.add(symbol);
        
        symbolIn = new JTextField(30);
        secondLine.add(symbolIn);
        symbolIn.addActionListener(new symbolListener());


        JPanel fourthLine = new JPanel();
        rightTop.add(fourthLine);
        fourthLine.setLayout(new BoxLayout(fourthLine,BoxLayout.X_AXIS));
        JLabel quantity = new JLabel("Quantity");
        fourthLine.add(quantity);
        
        quantityIn = new JTextField(30);
        fourthLine.add(quantityIn);
        quantityIn.addActionListener(new quantityListener());



        JPanel fifthLine = new JPanel();
        rightTop.add(fifthLine);
        fifthLine.setLayout(new BoxLayout(fifthLine,BoxLayout.X_AXIS));
        JLabel price = new JLabel("Price");
        fifthLine.add(price);
        
        priceIn = new JTextField(30);
        fifthLine.add(priceIn);
        priceIn.addActionListener(new priceListener());




        ///LEFT
        JButton sell = new JButton("Sell");
        sell.addActionListener(new SellListener());
        leftTop.add(sell);

        JButton reset = new JButton("Reset");
        reset.addActionListener(new ResetListener());
        leftTop.add(reset);


        ///Bottom
        low.setLayout(new BoxLayout(low,BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages");
        low.add(messages);

        messageArea = new JTextArea(10,30);
        messageArea.setEditable(false);
        messageScroll = new JScrollPane(messageArea);

        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        low.add(messageScroll);


    }

    /**
     * Buy investment method
     */
    public void addInvestment(){
        count = 0;

        for(int j = 0; j < investList.size();j++){     //if investment already exists
            if(investList.get(j).checkSymbol(symbolStr)){
                investList.get(j).updateExisting(Float.parseFloat(priceStr),Integer.parseInt(quantityStr));
                count++;
                break;
            }
          }
          if(count == 0){       //adding new investment
            if(typeInd == 0){
              investList.add(new Stock(nameStr,symbolStr,Float.parseFloat(priceStr),Integer.parseInt(quantityStr)));
            }
            else{
              investList.add(new MutualFund(nameStr,symbolStr,Float.parseFloat(priceStr),Integer.parseInt(quantityStr)));
            }
          }

          ///Hash Map

          String[] nSplit = investList.get(investList.size() - 1).getName().split(delimiters);
      
    
          for(int j = 0; j < nSplit.length; j++){
            nSplit[j] = nSplit[j].toLowerCase();

                if(hMap.containsKey(nSplit[j])){
                  hMap.get(nSplit[j]).add(investList.size() - 1);
                }
                else{
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(investList.size() - 1);
                    hMap.putIfAbsent(nSplit[j], list);
                }
            
          }

          messageArea.setText("Investment added succesfully!");
    }


    /**
     * Sell Invesment method
     */
    public void sellInvestment(){

          for(int j = 0; j < investList.size();j++){
            if(investList.get(j).checkSymbol(symbolStr)){
             

                if(Integer.parseInt(quantityStr) > investList.get(j).getQuantity()){        //if sell quantity more than available
                  messageArea.append(" \nSell quantity greater than available quantity");
                  count++;
                  break;
                }
                else if(Integer.parseInt(quantityStr) == investList.get(j).getQuantity()){      //full sell
                  String[] del = new String[hMap.size()];
                  int count1 = 0;
                  ///Hash remove
                  String[] nSplit = investList.get(j).getName().split(delimiters);
                  for(int l = 0; l < nSplit.length; l++){
                    for(String key : hMap.keySet()){
                      if(key.equalsIgnoreCase(nSplit[l])){
                        hMap.get(key).remove(Integer.valueOf(j));


                        if(hMap.get(key).size() == 0){
                          del[count1]= key;
                          count1++;
                        }
                      }
                    }
                  }

                  for(int l = 0; l < count1 ; l++){
                    hMap.remove(del[l]);
                  }

                  for(String key : hMap.keySet()){
                    for(int l = 0 ; l < hMap.get(key).size(); l++){
                      if(j < hMap.get(key).get(l)){
                        int val = hMap.get(key).get(l);
                        hMap.get(key).remove(hMap.get(key).get(l));
                        hMap.get(key).add(val - 1);
                      }
                    }
                    
                  }



                  investList.get(j).sell(Float.parseFloat(priceStr),Integer.parseInt(quantityStr));
                  deletedGain = deletedGain + investList.get(j).getGain();
                  investList.remove(j);
                  count++;
                  messageArea.append(" \nSell successful");
                  break;
                }
                else{
                  investList.get(j).sell(Float.parseFloat(priceStr),Integer.parseInt(quantityStr));        //partial sell
                  count++;
                  messageArea.append(" \nSell successful");
                  break;
                }
            }
          }
          if(count == 0){
            messageArea.setText("No investment exist of the given symbol");      //if stock doesnt exist
          }
    }


    /**
     * Update Panel GUI
     */
    public void updatePanel(){
        index = 0 ; 
        JPanel top = new JPanel();
        JPanel low = new JPanel();

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        JPanel leftTop = new JPanel();

        updatePanel.add(top);
        updatePanel.add(low);

        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.add(rightTop);
        top.add(leftTop);

        JLabel buying = new JLabel("Updating investments");
        rightTop.add(buying);


        JPanel secondLine = new JPanel();
        rightTop.add(secondLine);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
        JLabel symbol = new JLabel("Symbol");
        secondLine.add(symbol);
        
        symbolIn = new JTextField(30);
        symbolIn.setEditable(false);
        secondLine.add(symbolIn);
        symbolIn.addActionListener(new symbolListener());


        JPanel fourthLine = new JPanel();
        rightTop.add(fourthLine);
        fourthLine.setLayout(new BoxLayout(fourthLine,BoxLayout.X_AXIS));
        JLabel name = new JLabel("Name");
        fourthLine.add(name);
        
        nameIn = new JTextField(30);
        nameIn.setEditable(false);
        fourthLine.add(nameIn);
        nameIn.addActionListener(new quantityListener());


        JPanel fifthLine = new JPanel();
        rightTop.add(fifthLine);
        fifthLine.setLayout(new BoxLayout(fifthLine,BoxLayout.X_AXIS));
        JLabel price = new JLabel("Price");
        fifthLine.add(price);
        
        priceIn = new JTextField(30);
        fifthLine.add(priceIn);
        priceIn.addActionListener(new priceListener());

        if(investList.size() > 0){
            symbolIn.setText(investList.get(0).getSymbol());
            nameIn.setText(investList.get(0).getName());
        }

        leftTop.setLayout(new BoxLayout(leftTop, BoxLayout.Y_AXIS));

        ///LEFT
        JButton next = new JButton("Next");
        next.addActionListener(new NextListener());
        leftTop.add(next);

        JButton prev = new JButton("Previous");
        prev.addActionListener(new PrevListener());
        leftTop.add(prev);

        JButton save = new JButton("Save");
        save.addActionListener(new SaveListener());
        leftTop.add(save);


        ///Bottom
        low.setLayout(new BoxLayout(low,BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages");
        low.add(messages);

        messageArea = new JTextArea(10,30);
        messageArea.setEditable(false);
        messageScroll = new JScrollPane(messageArea);


        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        low.add(messageScroll);


    }


    /**
     * Next button listener
     */
    private class NextListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(index < investList.size() - 1){
                index++;
                nameIn.setText(investList.get(index).getName());
                symbolIn.setText(investList.get(index).getSymbol());
                messageArea.setText(" "); 
            }
        }

    }

    /**
     * Previous button listener
     */
    private class PrevListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if((index > 0)&&(index < investList.size())){
                index--;
                nameIn.setText(investList.get(index).getName());
                symbolIn.setText(investList.get(index).getSymbol());
                messageArea.setText(" "); 
            }
        }

    }

    /**
     * Save button listener
     */
    private class SaveListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

          priceStr = priceIn.getText();
          nullCheck();
          
            if(priceStr == null){
              messageArea.setText("Invalid value...try again");
              return;
            }

            if(priceStr.isEmpty()||priceStr.equals(" ")){
              messageArea.setText("Invalid value...try again");
              return;
            }
            updateInvestment();

        }

    }

    private void updateInvestment(){
            if(index < investList.size()){
              investList.get(index).updateExisting(Float.parseFloat(priceStr));
              messageArea.setText("Update successful"); 
            }
            else{
              messageArea.setText("Invalid value...try again");
            }
    
    }

    /**
     * Gain Panel GUI
     */
    public void gainPanel(){ 
        JPanel top = new JPanel();
        JPanel low = new JPanel();

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        JPanel leftTop = new JPanel();

        gainPanel.add(top);
        gainPanel.add(low);

        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.add(rightTop);
        top.add(leftTop);

        JLabel gainLabel = new JLabel("Getting total gain");
        rightTop.add(gainLabel);


        JPanel secondLine = new JPanel();
        rightTop.add(secondLine);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
        JLabel gain = new JLabel("Gain");
        secondLine.add(gain);
        
        gainIn = new JTextField(30);
        gainIn.setEditable(false);
        secondLine.add(gainIn);


        ///Bottom
        low.setLayout(new BoxLayout(low,BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages");
        low.add(messages);

        messageArea = new JTextArea(10,30);
        messageArea.setEditable(false);
        messageScroll = new JScrollPane(messageArea);


        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        low.add(messageScroll);


    }


    public void gainInvest(){
      for(int j = 0; j < investList.size();j++){       //gain from stocks
        messageArea.append("Previous price for " + investList.get(j).getName() + " : " + investList.get(j).getPrice() + " and Book Value : " + investList.get(j).getBookValue() + "\n");
        messageArea.append("Gain from the investment : " + investList.get(j).getGain()+ "\n");
        investGain = investGain + investList.get(j).getGain();
      }

      messageArea.append("Total Gain from current investment : " + investGain + " + from deleted investment " + deletedGain + "\n");

      gainIn.setText((investGain + deletedGain) + " ");

      investGain = 0.0f;
    }


    /**
     * Search Panel GUI
     */
    public void searchPanel(){

        JPanel top = new JPanel();
        JPanel low = new JPanel();

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        JPanel leftTop = new JPanel();

        searchPanel.add(top);
        searchPanel.add(low);

        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        top.add(rightTop);
        top.add(leftTop);

        JLabel selling = new JLabel("Searching investments");
        rightTop.add(selling);



        JPanel secondLine = new JPanel();
        rightTop.add(secondLine);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
        JLabel symbol = new JLabel("Symbol");
        secondLine.add(symbol);
        
        symbolIn = new JTextField(30);
        secondLine.add(symbolIn);
        symbolIn.addActionListener(new symbolListener());



        JPanel thirdLine = new JPanel();
        rightTop.add(thirdLine);
        thirdLine.setLayout(new BoxLayout(thirdLine,BoxLayout.X_AXIS));
        JLabel name = new JLabel("Name Keywords");
        thirdLine.add(name);
        
        nameIn = new JTextField(30);
        thirdLine.add(nameIn);
        nameIn.addActionListener(new nameListener());


        JPanel fourthLine = new JPanel();
        rightTop.add(fourthLine);
        fourthLine.setLayout(new BoxLayout(fourthLine,BoxLayout.X_AXIS));
        JLabel lPrice = new JLabel("Low price");
        fourthLine.add(lPrice);
        
        lPriceIn = new JTextField(30);
        fourthLine.add(lPriceIn);
        lPriceIn.addActionListener(new lPriceListener());



        JPanel fifthLine = new JPanel();
        rightTop.add(fifthLine);
        fifthLine.setLayout(new BoxLayout(fifthLine,BoxLayout.X_AXIS));
        JLabel hPrice = new JLabel("High price");
        fifthLine.add(hPrice);
        
        hPriceIn = new JTextField(30);
        fifthLine.add(hPriceIn);
        hPriceIn.addActionListener(new hPriceListener());


        ///LEFT
        leftTop.setLayout(new BoxLayout(leftTop,BoxLayout.Y_AXIS));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchListener());
        leftTop.add(searchButton);

        JButton reset = new JButton("Reset");
        reset.addActionListener(new ResetListener());
        leftTop.add(reset);


        ///Bottom
        low.setLayout(new BoxLayout(low,BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages");
        low.add(messages);

        messageArea = new JTextArea(10,30);
        messageArea.setEditable(false);
        messageScroll = new JScrollPane(messageArea);


        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        
        low.add(messageScroll);
    

    }

    /**
     * Low Price listener
     */
    private class lPriceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            lPriceStr = lPriceIn.getText();
        }

    }
    
    /**
     * High Price listener
     */
    private class hPriceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            hPriceStr = hPriceIn.getText();
        }

    }

    /**
     * Search listener
     */
    private class SearchListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {


          symbolStr = symbolIn.getText();
          nameStr =   nameIn.getText();

          lPriceStr = lPriceIn.getText();
          hPriceStr = hPriceIn.getText();
          nullCheck();

          String rangeIn;

          if((lPriceStr.isEmpty()||lPriceStr.equals(" "))&&!(hPriceStr.isEmpty()||hPriceStr.equals(" "))){
            rangeIn = "-" + hPriceStr;
          }
          else if(!(lPriceStr.isEmpty()||lPriceStr.equals(" "))&&(hPriceStr.isEmpty()||hPriceStr.equals(" "))){
            rangeIn = lPriceStr + "-";
          }
          else if(!(lPriceStr.isEmpty()||lPriceStr.equals(" "))&&!(hPriceStr.isEmpty()||hPriceStr.equals(" "))){
            rangeIn = lPriceStr + "-" + hPriceStr;
          }
          else{
            rangeIn = " ";
          }

          messageArea.setText("Investment with matching conditions : + \n");
          int count = 0;


          if(nameStr.isEmpty()||nameStr.equals(" ")){         //if keyword is empty
            for(int j = 0; j < investList.size(); j++){
              if(investList.get(j).search(symbolStr,nameStr,rangeIn)){
                messageArea.append(investList.get(j).toString());
                count++;
              }
            }
            if(count == 0){
              messageArea.setText("No search found");
              return;
            }
          }
          else{
            String[] keywords = nameStr.split(delimiters);
          for(int k = 0 ; k < keywords.length; k ++){
            keywords[k] = keywords[k].toLowerCase();
          }
  
          //Hash map search commands
          ArrayList<Integer> val = new ArrayList<>();
  
          if(keywords.length == 1){
            if(hMap.containsKey(nameStr.toLowerCase())){
              val.addAll(hMap.get(nameStr.toLowerCase()));
            }
          }
          else{
            for(int k = 0 ; k < keywords.length ; k++){
              for(int l = 0 ; l < keywords.length ; l++){
                if((hMap.get(keywords[k]).isEmpty()||hMap.get(keywords[l]).isEmpty())||(k == l)){
                  continue;
                }
  
                for(Integer t : hMap.get(keywords[k])){
                  if(hMap.get(keywords[l]).contains(t)){
                    if(!val.contains(t)){
                      val.add(t);
                    }
                  }
                }
              }
            }
          }
  
          if(val.size() == 0){
            messageArea.setText("No search found");
            return;
          }
  
          for(Integer t : val){
            if(investList.get(t).search(symbolStr,nameStr,rangeIn)){
              messageArea.append(investList.get(t).toString());
            }
          }
          }
          

        
        
    }
  }
    

}

