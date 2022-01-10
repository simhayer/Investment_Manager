/**
 * Parent class for stocks and mutual funds
 */
public abstract class  Investment {
    public float buyCOMMISSION = 0.0f;   //constant commission
    public float sellCOMMISSION = 0.0f;
    protected String symbol;
    protected String name;
    protected int quantity;
    protected int addQuantity;
    protected float prevPrice;
    protected float newPrice;
    protected float bookValue;
    protected float gain = 0.0f;
    protected float payment;
    protected float sellBookValue = 0.0f;
    protected float currPrice;
    protected int type;                 // 0 for stock, 1 for investment
  
    /**
     * updating existing investment
     * @param price
     * @param quantity
     */
    public abstract void updateExisting(float price,int quantity);      //updating existing stock
    
    /**
     * updating existing price
     * @param price
     */
    public abstract void updateExisting(float price);      //updating price

    public abstract void updateExisting(float price,int quantity, float bookValue);   //updating existing stock
    /**
     * selling existing investment
     * @param price
     * @param quantity
     */
    public abstract void sell(float price, int quantity);    //sell stock
    /**
     * returning gain from perticular investment
     * @return
     */
    public abstract float getGain();    //calculating book gain
        
  
    public boolean checkSymbol(String tSymbol){     //checking for existing symbol
  
      if(this.symbol.equals(tSymbol)){
        return true;
      }
  
      return false;
    }
    
    public String getSymbol(){
      return this.symbol;
    }

    public String getName(){
      return this.name;
    }
  
    public int getQuantity(){
      return this.quantity;
    }
  
    public float getPrice(){
      return this.prevPrice;
    }
  
    public float getBookValue(){
      return this.bookValue;
    }
  
    protected abstract void updateBookValue();
      
  
    public abstract float gainBookValue(float price);
       
      

    public int getType(){
      return type;
    }
    /**
     * search function
     * @param inSymbol
     * @param keyword
     * @param range
     * @return
     */
    public abstract boolean search (String inSymbol, String keyword, String range);

    public static boolean validate (String name,String symbol, float price,int quantity) throws NullPointerException{
      if(name.isEmpty()||name.equals(" ")){
        return false;
      }
      else if(symbol.isEmpty()||symbol.equals(" ")){
        return false;
      }
      else if(price <= 0.0){
        return false;
      }
      else if(quantity <= 0){
        return false;
      }
      return true;
    }


    public static boolean validate (String symbol, float price,int quantity) throws NullPointerException{
      if(symbol.isEmpty()||symbol.equals(" ")){
        return false;
      }
      else if(price <= 0.0){
        return false;
      }
      else if(quantity <= 0){
        return false;
      }
      return true;
    }

      @Override
      public String toString(){
        return " Symbol " + symbol + " Name " +  name + "\n";
      }

      @Override
      public boolean equals(Object other){
        Investment temp;
        if (other == null){
          return false;
        }
        if(getClass() != other.getClass()){
          return false;
        }
        else{
          temp = (Investment)other;             //privacy protection
          if(symbol.equals(temp.symbol) && name.equals(temp.name)&& quantity == temp.quantity && newPrice == temp.newPrice){
            return true;
          }
          else{
            return false;
          }
        }
          
      }
  }