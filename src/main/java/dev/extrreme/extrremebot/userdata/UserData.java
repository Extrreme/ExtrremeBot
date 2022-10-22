package dev.extrreme.extrremebot.userdata;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import dev.extrreme.extrremebot.stocks.StockPortfolio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

@DynamoDBTable(tableName = "extrremebot_userdata")
public class UserData {
    private long userId = 0L;
    private long guildId = 0L;

    private StockPortfolio portfolio = new StockPortfolio();

    public UserData() {}

    @DynamoDBHashKey
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @DynamoDBRangeKey
    public long getGuildId() {
        return guildId;
    }
    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public StockPortfolio getPortfolio() {
        return portfolio;
    }
    public void setPortfolio(StockPortfolio portfolio) {
        this.portfolio = portfolio;
    }

    public static UserData newData(long userId, long guildId) {
        UserData data = new UserData();
        data.setUserId(userId);
        data.setGuildId(guildId);

        return data;
    }

    public static UserData newData(User user, Guild guild) {
        return newData(user.getIdLong(), guild.getIdLong());
    }
}
