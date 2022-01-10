public class MutualFund extends Investment {

  public MutualFund(){            //standard no arg constructor
    buyCOMMISSION = 0.0f;
    sellCOMMISSION = 45.0f;
    type = 1;
  }
  
  public MutualFund(String name,String symbol, float price,int quantity){       //standard called constructor
    buyCOMMISSION = 0.0f;
    sellCOMMISSION = 45.0f;
    type = 1;
    this.name = name;
    this.symbol = symbol;
    this.newPrice = price;
    this.addQuantity = quantity;

  updateBookValue();
  
    prevPrice = newPrice;
    this.quantity = addQuantity;

    updateExisting(newPrice);
  }

  public MutualFund(String name,String symbol, float price,int quantity, float bookValue){        //standard called constructor
    buyCOMMISSION = 0.0f;
    sellCOMMISSION = 45.0f;
    type = 1;
    this.name = name;
    this.symbol = symbol;
    this.newPrice = price;
    this.addQuantity = quantity;
    this.bookValue = this.bookValue + bookValue;

    this.quantity = this.quantity + this.addQuantity;

    prevPrice = bookValue/(float)this.quantity;

    updateExisting(newPrice);

  }

  /**
     * updating existing investment
     * @param price
     * @param quantity
     */
    public void updateExisting(float price,int quantity){       //updating existing stock
      this.newPrice = price;
      this.addQuantity = quantity;
  
      updateBookValue();
  
      prevPrice = newPrice;
      this.quantity = this.quantity + addQuantity;

      updateExisting(newPrice);
    }


    /**
     * updating existing price
     * @param price
     */
    public void updateExisting(float price){        //updating price
      currPrice = price;
   }


   public void updateExisting(float price,int quantity, float bookValue){       //updating existing stock
    this.addQuantity = quantity;
    this.quantity = this.quantity + addQuantity;

    updateExisting(price);

    this.bookValue = this.bookValue + bookValue;
  }


  /**
     * selling existing investment
     * @param price
     * @param quantity
     */
    public void sell(float price, int quantity){    //sell stock
      payment = price*quantity - sellCOMMISSION;
      sellBookValue = bookValue *((float)quantity/(float)this.quantity);
      bookValue = bookValue - sellBookValue;
      gain = gain + payment - sellBookValue;
      this.quantity = this.quantity - quantity;

      updateExisting(price);
    }


    /**
     * returning gain from perticular investment
     * @return
     */
    public float getGain(){     //calculating book gain
      return gain + (quantity*currPrice - sellCOMMISSION - bookValue);
  }


  protected void updateBookValue(){
    bookValue = bookValue + addQuantity*newPrice + buyCOMMISSION;
  }

  public float gainBookValue(float price){
     
    return quantity*price + buyCOMMISSION;

  }


    /**
     * search function
     * @param inSymbol
     * @param keyword
     * @param range
     * @return
     */
    public boolean search (String inSymbol, String keyword, String range){
      int sCount = 0;
      int kCount = 0;
      int kCount2 = 0;
      int rCount = 0;
  
      String[] symbolArr = name.split(" ");
      String[] keywordArr = keyword.split(" ");
      String[] rangeStrArr = range.split("-");
      
      if(inSymbol.isEmpty()||inSymbol.equals(" ")){         //if symbol is empty
        sCount++;
      }
      else if(inSymbol.equals(symbol)){
        sCount++;
      }
  
      if(keyword.isEmpty()||keyword.equals(" ")){       //if keyword is empty
        kCount++;
      }
      else{
        for(int i = 0; i < keywordArr.length; i++ ){
          for(int j = 0; j < symbolArr.length; j++){
            if(keywordArr[i].equals(symbolArr[j])){
              kCount2++;
            }
          }
        }
  
      if(kCount2 == keywordArr.length){
          kCount++;
        }
      }
  
      if(range.isEmpty()||range.equals(" ")){       //if range is empty
        rCount++;
      }
      else{
        if(!range.contains("-")){           //if exact price is given
          float fRange = Float.parseFloat(range);
    
          if(Float.compare(fRange,newPrice) == 0){
            rCount++;
          }
        }
        else{                               //if range is given
          if((rangeStrArr.length == 1)||(rangeStrArr[0].isEmpty())){    //if one sided range
            float fRange;
  
            if(rangeStrArr[0].isEmpty()){
              fRange = Float.parseFloat(rangeStrArr[1]);
            }
            else{
              fRange = Float.parseFloat(rangeStrArr[0]);
            }
            if(range.indexOf("-") == 0){
              if(Float.compare(newPrice,fRange) < 0){
               rCount++;
              }
            }
            else{
              if(Float.compare(newPrice,fRange) > 0){
                rCount++;
               }
            }
          }
          else{             //two sides range
            float[] fRange = new float[2];
            fRange[0] = Float.parseFloat(rangeStrArr[0]);
            fRange[1] = Float.parseFloat(rangeStrArr[1]);
    
            if((Float.compare(newPrice,fRange[0] ) > 0) && (Float.compare(newPrice,fRange[1] ) < 0)){
              rCount++;
            }
          }
        }
      }
  
  
  
      if((sCount == 1)&&(kCount == 1)&& (rCount == 1)){
        return true;
      }
      
      return false;
    }
}
  
  