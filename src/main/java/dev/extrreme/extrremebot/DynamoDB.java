package dev.extrreme.extrremebot;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDB {
    private final AmazonDynamoDB dbClient;
    private final DynamoDBMapper dbMapper;

    public DynamoDB() {
        dbClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
        dbMapper = new DynamoDBMapper(dbClient);
    }

    public AmazonDynamoDB getClient() {
        return dbClient;
    }

    public DynamoDBMapper getMapper() {
        return dbMapper;
    }

}
