package sd.rtyy.com.example.qiu.drawer_try.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2017/7/24.
 */

public class Auction extends BmobObject{

    String auction_goodsname;
    int auction_goodsId;
    String whoWants;



    String auction_isUsername;
    float auction_startPrice;
    String action_tab;
    long timeLimit;
    float current_price;
    float money_range;
    String auction_description;
    BmobFile auction_photo;
    BmobFile auction_photo2;
    BmobFile auction_photo3;
    Boolean auctioned;
    public Auction(){}
    public Auction(String auction_goodsname,int auction_goodsId,String auction_isUsername,
                   float auction_startPrice,String action_tab,long timeLimit,float current_price,
                   float money_range,String auction_description,BmobFile auction_photo,
                   BmobFile auction_photo2,BmobFile auction_photo3, Boolean auctioned,String whoWants){
        setAuction_goodsname(auction_goodsname);
        setAuction_goodsId(auction_goodsId);
        setAuction_isUsername(auction_isUsername);
        setAuction_startPrice(auction_startPrice);
        setAction_tab(action_tab);
        setTimeLimit(timeLimit);
        setCurrent_price(current_price);
        setMoney_range(money_range);
        setAuction_description(auction_description);
        setAuction_photo(auction_photo);
        setAuction_photo2(auction_photo2);
        setAuction_photo3(auction_photo3);
        setAuctioned(auctioned);
        setWhoWants(whoWants);
    }
    public Auction(String auction_goodsname,int auction_goodsId,String auction_isUsername,
                   float auction_startPrice,String action_tab,long timeLimit,float current_price,
                   float money_range,String auction_description,BmobFile auction_photo,
                   Boolean auctioned,String whoWants){
        setAuction_goodsname(auction_goodsname);
        setAuction_goodsId(auction_goodsId);
        setAuction_isUsername(auction_isUsername);
        setAuction_startPrice(auction_startPrice);
        setAction_tab(action_tab);
        setTimeLimit(timeLimit);
        setCurrent_price(current_price);
        setMoney_range(money_range);
        setAuction_description(auction_description);
        setAuction_photo(auction_photo);

        setAuctioned(auctioned);
        setWhoWants(whoWants);
    }
    public Auction(String auction_goodsname,int auction_goodsId,String auction_isUsername,
                   float auction_startPrice,String action_tab,long timeLimit,float current_price,
                   float money_range,String auction_description,BmobFile auction_photo,
                   BmobFile auction_photo2,Boolean auctioned,String whoWants){
        setAuction_goodsname(auction_goodsname);
        setAuction_goodsId(auction_goodsId);
        setAuction_isUsername(auction_isUsername);
        setAuction_startPrice(auction_startPrice);
        setAction_tab(action_tab);
        setTimeLimit(timeLimit);
        setCurrent_price(current_price);
        setMoney_range(money_range);
        setAuction_description(auction_description);
        setAuction_photo(auction_photo);
        setAuction_photo2(auction_photo2);

        setAuctioned(auctioned);
        setWhoWants(whoWants);
    }

    public int getAuction_goodsId() {
        return auction_goodsId;
    }

    public void setAuction_goodsId(int auction_goodsId) {
        this.auction_goodsId = auction_goodsId;
    }
    public String getAuction_isUsername() {
        return auction_isUsername;
    }

    public void setAuction_isUsername(String auction_isUsername) {
        this.auction_isUsername = auction_isUsername;
    }

    public Boolean getAuctioned() {
        return auctioned;
    }

    public void setAuctioned(Boolean auctioned) {
        this.auctioned = auctioned;
    }

    public String getAuction_goodsname() {
        return auction_goodsname;
    }

    public void setAuction_goodsname(String auction_goodsname) {
        this.auction_goodsname = auction_goodsname;
    }

    public float getAuction_startPrice() {
        return auction_startPrice;
    }

    public void setAuction_startPrice(float auction_startPrice) {
        this.auction_startPrice = auction_startPrice;
    }

    public String getAction_tab() {
        return action_tab;
    }

    public void setAction_tab(String action_tab) {
        this.action_tab = action_tab;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }

    public float getMoney_range() {
        return money_range;
    }

    public void setMoney_range(float money_range) {
        this.money_range = money_range;
    }

    public String getAuction_description() {
        return auction_description;
    }

    public void setAuction_description(String auction_description) {
        this.auction_description = auction_description;
    }

    public BmobFile getAuction_photo() {
        return auction_photo;
    }

    public void setAuction_photo(BmobFile auction_photo) {
        this.auction_photo = auction_photo;
    }

    public BmobFile getAuction_photo2() {
        return auction_photo2;
    }

    public void setAuction_photo2(BmobFile auction_photo2) {
        this.auction_photo2 = auction_photo2;
    }

    public BmobFile getAuction_photo3() {
        return auction_photo3;
    }

    public void setAuction_photo3(BmobFile auction_photo3) {
        this.auction_photo3 = auction_photo3;
    }
    public String getWhoWants() {
        return whoWants;
    }

    public void setWhoWants(String whoWants) {
        this.whoWants = whoWants;
    }
}
